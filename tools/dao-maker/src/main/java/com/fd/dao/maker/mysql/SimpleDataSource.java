package com.fd.dao.maker.mysql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class SimpleDataSource implements DataSource {
	private static final String dirverClassName = "com.mysql.cj.jdbc.Driver";
	private final String url;
	private final String user;
	private final String pwd;
	// 连接池
	private final LinkedList<Connection> pool;

	static {
		try {
			Class.forName(dirverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public SimpleDataSource(String url, String user, String pwd) {
		this.url = url+"?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
		this.user = user;
		this.pwd = pwd;
		pool = new LinkedList<Connection>();
	}

	/**
	 * 获取一个数据库连接
	 * 
	 * @return 一个数据库连接
	 * @throws SQLException
	 */
	@Override
	public Connection getConnection() throws SQLException {
		synchronized (pool) {
			if (pool.size() > 0) {
				return pool.removeFirst();
			} else {
				return DriverManager.getConnection(url, user, pwd);
			}
		}
	}

	/**
	 * 连接归池
	 * 
	 * @param conn
	 */
	public void freeConnection(Connection conn) {
		pool.addLast(conn);
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

}