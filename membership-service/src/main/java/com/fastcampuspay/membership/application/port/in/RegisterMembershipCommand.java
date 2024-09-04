package com.fastcampuspay.membership.application.port.in;

import com.fastcampuspay.membership.adapter.in.web.RegisterMembershipRequest;
import com.fastcampuspay.membership.common.SelfValidating;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class RegisterMembershipCommand extends SelfValidating<RegisterMembershipCommand> {
	@NotEmpty
	private final String name;
	@NotEmpty
	private final String email;
	@NotEmpty
	private final String address;
	@AssertTrue
	private final boolean isValid;
	private final boolean isCorp;

	public static RegisterMembershipCommand of(String name, String email, String address, boolean corp) {
		return RegisterMembershipCommand.builder()
			.name(name)
			.email(email)
			.address(address)
			.isCorp(corp)
			.build();
	}

	public static RegisterMembershipCommand from(RegisterMembershipRequest request) {

		return RegisterMembershipCommand.builder()
			.name(request.getName())
			.email(request.getEmail())
			.address(request.getAddress())
			.isCorp(request.isCorp())
			.build();
	}
}
