package com.fastcampuspay.banking.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "registered_bank_account")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class RegisteredBankAccountJpaEntity {
	@Id
	@GeneratedValue
	private Long registeredBankAccountId;

	private String membershipId;

	private String bankName;

	private String bankAccountNumber;

	private boolean linkedStatusIsValid;

	@Builder
	public RegisteredBankAccountJpaEntity(String membershipId, String bankName, String bankAccountNumber, boolean linkedStatusIsValid) {
		this.membershipId = membershipId;
		this.bankName = bankName;
		this.bankAccountNumber = bankAccountNumber;
		this.linkedStatusIsValid = linkedStatusIsValid;
	}

	public static RegisteredBankAccountJpaEntity of(String membershipId, String bankName, String bankAccountNumber, boolean linkedStatusIsValid) {
		return RegisteredBankAccountJpaEntity.builder()
			.membershipId(membershipId)
			.bankName(bankName)
			.bankAccountNumber(bankAccountNumber)
			.linkedStatusIsValid(linkedStatusIsValid)
			.build();
	}

}
