package com.fastcampuspay.membership.application.port.in;

import com.fastcampuspay.membership.common.UseCase;
import com.fastcampuspay.membership.domain.Membership;

@UseCase
public interface RegisterMembershipUseCase {
	Membership registerMembership(RegisterMembershipCommand command);
}
