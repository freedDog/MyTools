package com.codemaker.maker.velocity;

import java.util.List;

/**
 * 模板数据订单	
 * VelocityOrder.java
 * @author JiangBangMing
 * 2019年1月9日下午4:34:13
 */
public class VelocityOrder {
	protected String orderName; // 订单名称
	protected String className; // 类名
	protected String fileName; // 文件名
	protected List<VelocityField> fields; // 变量列表
	protected List<VelocityDatas> datas; // 变量对应数据

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public VelocityField getField(int index) {
		if (fields == null || index < 0 || index >= fields.size()) {
			return null;
		}
		return fields.get(index);
	}

	/** 根据名字查找变量 **/
	public VelocityField getFieldByName(String name) {
		int fsize = (fields != null) ? fields.size() : 0;
		for (int i = 0; i < fsize; i++) {
			VelocityField field = fields.get(i);
			if (name.equals(field.getName())) {
				return field;
			}
		}
		return null;
	}

	/** 根据名字查找变量索引 **/
	public int getIndexByName(String name) {
		int fsize = (fields != null) ? fields.size() : 0;
		for (int i = 0; i < fsize; i++) {
			VelocityField field = fields.get(i);
			if (name.equals(field.getName())) {
				return i;
			}
		}
		return -1;
	}

	public void setFields(List<VelocityField> fields) {
		this.fields = fields;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public List<VelocityDatas> getDatas() {
		return datas;
	}

	public void setDatas(List<VelocityDatas> datas) {
		this.datas = datas;
	}

}
