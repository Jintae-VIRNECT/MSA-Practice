package com.fastcampuspay.money.aggregation.adapter.out.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.fastcampuspay.common.CommonHttpClient;
import com.fastcampuspay.common.ExternalSystemAdapter;
import com.fastcampuspay.money.aggregation.application.port.out.GetMoneySumPort;
import com.fastcampuspay.money.aggregation.application.port.out.MemberMoney;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@ExternalSystemAdapter
@Slf4j
public class MoneyServiceAdapter implements GetMoneySumPort {

	private final CommonHttpClient moneyServiceHttpClient;

	@Value("${service.money.url}")
	private String moneyServiceEndpoint;

	public MoneyServiceAdapter(
		CommonHttpClient commonHttpClient,
		@Value("${service.money.url}") String moneyServiceEndpoint
	) {
		this.moneyServiceHttpClient = commonHttpClient;
		this.moneyServiceEndpoint = moneyServiceEndpoint;
	}

	@Override
	public List<MemberMoney> getMoneySumByMembershipIds(List<String> membershipIds) {
		String url = String.join("/", moneyServiceEndpoint, "money/member-money");
		ObjectMapper mapper = new ObjectMapper();

		try {
			FindMemberMoneyRequest request = new FindMemberMoneyRequest(membershipIds);
			String jsonResponse = moneyServiceHttpClient.sendPostRequest(url, mapper.writeValueAsString(request)).get().body();

			return List.of(mapper.readValue(jsonResponse, new TypeReference<>() {
			}));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
