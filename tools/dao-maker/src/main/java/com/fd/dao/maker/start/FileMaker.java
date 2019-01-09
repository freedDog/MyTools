package com.fd.dao.maker.start;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codemaker.maker.FileOrder;
import com.codemaker.maker.IFileMaker;
import com.codemaker.parse.IParse;
import com.codemaker.utils.CodeUtils;
import com.fd.utils.FileUtils;
import com.fd.utils.ReflectUtils;
import com.fd.utils.StringUtils;
import com.fd.utils.SystemUtils;

/**
 * 文件生成器
 * 
 */
public abstract class FileMaker {
	private final static Logger LOGGER=LoggerFactory.getLogger(FileMaker.class);
	/** 开始处理 **/
	public void start(String[] args0) throws Exception {
		// 解析系统参数
		Map<String, String> inputArgs = SystemUtils.ArgUtils.systemArgs(args0);
		LOGGER.info("",inputArgs);

		// 帮助模式
		String help = inputArgs.get(CodeUtils.ARG_HELP);
		if (help != null) {
			showHelpInfo();
			return;
		}

		// 整理获取参数
		Map<String, Object> params = getParams(inputArgs);
		if (params == null) {
			LOGGER.error("参数整理错误!");
			return;
		}

		LOGGER.info("starting...");

		// 记录开始时间
		long startTimeL = System.currentTimeMillis();

		// 执行
		int count = execute(params);

		// 计算使用时间
		long endTimeL = System.currentTimeMillis();
		long useTimeL = endTimeL - startTimeL;
		float useTime = useTimeL * 0.001f;

		LOGGER.info("total files:" + count + " time:" + useTime + "s");
	}

	/** 获取整理参数 **/
	protected abstract Map<String, Object> getParams(Map<String, String> inputArgs);

	/** 运行 **/
	protected abstract int execute(Map<String, Object> params) throws Exception;

	/** 解析完文件后, 通过解析文件生成文件. **/
	protected boolean maker(IParse parse, IFileMaker fileMaker, Map<String, Object> params) {
		// 查看订单
		FileOrder[] orders = parse.orders();
		int count = (orders != null) ? orders.length : 0;
		if (count <= 0) {
			LOGGER.debug("没有订单处理!" + parse);
			return true;
		}

		// 参数
		boolean result = false;
		String outpath = (String) params.get(CodeUtils.ARG_OUTPATH);
		String filename = (String) params.get(CodeUtils.ARG_FILENAME);
		// 遍历订单
		for (int i = 0; i < count; i++) {
			FileOrder order = orders[i];
			// 名称过滤
			String asName = (String) params.get("-asname"); // 先用asname
			String baseName = (!StringUtils.isEmpty(asName)) ? asName : order.getName();

			// 订单名
			if (!baseName.matches("^[a-z0-9A-Z_]*$")) {
				LOGGER.error("订单名称异常: baseName=" + baseName);
				continue;
			}

			// 文件名
			String fileName = CodeUtils.getFileName(baseName, filename);
			fileName = CodeUtils.fristUpper(fileName);
			order.setFileName(fileName);
			order.setBaseName(baseName);

			// 编译代码
			boolean result0 = false;
			try {
				result0 = fileMaker.maker(order, params);
			} catch (Exception e) {
				LOGGER.error("文件生成错误: " + order.getName() + " ", e);
				continue;
			}

			if (!result0) {
				LOGGER.error("生成文件失败: " + order.getName());
				continue;
			}

			// 生成文件名和文件內容
			String str = fileMaker.toString();

			// 整理路径
			String filepath = outpath + "/" + fileName;
			filepath = filepath.replace("\\\\", "/");
			filepath = filepath.replace("//", "/");
			// 保存文件
			if (!FileUtils.saveFile(filepath, str, "UTF-8", false)) {
				LOGGER.error("文件保存失败: " + filepath);
				continue;
			}
			LOGGER.info("文件保存成功:" + filepath);
			result = true;
		}
		return result;
	}

	/** 输出帮助信息 **/
	protected void showHelpInfo() {
		LOGGER.info("-code=language	code language	[c#, java, json]");
		LOGGER.info("-type=filetype	input file type	[xlsx]");
		LOGGER.info("-in=inputpath	input file path	[F:/]");
		LOGGER.info("-out=outputpath	input file path	[F:/]");
		LOGGER.info("-mode=makemode	getter;setter;creater;public");
	}

	/**
	 * 获取语言编码器
	 * 
	 * @param language
	 * @return
	 */
	protected IFileMaker getFileMaker(IFileMaker[] makers, String type, String language) {
		// 过滤
		if (type == null || language == null) {
			return null;
		}

		// 遍历
		int count = (makers != null) ? makers.length : 0;
		for (int i = 0; i < count; i++) {
			IFileMaker maker = makers[i];
			String language0 = maker.language();
			String type0 = maker.type();
			if (type0.equals(type) && language0.equals(language)) {
				// 重新创建一个新的对象
				Class<?> clazz = maker.getClass();
				return (IFileMaker) ReflectUtils.createInstance(clazz);
			}
		}
		return null;
	}

}
