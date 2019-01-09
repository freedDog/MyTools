package com.fd.dao.maker.sgame.maker;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.codemaker.maker.FileOrder;
import com.fd.dao.maker.mysql.DaoParse;

/**
 * 
 * SGameDaoParse.java
 * @author JiangBangMing
 * 2019年1月9日下午6:09:08
 */
public class SGameDaoParse extends DaoParse {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(SGameDaoParse.class);
	public boolean parse(DataSource dataSource, JdbcTemplate template, String tableName) throws Exception {
		String schema = getSchemaName(template);
		if (schema == null) {
			LOGGER.error("获取不到表名");
			return false;
		}

		// 创建订单
		FileOrder order = getTableTypes(dataSource, schema, tableName);
		if (order == null) {
			return false;
		}

		// 处理order名称
		String orderName = order.getName();
		if (orderName.indexOf("t_s_") == 0 || orderName.indexOf("t_u_") == 0 || orderName.indexOf("t_p_") == 0) {
			orderName = orderName.substring(4);
			order.setName(orderName);
		}

		orders.add(order);
		return true;
	}
}