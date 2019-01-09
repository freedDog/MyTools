package com.fd.code.parse;


/**
 * xml解析<br>
 * 格式: 前4行为变量名称, 变量类型, 变量说明, 变量范围.
 * ExcelParseT.java
 * @author JiangBangMing
 * 2019年1月9日下午3:05:00
 */
public class ExcelParseT extends ExcelParse {
	public static final int rowType_name = 0; // 第一列名称
	public static final int rowType_type = 1; // 第二列类型
	public static final int rowType_tag = 2; // 第三列注释
	public static final int rowType_mode = 3; // 第四列模式
	protected FileOrder order = null;
	protected List<Object> datas = null;
	protected boolean readValues = false; // 是否读取数据

	@Override
	public boolean parse(String filePath, Map<String, Object> params) throws Exception {
		// 检测是否是储存格式
		String type = (String) params.get(CodeUtils.ARG_TYPE);
		if (type.equals(IFileParse.MAKETYPE_DATA)) {
			readValues = true; // 读取数据
		}
		return super.parse(filePath, params);
	}

	@Override
	public boolean onSheetStart(int sheetIndex, String sheetName, Sheet sheet) {
		if (sheetIndex <= 0) {
			if (sheetName.equals("list")) {
				Log.warn("跳过list表!");
				return false; // 跳过第一张list表
			}
		}
		// 创建code单
		order = new FileOrder(sheetName);
		return true;
	}

	@Override
	public void onSheetError(int sheetIndex, String sheetName, Sheet sheet) {
	}

	@Override
	public boolean onSheetFinish(int sheetIndex, String sheetName, Sheet sheet) {

		// 判断参数数量
		FieldValue[] fieldValues = order.getAll();
		int fsize = (fieldValues != null) ? fieldValues.length : 0;
		if (fsize <= 0) {
			Log.error("表没有获取到模板参数!" + sheetName);
			return false;
		}

		// 遍历整理订单
		List<List<Object>> datas = order.getDatas();
		int vsize = (datas != null) ? datas.size() : 0;
		for (int i = vsize - 1; i >= 0; i--) {
			List<Object> lines = datas.get(i);
			int lsize = (lines != null) ? lines.size() : 0;
			if (lsize <= 0) {
				datas.remove(i);
				continue;
			}
			// 第一个关键值不能为空
			Object key = lines.get(0);
			if (key == null) {
				datas.remove(i);
				continue;
			}
		}

		// 添加订单
		this.orders.add(order);
		return true;
	}

	@Override
	public boolean onRowStart(int sheetIndex, int rowIndex, Row row) {

		if (rowIndex > rowType_mode) {
			// 数据处理,新建一行数据
			datas = new ArrayList<>();
			setIndex(order.getDatas(), (rowIndex - rowType_mode - 1), datas);
		}
		return true;
	}

	@Override
	public boolean onRowFinish(int sheetIndex, int rowIndex, Row row) {
		return true;
	}

	@Override
	public boolean onColStart(int sheetIndex, int rowIndex, int colIndex, Cell cell) {
		// 跳过参数值
		if (!readValues) {
			// 非读取数据模式
			if (rowIndex > rowType_mode) {
				// Log.debug("跳过数据读取!");
				return false; // 这之后都是数据, 不读取.
			}
		}

		// 参数模式
		if (rowIndex <= rowType_mode) {
			if (cell == null) {
				Log.error("模板参数不能为空! 表名:" + order.getName() + " 第" + (rowIndex + 1) + "行, 第" + (colIndex + 1) + "列");
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean onColFinish(int sheetIndex, int rowIndex, int colIndex, Cell cell) {
		return true;
	}

	@Override
	public boolean onCell(int sheetIndex, int rowIndex, int colIndex, Object obj) {

		// 判断是参数部分还是数据部分
		if (rowIndex <= rowType_mode) {
			if (obj == null) {
				Log.error("模板参数不能为空! 表名:" + order.getName() + " 第" + (rowIndex + 1) + "行, 第" + (colIndex + 1) + "列");
				return false;
			}
			// 获取参数
			FileOrder.FieldValue value = null;
			if (rowIndex == rowType_name) {
				value = order.getAndCreate(colIndex);
			} else {
				value = order.get(colIndex);
			}
			// 检测是否存在变量
			if (value == null) {
				Log.error("模板参数没有对象!");
				return false;
			}
			// Log.info("模板数据! 表名:" + order.getName() + " 第" + (rowIndex + 1) + "行, 第" + (colIndex + 1) + "列 value=" + obj);

			// 填充数据
			switch (rowIndex) {
			case rowType_name:
				value.setName(obj.toString());// 变量名称
				break;
			case rowType_type:
				Class<?> clazz = getClass(obj.toString());// 获取类型
				value.setType(clazz);
				break;
			case rowType_tag:
				value.setTag((obj != null) ? obj.toString() : null);
				break;
			case rowType_mode:
				if (obj.getClass() != Integer.class) {
					Log.error("模板模式错误! sheetIndex=" + sheetIndex + " rowIndex=" + rowIndex + " colIndex=" + colIndex);
					return false;
				}
				value.setModel((Integer) obj);
				break;
			default:
				break;
			}
			return true;
		}

		// 写入数据
		setIndex(datas, colIndex, obj);
		return true;
	}

	/** 强制设置到对应索引 **/
	private static <T> void setIndex(List<T> list, int index, T obj) {
		int lsize = list.size();
		if (lsize <= index) {
			// 补全
			for (int i = lsize; i <= index; i++) {
				list.add(null);
			}
		}
		list.set(index, obj);
	}

	public static Class<?> getClass(String type) {
		// if (type.equals("int")) {
		// return Integer.class;
		// } else if (type.equals("float")) {
		// return Float.class;
		// } else if (type.equals("double")) {
		// return Double.class;
		// } else if (type.equals("boolean")) {
		// return Boolean.class;
		// }

		if (type.equals("short")) {
			return short.class;
		} else if (type.equals("int")) {
			return int.class;
		} else if (type.equals("float")) {
			return float.class;
		} else if (type.equals("double")) {
			return double.class;
		} else if (type.equals("boolean")) {
			return boolean.class;
		}
		return String.class;
	}

}
