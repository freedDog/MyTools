package com.fd.proto.entity;

import com.fd.proto.util.JavaTypeHelper;

public class ProtoAttribute {
	private String type; // 字段类型
	private String name; // 字段名字
	private boolean isArray; // 字段是否为数组
	private String annotation; // 字段的注释

	public ProtoAttribute(String type, String name, boolean isArray) {
		this.type = type;
		this.name = name;
		this.isArray = isArray;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isArray() {
		return isArray;
	}

	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}

	/**
	 * 判断是否有注释
	 */
	public boolean isAddAnnotation() {
		return annotation.length() > 0;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	/**
	 * 返回Lua的注释
	 */
	public String changeAnnotation() {
		return annotation;
	}

	public String getComplexType() {
		return JavaTypeHelper.toComplexType(type);
	}

	public String getCType() {
		if (type.equals("String")) {
			return "string";
		}
		return type;
	}

	public String getFirstUpperCaseName() {
		String firstLetter = name.substring(0, 1).toUpperCase();
		String restLetters = name.substring(1);
		return firstLetter + restLetters;
	}

	public String getFirstUpperCaseTypeName() {
		String firstLetter = type.substring(0, 1).toUpperCase();
		String restLetters = type.substring(1);
		return firstLetter + restLetters;
	}

	public int getJavaTypeStyle() {
		return JavaTypeHelper.getJavaTypeStyle(type);
	}

	public int getLength() {
		return JavaTypeHelper.getTypeLength(type);
	}

	public String toString() {
		return "\ntype : " + type + ", name : " + name + ", isArray : " + isArray + ", annotation : " + annotation;
	}
}

