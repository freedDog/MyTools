package com.fd.proto.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fd.proto.core.TempType;
import com.fd.proto.entity.ProtoMessage;

public class LuaGlobalUniqueNameMgr {
	private static Map<String, File> names = new LinkedHashMap<>();
	private static final FileFilter filter = new RpFileFilter();
	private static boolean isOpen = false;
	private static String srcPath;
	private static String pkgPreFix;

	public static boolean init(String srcPath, String pkgPreFix) {
		names.clear();
		LuaGlobalUniqueNameMgr.srcPath = srcPath;
		LuaGlobalUniqueNameMgr.pkgPreFix = pkgPreFix;
		String startPath = srcPath;
		if (!startPath.endsWith(File.separator)) {
			startPath += File.separator;
		}
		startPath += pkgPreFix.replaceAll("\\.", "\\" + File.separator);
		File file = new File(startPath);
		if (!file.exists() || !file.isDirectory()) {
			System.out.println("LUA生成路径异常,path:" + startPath);
			return false;
		}
		return itratorPath(file);
	}

	private static boolean itratorPath(File file) {
		File[] children = file.listFiles(filter);
		for (File child : children) {
			if (!child.exists()) {
				continue;
			}
			if (child.isFile()) {
				if (!checkAndAdd(child)) {
					System.out.println("lua文件名初始化检查异常，文件名不唯一:" + child.getName());
					return false;
				}
			} else if (child.isDirectory()) {
				itratorPath(child);
			}
		}
		return true;
	}

	public static boolean checkAndAdd(ProtoMessage protoMessage) {
		if (!isOpen) {
			return true;
		}
		String tempSrcPath = srcPath;
		if (!tempSrcPath.endsWith(File.separator)) {
			tempSrcPath += File.separator;
		}
		String pkgPath = pkgPreFix + "." + TempType.LUA.parseFullName(protoMessage);
		tempSrcPath += pkgPath.replaceAll("\\.", "\\" + File.separator) + TempType.LUA.getFileExtension();
		File file = new File(tempSrcPath);
		return checkAndAdd(file);
	}

	public static boolean checkAndAdd(File file) {
		if (!isOpen) {
			return true;
		}
		if (!names.containsKey(file.getName())) {
			names.put(file.getName(), file);
			return true;
		} else {
			return file.equals(names.get(file.getName()));
		}
	}

	public static void setIsOpen(boolean isOpen) {
		LuaGlobalUniqueNameMgr.isOpen = isOpen;
	}

	public static void writeReqFile(String filePath) {
		FileWriter fw = null;
		String srcPathStr = srcPath.replaceAll("[\\/\\\\]", "\\" + File.separator);
		if (!srcPathStr.endsWith(File.separator)) {
			srcPathStr = srcPathStr + File.separator;
		}
		try {
			fw = new FileWriter(filePath);
			for (Entry<String, File> entry : names.entrySet()) {
				File file = entry.getValue();
				String reqFullName = file.getPath().replace(srcPathStr, "");
				if (reqFullName.endsWith(".lua")) {
					reqFullName = reqFullName.replaceAll("\\.lua", "");
				}
				reqFullName = reqFullName.replaceAll("\\" + File.separator, "\\.");
				fw.append("require(\"" + reqFullName + "\")\n");
			}
		} catch (Exception e) {
			System.out.println("生成lua require文件异常 !" + e);
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (Exception e) {
					System.out.println("lua require文件关闭异常 !" + e);
				}
			}
		}
	}

	static class RpFileFilter implements FileFilter {
		@Override
		public boolean accept(File pathname) {
			if (!pathname.exists()) {
				return false;
			}

			return pathname.getName().endsWith(".lua") || pathname.isDirectory();
		}
	};

}
