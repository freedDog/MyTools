package com.fd.code.maker;

import java.util.HashMap;
import java.util.Map;

import com.codemaker.maker.velocity.VelocityMaker;
import com.codemaker.parse.IFileParse;
import com.codemaker.utils.CodeUtils;

public class C2Maker extends VelocityMaker {
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
		return (className != null) ? className : "string";
	}

	@Override
	public String getObjectClassStr(Class<?> clazz) {
		String className = getClassStr(objClassStrs, clazz);
		return (className != null) ? className : "string";
	}

	@Override
	public String filterKey(String key) {
		if (CodeUtils.check(filters, key)) {
			return key + "0";
		}
		return key;
	}

	@Override
	public String language() {
		return "c#";
	}

	@Override
	public String type() {
		return IFileParse.MAKETYPE_CLASS;
	}
}

