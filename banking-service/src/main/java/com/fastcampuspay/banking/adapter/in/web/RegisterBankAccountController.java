package com.fastcampuspay.banking.adapter.in.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampuspay.banking.application.port.in.RegisterBankAccountCommand;
import com.fastcampuspay.banking.application.port.in.RegisterBankAccountUseCase;
import com.fastcampuspay.banking.domain.RegisteredBankAccount;
import com.fastcampuspay.common.WebAdapter;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {
	private final RegisterBankAccountUseCase registerBankAccountUseCase;

	@PostMapping("/banking/account/register")
	public RegisteredBankAccount register(@RequestBody RegisterBankAccountRequest request) {

		//request -> command
		RegisterBankAccountCommand command = RegisterBankAccountCommand.from(request);

		// usecase
		RegisteredBankAccount registeredBankAccount = registerBankAccountUseCase.registerBankAccount(command);
		if (registeredBankAccount == null) {
			//TODO: error handling
			throw new RuntimeException("계좌 등록에 실패했습니다.");
		}

		return registeredBankAccount;
	}
}
