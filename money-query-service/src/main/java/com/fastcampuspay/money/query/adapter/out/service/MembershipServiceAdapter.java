package com.fastcampuspay.money.query.adapter.out.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fastcampuspay.common.CommonHttpClient;
import com.fastcampuspay.money.query.application.port.out.GetMemberAddressInfoPort;
import com.fastcampuspay.money.query.application.port.out.MemberAddressInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MembershipServiceAdapter implements GetMemberAddressInfoPort {

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
	public MemberAddressInfo getMemberAddressInfo(String membershipId) {
		String url = String.join("/", membershipServiceUrl, "membership", membershipId);
		try {
			String jsonResponse = commonHttpClient.sendGetRequest(url).body();

			ObjectMapper mapper = new ObjectMapper();
			Membership membership = mapper.readValue(jsonResponse, Membership.class);
			return new MemberAddressInfo(membership.getId(), membership.getAddress());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
