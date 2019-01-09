package com.fd.code.filtrater;

import java.util.ArrayList;
import java.util.List;

import com.fd.utils.StringUtils;

/**
 * 筛选器
 * Filtrater.java
 * @author JiangBangMing
 * 2019年1月9日下午2:45:49
 */
public class Filtrater {
	private String matcheStr; // 正则表达式
	private boolean positive; // 是否正表达

	private Filtrater(String matcheStr, boolean positive) {
		this.matcheStr = matcheStr;
		this.positive = positive;
	}

	/** 检测过滤 **/
	private boolean check(String str) {
		// 检测空
		if (StringUtils.isEmpty(str)) {
			return false;
		}

		// 全通过
		if (matcheStr.equals("*")) {
			return true;
		}

		// 匹配处理
		// Log.info("matches: " + str + " " + matcheStr +" "+ (str.matches(matcheStr)));
		if (!str.matches(matcheStr)) {
			return false;
		}
		return true;
	}

	private static Filtrater createFiltrater(String filtrateStr) {
		// 判断是否是非状态
		boolean positive = true;
		if (filtrateStr.charAt(0) == '!') {
			positive = false;
			filtrateStr = filtrateStr.substring(1);
		}
		// 创建筛选器
		Filtrater filtrater = new Filtrater(filtrateStr, positive);
		return filtrater;
	}

	/** 初始化筛选器 **/
	public static Filtrater[] create(String str) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		List<Filtrater> filtraters = new ArrayList<>();

		// 筛选器
		String[] filtrateStrs = str.split(",");
		int fsize = (filtrateStrs != null) ? filtrateStrs.length : 0;
		for (int i = 0; i < fsize; i++) {
			String filtrateStr = filtrateStrs[i];
			// 特殊判断
			if (filtrateStr.equals("*")) {
				return null; // 全通过, 不筛选.
			}

			// 创建筛选器
			Filtrater filtrater = createFiltrater(filtrateStr);
			if (filtrater != null) {
				filtraters.add(filtrater);
			}
		}
		return filtraters.toArray(new Filtrater[] {});
	}

	/** 检测处理 **/
	public static boolean check(Filtrater[] filtraters, String str) {
		int fsize = (filtraters != null) ? filtraters.length : 0;
		if (fsize <= 0) {
			return true; // 不过滤
		}

		// 遍历检测
		boolean baseResult = false;
		for (int i = 0; i < fsize; i++) {
			Filtrater filtrater = filtraters[i];

			// 判读筛选
			boolean result = filtrater.check(str);
			// 根据模式判断
			if (!filtrater.isPositive()) {
				if (result) {
					return false; // 非处理, 匹配直接失败.
				}
				baseResult = true; // 过了非处理, 没其他限制默认成功.
			} else {
				if (result) {
					return true; // 通过了就可以.
				}
			}
		}
		return baseResult;
	}

	public String getMatcheStr() {
		return matcheStr;
	}

	/** 是否正表达 **/
	public boolean isPositive() {
		return positive;
	}

	@Override
	public String toString() {
		return "Filtrater [matcheStr=" + matcheStr + ", positive=" + positive + "]";
	}

}

