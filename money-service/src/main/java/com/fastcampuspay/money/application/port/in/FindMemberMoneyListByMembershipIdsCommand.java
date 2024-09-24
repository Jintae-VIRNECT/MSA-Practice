package com.fastcampuspay.money.application.port.in;

import java.util.List;

import com.fastcampuspay.common.SelfValidating;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class FindMemberMoneyListByMembershipIdsCommand extends SelfValidating<FindMemberMoneyListByMembershipIdsCommand> {
	@NotNull
	private final List<String> membershipIds;

	public FindMemberMoneyListByMembershipIdsCommand(@NotNull List<String> targetMembershipId) {
		this.membershipIds = targetMembershipId;
		this.validateSelf();
	}
}
