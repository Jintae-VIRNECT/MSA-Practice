package com.fastcampuspay.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisteredBankAccount {
	@Getter
	private final String registeredBankAccountId;

	@Getter
	private final String membershipId;

	@Getter
	private final String bankName; // enum

	@Getter
	private final String bankAccountNumber;

	@Getter
	private final boolean linkedStatusIsValid;

	@Getter
	private final String aggregateIdentifier;

	public static RegisteredBankAccount generateRegisteredBankAccount(
		RegisteredBankAccount.RegisteredBankAccountId registeredBankAccountId,
		RegisteredBankAccount.MembershipId membershipId,
		RegisteredBankAccount.BankName bankName,
		RegisteredBankAccount.BankAccountNumber bankAccountNumber,
		RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid,
		RegisteredBankAccount.AggregateIdentifier aggregateIdentifier
	) {
		return new RegisteredBankAccount(
			registeredBankAccountId.registeredBankAccountId,
			membershipId.membershipId,
			bankName.bankName,
			bankAccountNumber.bankAccountNumber,
			linkedStatusIsValid.linkedStatusIsValid,
			aggregateIdentifier.aggregateIdentifier
		);
	}

	@Value
	public static class RegisteredBankAccountId {
		String registeredBankAccountId;

		public RegisteredBankAccountId(String value) {
			this.registeredBankAccountId = value;
		}
	}

	@Value
	public static class MembershipId {
		String membershipId;

		public MembershipId(String value) {
			this.membershipId = value;
		}
	}

	@Value
	public static class BankName {
		String bankName;

		public BankName(String value) {
			this.bankName = value;
		}
	}

	@Value
	public static class BankAccountNumber {
		String bankAccountNumber;

		public BankAccountNumber(String value) {
			this.bankAccountNumber = value;
		}
	}

	@Value
	public static class LinkedStatusIsValid {
		boolean linkedStatusIsValid;

		public LinkedStatusIsValid(boolean value) {
			this.linkedStatusIsValid = value;
		}
	}

	@Value
	public static class AggregateIdentifier {
		String aggregateIdentifier;

		public AggregateIdentifier(String value) {
			this.aggregateIdentifier = value;
		}
	}
}
