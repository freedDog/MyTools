package com.fd.code.maker.velocity;

/**
 * 模板变量 VelocityField.java
 * 
 * @author JiangBangMing 2019年1月9日下午2:55:45
 */
public class VelocityField {
	protected String name;
	protected Class<?> clazz;
	protected String type;
	protected String objType;
	protected String tag;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	protected static final Class<?>[] baseTypes = new Class[] { float.class, double.class, boolean.class, byte.class,
			char.class, short.class, int.class, long.class };

	/** 是否是对象类型(非基础类型) **/
	public boolean isBaseType() {
		for (Class<?> baseType : baseTypes) {
			if (baseType.equals(clazz)) {
				return true;
			}
		}
		return false;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public String toString() {
		return "VelocityField [name=" + name + ", clazz=" + clazz + ", type=" + type + ", objType=" + objType + ", tag="
				+ tag + "]";
	}
}
