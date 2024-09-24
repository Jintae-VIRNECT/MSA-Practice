package com.fastcampuspay.money.query.adapter.out.persistence.mongodb;

import java.util.Optional;
import java.util.UUID;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.context.annotation.Primary;

import com.fastcampuspay.common.PersistenceAdapter;
import com.fastcampuspay.money.query.adapter.axon.QueryMoneySumByAddress;
import com.fastcampuspay.money.query.application.port.out.GetMoneySumByAddressPort;
import com.fastcampuspay.money.query.application.port.out.InsertMoneyIncreaseEventByAddress;
import com.fastcampuspay.money.query.domain.MoneySumByRegion;

import lombok.RequiredArgsConstructor;

@Primary
@PersistenceAdapter
@RequiredArgsConstructor
public class MongoDBAdapter implements GetMoneySumByAddressPort, InsertMoneyIncreaseEventByAddress {

	private final MoneySumByAddressRepository moneySumByAddressRepository;

	@Override
	public int getMoneySumByAddress(String address) {
		// MongoDB에서 address에 해당하는 데이터 가져오기
		Optional<MoneySumByAddress> result = moneySumByAddressRepository.findByPkAndSk(address, "-1");
		return result.map(MoneySumByAddress::getBalance).orElse(0);
	}

	@Override
	public void insertMoneyIncreaseEventByAddress(String addressName, int moneyIncrease) {
		// 1. 원시 이벤트 insert
		String pk = addressName + "#" + "230728";
		String sk = String.valueOf(moneyIncrease);
		MoneySumByAddress event = new MoneySumByAddress(pk, sk, moneyIncrease);
		moneySumByAddressRepository.save(event);

		// 2-1. 지역별/일별 정보 업데이트
		String summaryPk = pk + "#summary";
		String summarySk = "-1";
		updateOrInsert(summaryPk, summarySk, moneyIncrease);

		// 2-2. 지역별 정보 업데이트
		String summaryPk2 = addressName;
		String summarySk2 = "-1";
		updateOrInsert(summaryPk2, summarySk2, moneyIncrease);
	}

	private void updateOrInsert(String pk, String sk, int balanceIncrement) {
		Optional<MoneySumByAddress> existingEntry = moneySumByAddressRepository.findByPkAndSk(pk, sk);
		if (existingEntry.isPresent()) {
			MoneySumByAddress entry = existingEntry.get();
			entry.setBalance(entry.getBalance() + balanceIncrement);
			moneySumByAddressRepository.save(entry);
		} else {
			MoneySumByAddress newEntry = new MoneySumByAddress(pk, sk, balanceIncrement);
			moneySumByAddressRepository.save(newEntry);
		}
	}

	@QueryHandler
	public MoneySumByRegion query(QueryMoneySumByAddress query) {
		return MoneySumByRegion.generateMoneySumByRegion(
			new MoneySumByRegion.MoneySumByRegionId(UUID.randomUUID().toString()),
			new MoneySumByRegion.RegionName(query.getAddress()),
			new MoneySumByRegion.MoneySum(getMoneySumByAddress(query.getAddress()))
		);
	}
}