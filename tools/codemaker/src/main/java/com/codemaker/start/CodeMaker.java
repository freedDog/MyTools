package com.codemaker.start;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codemaker.filtrater.Filtrater;
import com.codemaker.maker.IFileMaker;
import com.codemaker.parse.IFileParse;
import com.codemaker.utils.CodeUtils;
import com.fd.utils.FileUtils;
import com.fd.utils.FileUtils.IFileExecutor;
import com.fd.utils.SystemUtils;

public abstract class CodeMaker extends FileMaker {

	private static final Logger LOGGER=LoggerFactory.getLogger(CodeMaker.class);
	@Override
	protected int execute(Map<String, Object> params0) throws Exception {
		// 筛选器处理
		String filtrateStr = (String) params0.get(CodeUtils.ARG_FILTRATES); // 筛选器
		Filtrater[] filtraters = Filtrater.create(filtrateStr);
		params0.put(CodeUtils.ARG_FILTRATES, filtraters);

		// 转成静态参数表
		final Map<String, Object> params = Collections.unmodifiableMap(params0);

		String inpath = (String) params.get(CodeUtils.ARG_INPATH);
		String outpath = (String) params.get(CodeUtils.ARG_OUTPATH);
		String filetype = (String) params.get(CodeUtils.ARG_FILETYPE); // 文件格式

		// 创建线程池
		int cpuNums = Runtime.getRuntime().availableProcessors(); // 获取当前系统的CPU 数目
		final ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(cpuNums * 2); // 线程池

		// 尝试获取路径
		File file = new File(inpath);
		LOGGER.info("input file:" + file.getAbsolutePath());

		// 遍历文件
		int count = FileUtils.actionFolder(inpath, filetype, new IFileExecutor() {
			@Override
			public boolean onFile(final File file, final String name) {
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						// boolean result = maker(file, name, params);
						// String retStr = (result) ? "成功" : "失败";
						// Log.info("处理文件:" + file + "\t" + retStr);
						maker(file, name, params);
					}
				};
				executorService.execute(runnable); // 多线程运行
				// runnable.run(); // 单线程运行
				return true;
			}

			@Override
			public boolean onFolder(File file, String name) {
				// return true;
				return false;
			}
		});

		// 等待线程完成
		while (true) {
			if (executorService.getActiveCount() <= 0) {
				break;
			}
		}
		executorService.shutdown();
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

		// 打开文件
		if (params.get("-of") != null) {
			FileUtils.openFile(outpath);// 打开文件夹
		}
		return count;
	}

	@Override
	protected Map<String, Object> getParams(Map<String, String> inputArgs) {
		// 整理参数

		// 默认参数设置
		Map<String, String> defaultArgs = new HashMap<>();
		defaultArgs.put(CodeUtils.ARG_INPATH, ".");
		defaultArgs.put(CodeUtils.ARG_OUTPATH, ".");
		defaultArgs.put(CodeUtils.ARG_FILENAME, "%s");
		defaultArgs.put(CodeUtils.ARG_FILETYPE, "*");
		CodeUtils.defaultArgs(inputArgs, defaultArgs);

		String inpath = inputArgs.get(CodeUtils.ARG_INPATH); // 遍历输入路径
		inpath = inpath.replaceAll("\\\\", "/");

		String outpath = inputArgs.get(CodeUtils.ARG_OUTPATH); // 输出路径
		outpath = outpath.replaceAll("\\\\", "/");

		// 创建输出路径
		if (!FileUtils.checkAndCreateFolder(outpath)) {
			LOGGER.error("输出路径无法找到: outpath=" + outpath);
			return null;
		}

		// 子参数解析
		Map<String, String> argsMap = null;
		String argsStr = inputArgs.get(CodeUtils.ARG_ARGS); // 其他参数
		if (argsStr != null) {
			argsStr = argsStr.substring(1, argsStr.length() - 1);
			argsMap = SystemUtils.ArgUtils.getArgs(argsStr);
		}

		// 整合参数
		final Map<String, Object> params = new HashMap<>();
		Iterator<Map.Entry<String, String>> iter = inputArgs.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String value = entry.getValue();
			params.put(key, value);
		}
		params.put(CodeUtils.ARG_INPATH, inpath);
		params.put(CodeUtils.ARG_OUTPATH, outpath);
		params.put(CodeUtils.ARG_ARGS, argsMap);
		return params;
	}

	/**
	 * 根据文件类型获取解析器
	 * 
	 * @param type
	 * @return
	 */
	protected abstract IFileParse getFileParse(String type);

	/**
	 * 获取语言编码器
	 * 
	 * @param language
	 * @return
	 */
	protected abstract IFileMaker getFileMaker(String type, String language);

	/**
	 * 编码
	 * 
	 * @param file
	 *            文件对象
	 * @param name
	 *            文件名(包含后缀)
	 * @param type
	 *            文件类型(后缀)
	 * @param language
	 *            语言
	 * @return
	 */
	protected boolean maker(File file, String name, Map<String, Object> params) {
		// 获取解析器, 根据文件类型
		String filetype = (String) params.get(CodeUtils.ARG_FILETYPE);
		IFileParse fileParse = getFileParse(filetype);
		if (fileParse == null) {
			LOGGER.error("没找到对应的文件解析器: filetype=" + filetype + " filename=" + name);
			return false;
		}
		// 获取语言编码器
		String type = (String) params.get(CodeUtils.ARG_TYPE);
		String language = (String) params.get(CodeUtils.ARG_LANGUAGE);
		final IFileMaker fileMaker = getFileMaker(type, language);
		if (fileMaker == null) {
			LOGGER.error("没找到对应的文件生成器: language=" + language + " type=" + type + " filename=" + name);
			return false;
		}

		// 解析文件
		boolean result = false;
		try {
			result = fileParse.parse(file.getAbsolutePath(), params);
		} catch (Exception e) {
			LOGGER.error("解析文件错误!" + file, e);
			result = false;
		}
		// 失败处理
		if (!result) {
			LOGGER.error("解析文件失败: file=" + name);
			return false;
		}

		// 解析完毕通过订单生成
		return maker(fileParse, fileMaker, params);
	}

}
