package com.fastcampuspay.money.adapter.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.*;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.fastcampuspay.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import com.fastcampuspay.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.fastcampuspay.money.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import com.fastcampuspay.money.adapter.axon.event.IncreaseMemberMoneyEvent;
import com.fastcampuspay.money.adapter.axon.event.MemberMoneyCreatedEvent;
import com.fastcampuspay.money.adapter.axon.event.RechargingRequestCreatedEvent;
import com.fastcampuspay.money.application.port.out.GetRegisteredBankAccountPort;
import com.fastcampuspay.money.application.port.out.RegisteredBankAccountAggregateIdentifier;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Aggregate()
@Data
@Slf4j
public class MemberMoneyAggregate {
	@AggregateIdentifier
	private String id;

	private Long membershipId;

	private int balance;

	public MemberMoneyAggregate() {
	}

	@CommandHandler
	public MemberMoneyAggregate(MemberMoneyCreatedCommand command) {
		System.out.println("MemberMoneyCreatedCommand Handler");

		AggregateLifecycle.apply(new MemberMoneyCreatedEvent(command.getMembershipId()));
	}

	@CommandHandler
	public String handle(@NotNull IncreaseMemberMoneyCommand command) {
		System.out.println("IncreaseMemberMoneyCommand Handler");
		id = command.getAggregateIdentifier();

		// store event
		AggregateLifecycle.apply(new IncreaseMemberMoneyEvent(id, command.getMembershipId(), command.getAmount()));
		return id;
	}

	@CommandHandler
	public void handler(RechargingMoneyRequestCreateCommand command, GetRegisteredBankAccountPort getRegisteredBankAccountPort) {
		log.info("RechargingMoneyRequestCreateCommand Handler  id = {}", command.getAggregateIdentifier());
		log.info("MemeberMoneyAggregate Handler의 돈 증가 요청 핸들러.");
		id = command.getAggregateIdentifier();

		// new RechargingRequestCreatedEvent
		// banking 정보가 필요해요. -> bank svc (get RegisteredBankAccount) 를 위한. Port.
		RegisteredBankAccountAggregateIdentifier registeredBankAccountAggregateIdentifier
			= getRegisteredBankAccountPort.getRegisteredBankAccount(command.getMembershipId());

		// Saga Start
		apply(new RechargingRequestCreatedEvent(
			command.getRechargingRequestId(),
			command.getMembershipId(),
			command.getAmount(),
			registeredBankAccountAggregateIdentifier.getAggregateIdentifier(),
			registeredBankAccountAggregateIdentifier.getBankName(),
			registeredBankAccountAggregateIdentifier.getBankAccountNumber()
		));
	}

	@EventSourcingHandler
	public void on(MemberMoneyCreatedEvent event) {
		id = UUID.randomUUID().toString();
		membershipId = Long.parseLong(event.getMembershipId());
		balance = 0;

		log.info("MemberMoneyCreatedEvent Sourcing Handler. id : {}", id);
	}

	@EventSourcingHandler
	public void on(IncreaseMemberMoneyEvent event) {
		System.out.println("IncreaseMemberMoneyEvent Sourcing Handler");
		id = event.getAggregateIdentifier();
		membershipId = Long.parseLong(event.getTargetMembershipId());
		balance = event.getAmount();
	}
}
