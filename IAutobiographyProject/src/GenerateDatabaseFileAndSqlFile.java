import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;

public class GenerateDatabaseFileAndSqlFile {

	private static String[] sqlinfo = {
			"UPDATE corpcode.COMPANY SET COMPANY_CODE='compcode',COMPANY_NAME='corpcode CHINA',COMPANY_DESCRIPTION='corpcode CHINA',COUNTRY_OID=(SELECT COUNTRY_OID FROM corpcode.COUNTRY WHERE COUNTRY_CODE='CN');",
			"UPDATE corpcode.ENTITY SET ENTITY_CODE='compcode',ENTITY_NAME='corpcode CHINA'  where ENTITY_OID='000000000040200000000000000000';",
			"update corpcode.ENTITY set ENTITY_CODE='corpcode',ENTITY_NAME='corpcode CORPORATION' where ENTITY_OID='000000000000000000000000000001';",
			"update corpcode.ENTITY set ENTITY_CODE='CN',ENTITY_NAME='China' where ENTITY_OID='000000000000000000000000001045';",
			"UPDATE corpcode.DELEGATEA SET ACCESS_PARAM_CODE='compcode';",
			"UPDATE corpcode.CURRENCY_ROUNDING SET ACCESS_PARAM_CODE='compcode';",
			"UPDATE corpcode.WF_COMPETENCY_CODE_LABEL SET ACCESS_PARAM_CODE='compcode';",
			"update corpcode.CORP_CONFIG_PARAM set VALUE='corpcode' where CORP_CONFIG_PARAM_OID='000000000000000000000000050001';",
			"update corpcode.WF_GENERIC_CODE set ACCESS_PARAM_CODE='corpcode' WHERE ACCESS_PARAM_CODE='STANDARD';",
			"update corpcode.WF_GENERIC_CODE set ACCESS_PARAM_CODE='compcode' WHERE ACCESS_PARAM_CODE='ENTITY01';",
			"update corpcode.REPORTING_LEVEL_CODE set ACCESS_PARAM_CODE='compcode' WHERE ACCESS_PARAM_CODE='ENTITY01';",
			"update corpcode.WORKING_DAY_CODE set ACCESS_PARAM_CODE='compcode' WHERE ACCESS_PARAM_CODE='ENTITY01';",
			"delete from corpcode.USER_LOG;",
			"update corpcode.USE_ACCESS_PROFILE set ACCESS_PARAM_CODE='compcode' WHERE ACCESS_PARAM_CODE='ENTITY01';",
			"update corpcode.USE_ACCESS_PROFILE set ACCESS_PARAM_CODE='corpcode' WHERE ACCESS_PARAM_CODE='STANDARD';",
			"update corpcode.EMPLOYEE_ACCESS_PROFILE set ACCESS_PARAM_CODE='compcode' WHERE ACCESS_PARAM_CODE='ENTITY01';",
			"update corpcode.USE_ACCESS set ACCESS_PARAM_CODE='compcode' WHERE ACCESS_PARAM_CODE='ENTITY01';",
			"update corpcode.USE_ACCESS set ACCESS_PARAM_CODE='corpcode' WHERE ACCESS_PARAM_CODE='STANDARD';",
			"update corpcode.WF_REASON_CODE set ACCESS_PARAM_CODE='corpcode' WHERE ACCESS_PARAM_CODE='STANDARD';",
			"update corpcode.user_account set PASSWORD='sysadminpwd' WHERE user_id='sysadmin';",
			"update corpcode.user_account set PASSWORD='e10197pwd' WHERE user_id='e10197';",
			"update corpcode.user_account set PASSWORD='e10360pwd' WHERE user_id='e10360';",
			"update corpcode.user_account set PASSWORD='e10224pwd' WHERE user_id='e10224';",
			"update corpcode.user_account set ACCESS_PARAM_CODE='corpcode' WHERE ACCESS_PARAM_CODE='STANDARD';",
			"update corpcode.USER_PREFERENCE set VALUE='corpcode' WHERE VALUE='ENTITY';",
			"update corpcode.USER_PREFERENCE set VALUE='compcode' WHERE VALUE='ENTITY01';",
			"update corpcode.user_account set ACCESS_PARAM_CODE='corpcode' WHERE ACCESS_PARAM_CODE='ENTITY';",
			"update corpcode.ADMIN_ACL set ACCESS_PARAM_CODE='corpcode' WHERE ACCESS_PARAM_CODE='ENTITY';",
			"update corpcode.ADMIN_ACL set ACCESS_PARAM_CODE='compcode' WHERE ACCESS_PARAM_CODE='ENTITY01';",
			"update corpcode.GENERIC_CODE set ACCESS_PARAM_CODE='compcode' where CODE_CATEGORY_TYPE='COST_CENTRE';",
			"update corpcode.IMP_DATA_IMPORT_TEMPLATE_HEADER set TEMPLATE_NAME='corpcode-New hire_new';",
			"update corpcode.PR_SYSTEM_PARAMETER set ACCESS_PARAM_CODE='compcode';" };
	
	private static String[] dbinfo = { "<jdo-conf>", "<database name=\"corpcode\" engine=\"db2\">",
			"	<driver class-name=\"com.ibm.db2.jcc.DB2Driver\" url=\"jdbc:db2://10.0.2.213:50000/ESSGLOB\">",
			"		<param name=\"user\" value=\"username\" />",
			"		<param name=\"password\" value=\"A6ED67ADA96E2ED222AD5DD790785CAB4AA34F74C3FE6BAD\" />", "	</driver>",
			"	<mapping href=\"EHRMS-Admin-mapping.xml\" />", "	<mapping href=\"EHRMS-WFM-mapping.xml\" />",
			"	<mapping href=\"EHRMS-Leave-mapping.xml\" />", "	<mapping href=\"EHRMS-Payroll-mapping.xml\" />",
			"	<mapping href=\"EHRMS-PayTimer-mapping.xml\" />", "	<mapping href=\"EHRMS-ESOP-mapping.xml\" />",
			"  	<mapping href=\"EHRMS-TB-mapping.xml\" />", "	<mapping href=\"EHRMS-QB-mapping.xml\" />",
			"  	<mapping href=\"EHRMS-FB-mapping.xml\" />", "	<mapping href=\"EHRMS-BE-mapping.xml\" />",
			"	<mapping href=\"EHRMS-MB-mapping.xml\" />", "	<mapping href=\"EHRMS-EO-mapping.xml\" />",
			"	<mapping href=\"EHRMS-TA-mapping.xml\" />", "	<mapping href=\"EHRMS-DataImport-mapping.xml\" />",
			"	<mapping href=\"EHRMS-OT-mapping.xml\" />", "</database>", " <transaction-demarcation mode=\"local\"/>",
			"</jdo-conf>" };

	private static String welcomeMailTemplate = "Dear $1,\n"
			+ "\n"
			+ "Welcome to Excelity Payroll Service system. We are extremely pleased to provide payroll service for you as the payroll service provider of Emerson.\n"
			+ "\n"
			+ "You can log in Excelity Payroll Service system to view and print your online pay slip via the below website and user information. \n"
			+ "\n" + "Website: https://ezp.excelityglobal.cn/embrace/logincontroller/login\n" + "User Name:$2\n" + "Password:$3\n"
			+ "Corporation:$4\n" + "\n"
			+ "For security reasons, please remember to change your password after your first login.\n" + "\n"
			+ "If you have any questions, please contact your local HR.\n" + "\n"
			+ "Thanks a lot for your awareness.\n" + "\n" + "Excelity Service Delivery Team\n" + "\n" + "$5 您好,\n"
			+ "\n" + "欢迎使用Excelity薪酬服务系统。我们很荣幸为您提供服务。\n" + "\n"
			+ "您可通过以下网址和用户信息登录Excelity薪酬服务系统查看及打印您的在线工资单。\n" + "\n" + "网址：https://ess.excelityglobal.cn \n"
			+ "用户名：$6\n" + "密码：$7\n" + "公司名：$8\n" + "\n" + "为了您的信息安全，请在第一次登录后更改您的密码。\n" + "\n"
			+ "如果有任何疑问，请与您公司人力资源部联系。\n" + "\n" + "非常感谢您的支持!\n" + "\n" + "Excelity服务团队\n";

	private static String resetPwdMailTemplate = "Dear $1,\n"
			+ "\n"
			+ "Your password to Excelity Payroll Service system https://ezp.excelityglobal.cn/embrace/logincontroller/login has been changed as per your request. Below is your updated login information:\n"
			+ "\n" + "User Name:$2\n" + "Password:$3\n" + "Corporation:$4\n" + "\n"
			+ "You are advised to change your password as soon as possible for security reason.\n" + "\n"
			+ "If you have any questions, please contact your HR.\n" + "Thanks a lot for your awareness.\n" + "\n"
			+ "Excelity Service Delivery Team\n" + "\n" + "$5 您好，\n" + "\n"
			+ "根据您的要求，您登录到Excelity薪酬服务系统 https://ess.excelityglobal.cn 的密码已经更改如下：\n" + "\n" + "用户名：$6\n" + "密码：$7\n"
			+ "公司名：$8\n" + "\n" + "为了安全起见,请在登录后更改您的密码。\n" + "\n" + "如果有任何疑问,请与您公司人力资源部联系。\n" + "\n" + "非常感谢您的支持！\n"
			+ "\n" + "Excelity服务团队\n" + "\n" + "\n" + "\n";

	public static void main(String arg[]) {
		if (arg.length < 2) {
			System.out.println("lack of parameter, please provide corp code and company code");
			return;
		}
		String corpCode = arg[0];
		String compCode = arg[1];
		try {
			generateDatabaseFile(corpCode);
			generateSqlFile(corpCode, compCode);
			generateWelcomeMailTemplate(corpCode);
			generateResetPwdMailTemplate(corpCode);
			generateShellCommandFile(corpCode, compCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void generateWelcomeMailTemplate(String corpCode) throws IOException {
		File file = new File(corpCode.toUpperCase() + "_new_account_mail.txt");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(welcomeMailTemplate);
		bw.flush();
		bw.close();
		fw.close();
	}

	private static void generateResetPwdMailTemplate(String corpCode) throws IOException {
		File file = new File(corpCode.toUpperCase() + "_change_pwd_mail.txt");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(resetPwdMailTemplate);
		bw.flush();
		bw.close();
		fw.close();
	}

	private static void generateDatabaseFile(String corpCode) throws IOException {
		File file = new File(corpCode.toUpperCase() + "-database.xml");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		dbinfo[1] = dbinfo[1].replace("corpcode", corpCode.toUpperCase());
		dbinfo[3] = dbinfo[3].replace("username", corpCode.toLowerCase());
		for (int i = 0; i < dbinfo.length; i++) {
			bw.write(dbinfo[i]);
			bw.newLine();
		}
		bw.flush();
		bw.close();
		fw.close();
	}

	private static void generateSqlFile(String corpCode, String compCode) throws IOException {
		File file = new File(corpCode.toUpperCase() + " prepare data.sql");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		for (int i = 0; i < sqlinfo.length; i++) {
			bw.write(sqlinfo[i].replaceAll("corpcode", corpCode.toUpperCase())
					.replaceAll("compcode", compCode.toUpperCase())
					.replaceAll("sysadminpwd", setLock(corpCode.toUpperCase(),"sysadmin","pa55word"))
					.replaceAll("e10197pwd", setLock(corpCode.toUpperCase(),"e10197","pa55word"))
					.replaceAll("e10360pwd", setLock(corpCode.toUpperCase(),"e10360","pa55word"))
					.replaceAll("e10224pwd", setLock(corpCode.toUpperCase(),"e10224","pwd12345")));
			bw.newLine();
		}
		bw.flush();
		bw.close();
		fw.close();
	}

	private static void generateShellCommandFile(String corpCode, String compCode) throws IOException {
		File file = new File(corpCode.toUpperCase() + " generate payslip template directory command.txt");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("mkdir /usr/local/tomcat/repository/ENTITY/ENTITY01".replaceAll("ENTITY",corpCode).replaceAll("ENTITY01", compCode));
		bw.newLine();
		bw.write("mkdir /usr/local/tomcat/documents/ENTITY01".replaceAll("ENTITY",corpCode).replaceAll("ENTITY01", compCode));
		bw.newLine();
		bw.write("mkdir /apps/tomcat/documents/ENTITY01/TaxReturnFormTemplates".replaceAll("ENTITY",corpCode).replaceAll("ENTITY01", compCode));
		bw.newLine();
		
		bw.flush();
		bw.close();
		fw.close();
	}
	
	public static String setLock(String corp, String userID, String pwd) {
		String hashResult = null;
		try {
			StringBuffer password = new StringBuffer();
			password.append(corp);
			password.append(userID);
			password.append(pwd);

			MessageDigest mDigest = MessageDigest.getInstance("SHA");
			byte[] output = mDigest.digest((password.toString()).getBytes());

			hashResult = asHex(output);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return hashResult;
	}

	public static String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;
		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");
			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}
		return strbuf.toString();
	}
}
