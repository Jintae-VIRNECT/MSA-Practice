package com.fastcampuspay.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {
	@Getter
	private final String memberMoneyId;

	@Getter
	private final String membershipId;

	// 잔액
	@Getter
	private final int balance;

	// @Getter private final int linkedBankAccount;

	public static MemberMoney generateMemberMoney(
		MemberMoneyId memberMoneyId,
		MembershipId membershipId,
		MoneyBalance moneyBalance
	) {
		return new MemberMoney(
			memberMoneyId.memberMoneyId,
			membershipId.membershipId,
			moneyBalance.balance
		);
	}

	@Value
	public static class MemberMoneyId {
		String memberMoneyId;

		public MemberMoneyId(String value) {
			this.memberMoneyId = value;
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
	public static class MoneyBalance {
		int balance;

		public MoneyBalance(int value) {
			this.balance = value;
		}
	}

	@Value
	public static class MoneyAggregateIdentifier {
		String aggregateIdentifier;

		public MoneyAggregateIdentifier(String aggregateIdentifier) {
			this.aggregateIdentifier = aggregateIdentifier;
		}
	}
}
