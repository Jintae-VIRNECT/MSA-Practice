package com.fastcampuspay.membership.adapter.in.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampuspay.membership.application.port.in.RegisterMembershipCommand;
import com.fastcampuspay.membership.application.port.in.RegisterMembershipUseCase;
import com.fastcampuspay.membership.common.WebAdapter;
import com.fastcampuspay.membership.domain.Membership;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMembershipController {
	private final RegisterMembershipUseCase registerMembershipUseCase;

	@PostMapping("/membership/register")
	public Membership register(@RequestBody RegisterMembershipRequest request) {

		//request -> command
		RegisterMembershipCommand command = RegisterMembershipCommand.from(request);

		// usecase
		return registerMembershipUseCase.registerMembership(command);
	}
}
