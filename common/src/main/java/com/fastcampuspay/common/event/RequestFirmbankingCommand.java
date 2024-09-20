package com.fastcampuspay.common.event;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestFirmbankingCommand {
	private String requestFirmbankingId;
	@TargetAggregateIdentifier
	private String aggregateIdentifier;
	private String rechargeRequestId;
	private String membershipId;
	private String fromBankName;
	private String fromBankAccountNumber;
	private String toBankName;
	private String toBankAccountNumber;
	private int moneyAmount; // only won
}
