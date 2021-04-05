package customer.onpremise_service.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sap.cds.feature.xsuaa.XsuaaUserInfo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import customer.onpremise_service.util.BPMSocketFactory;
import customer.onpremise_service.util.Constants;
import customer.onpremise_service.util.DBConnection;

@RestController
@RequestMapping("/api/bpm")
public class BPMController {
	
    private static final Logger logger = LoggerFactory.getLogger(BPMController.class);
    
    @Autowired
    XsuaaUserInfo xsuaaUserInfo;


	@GetMapping("/account")
	public ResponseEntity<String> getAccount() {
        logger.error("getId: " + xsuaaUserInfo.getId());
        logger.error("getName: " + xsuaaUserInfo.getName());
        logger.error("getGivenName: " + xsuaaUserInfo.getGivenName());
        logger.error("getEmail: " + xsuaaUserInfo.getEmail());
        logger.error("getOrigin: " + xsuaaUserInfo.getOrigin());
        logger.error("getSubDomain: " + xsuaaUserInfo.getSubDomain());
        logger.error("getFamilyName: " + xsuaaUserInfo.getFamilyName());
        logger.error("getTenant: " + xsuaaUserInfo.getTenant());
        logger.error("getRoles: " + xsuaaUserInfo.getRoles());
        logger.error("getAttributes: " + xsuaaUserInfo.getAttributes());
        logger.error("getAdditionalAttributes: " + xsuaaUserInfo.getAdditionalAttributes());
		return new ResponseEntity<String>(xsuaaUserInfo.getName(), HttpStatus.OK);
    }
	
	@GetMapping("/todoCount")
	public ResponseEntity<String> getTodoCountByEmpNo() {
        Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = -1;

		try {
            String empNo = xsuaaUserInfo.getName();
            if (StringUtils.isNotBlank(empNo)) {
                con = DBConnection.getSQLServerConnection(BPMSocketFactory.class.getCanonicalName(), Constants.BPM_DB_USERNAME, Constants.BPM_DB_PASSWORD, Constants.BPM_DB_DATABASENAME);
                if (con != null) {
                    logger.info("connect successfully");
                    
                    String sql = "select COUNT(LoginID) AS total from vw_task_all WHERE LoginID = ?";
                    pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, empNo);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        count = rs.getInt("total");
                    }
                }
            }
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
					logger.error(e.getMessage());
				}
            }
		}

		if (count != -1) {
			return new ResponseEntity<String>(Integer.toString(count), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

}
