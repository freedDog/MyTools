package com.codemaker.maker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件订单<br>
 * 一个解析后的文件数据.
 * FileOrder.java
 * @author JiangBangMing
 * 2019年1月9日下午4:16:57
 */
public class FileOrder {
	protected String name; // 订单名称
	protected String baseName; // 生成的类名
	protected String fileName; // 生成的文件名
	protected final Map<Integer, FieldValue> fieldMap = new HashMap<Integer, FieldValue>(); // 变量列表
	protected final List<List<Object>> datas = new ArrayList<>(); // 数据列表

	public FileOrder(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取或者创建
	 * 
	 * @param index
	 * @return
	 */
	public FieldValue getAndCreate(int index) {
		FieldValue value = fieldMap.get(index);
		if (value != null) {
			return value;
		}
		value = new FieldValue();
		fieldMap.put(index, value);
		return value;
	}

	/**
	 * 获取
	 * 
	 * @param index
	 * @return
	 */
	public FieldValue get(int index) {
		return fieldMap.get(index);
	}

	/**  **/
	public FieldValue[] getAll() {
		return fieldMap.values().toArray(new FieldValue[0]);
	}

	@Override
	public String toString() {
		return "FileOrder [name=" + name + ", fieldMap=" + fieldMap + "]";
	}

	/**
	 * 变量值
	 * 
	 */
	public class FieldValue {
		protected String name; // 名称
		protected Class<?> type; // 类型
		protected String tag; // 备注
		protected int model; // 模式

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Class<?> getType() {
			return type;
		}

		public void setType(Class<?> type) {
			this.type = type;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		public int getModel() {
			return model;
		}

		public void setModel(int model) {
			this.model = model;
		}

		@Override
		public String toString() {
			return "FieldValue [name=" + name + ", type=" + type + ", tag=" + tag + ", model=" + model + "]";
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public List<List<Object>> getDatas() {
		return datas;
	}
}

