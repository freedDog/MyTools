package com.fd.code.parse;

import java.util.ArrayList;
import java.util.List;

import com.fd.code.maker.FileOrder;

/**
 * 解析读取xlsx<br>
 * 基类, 解析通用的xlsx.
 * ExcelParse.java
 * @author JiangBangMing
 * 2019年1月9日下午3:05:38
 */
public abstract class ExcelParse implements IFileParse {
	protected String filePath;
	protected List<FileOrder> orders = new ArrayList<FileOrder>();

	@Override
	public String type() {
		return "xlsx";
	}

	private boolean parseSheet(String filePath, Map<String, Object> params, FormulaEvaluator evaluator, int index, Sheet sheet) {
		String name = sheet.getSheetName();

		// 记录数据
		int r = 0, c = 0;
		String valueStr = "";
		try {
			// 遍历行
			int startRow = sheet.getFirstRowNum();
			// int endRow = sheet.getLastRowNum();
			int endRow = sheet.getPhysicalNumberOfRows();
			for (r = startRow; r <= endRow; r++) {
				Row row = sheet.getRow(r);
				if (row == null) {
					continue; // 过滤整行
				}

				// 激活行开始事件
				if (!onRowStart(index, r, row)) {
					return false;
				}

				// 遍历所有列
				int startCol = row.getFirstCellNum();
				int endCol = row.getLastCellNum();
				for (c = startCol; c < endCol; c++) {
					Cell cell = row.getCell(c);

					// 激活cell开始事件
					if (!onColStart(index, r, c, cell)) {
						continue;
					}
					valueStr = (cell != null) ? cell.toString() : null;

					// 获取参数数值
					CellValue value = evaluator.evaluate(cell);

					// 处理cell事件
					Object value0 = getArg(value);
					if (!onCell(index, r, c, value0)) {
						Log.error("处理数据失败! 表名:" + name + " 第" + (r + 1) + "[" + startRow + "-" + endRow + "]行, 第" + (c + 1) + "[" + startCol + "-" + endCol + "]列");
						return false;
					}

					// 输出
					// String valueStr = (value0 != null) ? value0.toString() : "";
					// Log.debug(filePath + " [" + index + "] " + name + " [" + r + "," + c + "]" + valueStr);

					// 激活cell结束事件
					if (!onColFinish(index, r, c, cell)) {
						return false;
					}

				}

				// 激活行结束事件
				if (!onRowFinish(index, r, row)) {
					return false;
				}
			}

		} catch (Exception e) {
			// e.printStackTrace();
			Log.error(filePath + " [" + index + "] " + name + "[" + r + "," + c + "] :" + valueStr + " error", e);
			// 错误处理
			onSheetError(index, name, sheet);
			return false;
		}
		return true;
	}

	@Override
	public boolean parse(String filePath, Map<String, Object> params) throws Exception {
		// 文件参数
		this.filePath = filePath;
		this.orders.clear();

		// 过滤空路径
		if (filePath == null || filePath.length() <= 0) {
			return true; // 跳过
		}

		// 过滤临时文件
		if (filePath.indexOf("~$") >= 0) {
			Log.info("跳过临时文件! " + filePath);
			return true; // 跳过临时文件
		}

		// 加载文件
		Workbook xwb = load(filePath);
		if (xwb == null) {
			Log.error("打开文件失败!" + filePath);
			return false;
		}
		try {
			// 获取筛选器
			Filtrater[] filtraters = (Filtrater[]) params.get(CodeUtils.ARG_FILTRATES);

			// 获取计算器
			FormulaEvaluator evaluator = xwb.getCreationHelper().createFormulaEvaluator();
			// 遍历所有表
			boolean result = true;
			int sheetCount = xwb.getNumberOfSheets();
			for (int index = 0; index < sheetCount; index++) {
				// 获取表信息
				Sheet sheet = xwb.getSheetAt(index);
				String name = sheet.getSheetName();

				// 名称过滤
				if (!name.matches("^[a-z0-9A-Z_]*$")) {
					Log.error("表名称异常, 过滤跳过: name=" + name);
					continue;
				}
				// 检测是否过滤
				if (!Filtrater.check(filtraters, name)) {
					Log.info("筛选器过滤跳过:name=" + name);
					continue;
				}

				// 激活表开始事件
				if (!onSheetStart(index, name, sheet)) {
					continue; // 开始失败
				}

				// 解析单个表
				if (!parseSheet(filePath, params, evaluator, index, sheet)) {
					continue; // 解析失败
				}

				// 激活表结束事件
				if (!onSheetFinish(index, name, sheet)) {
					continue; // 结束失败
				}
			}
			return result;
		} finally {
			// 关闭文件
			xwb.close();
		}

	}

	// 相应事件
	/** 表解析开始 **/
	public abstract boolean onSheetStart(int sheetIndex, String sheetName, Sheet sheet);

	/** 表解析结束 **/
	public abstract boolean onSheetFinish(int sheetIndex, String sheetName, Sheet sheet);

	/** 表解析错误 **/
	public abstract void onSheetError(int sheetIndex, String sheetName, Sheet sheet);

	/** 行解析开始 **/
	public abstract boolean onRowStart(int sheetIndex, int rowIndex, Row row);

	/** 行解析结束 **/
	public abstract boolean onRowFinish(int sheetIndex, int rowIndex, Row row);

	/** 行-列解析开始 **/
	public abstract boolean onColStart(int sheetIndex, int rowIndex, int colIndex, Cell cell);

	/** 行-列解析結束 **/
	public abstract boolean onColFinish(int sheetIndex, int rowIndex, int colIndex, Cell cell);

	/** 单元格解析 **/
	public abstract boolean onCell(int sheetIndex, int rowIndex, int colIndex, Object obj);

	@Override
	public FileOrder[] orders() {
		return orders.toArray(new FileOrder[0]);
	}

	/**
	 * 解析cell的数据进行转换
	 * 
	 * @param value
	 * @return
	 */
	protected static Object getArg(CellValue value) {
		if (value == null) {
			return null;
		}
		Object cellObj = null;
		int cellType = value.getCellType();
		switch (cellType) {
		case XSSFCell.CELL_TYPE_STRING:
			cellObj = value.getStringValue();
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
		case XSSFCell.CELL_TYPE_FORMULA:
			Double number = value.getNumberValue();
			// 尝试转成整型
			int intValue = number.intValue();
			if (number == intValue) {
				cellObj = intValue; // 数据确实没带小数点, 是整型.
			} else {
				cellObj = number;
			}
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			cellObj = value.getBooleanValue();
			break;
		case XSSFCell.CELL_TYPE_BLANK: // 空值
			break;
		default:
			cellObj = value.getStringValue();
		}

		return cellObj;
	}

	/**
	 * 加载文件
	 * 
	 * @param filePath
	 * @return
	 */
	protected Workbook load(String filePath) {
		try {
			Workbook xwb = new XSSFWorkbook(new FileInputStream(filePath));
			return xwb;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		return "ExcelParse [filePath=" + filePath + "]";
	}

}

