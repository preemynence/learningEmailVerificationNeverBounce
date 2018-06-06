package com.preEmynence.learningEmail.restController;

import com.preEmynence.learningEmail.util.neverBounce.NeverBounce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class TestEmailController {

	@Autowired
	NeverBounce neverBounce;

	//Get the account information about how much monthly quota is left.
	//	curl -X GET http://localhost:8080/api/getNeverBounceAccountInfo
	@GetMapping(value = "/getNeverBounceAccountInfo")
	public Object getNeverBounceAccountInfo() {
		return neverBounce.getAccountInfo();
	}

	//Send single email address
	//	curl -X POST http://localhost:8080/api/checkEmail -d 'test1@gmail.com'
	@PostMapping(value = "/checkEmail")
	public Object validateEmail(@RequestBody String email) {
		return neverBounce.validateSingleEmail(email);
	}

	//Send comma separated email addresses you want to validate.
	//	curl -X POST http://localhost:8080/api/checkBulkEmail -d 'test1@gmail.com, test2@gmail.com'
	@PostMapping(value = "/checkBulkEmail")
	public Object validateBuckEmail(@RequestBody String emails) {
		return neverBounce.validateBulkEmail(Arrays.asList(emails.split(",")));
	}
}
