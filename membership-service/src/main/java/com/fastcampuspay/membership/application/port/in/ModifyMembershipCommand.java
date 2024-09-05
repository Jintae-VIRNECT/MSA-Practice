package com.fastcampuspay.membership.application.port.in;

import com.fastcampuspay.common.SelfValidating;
import com.fastcampuspay.membership.adapter.in.web.ModifyMembershipRequest;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ModifyMembershipCommand extends SelfValidating<RegisterMembershipCommand> {
	@NotEmpty
	private final String membershipId;
	@NotEmpty
	private final String name;
	@NotEmpty
	private final String email;
	@NotEmpty
	private final String address;
	private final boolean isValid;
	@AssertTrue
	private final boolean isCorp;

	@Builder
	public ModifyMembershipCommand(String membershipId, String name, String email, String address, boolean isValid, boolean isCorp) {
		this.membershipId = membershipId;
		this.name = name;
		this.email = email;
		this.address = address;
		this.isValid = isValid;
		this.isCorp = isCorp;

		this.validateSelf();
	}

	public static ModifyMembershipCommand from(ModifyMembershipRequest request) {
		return ModifyMembershipCommand.builder()
			.membershipId(request.getMembershipId())
			.name(request.getName())
			.email(request.getEmail())
			.address(request.getAddress())
			.isCorp(request.isCorp())
			.isValid(request.isValid())
			.build();
	}
}
