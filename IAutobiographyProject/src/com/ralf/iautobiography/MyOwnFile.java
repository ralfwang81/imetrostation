package com.ralf.iautobiography;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class MyOwnFile {
	private Map<String,String> cypherMap = new HashMap<String,String>();
	public static void main(String[] args) throws Exception {
		new MyOwnFile().getSixDeduction();
    }

	public void getSixDeduction() throws Exception {
		Connection conn = getEZPConn();
		String[] strs = {"AUTODESK","BHGE","BLK","CALTERAH","CHJUMP","CPAY","EXCELITY","FROG","IDCP","KUNTUO","QUINTLES","STN","TREND","WTC"};
		String sql = "select count(1) from CORP.TAX_EXEMPTION_APPLICATION";
		Statement pstm = null;
		ResultSet rs = null;
		pstm = conn.createStatement();
		for(String temp : strs) {
			try {
//				pstm.executeUpdate(sql.replaceAll("CORP", temp));
				rs = pstm.executeQuery(sql.replaceAll("CORP", temp));
				if(rs.next())
					if(rs.getInt(1) > 0) {
						System.out.println(rs.getInt(1));
						System.out.println(temp);
					}
				rs.close();
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		pstm.close();
	}
	private void saveFileToRepository() throws Exception {
//		String sql = "select * from nsn.prtb_epayslip_ew_v where period = '201911' and entity = 'WTC01'"
//				+ " and EmployeeID = 'BTB0383'"
//				;
		String sql = "    SELECT distinct a.COMPANY_CODE_N,a.EMP_ID_C,a.current_period_c, TO_DATE('01/03/2020','dd/mm/yyyy') EFFECTIVE_DATE_DT,4 LATEST_FLAG_N ,b.PAY_ELEMENT_CODE_C,0 PAY_ELEMENT_VALUE_N ,sysdate CREATED_ON_DT,'1303113241' CREATED_BY_C,NULL EFFECTIVE_TO_DATE_DT,a.PAY_GROUP_CODE_C,'TER<15' REASON_C FROM ( SELECT * FROM C_30106.HRTB_EMP_MASTER_DATES a WHERE   a.DATE_TYPE_C = 'TER'    AND a.CURRENT_PERIOD_C = 135  AND A.DATE_DT<TO_DATE('15/03/2020','dd/mm/yyyy') and a.emp_id_c not in         (select distinct emp_id_c from C_30106.PRTB_PAY_RECURRING_SAL_DET where current_period_c = 135 and REASON_C='TER<15')  ) A LEFT JOIN ( SELECT  DISTINCT  EMP_ID_C, PAY_GROUP_CODE_C , PAY_ELEMENT_CODE_C FROM C_30106.PRTB_PAYELEM_EMP_MFT  WHERE (PAY_ELEMENT_CODE_C LIKE '%IN' or PAY_ELEMENT_CODE_C LIKE '%INREC') ) B ON A.EMP_ID_C = B.EMP_ID_C AND A.PAY_GROUP_CODE_C = B.PAY_GROUP_CODE_C  ";
		Connection conn = getConn();
		Statement pstm = null;
		ResultSet rs = null;
		pstm = conn.createStatement();
//		pstm.executeUpdate(sql);
		rs = pstm.executeQuery(sql);
		while(rs.next()) {
			System.out.print(rs.getString(2));
			System.out.print(" ");
			System.out.print(rs.getString(3));
			System.out.print(" ");
			System.out.print(rs.getString(5));
			System.out.print(" ");
			System.out.print(rs.getString(6));
			System.out.print(" ");
			System.out.print(rs.getString(11));
			System.out.println();
			
			String sql2 = "select *  from C_30106.PRTB_PAY_RECURRING_SAL_DET where EMP_ID_C=? and  CURRENT_PERIOD_C=? and  LATEST_FLAG_N=? and  PAY_ELEMENT_CODE_C=? and  PAY_GROUP_CODE_C=?";
			PreparedStatement pstm2 = conn.prepareStatement(sql2);
			pstm2.setString(1, rs.getString(2));
			pstm2.setString(2, rs.getString(3));
			pstm2.setString(3, rs.getString(5));
			pstm2.setString(4, rs.getString(6));
			pstm2.setString(5, rs.getString(11));
			ResultSet rs2 = pstm2.executeQuery();
			if(rs2.next()) {
				System.out.println("22222");
				rs2.close();
				pstm2.close();
				break;
			}
		}
		rs.close();
		pstm.close();
	}

	private void saveFileToRepository1() throws Exception {
//		String sql = "select * from nsn.prtb_epayslip_ew_v where period = '201911' and entity = 'WTC01'"
//				+ " and EmployeeID = 'BTB0383'"
//				;
		String sql = "SELECT distinct COMPANY_CODE_N,EMP_ID_C,STAT_PERIOD_CODE_N PERIOD_NUMBER_C,TO_DATE(AS_ON_DATE_DT,'dd/mm/yyyy') EFFECTIVE_DATE_DT,2 LATEST_FLAG_N,STAT_ELEMENT_CODE_C PAY_ELEMENT_CODE_C,VALUE_N PAY_ELEMENT_VALUE_N,sysdate CREATED_ON_DT,'1303113241' CREATED_BY_C,NULL EFFECTIVE_TO_DATE_DT,PAY_GROUP_CODE_C FROM  C_30106.HRTB_EMP_MB_N_BHGE WHERE STAT_YEAR_CODE_C LIKE '202003A' AND value_n  IS NOT NULL";
		Connection conn = getConn();
		Statement pstm = null;
		ResultSet rs = null;
		pstm = conn.createStatement();
//		pstm.executeUpdate(sql);
		rs = pstm.executeQuery(sql);
		while(rs.next()) {
			System.out.print(rs.getString(2));
			System.out.print(" ");
			System.out.print(rs.getString(3));
			System.out.print(" ");
			System.out.print(rs.getString(5));
			System.out.print(" ");
			System.out.print(rs.getString(6));
			System.out.print(" ");
			System.out.print(rs.getString(11));
			System.out.println();
		}
		rs.close();
		pstm.close();
	}
	
	private void saveFileToRepository2() throws Exception {
//		String sql = "select * from nsn.prtb_epayslip_ew_v where period = '201911' and entity = 'WTC01'"
//				+ " and EmployeeID = 'BTB0383'"
//				;
		String sql = "SELECT distinct COMPANY_CODE_N,EMP_ID_C,STAT_PERIOD_CODE_N PERIOD_NUMBER_C,TO_DATE(AS_ON_DATE_DT,'dd/mm/yyyy') EFFECTIVE_DATE_DT,1 LATEST_FLAG_N,STAT_ELEMENT_CODE_C PAY_ELEMENT_CODE_C,VALUE_N PAY_ELEMENT_VALUE_N,sysdate CREATED_ON_DT,'1303113241' CREATED_BY_C,TO_DATE('29/02/2020','dd/mm/yyyy') EFFECTIVE_TO_DATE_DT,PAY_GROUP_CODE_C  FROM  C_30106.HRTB_EMP_MB_N_BHGE WHERE STAT_YEAR_CODE_C LIKE '202003RA' AND value_n IS NOT NULL";
		Connection conn = getConn();
		Statement pstm = null;
		ResultSet rs = null;
		pstm = conn.createStatement();
//		pstm.executeUpdate(sql);
		rs = pstm.executeQuery(sql);
		while(rs.next()) {
			System.out.print(rs.getString(2));
			System.out.print(" ");
			System.out.print(rs.getString(3));
			System.out.print(" ");
			System.out.print(rs.getString(5));
			System.out.print(" ");
			System.out.print(rs.getString(6));
			System.out.print(" ");
			System.out.print(rs.getString(11));
			System.out.println();
		}
		rs.close();
		pstm.close();
	}
	
	private void saveFileToRepository3() throws Exception {
//		String sql = "select * from nsn.prtb_epayslip_ew_v where period = '201911' and entity = 'WTC01'"
//				+ " and EmployeeID = 'BTB0383'"
//				;
		String sql = "SELECT distinct COMPANY_CODE_N,EMP_ID_C,STAT_PERIOD_CODE_N PERIOD_NUMBER_C,TO_DATE(AS_ON_DATE_DT,'dd/mm/yyyy') EFFECTIVE_DATE_DT,2 LATEST_FLAG_N,STAT_ELEMENT_CODE_C PAY_ELEMENT_CODE_C,VALUE_N PAY_ELEMENT_VALUE_N,sysdate CREATED_ON_DT,'1303113241' CREATED_BY_C,NULL EFFECTIVE_TO_DATE_DT,PAY_GROUP_CODE_C,'with arrear,without current amount' REASON_C FROM (SELECT  a.EMP_ID_C, '202003A'  STAT_YEAR_CODE_C,a.STAT_ELEMENT_CODE_C,a.PAY_GROUP_CODE_C,a.STAT_PERIOD_CODE_N ,a.COMPANY_CODE_N, '01/03/2020' AS_ON_DATE_DT ,a.PROCESS_CODE_C,a.PROCESS_TYPE_C ,c.ACTUAL_AMOUNT_N VALUE_N   FROM ( SELECT  * FROM   C_30106.HRTB_EMP_MB_N_BHGE  WHERE STAT_YEAR_CODE_C LIKE '202003RA'  AND value_n IS NOT NULL   ) A LEFT JOIN ( SELECT  * FROM   C_30106.HRTB_EMP_MB_N_BHGE  WHERE STAT_YEAR_CODE_C LIKE '202003A' ) B ON A.EMP_ID_C = B.EMP_ID_C AND A.PAY_GROUP_CODE_C = B.PAY_GROUP_CODE_C AND A.STAT_ELEMENT_CODE_C = B.STAT_ELEMENT_CODE_C LEFT JOIN (SELECT * FROM C_30106.PRTB_PAY_SALARY_REGISTER WHERE  PAID_PERIOD_NUMBER_C = 134 AND pay_element_code_c LIKE '%IN' and process_type_c = 'R' ) c ON A.EMP_ID_C = c.EMP_ID_C AND A.PAY_GROUP_CODE_C = c.PAY_GROUP_CODE_C  AND A.STAT_PERIOD_CODE_N = c.PAID_PERIOD_NUMBER_C+1 AND A.STAT_ELEMENT_CODE_C = c.PAY_ELEMENT_CODE_C WHERE   B.VALUE_N IS  NULL   AND  c.ACTUAL_AMOUNT_N IS NOT NULL)";
		Connection conn = getConn();
		Statement pstm = null;
		ResultSet rs = null;
		pstm = conn.createStatement();
//		pstm.executeUpdate(sql);
		rs = pstm.executeQuery(sql);
		while(rs.next()) {
			System.out.print(rs.getString(2));
			System.out.print(" ");
			System.out.print(rs.getString(3));
			System.out.print(" ");
			System.out.print(rs.getString(5));
			System.out.print(" ");
			System.out.print(rs.getString(6));
			System.out.print(" ");
			System.out.print(rs.getString(11));
			System.out.println();
		}
		rs.close();
		pstm.close();
	}
	
	private void saveFileToRepository4() throws Exception {
//		String sql = "select * from nsn.prtb_epayslip_ew_v where period = '201911' and entity = 'WTC01'"
//				+ " and EmployeeID = 'BTB0383'"
//				;
		String sql = "SELECT   COMPANY_CODE_N, EMP_ID_C, CURRENT_PERIOD_C, EFFECTIVE_DATE_DT,2  LATEST_FLAG_N, 'HOUSERINREC' PAY_ELEMENT_CODE_C, PAY_ELEMENT_VALUE_N, sysdate CREATED_ON_DT, CREATED_BY_C, EFFECTIVE_TO_DATE_DT, PAY_GROUP_CODE_C   FROM C_30106.PRTB_PAY_RECURRING_SAL_DET WHERE CURRENT_PERIOD_C = 135 AND  LATEST_FLAG_N = 2 AND PAY_ELEMENT_CODE_C LIKE 'HOUSERIN'     and emp_id_c in       (SELECT FIELD2 FROM C_30106.TBGE_MB_BHGE tm WHERE FIELD150 LIKE '202003%') ";
		Connection conn = getConn();
		Statement pstm = null;
		ResultSet rs = null;
		pstm = conn.createStatement();
//		pstm.executeUpdate(sql);
		rs = pstm.executeQuery(sql);
		while(rs.next()) {
			System.out.print(rs.getString(2));
			System.out.print(" ");
			System.out.print(rs.getString(3));
			System.out.print(" ");
			System.out.print(rs.getString(5));
			System.out.print(" ");
			System.out.print(rs.getString(6));
			System.out.print(" ");
			System.out.print(rs.getString(11));
			System.out.println();
		}
		rs.close();
		pstm.close();
	}
	
	private void saveFileToRepository5() throws Exception {
//		String sql = "select * from nsn.prtb_epayslip_ew_v where period = '201911' and entity = 'WTC01'"
//				+ " and EmployeeID = 'BTB0383'"
//				;
		//HRTB_EMP_MASTER_DATES PRTB_PAYELEM_EMP_MFT
//		String sql = "select COMPANY_CODE_N, EMP_ID_C, CURRENT_PERIOD_C, EFFECTIVE_DATE_DT,    LATEST_FLAG_N, PAY_ELEMENT_CODE_C, PAY_ELEMENT_VALUE_N,CREATED_ON_DT,CREATED_BY_C, EFFECTIVE_TO_DATE_DT, PAY_GROUP_CODE_C, REASON_C from C_30106.PRTB_PAY_RECURRING_SAL_DET t where (t.pay_element_code_c like '%IN' or t.pay_element_code_c like '%INREC' ) and t.current_period_c = '135' and t.emp_id_c in (SELECT field2 FROM C_30106.TBGE_MB_BHGE tm WHERE FIELD150 LIKE '202003%')";
		String sql = "SELECT * FROM nsn.prtb_epayslip_ew_v where period= '202003' and entity = 'NSN01'";
		Connection conn = getConn();
		Statement pstm = null;
		ResultSet rs = null;
		pstm = conn.createStatement();
//		pstm.executeUpdate(sql);
		rs = pstm.executeQuery(sql);
		if(rs.next())
			System.out.println(1);
//		ResultSetMetaData md = rs.getMetaData();
//		System.out.println(md.getColumnCount() - 3);
		rs.close();
		pstm.close();
	}
	
	private Connection getConn() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
//		conn = DriverManager.getConnection(
////				"jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=YES)(ADDRESS=(PROTOCOL=TCP)(HOST=epaydb-scan.excelityglobal.cn)(PORT=1521))(CONNECT_DATA=(Service_name=shhapy)))",
//				"jdbc:oracle:thin:@//10.0.2.151:1521/shhapy",
////				"jdbc:oracle:thin:@//epaydb-scan.excelityglobal.cn:1521/shhapy",
//				"epay2eatw", "Pqnda1kw");
////				"jdbc:oracle:thin:@//10.0.5.211:1521/ORCL",
////				"C_30106", "sting23ret");
		conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@//10.0.2.64:15210/nokia.hewitt.com",
				"nsn", "sh_its_123");
		return conn;
	}
	
	private Connection getEZPConn() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Class.forName("com.ibm.db2.jcc.DB2Driver");
		conn = DriverManager.getConnection(
				"jdbc:db2://10.0.2.213:50000/ESSGLOB",
				"db2inst1", "Excelity1");
//				"jdbc:db2://10.0.5.136:50000/ESSGLOB",
//				"wtc", "Pdajklom_1");
		return conn;
	}
	private Map getCodeEmpnoMap() throws Exception {
		long time = System.currentTimeMillis();
		System.out.println(time);
		String sql = "select b.emp_no,d.CODE from wf_employee b,WF_EMP_JOB_LATEST_EFFECTIVE_DATE_V c,REPORTING_LEVEL_CODE d where b.emp_oid=c.emp_oid and c.LEVEL1_CODE_OID=d.REPORTING_LEVEL_CODE_OID ";
		Statement stmt = getEZPConn().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		HashMap map = new HashMap();
		while(rs.next()) {
			map.put(rs.getString(1),rs.getString(2));
		}
		System.out.println(map.size());
		System.out.println(System.currentTimeMillis() - time);
		return map;
	}
	private String getGLReportPath() {
		return "/usr/local/tomcat/repository";
	}
	
	public void getHeaders() {
		String[] strs = {"序号","客户编号","客户","唯一号","姓名","身份证号","内部编号","应收年月","服务年月","Company Code","养老企业基数","养老企业比例","养老个人基数","养老个人比例","Old Age Pension养老保险","","医疗企业基数","医疗企业比例","医疗个人基数","医疗个人比例","Medical医疗保险","","失业企业基数","失业企业比例","失业个人基数","失业个人比例","Unemployment失业保险","","Critical Illness大病保险","","生育企业基数","生育企业比例","Maternity 生育保险","工伤企业基数","工伤企业比例","Disability工伤保险","Disabled benefite残疾保障金","Old Age Pension养老保险","","Medical医疗保险","","Unemployment失业保险","","Critical Illness大病保险","","Maternity 生育保险","Disability工伤保险","Disabled benefite残疾保障金","Housing Fund No.公积金帐号","Housing Fund Contribution Base公积金基数","公积金比例","Housing Fund公积金","C","补充公积金比例","supplementary housing fund补充公积金","C","Housing Fund公积金","C","supplementary housing fund补充公积金","C","服务费","企业部分","个人部分","合计","备注"};
		ArrayList al1 = new ArrayList(Arrays.asList(strs));
		ArrayList al = new ArrayList();
		String fileName = "";
		InputStream is = null;
		BufferedInputStream bis = null;
		ArrayList heads = new ArrayList();
		try {
			HSSFWorkbook wb2 = new HSSFWorkbook(
					new FileInputStream("C:\\Users\\e11222\\Desktop\\UOBCN Combine MB RD.xls"));
			HSSFSheet sheet = wb2.getSheetAt(3);
			HSSFRow row = sheet.getRow(2);
			int cellNum = row.getPhysicalNumberOfCells();
			System.out.println(cellNum);
			for (int i = 1; i <= cellNum; i++) {
				String value = row.getCell(i).getStringCellValue();
				heads.add(value.replaceAll("\\n", ""));
			}
			System.out.println(al1);
			System.out.println(heads);
			if(al1.containsAll(heads)) {
				System.out.println("11");
			}else {
				System.out.println("22");
			}
		} catch (Exception e) {
			al.add("error when parsing file data!");
			e.printStackTrace();
//			cat.info(e, e);
		}
	}
//	private String combineExcelFile2(String tempFileName, ArrayList al) {
//		String fileName = "";
//		InputStream is = null;
//		BufferedInputStream bis = null;
//		ZipInputStream zis = null;
//		ZipFile zf = null;
//		String effectiveDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
//		HSSFWorkbook outWb = new HSSFWorkbook();
//		HSSFSheet outSheet = outWb.createSheet("EZP MB Non Recurring");
//		HSSFRow outRow = outSheet.createRow(0);
//		for (int i = 0; i < targetFileHeader.size(); i++) {
//			outRow.createCell(i).setCellValue(targetFileHeader.get(i));
//		}
//		try {
//			zf = new ZipFile(tempFileName);
//			is = new FileInputStream(tempFileName);
//			bis = new BufferedInputStream(is);
//			zis = new ZipInputStream(bis);
//			ZipEntry ze = null;
//			boolean validFlag = true;
//			Enumeration e = zf.getEntries();
//			while(e.hasMoreElements()) {
////			while ((ze = zis.getNextEntry()) != null) {
//				ze = (ZipEntry)e.nextElement();
//				Workbook wb2 = null;
//				if (ze.getName().endsWith(".xls") || ze.getName().endsWith(".XLS")) {
//					wb2 = new HSSFWorkbook(zf.getInputStream(ze));
//				} else {
//					wb2 = new XSSFWorkbook(zf.getInputStream(ze));
//				}
//				Sheet sheet = wb2.getSheetAt(0);
//				int rowNum = sheet.getPhysicalNumberOfRows();
//				if (rowNum < 5) {
//					al.add("no data in file: " + ze.getName());
//					validFlag = false;
//					continue;
//				}
//				Row row = sheet.getRow(2);
//				int cellNum = row.getPhysicalNumberOfCells();
//				ArrayList headerTemp = new ArrayList();
//				for (int i = 1; i < cellNum; i++) {
//					String value = row.getCell(i).getStringCellValue();
//					headerTemp.add(value.replaceAll("\\n", ""));
//				}
//				if (!sourceFileHeader.containsAll(headerTemp)) {
//					al.add("different row head in file: " + ze.getName());
//					validFlag = false;
//					continue;
//				}
//				if (validFlag) {
//					for (int i = 4; i < rowNum; i++) {
//						row = sheet.getRow(i);
//						if (row.getCell(1).getCellType() == Cell.CELL_TYPE_BLANK || row.getCell(2).getCellType() == Cell.CELL_TYPE_BLANK)
//							break;
//						outRow = outSheet.createRow(outSheet.getPhysicalNumberOfRows());
//						outRow.createCell(0).setCellValue(effectiveDate);
//						for (int j = 1; j <= cellNum; j++) {
//							Cell cell = row.getCell(j);
//							if (cell != null) {
//								if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
//									outRow.createCell(j).setCellValue(String.valueOf(cell.getNumericCellValue()));
//								} else if (Cell.CELL_TYPE_FORMULA == cell.getCellType()) {
//									outRow.createCell(j).setCellFormula(cell.getCellFormula().replaceAll(String.valueOf(i+1), String.valueOf(outSheet.getPhysicalNumberOfRows())));
//								} else if (Cell.CELL_TYPE_BLANK == cell.getCellType()) {
//									outRow.createCell(j).setCellType(Cell.CELL_TYPE_BLANK);
//								} else {
//									outRow.createCell(j).setCellValue(cell.getStringCellValue());
//								}
//							}
//						}
//					}
//				}
//			}
//			fileName = getGLReportPath() + File.separator + String.valueOf(new Date().getTime()) + ".xls";
//			FileOutputStream resultFile = new FileOutputStream(fileName);
//			outWb.write(resultFile);
//			resultFile.close();
//		} catch (Exception e) {
//			al.add("error when parsing file data!");
//			e.printStackTrace();
//		} finally {
//			try {
//				if (zis != null) {
//					zis.close();
//				}
//				if (bis != null) {
//					bis.close();
//				}
//				if (is != null) {
//					is.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return fileName;
//	}
	
	private String zipAndEncryptFiles(String srcFileName, ArrayList al) {
		cypherMap.put("EXCELITY_TO_EXEC","Salary123");
		cypherMap.put("EXCELITY_TO_EPMSH","EXSH2020");
		cypherMap.put("EXCELITY_TO_CORPCN","Eemrson18");
		cypherMap.put("EXCELITY_TO_ECTSZ","Ectsuzhou@2018");
		cypherMap.put("EXCELITY_TO_SZRD","newpayroll***");
		cypherMap.put("EXCELITY_TO_AFTC","AFTCnjp*");
		cypherMap.put("EXCELITY_TO_BMMC","bmmcebic");
		cypherMap.put("EXCELITY_TO_PRMSH","0624****");
		cypherMap.put("EXCELITY_TO_MARSH","Marine123");
		cypherMap.put("EXCELITY_TO_FCVTJ","emsemsems");
		cypherMap.put("EXCELITY_TO_FCVSZ","ems755**");
		cypherMap.put("EXCELITY_TO_PRMCD","emrjeon@1");
		cypherMap.put("EXCELITY_TO_SZSOL","790690**");
		cypherMap.put("EXCELITY_TO_SZRSS","SOL389**");
		cypherMap.put("EXCELITY_TO_SZSAL","EMR927927#");
		cypherMap.put("EXCELITY_TO_ASSYSH","bransonsh");
		cypherMap.put("EXCELITY_TO_FLMCCN","ASCOASCO");
		cypherMap.put("EXCELITY_TO_FCTLBJ","VCBJ1234");
		cypherMap.put("EXCELITY_TO_ISVCD","VCCD1234");
		cypherMap.put("EXCELITY_TO_PRMQP","VCQP1234");
		cypherMap.put("EXCELITY_TO_TODZH","TODZH159");
		cypherMap.put("EXCELITY_TO_TODTL","EMERSONTL");
		cypherMap.put("EXCELITY_TO_PTSH","emerson13");
		cypherMap.put("EXCELITY_TO_ISENJ","807008ww");
		String destFileName = getGLReportPath() + File.separator + String.valueOf(new Date().getTime()) + ".zip";
		String effectiveDate = new SimpleDateFormat("yyyyMM").format(new Date());
		ZipFile zf = null;
		ZipFile destZf = null;
		Map<String,Set<FileHeader>> nameMap = new HashMap<String,Set<FileHeader>>();
		try {
			destZf = new ZipFile(destFileName);
			zf = new ZipFile(srcFileName);
			List<FileHeader> fileHeaders = zf.getFileHeaders();
			for(FileHeader fileHeader : fileHeaders) {
				if(!fileHeader.isDirectory()) {
					String directoryName = fileHeader.getFileName().split("/")[0];
					int i = fileHeader.getFileNameLength();
					Set nameSet = nameMap.get(directoryName);
					if (nameSet == null) {
						nameSet = new HashSet<FileHeader>();
					}
					nameSet.add(fileHeader);
					nameMap.put(directoryName, nameSet);
				}
			}
			for(String directoryName : nameMap.keySet()) {
				File temZipFile = new File(getGLReportPath() + File.separator + directoryName+"_"+effectiveDate+".zip");
				ZipFile tempZf = new ZipFile(temZipFile);
				ZipParameters zp = new ZipParameters();
				String tempFile = getGLReportPath() + File.separator;
				for(FileHeader fh : nameMap.get(directoryName)) {
					zf.extractFile(fh, getGLReportPath() + File.separator);
					tempZf.addFile(new File(tempFile + fh.getFileName()),zp);
				}
				zp.setEncryptFiles(true);
				zp.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
				zp.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
				zp.setPassword(cypherMap.get(directoryName.substring(0, directoryName.indexOf("_", 12))));
				destZf.addFile(temZipFile, zp);
				temZipFile.delete();
			}
		} catch (Exception e) {
			al.add("error when parsing file data!");
			e.printStackTrace();
		}
		return destFileName; 
	}
}
class MyThread extends Thread {
	public void run() {
		Thread hello = new HelloThread();
		hello.start(); // 启动hello线程
		try {
			hello.join(); // 等待hello线程结束
		} catch (InterruptedException e) {
			System.out.println("t interrupted!");
		}
		hello.interrupt();
	}
}

class HelloThread extends Thread {
	public void run() {
		int n = 0;
		while (!isInterrupted()) {
			n++;
			try {
				System.out.println(n + " hello!");
				Thread.sleep(1);
			} catch (InterruptedException e) {
				System.out.println("hello interrupted!");
				break;
			}
		}
	}
}
class TaskQueue {
    Queue<String> queue = new LinkedList<>();

    public synchronized void addTask(String s) {
        this.queue.add(s);
        this.notifyAll();
    }

    public synchronized String getTask() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait();
        }
        return queue.remove();
    }
}