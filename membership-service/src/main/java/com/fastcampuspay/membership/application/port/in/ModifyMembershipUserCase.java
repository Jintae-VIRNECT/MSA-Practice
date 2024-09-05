package com.fastcampuspay.membership.application.port.in;

import com.fastcampuspay.membership.domain.Membership;

public interface ModifyMembershipUserCase {
	Membership modifyMembership(ModifyMembershipCommand command);
}
