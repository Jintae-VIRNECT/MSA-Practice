package com.fastcampuspay.money.application.port.in;

import com.fastcampuspay.common.SelfValidating;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateMemberMoneyCommand extends SelfValidating<CreateMemberMoneyCommand> {

	@NotNull
	private final String membershipId;

	public CreateMemberMoneyCommand(@NotNull String membershipId) {
		this.membershipId = membershipId;
		this.validateSelf();
	}
}
