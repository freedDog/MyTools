package com.fd.code.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fd.code.resource.Config;
import com.fd.utils.StringUtils;

public class CodeUtils {
	private final static Logger LOGGER=LoggerFactory.getLogger(CodeUtils.class);
	public static final Config contig = new Config("config");
	public static final boolean debug = contig.get("debug", false);

	public static final String ARG_FILETYPE = "-filetype"; // 文件类型
	public static final String ARG_FILENAME = "-filename"; // 输出文件名格式
	public static final String ARG_HELP = "-help"; // 帮助字符串
	public static final String ARG_INPATH = "-in"; // 输入路径
	public static final String ARG_OUTPATH = "-out"; // 输出路径
	public static final String ARG_ARGS = "-args"; // 模板参数
	public static final String ARG_TYPE = "-type"; // 类型
	public static final String ARG_LANGUAGE = "-language"; // 处理语言
	public static final String ARG_VM = "-vm"; // 模板
	public static final String ARG_FILTRATES = "-filtrates"; // 筛选器

	/** 生成文件名 **/
	public static String getFileName(String name, String format) {
		return String.format(format, name);
	}

	/** 首字母大写 **/
	public static String fristUpper(String res) {
		if (res == null) {
			return "";
		}
		String frist = res.substring(0, 1);
		frist = frist.toUpperCase();
		frist += res.substring(1);
		return frist;
	}

	/** 首字母小写 **/
	public static String fristLower(String res) {
		if (res == null) {
			return "";
		}
		String frist = res.substring(0, 1);
		frist = frist.toLowerCase();
		frist += res.substring(1);
		return frist;
	}

	/** 检测空参数 **/
	public static boolean checkEmptyArgs(String[] names, Map<String, String> args) {
		for (String key : names) {
			String value = args.get(key);
			if (StringUtils.isEmpty(value)) {
				LOGGER.error(key + "为空!!");
				return false;
			}
		}
		return true;
	}

	/** 默认参数设置 **/
	public static boolean defaultArgs(Map<String, String> args, Map<String, String> defaults) {
		for (Map.Entry<String, String> entry : defaults.entrySet()) {
			String key = entry.getKey();
			String value = args.get(key);
			if (StringUtils.isEmpty(value)) {
				args.put(key, entry.getValue());
			}
		}
		return true;
	}

	/** 检测数据是否在数组内 **/
	public static <T> boolean check(T[] array, T t) {
		for (T t0 : array) {
			if (t0.equals(t)) {
				return true;
			}
		}
		return false;
	}

}
