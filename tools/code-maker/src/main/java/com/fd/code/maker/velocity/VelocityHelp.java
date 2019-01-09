package com.fd.code.maker.velocity;

import com.fd.code.utils.CodeUtils;
import com.fd.utils.StringUtils;

/**
 * 模板帮助类
 * VelocityHelp.java
 * @author JiangBangMing
 * 2019年1月9日下午2:54:26
 */
public class VelocityHelp {

	public boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}

	/** 首字母大写 **/
	public String fristUpper(String res) {
		return CodeUtils.fristUpper(res);
	}

	/** 首字母小写 **/
	public String fristLower(String res) {
		return CodeUtils.fristLower(res);
	}

	/**
	 * 字符串格式化
	 */
	public String format(String format, Object... args) {
		return String.format(format, args);
	}

	/**
	 * 生成get函数名
	 */
	public String getFuncStr(String name) {
		StringBuilder strBdr = new StringBuilder();
		strBdr.append("get");
		strBdr.append(fristUpper(name));
		return strBdr.toString();
	}

	/**
	 * 生成get函数名
	 */
	public String setFuncStr(String name) {
		StringBuilder strBdr = new StringBuilder();
		strBdr.append("set");
		strBdr.append(fristUpper(name));
		return strBdr.toString();
	}
}

