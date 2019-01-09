package com.fd.code;

public class CodeMain {

	public static void main(String[] args) throws Exception {
		if (args == null || args.length <= 0) {
			// args = new String[] { "-help" };
			// args = new String[] { "-in=files/", "-out=target", "-type=xlsx", "-code=json", "-mode=getter;setting;creater" };
			// args = new String[] { "-in=files/", "-out=target", "-type=xlsx", "-code=c#", "-mode=getter;setting;creater" };

			// args = new String[] { "-in=files/", "-out=target", "-file=xlsx", "-type=data", "-language=json", "-params=a=3,b=5" };

			// args = new String[] { "-in=files/", "-out=target", "-filetype=xlsx", "-filename=%sModel.cs", "-type=class", "-language=c#", "-vm=vms/C#.vm", "-args=" };
			// args = new String[] { "-in=files/", "-out=target/out/", "-filetype=xlsx", "-filename=%s.json", "-type=data", "-language=json" };
			// args = new String[] { "-in=files/", "-out=src/test/java/test/out/", "-filetype=xlsx", "-filename=%sTempInfo.java", "-type=class", "-language=java", "-vm=vms/TempInfo.java.vm",
			// "-args=[package=test.out]" };
			// args = new String[] { "-in=files/", "-out=src/test/java/test/out/", "-filetype=xlsx", "-filename=%sTempInfoMgr.java", "-type=class", "-language=java", "-vm=vms/TempInfoMgr.java.vm",
			// "-args=[package=test.out]" };
			// args = new String[] { "-in=F:/temp/guide/", "-out=G:/360yun/other/CodeMakerTest/", "-type=xlsx", "-code=json", };
			// args = new String[] { "-in=F:/temp/guide/", "-out=G:/360yun/other/CodeMakerTest/", "-type=xlsx", "-code=c#", "-mode=getter;creater" };
			// args = new String[] { "-in=E:/", "-out=F:/temp/model" };
			// args = new String[] { "-in=F:/temp/guide/", "-out=G:/360yun/sync/prj/CodeSave/", "-type=xlsx", "-code=json", "-mode=getter;setter;creater;values" };

			// args = new String[] { "-in=../../../../design/configuration/", "-out=E:/prj/uugame/server/trunk/workspace/Lib/config/configuration/", "-filetype=xlsx", "-filename=%s.json",
			// "-type=data",
			// "-language=json", "-filtrates=Item" };

			// args = new String[] { "-in=../../../../design/configuration/", "-out=../../../trunk/workspace/Entity/src/com/tgt/uu/configuration/", "-filetype=xlsx", "-filename=%sTempInfo.java",
			// "-type=class", "-language=java", "-vm=vms/TempInfo.java.vm", "-args=[package=com.tgt.uu.configuration]", "-filtrates=" };

			args = new String[] { "-in=../../../../design/configuration/", "-out=../../../trunk/workspace/Service/src/com/tgt/uu/constant/", "-filetype=xlsx", "-filename=%sTempId.java", "-type=data",
					"-language=common", "-vm=vms/TempId.java.vm", "-args=[package=com.tgt.uu.constant, utils_package=com.test]", "-filtrates=RandomName" };
		}
		CodeStart makder = new CodeStart();
		makder.start(args);
	}
}
