package com.fastcampuspay.banking.adapter.axon.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateFirmbankingRequestCommand {

	@NotNull
	@TargetAggregateIdentifier
	private String aggregateIdentifier;

	private int firmbankingStatus;

}
