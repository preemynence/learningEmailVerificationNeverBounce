package com.preemynence.emailvalidator.rest;

import com.neverbounce.api.model.AccountInfoResponse;
import com.neverbounce.api.model.JobsResultsResponse;
import com.neverbounce.api.model.SingleCheckResponse;
import com.preemynence.emailvalidator.util.neverbounce.NeverBounce;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class TestEmailController {

	@Autowired
	private NeverBounce neverBounce;

	//Get the account information about how much monthly quota is left.
	//	curl -X GET http://localhost:8080/api/getNeverBounceAccountInfo
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Get the account information about how much monthly quota is left.", response = AccountInfoResponse.class)})
	@GetMapping(value = "/getNeverBounceAccountInfo")
	public Object getNeverBounceAccountInfo() {
		return neverBounce.getAccountInfo();
	}

	//Send single email address to validate.
	//	curl -X POST http://localhost:8080/api/checkEmail -d 'test1@gmail.com'
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Send single email address to validate.", response = SingleCheckResponse.class)})
	@PostMapping(value = "/checkEmail")
	public Object validateEmail(@ApiParam(value = "Single email value. Ex - test@gmail.com") @RequestBody String email) {
		return neverBounce.validateSingleEmail(email);
	}

	//Send comma separated email addresses you want to validate.
	//	curl -X POST http://localhost:8080/api/checkBulkEmail -d 'test1@gmail.com, test2@gmail.com'
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Send comma separated email addresses you want to validate.", response = JobsResultsResponse.class)})
	@PostMapping(value = "/checkBulkEmail")
	public Object validateBuckEmail(@ApiParam(value = "Comma separated email values. Ex - test1@gmail.com, test2@gmail.com") @RequestBody String emails) {
		return neverBounce.validateBulkEmail(Arrays.asList(emails.split(",")));
	}
}
