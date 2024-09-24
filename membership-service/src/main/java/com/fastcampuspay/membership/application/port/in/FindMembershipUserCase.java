package com.fastcampuspay.membership.application.port.in;

import java.util.List;

import com.fastcampuspay.membership.domain.Membership;

public interface FindMembershipUserCase {
	Membership findMembership(FindMembershipCommand command);

	List<Membership> findMembershipListByAddress(FindMembershipListByAddressCommand command);
}
