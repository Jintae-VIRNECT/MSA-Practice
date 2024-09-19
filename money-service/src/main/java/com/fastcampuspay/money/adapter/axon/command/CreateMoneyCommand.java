package com.fastcampuspay.money.adapter.axon.command;

import com.fastcampuspay.common.SelfValidating;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateMoneyCommand extends SelfValidating<CreateMoneyCommand> {
	@NotNull
	private String membershipId;

	public CreateMoneyCommand() {
		// Required by Axon to construct an empty instance to initiate Event Sourcing.
	}

	public CreateMoneyCommand(String membershipId) {
		this.membershipId = membershipId;
		this.validateSelf();
	}

	@Override
	public String toString() {
		return "CreateMoneyCommand{" +
			"membershipId='" + membershipId + '\'' +
			'}';
	}
}
