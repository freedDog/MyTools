package com.fd.proto.core;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.fd.proto.entity.ProtoMessage;
import com.fd.proto.yl.MessageLexer;
import com.fd.proto.yl.MessageParser;

/**
 * Constructor.java
 * @author JiangBangMing
 * 2019年1月9日下午3:22:22
 */
public class Constructor {
	private String filePath;
	private ProtoMessage protoMessage;

	private AtomicInteger errCountor;
	private String result;

	private OutputStreamWriter writer;

	public Constructor(String filePath, AtomicInteger errCountor, OutputStreamWriter writer) {
		this.filePath = filePath;
		this.errCountor = errCountor;
		this.writer = writer;
	}

	public boolean parse() {
		result = parse0();
		return ParseResult.SUCC.equals(result);
	}

	private String parse0() {
		if (filePath == null) {
			return ParseResult.SOURCE_FILE_PATH_ERROR;
		}

		MessageLexer lexer = new MessageLexer();
		lexer.setErrorCountor(errCountor);
		lexer.setFilePath(filePath);

		protoMessage = new ProtoMessage();
		MessageParser parser = new MessageParser();
		parser.setErrorCountor(errCountor);
		parser.setRpFilePath(filePath);
		parser.setProtoMessage(protoMessage);

		lexer.yyerr = writer;
		parser.yyerr = writer;
		try {
			File file = new File(filePath);
			if (!file.exists() || !file.isFile()) {
				return ParseResult.SOURCE_FILE_PATH_ERROR;
			}

			lexer.yyin = new FileReader(file);
			int n = 1;
			if (parser.yycreate(lexer)) {
				if (lexer.yycreate(parser)) {
					n = parser.yyparse();
				}
			}

			FileInputStream fis = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			try {
				String tempString = null;
				int startIndex = 0;
				List<String> annotationList = new ArrayList<>();
				while ((tempString = reader.readLine()) != null) {
					if (tempString.trim().length() == 0) {
						continue;
					}

					if (tempString.contains("{")) {
						startIndex = 1;
						continue;
					}

					if (tempString.contains("}")) {
						break;
					}

					if (startIndex < 1) {
						continue;
					}

					String annotation = "";
					if (tempString.contains("//")) {
						annotation = tempString.split("//")[1].trim();
					} else if (tempString.contains("/*")) {
						annotation = tempString.split(";")[1].trim();
						int len = annotation.length();
						if (len > 2) {
							annotation = annotation.substring(2, len).trim();
						}
					}
					annotationList.add(annotation);
				}

				int attriSize = protoMessage.getAttributes().size();
				int annotationSize = annotationList.size();
				if (attriSize != annotationSize) {
					System.out.println("解析出错了, FileName: " + filePath);
					return ParseResult.PARSE_ERROR;
				}
				protoMessage.addAnnotation(annotationList);
				reader.close();
			} catch (IOException e) {
				System.out.println("解析出错了,文件格式不对，请按照格式编写. FileName: " + filePath);
				e.printStackTrace();
			} finally {
				reader.close();
				in.close();
				fis.close();
			}

			return n == 0 ? ParseResult.SUCC : ParseResult.PARSE_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			return ParseResult.IO_STREAM_OPEN_ERROR;
		} finally {
			try {
				lexer.yyin.close();
				if (errCountor.get() > 0) {
					return ParseResult.PARSE_ERROR;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int getParseErrorCount() {
		return errCountor.get();
	}

	public String getParseResult() {
		return result;
	}

	public ProtoMessage getProtoMessage() {
		return protoMessage;
	}
}
