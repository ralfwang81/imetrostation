import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
public class BeanCopyTest {
	public static void main(String[] args) {
		CopyTestFrom cf = new CopyTestFrom();
		cf.setStr("1");
//		cf.setDate1(new Date());
		CopyTestFrom cf2 = new CopyTestFrom();
		try {
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);
			BeanUtils.copyProperties(cf2, cf);
			System.out.println(cf.getDate1());
			System.out.println(cf.getStr());
			System.out.println(cf2.getDate1());
			System.out.println(cf2.getStr());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class CopyFrom{
	private String str;
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public Date getDate1() {
		return date1;
	}
	public void setDate1(Date date1) {
		this.date1 = date1;
	}
	private Date date1;
}
class CopyTo{
	private String str;
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public Date getDate1() {
		return date1;
	}
	public void setDate1(Date date1) {
		this.date1 = date1;
	}
	private Date date1;
}
