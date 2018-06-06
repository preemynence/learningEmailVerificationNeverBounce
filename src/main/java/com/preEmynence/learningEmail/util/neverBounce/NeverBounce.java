package com.preEmynence.learningEmail.util.neverBounce;

import com.neverbounce.api.client.NeverbounceClient;
import com.neverbounce.api.client.NeverbounceClientFactory;
import com.neverbounce.api.client.exception.NeverbounceApiException;
import com.neverbounce.api.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class NeverBounce {

	private NeverbounceClient neverbounceClient;

	@Value("${never.bounce.key.v4}")
	private String neverBounceV4Key;

	@PostConstruct
	public void init() {
		neverbounceClient = NeverbounceClientFactory.create(neverBounceV4Key);
	}

	public SingleCheckResponse validateSingleEmail(String email) {

		return neverbounceClient
				.prepareSingleCheckRequest()
				.withEmail(email) // address to verify
				.withAddressInfo(true)  // return address info with response
				.withCreditsInfo(true)  // return account credits info with response
				.withTimeout(20)  // only wait on slow email servers for 20 seconds max
				.build()
				.execute();
	}

	public AccountInfoResponse getAccountInfo() {

		return neverbounceClient
				.createAccountInfoRequest()
				.execute();
	}

	public JobsResultsResponse validateBulkEmail(List<String> emails) {

		JobsCreateWithSuppliedJsonRequest.Builder builder = neverbounceClient
				.prepareJobsCreateWithSuppliedJsonRequest();

		for (String email : emails) {
			builder.addInput(email.trim());
		}

		JobsCreateResponse jobsCreateResponse =
				builder.withAutoParse(true) //Start parsing ASAP
						.withAutoStart(true) //Start job ASAP.
						.build()
						.execute();

		long jobId = jobsCreateResponse.getJobId();

		JobsResultsResponse jobsResultsResponse;

		while (true) {
			try {
				jobsResultsResponse = neverbounceClient
						.prepareJobsResultsRequest()
						.withJobId(jobId)
						.build()
						.execute();
				break;
			} catch (NeverbounceApiException nae) {
				System.out.println("JobsResultsResponse: " + nae.getMessage());

				// Sleep
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		return jobsResultsResponse;
	}
}
