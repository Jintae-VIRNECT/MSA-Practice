package com.fastcampuspay.payment.adapter.out.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fastcampuspay.common.CommonHttpClient;
import com.fastcampuspay.payment.application.port.out.GetMembershipPort;
import com.fastcampuspay.payment.application.port.out.MembershipStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MembershipServiceAdapter implements GetMembershipPort {

	private final CommonHttpClient commonHttpClient;

	private final String membershipServiceUrl;

	public MembershipServiceAdapter(
		CommonHttpClient commonHttpClient,
		@Value("${service.membership.url}") String membershipServiceUrl
	) {
		this.commonHttpClient = commonHttpClient;
		this.membershipServiceUrl = membershipServiceUrl;
	}

	@Override
	public MembershipStatus getMembership(String membershipId) {

		String url = String.join("/", membershipServiceUrl, "membership", membershipId);
		try {
			String jsonResponse = commonHttpClient.sendGetRequest(url).body();
			// json Membership

			ObjectMapper mapper = new ObjectMapper();
			Membership membership = mapper.readValue(jsonResponse, Membership.class);

			if (membership.isValid()) {
				return new MembershipStatus(membership.getMembershipId(), true);
			} else {
				return new MembershipStatus(membership.getMembershipId(), false);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
