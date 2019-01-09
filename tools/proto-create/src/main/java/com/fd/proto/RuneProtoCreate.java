package com.fd.proto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;

import com.fd.proto.core.Constructor;
import com.fd.proto.core.TempType;
import com.fd.proto.entity.ProtoMessage;

public class RuneProtoCreate {
	
	public static Properties prop = new Properties();
	static {
		// debug时注释此处的ClasspathResourceLoader
		// prop.put("file.resource.loader.class",
		// "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		prop.put("input.encoding", "UTF-8");
		prop.put("output.encoding", "UTF-8");
	}

	/*
	 * 通过递归得到某一路径下所有的目录及其文件
	 */
	public static void getTotalFiles(String filePath, List<String> totalFileList) {
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.getName().equals(".") || file.getName().equals("..")) {
				continue;
			}
			if (file.isDirectory()) {
				getTotalFiles(file.getAbsolutePath(), totalFileList);
			} else if (file.getName().endsWith(".rp")) {
				totalFileList.add(file.getAbsolutePath());
			}
		}
	}

	public static void main(String[] args) {
		// for (int i = 0; i < args.length; i++) {
		// System.out.println("args:" + args[i]);
		// }

		// rp文件所在的路径
		String rpInputPath = "E:/prj/uugame/server/tools/run/proto_tool/proto";

		// 生成消息文件的存放路径
		String msgOutputPath = "E:/prj/uugame/server/tools/run/proto_tool/out";
		// 消息文件的包空间
		String msgPkgPrefix = "net.rp";

		// 生成消息的类型(Java或者Lua)
		String msgType = "json";

		// 当前所需要生成的路径（可选，默认为Java的根路径，即生成所有的，当指定路径时，则只生成该路径下的所有rp文件）
		String curMsgPath = rpInputPath;
		int argLen = args.length;
		if (argLen >= 4) {
			rpInputPath = args[0];
			msgOutputPath = args[1];
			msgPkgPrefix = args[2];
			msgType = args[3];
			curMsgPath = rpInputPath;

			if (argLen >= 5) {
				curMsgPath = args[4]; // "G:\\server\\workspace\\RuneProto\\src\\com\\changic\\rh\\proto\\activity";
			}
		}

		TempType tempType = null;
		if (msgType.equalsIgnoreCase("lua")) {
			tempType = TempType.LUA;
		} else if (msgType.equalsIgnoreCase("java")) {
			tempType = TempType.JAVA;
		} else if (msgType.equalsIgnoreCase("json")) {
			tempType = TempType.JSON;
		} else {
			System.out.println("未知文件格式");
		}

		List<String> totalFileList = new ArrayList<String>();
		getTotalFiles(curMsgPath, totalFileList);
		for (String filePath : totalFileList) {
			if (!createProto(filePath, msgOutputPath, msgPkgPrefix, tempType)) {
				System.exit(0);
			}
		}
		System.out.println("消息文件生成成功");
	}

	/**
	 * 生成对应的消息文件，根据msgType
	 */
	private static boolean createProto(String filePath, String msgOutputPath, String msgPkgPrefix, TempType tempType) {
		StringBuffer result = new StringBuffer();
		boolean isSuccess = true;
		try {
			PipedOutputStream pos = new PipedOutputStream();
			PipedInputStream pis = new PipedInputStream();
			OutputStreamWriter writer = new OutputStreamWriter(pos);
			BufferedReader reader = new BufferedReader(new InputStreamReader(pis));
			pos.connect(pis);
			Constructor wc = new Constructor(filePath, new AtomicInteger(), writer);
			if (wc.parse()) {
				ProtoMessage protoMsg = wc.getProtoMessage();
				createMsg(protoMsg, msgOutputPath, msgPkgPrefix, tempType);
			} else {
				isSuccess = false;
				result.append(wc.getParseResult() + "\n");
				while (reader.ready()) {
					result.append(reader.readLine() + "\n");
				}
			}

			pos.close();
			pis.close();
		} catch (Exception e) {
			e.printStackTrace();
			result.append(e.getMessage());
		}

		if (!isSuccess) {
			System.out.println(result.toString());
		}

		return isSuccess;
	}

	private static boolean createMsg(ProtoMessage protoMessage, String srcPath, String pkgPreFix, TempType tempType) {
		try {
			Velocity.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute()); // 防止log导致空指针异常
			Velocity.init(prop);
			VelocityContext ctx = new VelocityContext();
			ctx.put("protoMessage", protoMessage);
			ctx.put("pkgPreFix", pkgPreFix);
			if (!srcPath.endsWith(File.separator)) {
				srcPath += File.separator;
			}
			String pkgPath = pkgPreFix + "." + tempType.parseFullName(protoMessage);
			srcPath += pkgPath.replaceAll("\\.", "\\" + File.separator) + tempType.getFileExtension();
			File file = new File(srcPath);
			file.getParentFile().mkdirs();
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
			Template temp = Velocity.getTemplate(tempType.getVmName(), "UTF-8");
			temp.merge(ctx, writer);
			writer.flush();
			writer.close();
			fos.close();
		} catch (Exception e) {
			StringBuffer errorDetail = new StringBuffer().append("detail:");
			for (StackTraceElement ele : e.getStackTrace()) {
				errorDetail.append(ele.toString()).append("\n");
			}
			System.out.println("异常:\n" + errorDetail.toString() + "   " + e);
			return false;
		}
		return true;
	}
}

