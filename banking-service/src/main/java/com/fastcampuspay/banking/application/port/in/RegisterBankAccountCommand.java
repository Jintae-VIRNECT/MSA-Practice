package com.fastcampuspay.banking.application.port.in;

import com.fastcampuspay.banking.adapter.in.web.RegisterBankAccountRequest;
import com.fastcampuspay.common.SelfValidating;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RegisterBankAccountCommand extends SelfValidating<RegisterBankAccountCommand> {

	@NotNull
	private final String membershipId;

	@NotNull
	private final String bankName;

	@NotNull
	@NotBlank
	private final String bankAccountNumber;

	private final boolean isValid;

	@Builder
	public RegisterBankAccountCommand(String membershipId, String bankName, String bankAccountNumber, boolean isValid) {
		this.membershipId = membershipId;
		this.bankName = bankName;
		this.bankAccountNumber = bankAccountNumber;
		this.isValid = isValid;

		this.validateSelf();
	}

	public static RegisterBankAccountCommand from(RegisterBankAccountRequest request) {

		return RegisterBankAccountCommand.builder()
			.membershipId(request.getMembershipId())
			.bankName(request.getBankName())
			.bankAccountNumber(request.getBankAccountNumber())
			.isValid(request.isValid())
			.build();
	}
}
