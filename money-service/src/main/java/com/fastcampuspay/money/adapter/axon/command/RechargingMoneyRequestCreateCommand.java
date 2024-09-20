package com.fastcampuspay.money.adapter.axon.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RechargingMoneyRequestCreateCommand {
	@TargetAggregateIdentifier
	private String aggregateIdentifier;

	private String rechargingRequestId;

	private String membershipId;
	private int amount;
}
