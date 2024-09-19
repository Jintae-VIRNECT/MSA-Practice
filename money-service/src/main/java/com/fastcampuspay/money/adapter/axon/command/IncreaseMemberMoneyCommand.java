package com.fastcampuspay.money.adapter.axon.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.fastcampuspay.common.SelfValidating;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class IncreaseMemberMoneyCommand extends SelfValidating<MemberMoneyCreatedCommand> {

	@NotNull
	private final String membershipId;
	@NotNull
	private final int amount;
	@NotNull
	@TargetAggregateIdentifier
	private String aggregateIdentifier;
}
