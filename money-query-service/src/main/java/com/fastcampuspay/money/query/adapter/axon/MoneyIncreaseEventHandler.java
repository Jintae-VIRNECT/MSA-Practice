package com.fastcampuspay.money.query.adapter.axon;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.fastcampuspay.common.event.RequestFirmbankingFinishedEvent;
import com.fastcampuspay.money.query.application.port.out.GetMemberAddressInfoPort;
import com.fastcampuspay.money.query.application.port.out.InsertMoneyIncreaseEventByAddress;
import com.fastcampuspay.money.query.application.port.out.MemberAddressInfo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MoneyIncreaseEventHandler {
	@EventHandler
	public void handler(
		RequestFirmbankingFinishedEvent event
		, GetMemberAddressInfoPort getMemberAddressInfoPort
		, InsertMoneyIncreaseEventByAddress insertMoneyIncreaseEventByAddress
	) {
		log.info("Money Increase Event Received: " + event.toString());

		// 고객의 주소 정보
		MemberAddressInfo memberAddressInfo = getMemberAddressInfoPort.getMemberAddressInfo(event.getMembershipId());

		// Dynamodb Insert!
		String address = memberAddressInfo.getAddress(); // "강남구"
		int moneyIncrease = event.getMoneyAmount(); // "1000"
		System.out.println("Dynamodb Insert: " + address + ", " + moneyIncrease);

		insertMoneyIncreaseEventByAddress.insertMoneyIncreaseEventByAddress(address, moneyIncrease);
	}
}
