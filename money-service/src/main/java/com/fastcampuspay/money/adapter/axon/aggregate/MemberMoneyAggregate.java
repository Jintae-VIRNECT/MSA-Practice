package com.fastcampuspay.money.adapter.axon.aggregate;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.fastcampuspay.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import com.fastcampuspay.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.fastcampuspay.money.adapter.axon.event.IncreaseMemberMoneyEvent;
import com.fastcampuspay.money.adapter.axon.event.MemberMoneyCreatedEvent;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Aggregate()
@Data
public class MemberMoneyAggregate {
	@AggregateIdentifier
	private String id;

	private Long membershipId;

	private int balance;

	@CommandHandler
	public MemberMoneyAggregate(MemberMoneyCreatedCommand command) {
		System.out.println("MemberMoneyCreatedCommand Handler");

		AggregateLifecycle.apply(new MemberMoneyCreatedEvent(command.getMembershipId()));
	}

	public MemberMoneyAggregate() {
	}

	@CommandHandler
	public String handle(@NotNull IncreaseMemberMoneyCommand command) {
		System.out.println("IncreaseMemberMoneyCommand Handler");
		id = command.getAggregateIdentifier();

		// store event
		AggregateLifecycle.apply(new IncreaseMemberMoneyEvent(id, command.getMembershipId(), command.getAmount()));
		return id;
	}

	@EventSourcingHandler
	public void on(MemberMoneyCreatedEvent event) {
		System.out.println("MemberMoneyCreatedEvent Sourcing Handler");
		id = UUID.randomUUID().toString();
		membershipId = Long.parseLong(event.getMembershipId());
		balance = 0;
	}

	@EventSourcingHandler
	public void on(IncreaseMemberMoneyEvent event) {
		System.out.println("IncreaseMemberMoneyEvent Sourcing Handler");
		id = event.getAggregateIdentifier();
		membershipId = Long.parseLong(event.getTargetMembershipId());
		balance = event.getAmount();
	}
}
