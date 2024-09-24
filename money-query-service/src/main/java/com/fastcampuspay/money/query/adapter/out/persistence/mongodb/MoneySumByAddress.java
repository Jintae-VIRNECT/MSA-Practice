package com.fastcampuspay.money.query.adapter.out.persistence.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "money_sum_by_address")
public class MoneySumByAddress {
	@Id
	private String id;
	private String pk;
	private String sk;
	private int balance;

	public MoneySumByAddress(String pk, String sk, int moneyIncrease) {
		this.pk = pk;
		this.sk = sk;
		this.balance = moneyIncrease;
	}
}