package com.fd.proto.core;

/**
 * 解析结果
 * ParseResult.java
 * @author JiangBangMing
 * 2019年1月9日下午3:23:05
 */
public interface ParseResult {
	public static final String SUCC = "解析成功";

	public static final String PARSE_ERROR = "解析失败";

	public static final String SOURCE_FILE_PATH_ERROR = "文件不存在";

	public static final String IO_STREAM_OPEN_ERROR = "文件流打开错误";
}
