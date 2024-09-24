package com.fastcampuspay.money.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.fastcampuspay.money.domain.MemberMoney;

@Component
public class MemberMoneyMapper {
	public MemberMoney mapToDomainEntity(MemberMoneyJpaEntity memberMoneyJpaEntity) {
		return MemberMoney.generateMemberMoney(
			new MemberMoney.MemberMoneyId(memberMoneyJpaEntity.getMemberMoneyId() + ""),
			new MemberMoney.MembershipId(memberMoneyJpaEntity.getMembershipId() + ""),
			new MemberMoney.MoneyBalance(memberMoneyJpaEntity.getBalance())
		);
	}
}
