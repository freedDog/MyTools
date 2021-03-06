package com.codemaker.maker.velocity;

import java.util.List;

/**
 * 模板变量数据
 * 
 */
public class VelocityDatas {
	protected VelocityOrder order;
	protected List<Object> datas;

	public VelocityDatas(VelocityOrder order, List<Object> datas) {
		this.order = order;
		this.datas = datas;
	}

	public Object get(String name) {
		int findex = (order != null) ? order.getIndexByName(name) : null;
		return get(findex);
	}

	public Object get(int index) {
		if (index < 0 || index >= size()) {
			return null;
		}
		return datas.get(index);
	}

	public int size() {
		return (datas != null) ? datas.size() : 0;
	}
}
