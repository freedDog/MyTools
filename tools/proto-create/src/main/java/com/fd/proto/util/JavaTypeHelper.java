package com.fd.proto.util;

import java.util.HashMap;
import java.util.Map;

/**
 *  java类型工具
 * JavaTypeHelper.java
 * @author JiangBangMing
 * 2019年1月9日下午3:19:37
 */
public class JavaTypeHelper {
	public static final int BASE = 0;
	public static final int STRING = 1;
	public static final int OBJECT = 2;

	private static final Map<String, String> types = new HashMap<>();
	private static final Map<String, Integer> typeLength = new HashMap<>();

	static {
		types.put("int", "Integer");
		types.put("short", "Short");
		types.put("long", "Long");
		types.put("boolean", "Boolean");
		types.put("char", "Character");
		types.put("byte", "Byte");
		types.put("float", "Float");
		types.put("double", "Double");

		typeLength.put("short", 2);
		typeLength.put("int", 4);
		typeLength.put("long", 8);
		typeLength.put("boolean", 1);
		typeLength.put("char", 1);
		typeLength.put("byte", 1);
		typeLength.put("float", 4);
		typeLength.put("double", 8);
	}

	public static int getTypeLength(String type) {
		return typeLength.get(type);
	}

	public static int getJavaTypeStyle(String type) {
		if (types.containsKey(type)) {
			return BASE;
		} else if ("String".equals(type)) {
			return STRING;
		} else {
			return OBJECT;
		}
	}

	public static String toComplexType(String simpleType) {
		return types.containsKey(simpleType) ? types.get(simpleType) : simpleType;
	}
}
