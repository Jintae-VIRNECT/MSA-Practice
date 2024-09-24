package com.fastcampuspay.money.aggregation.adapter.out.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;

import com.fastcampuspay.common.CommonHttpClient;
import com.fastcampuspay.common.ExternalSystemAdapter;
import com.fastcampuspay.money.aggregation.application.port.out.GetMembershipPort;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExternalSystemAdapter
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
	public List<String> getMembershipByAddress(String address) {
		String url = String.join("/", membershipServiceUrl, "membership/address", address);
		try {
			String jsonResponse = commonHttpClient.sendGetRequest(url).body();
			// json Membership

			ObjectMapper mapper = new ObjectMapper();
			List<Membership> memberships = mapper.readValue(jsonResponse, new TypeReference<>() {
			});
			return memberships.stream()
				.map(Membership::getId)
				.collect(Collectors.toList());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
