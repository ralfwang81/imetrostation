import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class HRMSDBTest {
	private static Connection conn = null;
	
	public static void main(String[] args) throws Exception {
		conn = getConn();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("Select t.ee_id,t.effective_date,t.dob,t.gender,t.web_ad_tx,t.employee_type,t.gl_cd_ds,t.org_titl_tx,"
				+ "t.job_grade,t.job_titl_tx,t.joining_date,t.reporting_eeid,t.* from p0w20001.EW_20001_EMPMAST_FULLD t "
				+ "where  t.ee_id='1973480'");
		if(rs.next()) {
			ResultSetMetaData rsm = rs.getMetaData();
			int cols = rsm.getColumnCount();
			System.out.println(cols);
			for(int i = 1;i <= cols; i++) {
				System.out.println(rsm.getColumnName(i) + ": " + rs.getString(i));
			}			
		}
		stat.close();
		conn.close();
	}
	
	private static Connection getConn() throws ClassNotFoundException, SQLException {
		if (conn != null && !conn.isClosed()) {
			return conn;
		} else {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=YES)(ADDRESS=(PROTOCOL=TCP)(HOST=10.0.2.181)(PORT=1521))(CONNECT_DATA=(Service_name=shwfa)(FAILOVER_MODE=(TYPE=SELECT)(METHOD=BASIC)(RETRIES=30)(DELAY=5))))",
					 "e10197", "Excelity1");
			return conn;
		}
	}
}
