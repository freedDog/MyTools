package com.fd.dao.maker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fd.dao.maker.start.DaoMaker;

public class DaoMain {

	private final static Logger LOGGER=LoggerFactory.getLogger(DaoMain.class);
	public static void main(String[] args) throws Exception {
		if (args == null || args.length <= 0) {
			test();
			return;
		}
		start(args);
	}

	public static void start(String[] args) throws Exception {
		if (args == null || args.length <= 0) {
			LOGGER.error("没有参数");
			return;
		}
		DaoMaker makder = new DaoMaker();
		makder.start(args);
	}

	public static void test() throws Exception {
		final String url = "jdbc:mysql://127.0.0.1:3306/db_game_sg_001";
		// final String url = "jdbc:mysql://127.0.0.1:3306/db_game_sg_temp";
		final String user = "root";
		final String pwd = "admin";

		final String outpath = "src/test/java/test/out/";
		final String filename = "%sInfo.java";
		final String language = "java";
		final String jargs = "[package=com.tgt.uu.entity, utils_package=com.tgt.uu.entity.utils]";
		// final String vm = "vms/Info.java.vm";
		final String vm = "vms/Dao.java.vm";
		// final String vm = "vms/TempInfo.java.vm";
		String[] args = null;

		// info列表
		// args = new String[] { "-table=t_u_user", "-url=" + url, "-user=" +
		// user, "-pwd=" + pwd, "-out=" + outpath, "-filename=" + filename,
		// "-language=" + language, "-vm=" + vm, "-args=" + jargs };
		// start(args);

		// args = new String[] { "-table=a", "-url=" + url, "-user=" + user,
		// "-pwd=" + pwd, "-out=" + outpath, "-filename=" + filename,
		// "-language=" + language, "-vm=" + vm, "-args=" + jargs };
		// start(args);

		// args = new String[] { "-table=t_s_gameconfig", "-asname=GameConfig",
		// "-filename=" + filename, "-url=" + url, "-user=" + user, "-pwd=" +
		// pwd, "-out=" + outpath,
		// "-language=" + language, "-vm=" + vm, "-args=" + jargs };
		// start(args);

		// String argStr =
		// "-table=t_s_gameconfig -asname=GameConfig -filename=%sTempDAO.java
		// -type=class -language=java -url=jdbc:mysql://127.0.0.1:3306/db_game_sg_temp
		// -user=root -pwd=admin -out="
		// + outpath +
		// " -vm=vms/TempDao.java.vm
		// -args=[package=com.changic.sg.dao,entity_package=com.changic.sg.entity]";
		// args = argStr.split(" ");

		args = new String[] { "-table=t_u_guild", "-url=" + url, "-user=" + user, "-pwd=" + pwd, "-out=" + outpath,
				"-filename=" + filename, "-language=" + language, "-vm=" + vm, "-args=" + jargs, "-of=true" };
		start(args);

		// start(args);
	}
}