package com.fd.code.parse;

import java.util.Map;

/**
 * 文件解析器<br>
 * 解析文件数据
 * IFileParse.java
 * @author JiangBangMing
 * 2019年1月9日下午2:48:36
 */
public interface IFileParse extends IParse {
	/** 类型解析, 数据 **/
	public static final String MAKETYPE_DATA = "data";
	/** 类型解析, 类 **/
	public static final String MAKETYPE_CLASS = "class";

	/** 文件类型 **/
	String type();

	/** 解析文件 **/
	boolean parse(String filePath, Map<String, Object> params) throws Exception;

}
