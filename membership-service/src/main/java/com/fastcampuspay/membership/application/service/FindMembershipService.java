package com.fastcampuspay.membership.application.service;

import com.fastcampuspay.membership.adapter.out.persistence.MembershipConvertor;
import com.fastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.fastcampuspay.membership.application.port.in.FindMembershipCommand;
import com.fastcampuspay.membership.application.port.in.FindMembershipUserCase;
import com.fastcampuspay.membership.application.port.out.FindMembershipPort;
import com.fastcampuspay.membership.common.UseCase;
import com.fastcampuspay.membership.domain.Membership;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class FindMembershipService implements FindMembershipUserCase {

	private final FindMembershipPort findMembershipPort;

	@Override
	public Membership findMembership(FindMembershipCommand command) {
		MembershipJpaEntity membership = findMembershipPort.findMembership(new Membership.MembershipId(command.getMembershipId()));

		return MembershipConvertor.entityToDomain(membership);
	}
}
