package com.fastcampuspay.money.adapter.in.web;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMemberMoneyListByMembershipIdsRequest {
	private List<String> membershipIds;
}
