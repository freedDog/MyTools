package com.fd.code.parse;

import com.fd.code.maker.FileOrder;

/**
 * 解析器
 * IParse.java
 * @author JiangBangMing
 * 2019年1月9日下午2:39:57
 */
public interface IParse {

	/** 生成订单 **/
	FileOrder[] orders();
}
