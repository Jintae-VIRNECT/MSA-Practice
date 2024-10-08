package com.fastcampuspay.membership.application.port.in;

import com.fastcampuspay.common.SelfValidating;
import com.fastcampuspay.membership.adapter.in.web.RegisterMembershipRequest;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
	private final boolean isCorp;
	private final boolean isValid;

	@Builder
	public RegisterMembershipCommand(String name, String email, String address, boolean isValid, boolean isCorp) {
		this.name = name;
		this.email = email;
		this.address = address;
		this.isValid = isValid;
		this.isCorp = isCorp;

		this.validateSelf();
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
