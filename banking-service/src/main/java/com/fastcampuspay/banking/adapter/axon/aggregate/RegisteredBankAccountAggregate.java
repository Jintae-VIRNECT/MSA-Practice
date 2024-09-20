package com.fastcampuspay.banking.adapter.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.*;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import com.fastcampuspay.banking.adapter.axon.command.CreateRegisteredBankAccountCommand;
import com.fastcampuspay.banking.adapter.axon.event.CreateRegisteredBankAccountEvent;
import com.fastcampuspay.banking.adapter.out.external.bank.BankAccount;
import com.fastcampuspay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.fastcampuspay.banking.application.port.out.RequestBankAccountInfoPort;
import com.fastcampuspay.common.event.CheckRegisteredBankAccountCommand;
import com.fastcampuspay.common.event.CheckedRegisteredBankAccountEvent;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Aggregate()
@Slf4j
public class RegisteredBankAccountAggregate {
	@AggregateIdentifier
	private String id;

	private String membershipId;

	private String bankName;

	private String bankAccountNumber;

	@CommandHandler
	public RegisteredBankAccountAggregate(CreateRegisteredBankAccountCommand command) {
		System.out.println("CreateRegisteredBankAccountCommand Sourcing Handler");
		apply(new CreateRegisteredBankAccountEvent(command.getMembershipId(), command.getBankName(), command.getBankAccountNumber()));
	}

	public RegisteredBankAccountAggregate() {
	}

	@CommandHandler
	public void handle(@NotNull CheckRegisteredBankAccountCommand command, RequestBankAccountInfoPort bankAccountInfoPort) {
		System.out.println("CheckRegisteredBankAccountCommand Handler");

		// command 를 통해, 이 어그리거트(RegisteredBankAccount) 가 정상인지를 확인해야해요.
		id = command.getAggregateIdentifier();
		log.info("뱅크에 존재하는 계정이 있는지 확인. id : {}", id);

		// Check! Registerd Bank Account
		BankAccount account = bankAccountInfoPort.getBankAccountInfo(
			new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber()));
		boolean isValidAccount = account.isValid();

		String firmbankingUUID = UUID.randomUUID().toString();

		// CheckedRegisteredBankAccountEvent
		apply(new CheckedRegisteredBankAccountEvent(
				command.getRechargeRequestId()
				, command.getCheckRegisteredBankAccountId()
				, command.getMembershipId()
				, isValidAccount
				, command.getAmount()
				, firmbankingUUID
				, account.getBankName()
				, account.getBankAccountNumber()
			)
		);

	}

	@EventSourcingHandler
	public void on(CreateRegisteredBankAccountEvent event) {
		System.out.println("CreateRegisteredBankAccountEvent Sourcing Handler");
		id = UUID.randomUUID().toString();
		membershipId = event.getMembershipId();
		bankName = event.getBankName();
		bankAccountNumber = event.getBankAccountNumber();

		log.info("CreateRegisteredBankAccountEvent Sourcing Handler. id : {}", id);
	}
}
