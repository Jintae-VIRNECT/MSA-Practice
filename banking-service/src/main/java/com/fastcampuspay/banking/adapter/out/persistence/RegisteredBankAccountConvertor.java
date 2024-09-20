package com.fastcampuspay.banking.adapter.out.persistence;

import com.fastcampuspay.banking.domain.RegisteredBankAccount;

public class RegisteredBankAccountConvertor {

	public static RegisteredBankAccount entityToDomain(RegisteredBankAccountJpaEntity registeredBankAccountJpaEntity) {
		return RegisteredBankAccount.generateRegisteredBankAccount(
			new RegisteredBankAccount.RegisteredBankAccountId(registeredBankAccountJpaEntity.getRegisteredBankAccountId() + ""),
			new RegisteredBankAccount.MembershipId(registeredBankAccountJpaEntity.getMembershipId()),
			new RegisteredBankAccount.BankName(registeredBankAccountJpaEntity.getBankName()),
			new RegisteredBankAccount.BankAccountNumber(registeredBankAccountJpaEntity.getBankAccountNumber()),
			new RegisteredBankAccount.LinkedStatusIsValid(registeredBankAccountJpaEntity.isLinkedStatusIsValid()),
			new RegisteredBankAccount.AggregateIdentifier(registeredBankAccountJpaEntity.getAggregateIdentifier())
		);
	}
}
