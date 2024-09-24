package com.fastcampuspay.money.application.port.out;

import java.util.List;

import com.fastcampuspay.money.adapter.out.persistence.MemberMoneyJpaEntity;

public interface GetMemberMoneyListPort {
	List<MemberMoneyJpaEntity> getMemberMoneyPort(List<String> membershipIds);
}
