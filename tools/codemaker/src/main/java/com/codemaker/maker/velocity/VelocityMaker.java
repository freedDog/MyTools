package com.codemaker.maker.velocity;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codemaker.maker.FileOrder;
import com.codemaker.maker.FileOrder.FieldValue;
import com.codemaker.maker.IFileMaker;
import com.codemaker.utils.CodeUtils;
import com.fd.utils.FileUtils;
import com.fd.utils.StringUtils;

public abstract class VelocityMaker implements IFileMaker {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(VelocityMaker.class);
	protected StringWriter writer = null;

	@Override
	public boolean maker(FileOrder order, Map<String, Object> params) {
		// 获取VM文件路径
		String fileVM = (String) params.get(CodeUtils.ARG_VM);
		if (StringUtils.isEmpty(fileVM)) {
			LOGGER.error("vm文件路径为空, file=" + order.getName());
			return false;
		}

		// 创建模板引擎
		VelocityEngine ve = new VelocityEngine();
		ve.init();

		// 创建模板
		Template t = ve.getTemplate(fileVM, "UTF-8");
		VelocityContext context = new VelocityContext();

		// 写入参数
		@SuppressWarnings("unchecked")
		Map<String, String> args = (Map<String, String>) params.get(CodeUtils.ARG_ARGS);
		if (args != null) {
			Iterator<Map.Entry<String, String>> iter = args.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = iter.next();
				String key = entry.getKey();
				String value = entry.getValue();
				if (key == null || value == null) {
					continue;
				}

				// 插入参数
				context.put(key, value);
			}
		}

		// 设置参数
		VelocityHelp help = new VelocityHelp();
		context.put("help", help);
		VelocityOrder vorder = new VelocityOrder();
		context.put("order", vorder);

		// 基础名称
		String orderName = order.getName();
		context.put("orderName", orderName);
		vorder.setOrderName(orderName);

		// 参数获取
		String fileName = order.getFileName();
		String baseName = order.getBaseName();

		// 类名
		String className = CodeUtils.fristUpper(FileUtils.getFileNameBody(fileName));
		context.put("className", className);
		context.put("fileName", fileName);
		context.put("baseName", baseName);
		vorder.setClassName(className);

		// 类成员变量
		List<VelocityField> fields = new ArrayList<>();

		// 遍历生成变量字段
		FieldValue[] values = order.getAll();
		int count = (values != null) ? values.length : 0;
		for (int i = 0; i < count; i++) {
			FieldValue value = values[i];
			// 变量注释
			String tag = value.getTag(); // 注释
			// 变量名
			String name0 = value.getName(); // 变量名称
			String name = filterKey(name0); // 变量名称
			// 变量类型
			if(name0.equals("CreateTime")) {
				System.out.println("");
			}
			String typeStr = getClassStr(value.getType()); // 类型
			String objTypeStr = getObjectClassStr(value.getType()); // 对象类型
		
			// 创建模板变量
			VelocityField field0 = new VelocityField();
			field0.setName(name);
			field0.setClazz(value.getType());
			field0.setType(typeStr);
			field0.setObjType(objTypeStr);
			field0.setTag(tag);
			fields.add(field0);
			// Log.debug("BaseType:" + field0.isBaseType() + " " + field0);
		}
		context.put("fields", fields);
		vorder.setFields(fields);

		// 数据
		List<VelocityDatas> vdataList = new ArrayList<>();
		List<List<Object>> dataList = order.getDatas();
		int dsize = (dataList != null) ? dataList.size() : 0;
		for (int i = 0; i < dsize; i++) {
			List<Object> datas = dataList.get(i);
			if (datas == null) {
				continue;
			}
			// 创建变量数据
			VelocityDatas vdatas = new VelocityDatas(vorder, datas);
			vdataList.add(vdatas);
		}
		Collections.sort(vdataList, new Comparator<VelocityDatas>() {
			@Override
			public int compare(VelocityDatas o1, VelocityDatas o2) {
				return Integer.compare(o1.get(0).hashCode(), o2.get(0).hashCode());
			}
		});

		context.put("datas", vdataList);
		vorder.setDatas(vdataList);

		// 输出
		writer = new StringWriter();
		t.merge(context, writer);
		return true;
	}

	@Override
	public String toString() {
		return writer.toString();
	}

	/** 类字符串(基础类名) **/
	public abstract String getClassStr(Class<?> clazz);

	/** 类字符串(封包类名) **/
	public abstract String getObjectClassStr(Class<?> clazz);

	/** 根据类名列表找出符合的名称 **/
	protected static String getClassStr(Map<String, Class<?>[]> classStrs, Class<?> clazz) {

		for (Map.Entry<String, Class<?>[]> entry : classStrs.entrySet()) {
			Class<?>[] clazzs = entry.getValue();
			int csize = (clazzs != null) ? clazzs.length : 0;
			if (csize <= 0) {
				continue;
			}
			// 遍历检测是否符合
			String className = entry.getKey();
			for (int i = 0; i < csize; i++) {
				Class<?> clazz0 = clazzs[i];
				if (clazz == clazz0) {
					return className;
				}
			}
		}
		return null;
	}

	/**
	 * 过滤关键字
	 * 
	 * @param key
	 * @param lg
	 * @return
	 */
	public abstract String filterKey(String key);

}

