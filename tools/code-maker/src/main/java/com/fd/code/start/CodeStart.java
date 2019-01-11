package com.fd.code.start;

import com.codemaker.maker.IFileMaker;
import com.codemaker.parse.IFileParse;
import com.codemaker.start.CodeMaker;
import com.fd.code.maker.C2Maker;
import com.fd.code.maker.CommonMaker;
import com.fd.code.maker.JavaMaker;
import com.fd.code.maker.JsonMaker;
import com.fd.code.parse.ExcelParseT;
import com.fd.utils.ReflectUtils;

public class CodeStart extends CodeMaker {
	// 编码器列表
	protected final static IFileMaker[] makers = new IFileMaker[] { new JavaMaker(), new C2Maker(), new JsonMaker(), new CommonMaker() };
	// 解析器列表
	protected final static IFileParse[] parses = new IFileParse[] { new ExcelParseT(), };

	/**
	 * 获取语言编码器
	 * 
	 * @param language
	 * @return
	 */
	@Override
	protected IFileMaker getFileMaker(String type, String language) {
		return getFileMaker(makers, type, language);
	}

	/**
	 * 根据文件类型获取解析器
	 * 
	 * @param type
	 * @return
	 */
	@Override
	protected IFileParse getFileParse(String type) {
		int count = (parses != null) ? parses.length : 0;
		for (int i = 0; i < count; i++) {
			IFileParse fileParse = parses[i];
			String type0 = fileParse.type();
			if (type != null && type0.equals(type)) {
				// return fileParse;

				// 重新创建一个新的对象
				Class<?> clazz = fileParse.getClass();
				return (IFileParse) ReflectUtils.createInstance(clazz);
			}
		}
		return null;
	}

}
