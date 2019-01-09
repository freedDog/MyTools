package com.fd.code.maker;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fd.code.maker.FileOrder.FieldValue;
import com.fd.code.utils.CodeUtils;

public class JsonMaker implements IFileMaker {
	protected String filename = null;
	protected String jsonStr = null;

	@Override
	public String language() {
		return "json";
	}

	@Override
	public String type() {
		return "data";
	}

	@Override
	public boolean maker(FileOrder order, Map<String, Object> params) {
		// 类名
		String className = CodeUtils.fristUpper(order.getName() + "Model");
		filename = className + ".json";

		// 获取变量数据
		FieldValue[] values = order.getAll();
		int count = (values != null) ? values.length : 0;

		// 整理数据
		List<List<Object>> datas = order.getDatas();
		int dsize = (datas != null) ? datas.size() : 0;

		// 遍历所有数值
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < dsize; i++) {
			// 遍历写入参数
			JSONObject jsonObject = new JSONObject();
			List<Object> values1 = datas.get(i);
			int vlen = (values1 != null) ? values1.size() : 0;
			for (int j = 0; j < count; j++) {
				FieldValue value = values[j];
				String name0 = value.getName(); // 变量名称
				String name = filterKey(name0); // 变量名称
				// 获取数值
				Object value0 = (values1 != null && j < vlen) ? values1.get(j) : null;
				// 写入数据
				if (value0 != null) {
					jsonObject.put(name, value0);
				}
			}
			// 加入数组
			jsonArray.add(jsonObject);
		}

		// json编码
		jsonStr = jsonArray.toJSONString();
		return true;
	}

	@Override
	public String toString() {
		return jsonStr;
	}

	/** 类字符串 **/
	public static String getClassStr(Class<?> clazz) {
		if (clazz == Integer.class) {
			return "int";
		} else if (clazz == Float.class) {
			return "float";
		} else if (clazz == Double.class) {
			return "double";
		} else if (clazz == Boolean.class) {
			return "boolean";
		}
		return "String";
	}

	/**
	 * 过滤关键字
	 * 
	 * @param key
	 * @param lg
	 * @return
	 */
	public static String filterKey(String key) {
		if (key.equals("int") || key.equals("float") || key.equals("double") || key.equals("boolean")) {
			return key + "0";
		}
		return key;
	}

	/**
	 * 值转变
	 * 
	 * @param type
	 * @param obj
	 * @return
	 */
	public static String valueStr(Class<?> type, Object obj) {
		if (obj == null) {
			return "\"\"";
		}
		// 字符串加分号
		String valueStr = obj.toString();
		if (type == String.class) {
			valueStr = "\"" + valueStr + "\"";
		} else if (type == Float.class) {
			valueStr = valueStr + "f";
		}
		return valueStr;
	}

}

