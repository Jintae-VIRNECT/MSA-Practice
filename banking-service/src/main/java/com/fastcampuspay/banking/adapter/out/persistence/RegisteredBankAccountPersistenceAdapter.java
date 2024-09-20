package com.fastcampuspay.banking.adapter.out.persistence;

import java.util.List;

import com.fastcampuspay.banking.application.port.in.GetRegisteredBankAccountCommand;
import com.fastcampuspay.banking.application.port.out.GetRegisteredBankAccountPort;
import com.fastcampuspay.banking.application.port.out.RegisterBankAccountPort;
import com.fastcampuspay.banking.domain.RegisteredBankAccount;
import com.fastcampuspay.common.PersistenceAdapter;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort, GetRegisteredBankAccountPort {
	private final SpringDataRegisteredBankAccountRepository registeredBankAccountRepository;

	@Override
	public RegisteredBankAccountJpaEntity createRegisteredBankAccount(
		RegisteredBankAccount.MembershipId membershipId, RegisteredBankAccount.BankName bankName,
		RegisteredBankAccount.BankAccountNumber bankAccountNumber, RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid,
		RegisteredBankAccount.AggregateIdentifier aggregateIdentifier
	) {
		return registeredBankAccountRepository.save(
			RegisteredBankAccountJpaEntity.of(
				membershipId.getMembershipId(),
				bankName.getBankName(),
				bankAccountNumber.getBankAccountNumber(),
				linkedStatusIsValid.isLinkedStatusIsValid(),
				aggregateIdentifier.getAggregateIdentifier()
			)
		);
	}

	@Override
	public RegisteredBankAccountJpaEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {
		List<RegisteredBankAccountJpaEntity> entityList = registeredBankAccountRepository.findByMembershipId(command.getMembershipId());
		if (entityList.size() > 0) {
			return entityList.get(0);
		}
		return null;
	}
}
