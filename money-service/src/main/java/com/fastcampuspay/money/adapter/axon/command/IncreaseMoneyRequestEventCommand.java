package com.fastcampuspay.money.adapter.axon.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class IncreaseMoneyRequestEventCommand {
	@NotNull
	private final String targetMembershipId;
	@NotNull
	private final int amount;
	@NotNull
	@TargetAggregateIdentifier
	private String aggregateIdentifier;

}
