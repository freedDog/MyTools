package com.fd.dao.maker.start;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.codemaker.maker.IFileMaker;
import com.codemaker.utils.CodeUtils;
import com.fd.dao.maker.mysql.DaoParse;
import com.fd.dao.maker.mysql.SimpleDataSource;
import com.fd.dao.maker.sgame.maker.SGameDaoParse;
import com.fd.dao.maker.utils.DaoUtils;
import com.fd.utils.FileUtils;
import com.fd.utils.SystemUtils;

public class DaoMaker extends FileMaker {

	private static final Logger LOGGER=LoggerFactory.getLogger(DaoMaker.class);
	@Override
	protected Map<String, Object> getParams(Map<String, String> inputArgs) {
		// 整理参数

		// 空判断
		String[] checkEmptys = new String[] { DaoUtils.ARG_DAO_URL, DaoUtils.ARG_DAO_USER, DaoUtils.ARG_DAO_PWD,
				DaoUtils.ARG_DAO_TABLE };
		if (!CodeUtils.checkEmptyArgs(checkEmptys, inputArgs)) {
			return null;
		}

		// 默认参数设置
		Map<String, String> defaultArgs = new HashMap<>();
		defaultArgs.put(CodeUtils.ARG_OUTPATH, ".");
		defaultArgs.put(CodeUtils.ARG_FILENAME, "%s");
		defaultArgs.put(CodeUtils.ARG_TYPE, "class");
		defaultArgs.put(CodeUtils.ARG_LANGUAGE, "java");
		CodeUtils.defaultArgs(inputArgs, defaultArgs);

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
		params.put(CodeUtils.ARG_OUTPATH, outpath);
		params.put(CodeUtils.ARG_ARGS, argsMap);
		return params;
	}

	@Override
	protected int execute(Map<String, Object> params) throws Exception {

		// 获取语言编码器
		String type = (String) params.get(CodeUtils.ARG_TYPE);
		String language = (String) params.get(CodeUtils.ARG_LANGUAGE);
		final IFileMaker fileMaker = getFileMaker(type, language);
		if (fileMaker == null) {
			LOGGER.error("没找到对应的文件生成器: language=" + language + " type=" + type);
			return 0;
		}

		// 连接数据库
		String url = (String) params.get(DaoUtils.ARG_DAO_URL);
		String user = (String) params.get(DaoUtils.ARG_DAO_USER);
		String pwd = (String) params.get(DaoUtils.ARG_DAO_PWD);
		String table = (String) params.get(DaoUtils.ARG_DAO_TABLE);
		// 创建数据库
		DataSource dataSource = new SimpleDataSource(url, user, pwd);
		JdbcTemplate template = new JdbcTemplate(dataSource);

		// 获取表结构
		DaoParse parse = new SGameDaoParse();
		if (!parse.parse(dataSource, template, table)) {
			LOGGER.error("解析错误!");
			return 0;
		}

		// 生成文件
		if (!this.maker(parse, fileMaker, params)) {
			return 0;
		}

		// 打开文件
		if (params.get("-of") != null) {
			String outpath = (String) params.get(CodeUtils.ARG_OUTPATH);
			FileUtils.openFile(outpath);// 打开文件夹
		}
		return 1;
	}

	// 编码器列表
	protected final static IFileMaker[] makers = new IFileMaker[] { new JavaMaker() };

	/**
	 * 获取语言编码器
	 * 
	 * @param language
	 * @return
	 */
	protected IFileMaker getFileMaker(String type, String language) {
		return getFileMaker(makers, type, language);
	}
}
