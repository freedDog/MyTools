package com.codemaker.maker;

import java.util.Map;

/**
 * 代码编辑器 IFileMaker.java
 * 
 * @author JiangBangMing 2019年1月9日下午4:16:16
 */
public interface IFileMaker {
	/** 语言 **/
	String language();

	/** 类型, 生成类还是生成数据 **/
	String type();

	/** 编码 **/
	boolean maker(FileOrder order, Map<String, Object> params);

	/** 返回数据 **/
	String toString();
}
