package com.fd.dao.maker.utils;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

public class DaoUtils {
	public static final String ARG_DAO_URL = "-url"; // 数据库url
	public static final String ARG_DAO_USER = "-user"; // 数据库user
	public static final String ARG_DAO_PWD = "-pwd"; // 数据库密码
	public static final String ARG_DAO_TABLE = "-table"; // 数据库表名

	@SuppressWarnings("unchecked")
	public static <T> RowMapper<T> getRowMapper(Class<T> clazz) {
		// // 基础类列表
		// Class<?>[] clazzs = new Class<?>[] { byte.class, Byte.class, short.class, Short.class, int.class, Integer.class, long.class, Long.class, float.class, Float.class, double.class,
		// Double.class,
		// String.class };
		// // 遍历检测是否属于基础类型, 是则返回SingleColumnRowMapper
		// for (int i = 0; i < clazzs.length; i++) {
		// Class<?> check = clazzs[i];
		// if (check.isAssignableFrom(clazz)) {
		// return new SingleColumnRowMapper<T>(clazz);
		// }
		// }

		if (byte.class.isAssignableFrom(clazz) || Byte.class.isAssignableFrom(clazz)) {
			return (RowMapper<T>) new SingleColumnRowMapper<Byte>(Byte.class);
		} else if (short.class.isAssignableFrom(clazz) || Short.class.isAssignableFrom(clazz)) {
			return (RowMapper<T>) new SingleColumnRowMapper<Short>(Short.class);
		} else if (int.class.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz)) {
			return (RowMapper<T>) new SingleColumnRowMapper<Integer>(Integer.class);
		} else if (long.class.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz)) {
			return (RowMapper<T>) new SingleColumnRowMapper<Long>(Long.class);
		} else if (float.class.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz)) {
			return (RowMapper<T>) new SingleColumnRowMapper<Float>(Float.class);
		} else if (double.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz)) {
			return (RowMapper<T>) new SingleColumnRowMapper<Double>(Double.class);
		} else if (String.class.isAssignableFrom(clazz)) {
			return (RowMapper<T>) new SingleColumnRowMapper<String>(String.class);
		}
		// 对象处理
		BeanPropertyRowMapper<T> mapper = BeanPropertyRowMapper.newInstance(clazz);
		return mapper;
	}

	/** 输出结构 **/
	public static void showData(int i, ResultSetMetaData data) throws SQLException {
		// 获得所有列的数目及实际列数
		int columnCount = data.getColumnCount();
		// 获得指定列的列名
		String columnName = data.getColumnName(i);
		// 获得指定列的数据类型名
		String columnTypeName = data.getColumnTypeName(i);
		// 获得指定列的列值
		int columnType = data.getColumnType(i);
		// 所在的Catalog名字
		String catalogName = data.getCatalogName(i);
		// 对应数据类型的类
		String columnClassName = data.getColumnClassName(i);
		// 在数据库中类型的最大字符个数
		int columnDisplaySize = data.getColumnDisplaySize(i);
		// 默认的列的标题
		String columnLabel = data.getColumnLabel(i);
		// 获得列的模式
		String schemaName = data.getSchemaName(i);
		// 某列类型的精确度(类型的长度)
		int precision = data.getPrecision(i);
		// 小数点后的位数
		int scale = data.getScale(i);
		// 获取某列对应的表名
		String tableName = data.getTableName(i);
		// 是否自动递增
		boolean isAutoInctement = data.isAutoIncrement(i);
		// 在数据库中是否为货币型
		boolean isCurrency = data.isCurrency(i);
		// 是否为空
		int isNullable = data.isNullable(i);
		// 是否为只读
		boolean isReadOnly = data.isReadOnly(i);
		// 能否出现在where中
		boolean isSearchable = data.isSearchable(i);
		System.out.println(columnCount);
		System.out.println("获得列" + i + "的字段名称:" + columnName);
		System.out.println("获得列" + i + "的类型,返回SqlType中的编号:" + columnType);
		System.out.println("获得列" + i + "的数据类型名:" + columnTypeName);
		System.out.println("获得列" + i + "所在的Catalog名字:" + catalogName);
		System.out.println("获得列" + i + "对应数据类型的类:" + columnClassName);
		System.out.println("获得列" + i + "在数据库中类型的最大字符个数:" + columnDisplaySize);
		System.out.println("获得列" + i + "的默认的列的标题:" + columnLabel);
		System.out.println("获得列" + i + "的模式:" + schemaName);
		System.out.println("获得列" + i + "类型的精确度(类型的长度):" + precision);
		System.out.println("获得列" + i + "小数点后的位数:" + scale);
		System.out.println("获得列" + i + "对应的表名:" + tableName);
		System.out.println("获得列" + i + "是否自动递增:" + isAutoInctement);
		System.out.println("获得列" + i + "在数据库中是否为货币型:" + isCurrency);
		System.out.println("获得列" + i + "是否为空:" + isNullable);
		System.out.println("获得列" + i + "是否为只读:" + isReadOnly);
		System.out.println("获得列" + i + "能否出现在where中:" + isSearchable);
	}

	/** 获取对应类 **/
	public static Class<?> getDataClassByName(String name) {
		if (name.equals("VARCHAR") || name.equals("CHAR") || name.equals("TEXT")) {
			return String.class;
		} else if (name.equals("BLOB")) {
			return byte[].class;
		} else if (name.equals("int") || name.equals("INTEGER") || name.equals("ID")) {
			return long.class;
		} else if (name.equals("TINYINT") || name.equals("SMALLINT") || name.equals("MEDIUMINT")) {
			return int.class;
		} else if (name.equals("BIT") || name.equals("BOOLEAN")) {
			return boolean.class;
		} else if (name.equals("BIGINT")) {
			return java.math.BigInteger.class;
		} else if (name.equals("FLOAT")) {
			return float.class;
		} else if (name.equals("DOUBLE")) {
			return double.class;
		} else if (name.equals("DECIMAL")) {
			return java.math.BigDecimal.class;
		} else if (name.equals("DATE") || name.equals("TIME") || name.equals("DATETIME") || name.equals("YEAR")) {
			return Date.class;
		}
		return null;
	}

	public static Class<?> getDataClass(String dataType, long length, String columnType) {

		boolean isUnsigned = columnType.toUpperCase().indexOf("UNSIGNED") == -1 ? false : true;
		if ("BIT".equalsIgnoreCase(dataType)) {
			if (length > 1) {
				return byte[].class;
			} else {
				return boolean.class;
			}
		}
		if ("TINYINT".equalsIgnoreCase(dataType)) {
			if (length == 1 || "tinyint(1)".equalsIgnoreCase(columnType)) {
				return boolean.class;
			} else {
				return short.class;
			}
		}
		if ("BOOL".equalsIgnoreCase(dataType) || "BOOLEAN".equalsIgnoreCase(dataType)) {
			return boolean.class;
		}
		if ("SMALLINT".equalsIgnoreCase(dataType)) {
			return short.class;
		}
		if ("MEDIUMINT".equalsIgnoreCase(dataType)) {
			return int.class;
		}
		if ("INTEGER".equalsIgnoreCase(dataType) || "INT".equalsIgnoreCase(dataType)) {
			if (isUnsigned) {
				return long.class;
			} else {
				return int.class;
			}
		}
		if ("BIGINT".equalsIgnoreCase(dataType)) {
			return long.class;
		}
		if ("FLOAT".equalsIgnoreCase(dataType)) {
			return float.class;
		}
		if ("DOUBLE".equalsIgnoreCase(dataType)) {
			return double.class;
		}
		if ("DECIMAL".equalsIgnoreCase(dataType)) {
			return java.math.BigDecimal.class;
		}
		if ("DATE".equalsIgnoreCase(dataType)) {
			return Date.class;
		}
		if ("DATETIME".equalsIgnoreCase(dataType) || "TIMESTAMP".equalsIgnoreCase(dataType)) {
			return Date.class;
		}
		if ("TIME".equalsIgnoreCase(dataType)) {
			return java.sql.Time.class;
		}
		if ("YEAR".equalsIgnoreCase(dataType)) {
			return java.sql.Date.class;
		}
		if ("CHAR".equalsIgnoreCase(dataType)) {
			return String.class;
		}
		if ("VARCHAR".equalsIgnoreCase(dataType)) {
			return String.class;
		}
		if ("TINYTEXT".equalsIgnoreCase(dataType)) {
			return String.class;
		}
		if ("TEXT".equalsIgnoreCase(dataType)) {
			return String.class;
		}
		if ("MEDIUMTEXT".equalsIgnoreCase(dataType)) {
			return String.class;
		}
		if ("LONGTEXT".equalsIgnoreCase(dataType)) {
			return String.class;
		}
		if ("BINARY".equalsIgnoreCase(dataType)) {
			return byte[].class;
		}
		if ("VARBINARY".equalsIgnoreCase(dataType)) {
			return byte[].class;
		}
		if ("TINYBLOB".equalsIgnoreCase(dataType)) {
			return byte[].class;
		}
		if ("BLOB".equalsIgnoreCase(dataType)) {
			return byte[].class;
		}
		if ("MEDIUMBLOB".equalsIgnoreCase(dataType)) {
			return byte[].class;
		}
		if ("LONGBLOB".equalsIgnoreCase(dataType)) {
			return byte[].class;
		}
		return null;
	}
}
