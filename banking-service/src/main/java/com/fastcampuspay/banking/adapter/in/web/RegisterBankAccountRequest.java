package com.fastcampuspay.banking.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterBankAccountRequest {
	private String membershipId;
	private String bankName;
	private String bankAccountNumber;
	private boolean isValid;

}
