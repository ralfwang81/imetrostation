package com.ralf.iautobiography;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/EClaimUatTestUtil")
public class EClaimUatDataModify {

	@Autowired
	DataSource dataSource;

	@ResponseBody
	@RequestMapping(value = "/modifyEClaimActionDate", method = RequestMethod.POST)
	public String modifyEClaimActionDate(HttpServletRequest request, HttpServletResponse response) {
		return "";
	}

	@ResponseBody
	@RequestMapping(value = "/modifyEClaimClaimDate", method = {RequestMethod.POST,RequestMethod.GET})
	public String modifyEClaimClaimDate(HttpServletRequest request, HttpServletResponse response) {
		String result = "success";
		try {
			String claimDate = request.getParameter("claimDate");
			String claimRefNo = request.getParameter("claimRefNo");
			Connection conn = dataSource.getConnection();
			String sql = "update be_emp_claim set claim_date = ? where claim_ref_no = ?";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, claimDate);
			stat.setString(2, claimRefNo);
			int i = stat.executeUpdate();
			if( i == 0) 
				result = "no data changed!";
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/restartEClaimScheduler", method = RequestMethod.POST)
	public String restartEClaimScheduler(HttpServletRequest request, HttpServletResponse response) {
		return "";
	}

}
