import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.higgins.saml2idp.saml2.SAMLAssertion;
import org.eclipse.higgins.saml2idp.saml2.SAMLAuthnStatement;
import org.eclipse.higgins.saml2idp.saml2.SAMLConditions;
import org.eclipse.higgins.saml2idp.saml2.SAMLConstants;
import org.eclipse.higgins.saml2idp.saml2.SAMLResponse;
import org.eclipse.higgins.saml2idp.saml2.SAMLSubject;

public class ArrayListAddTest {
	public static void main(String[] args) throws Exception {
//		System.out.println("asdasd https://ess.excelityglobal.cn ssddasdasd https://ess.excelityglobal.cn ssddasas".replaceAll("https://iam.lshmnc.com.cn/api/v1/saml/idp/init?sp=ess.excelityglobal.cn:saml2.0&config_id=307836c7-7c4b-4a07-8535-06c0cfefaf06","https://ess.excelityglobal.cn"));
//		FileInputStream fs = new FileInputStream(new File("C:\\Users\\e11222\\Desktop\\emp_master_data_setup_20200906031004779.xls"));
//		HSSFWorkbook wb = new HSSFWorkbook(fs);
//		HSSFSheet sheet = wb.getSheetAt(0);
//		System.out.println(sheet.getLastRowNum());
//		fs.close();
//		System.out.println(postResponse("810462","destination","relayState","https://sts.windows.net/5989ece0-f90e-40bf-9c79-1a7beccdb861/"));
//		regularExpressionTest();
	}
	
	public static String postResponse(String nameid, String destination, String relayState,String issuer) throws  Exception {
		SAMLResponse samlResponse = new SAMLResponse();
		samlResponse.setStatusCodeValue(SAMLConstants.STATUSCCODE_SUCCESS);
		samlResponse.setDestination(destination);
							
		
		SAMLAssertion samlAssertion = new SAMLAssertion(samlResponse.getDocument());
		samlAssertion.setIssuer(issuer);
		samlResponse.setSAMLAssertion(samlAssertion);
						
		SAMLSubject samlSubject = new SAMLSubject(samlAssertion.getDocument());
		
		samlSubject.setNameIDFormat(SAMLConstants.NAMEIDFORMAT_ENTITY);
		samlSubject.setNameID(nameid);
		samlSubject.setSubjectConfirmationMethod(SAMLConstants.SUBJECTCONFIRMATIONMETHOD_BEARER);
		samlAssertion.setSAMLSubject(samlSubject);

		SAMLConditions samlConditions = new SAMLConditions(samlAssertion.getDocument());
		samlConditions.setNotBefore(new Date(samlAssertion.getIssueInstant().getTime() - 86400000));
		samlConditions.setNotOnOrAfter(new Date(samlAssertion.getIssueInstant().getTime() + 86400000));
		samlAssertion.setSAMLConditions(samlConditions);
						
		SAMLAuthnStatement samlAuthnStatement = new SAMLAuthnStatement(samlAssertion.getDocument());
		samlAuthnStatement.setAuthnContextClassRef(SAMLConstants.AUTHNCONTEXTCLASSREF_PASSWORD);
		samlAssertion.setSAMLAuthnStatement(samlAuthnStatement);
				// Sign it.

		File keyFile = new File("C:\\work\\newworkspace\\embrace-ezp\\build\\embrace\\WEB-INF\\classes\\com\\embrace\\properties\\privkey.der");
		FileInputStream stream = new FileInputStream(keyFile);
		byte[] keyBytes = new byte[stream.available()];
		stream.read(keyBytes);
		stream.close();
		KeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("DSA");
		PrivateKey idpPrivateKey = keyFactory.generatePrivate(keySpec);

		keyFile = new File("C:\\work\\newworkspace\\embrace-ezp\\build\\embrace\\WEB-INF\\classes\\com\\embrace\\properties\\cacert.pem");
		stream = new FileInputStream(keyFile);
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		X509Certificate idpCertificate = (X509Certificate) certificateFactory.generateCertificate(stream);
		PublicKey idpPublicKey = idpCertificate.getPublicKey();
		if (idpPrivateKey != null && idpPublicKey != null) {
			try {
				samlResponse = new SAMLResponse(new StringReader(samlResponse.dump()));
				samlResponse.sign(idpPrivateKey, idpPublicKey);
			} catch (Exception ex) {
				throw new IOException("Cannot sign SAML2 message.");
			}
		}
		// We need to convert the SAML message to Base64.
		String samlString = samlResponse.dump();
		byte[] samlBytes = samlString.getBytes();
		samlString = new String(Base64.encodeBase64(samlBytes));
		return samlString;
	}
	
	public static void regularExpressionTest() throws Exception {
		File file = new File("C:\\Users\\e11222\\Desktop\\SSOUtil.java");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		StringBuilder sb = new StringBuilder();
		String temp = null;
		while((temp = br.readLine())!= null) {
			sb.append(temp);
		}
		br.close();
		fr.close();
		Pattern pattern = Pattern.compile("\\(\\d+,");
		Matcher matcher = pattern.matcher(sb.toString());
		while(matcher.find()) {
			System.out.println(matcher.group(0));
		}
		System.out.println(sb.toString().replaceAll("\\(\\d+,", "\\("));
	}
}
