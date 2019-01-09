package com.fd.dao.maker.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.codemaker.maker.FileOrder;
import com.codemaker.maker.FileOrder.FieldValue;
import com.codemaker.parse.IParse;
import com.fd.dao.maker.utils.DaoUtils;

public class DaoParse implements IParse {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(DaoParse.class);
	protected List<FileOrder> orders = new ArrayList<FileOrder>();

	public boolean parse(DataSource dataSource, JdbcTemplate template, String tableName) throws Exception {

		// try {
		// // getCommentByTableName(dataSource, tableName);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		String schema = getSchemaName(template);
		if (schema == null) {
			LOGGER.error("获取不到表名");
			return false;
		}

		// 创建订单
		// FileOrder order = getTableOrder(template, tableName);
		FileOrder order = getTableTypes(dataSource, schema, tableName);
		if (order == null) {
			return false;
		}
		orders.add(order);
		return true;
	}

	@Override
	public FileOrder[] orders() {
		return orders.toArray(new FileOrder[0]);
	}

	/** 获取表结构类型 **/
	public Map<String, Class<?>> getTableTypes(JdbcTemplate template, String tableName) {
		String sql = "select * from `" + tableName + "` limit 1";
		LOGGER.debug("sql=" + sql); 

		// 返回
		PreparedStatementCallback<Map<String, Class<?>>> statementCallback = new PreparedStatementCallback<Map<String, Class<?>>>() {
			@Override
			public Map<String, Class<?>> doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				boolean result = ps.execute();
				if (!result) {
					return null;
				}

				Map<String, Class<?>> paramTypes = new LinkedHashMap<>();

				// 结构
				ResultSetMetaData data = ps.getMetaData();
				for (int i = 1; i <= data.getColumnCount(); i++) {
					// DaoUtils.showData(i, data);
					// 获得指定列的列名
					String columnName = data.getColumnName(i);
					// 对应数据类型的类
					String columnClassName = data.getColumnClassName(i);

					// 获取对应类
					Class<?> clazz = null;
					try {
						clazz = Class.forName(columnClassName);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						return null;
					}
					paramTypes.put(columnName, clazz);
				}

				return paramTypes;
			}
		};
		try {
			return template.execute(sql, statementCallback);
		} catch (Exception e) {
			System.err.println("error sql:" + sql);
			// throw e;
			e.printStackTrace();
		}
		return null;
	}

	/** 获取创表结构 **/
	public static String getCommentByTableName(DataSource dataSource, String tableName) throws Exception {
		// String sql =
		// "use information_schema; select * from columns where table_name='test';";

		Connection conn = dataSource.getConnection();
		Statement stmt = conn.createStatement();

		String retStr = null;
		ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + tableName);
		if (rs != null && rs.next()) {
			retStr = rs.getString(2);
			LOGGER.debug("retStr = " + retStr);
		}
		rs.close();
		stmt.close();
		conn.close();

		return retStr;
	}

	/** 获取表结构类型 **/
	public static FileOrder getTableOrder(JdbcTemplate template, final String tableName) {
		String sql = "select * from `" + tableName + "` limit 1";
		LOGGER.debug("sql=" + sql);

		// 返回
		PreparedStatementCallback<FileOrder> statementCallback = new PreparedStatementCallback<FileOrder>() {
			@Override
			public FileOrder doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				boolean result = ps.execute();
				if (!result) {
					return null;
				}

				FileOrder order = new FileOrder(tableName);
				// 结构
				ResultSetMetaData data = ps.getMetaData();
				for (int i = 1; i <= data.getColumnCount(); i++) {
					DaoUtils.showData(i, data);
					FieldValue value = order.getAndCreate(i - 1);

					// 获得指定列的列名
					String columnName = data.getColumnName(i);
					// 对应数据类型的类
					String columnClassName = data.getColumnClassName(i);

					// 获取对应类
					Class<?> clazz = null;
					try {
						clazz = Class.forName(columnClassName);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						return null;
					}

					value.setType(clazz);
					value.setName(columnName);
					// value.setTag(tag);

				}

				return order;
			}
		};
		try {
			return template.execute(sql, statementCallback);
		} catch (Exception e) {
			System.err.println("error sql:" + sql);
			// throw e;
			e.printStackTrace();
		}
		return null;
	}

	/** 获取创表结构, @param schema数据库名 **/
	public static FileOrder getTableTypes(DataSource dataSource, String schema, String tableName) throws Exception {
		FileOrder order = new FileOrder(tableName);
		int index = 0;
		// 创建数据库连接
		Connection conn = dataSource.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		try {
			// 跳转到表信息中
			final String sqlA = "use information_schema;";
			stmt.execute(sqlA);

			// 读取表结构
			String sqlB = "select * from columns where table_name='" + tableName + "' and `TABLE_SCHEMA`='" + schema
					+ "';";
			rs = stmt.executeQuery(sqlB);
			while (rs != null && rs.next()) {
				FieldValue fieldValue = order.getAndCreate(index++);

				String columnName = rs.getString("COLUMN_NAME");
				String columuComment = rs.getString("COLUMN_COMMENT");
				String columnType = rs.getString("COLUMN_TYPE");
				String dataType = rs.getString("DATA_TYPE");
				long clen = rs.getLong("CHARACTER_MAXIMUM_LENGTH");

				// 未知类型
				// Class<?> clazz = DaoUtils.getDataClassByName(dataType);
				Class<?> clazz = DaoUtils.getDataClass(dataType, clen, columnType);
				if (clazz == null) {
					LOGGER.error("无法获取类型: dataType=" + dataType);
					return null;
				}
				// Log.info("=====i===== " + index);
				// Log.info("columnName = " + columnName);
				// Log.info("dataType = " + dataType);
				// Log.info("columuComment = " + columuComment);
				// Log.info("clazz = " + clazz);

				fieldValue.setName(columnName);
				fieldValue.setTag(columuComment);
				fieldValue.setType(clazz);
			}

		} catch (Exception e) {
			LOGGER.error("读取错误! tableName=" + tableName, e);
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
			stmt.close();
			conn.close();
		}

		// 判断是否有结构
		if (index <= 0) {
			LOGGER.error("读取不出结构结构! tableName=" + tableName);
			return null;
		}

		return order;
	}

	protected static String getSchemaName(JdbcTemplate template) {
		// 获取当前表名
		RowMapper<String> mapper = new SingleColumnRowMapper<String>(String.class);
		List<String> schemas = template.query("select database();", mapper);
		int size = (schemas != null) ? schemas.size() : 0;
		if (size <= 0) {
			return null;
		}
		String schema = schemas.get(0);
		return schema;
	}
}
