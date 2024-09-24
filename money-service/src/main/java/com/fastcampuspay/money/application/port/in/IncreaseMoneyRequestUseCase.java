package com.fastcampuspay.money.application.port.in;

import java.util.List;

import com.fastcampuspay.money.domain.MemberMoney;
import com.fastcampuspay.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyRequestUseCase {
	MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);

	MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command);

	void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command);

	List<MemberMoney> findMemberMoneyListByMembershipIds(FindMemberMoneyListByMembershipIdsCommand command);

}
