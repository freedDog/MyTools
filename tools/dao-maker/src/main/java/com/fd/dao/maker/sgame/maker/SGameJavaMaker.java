package com.fd.dao.maker.sgame.maker;

import java.util.HashMap;
import java.util.Map;

import com.codemaker.maker.velocity.VelocityMaker;
import com.codemaker.utils.CodeUtils;

/**
 *  SGamejava代码生成器
 * SGameJavaMaker.java
 * @author JiangBangMing
 * 2019年1月9日下午6:10:26
 */
public class SGameJavaMaker extends VelocityMaker {

	/** 基础类字段 **/
	protected final static Map<String, Class<?>[]> classStrs = new HashMap<>();
	/** 封包类字段 **/
	protected final static Map<String, Class<?>[]> objClassStrs = new HashMap<>();

	/** 过滤字段 **/
	protected final static String[] filters;
	static {
		classStrs.put("int", new Class<?>[] { Integer.class, int.class });
		classStrs.put("short", new Class<?>[] { Short.class, short.class });
		classStrs.put("long", new Class<?>[] { Long.class, long.class });
		classStrs.put("float", new Class<?>[] { Float.class, float.class });
		classStrs.put("double", new Class<?>[] { Double.class, double.class });
		classStrs.put("byte", new Class<?>[] { Byte.class, byte.class });
		classStrs.put("boolean", new Class<?>[] { Boolean.class, boolean.class });
		classStrs.put("char", new Class<?>[] { Character.class, char.class });

		objClassStrs.put("Integer", new Class<?>[] { Integer.class, int.class });
		objClassStrs.put("Short", new Class<?>[] { Short.class, short.class });
		objClassStrs.put("Long", new Class<?>[] { Long.class, long.class });
		objClassStrs.put("Float", new Class<?>[] { Float.class, float.class });
		objClassStrs.put("Double", new Class<?>[] { Double.class, double.class });
		objClassStrs.put("Byte", new Class<?>[] { Byte.class, byte.class });
		objClassStrs.put("Boolean", new Class<?>[] { Boolean.class, boolean.class });
		objClassStrs.put("Character", new Class<?>[] { Character.class, char.class });

		filters = new String[] { "int", "float", "double", "long", "short", "byte", "char", "byte" };
	}

	/** 类字符串 **/
	@Override
	public String getClassStr(Class<?> clazz) {
		String className = getClassStr(classStrs, clazz);
		return (className != null) ? className : "String";
	}

	@Override
	public String getObjectClassStr(Class<?> clazz) {
		String className = getClassStr(objClassStrs, clazz);
		return (className != null) ? className : "String";
	}

	@Override
	public String filterKey(String key) {
		if (CodeUtils.check(filters, key)) {
			return key + "0";
		}
		// if (key.equals("int") || key.equals("float") || key.equals("double") || key.equals("boolean") || key.equals("String")) {
		// return key + "0";
		// }
		return key;
	}

	@Override
	public String language() {
		return "java";
	}

	@Override
	public String type() {
		return "class";
	}
}