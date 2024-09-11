package com.fastcampuspay.remittance.adapter.out.persistence;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "remittance")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemittanceJpaEntity {
	@Id
	@GeneratedValue
	private Long remittanceId;

	private String status;

	public RemittanceJpaEntity(
		String fromBankName, String fromBankAccountNumber, String toBankName, String toBankAccountNumber, int moneyAmount, int firmbankingStatus,
		UUID uuid
	) {

	}

}
