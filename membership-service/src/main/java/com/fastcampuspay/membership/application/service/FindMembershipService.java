package com.fastcampuspay.membership.application.service;

import java.util.ArrayList;
import java.util.List;

import com.fastcampuspay.common.UseCase;
import com.fastcampuspay.membership.adapter.out.persistence.MembershipConvertor;
import com.fastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.fastcampuspay.membership.application.port.in.FindMembershipCommand;
import com.fastcampuspay.membership.application.port.in.FindMembershipListByAddressCommand;
import com.fastcampuspay.membership.application.port.in.FindMembershipUserCase;
import com.fastcampuspay.membership.application.port.out.FindMembershipPort;
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

	@Override
	public List<Membership> findMembershipListByAddress(FindMembershipListByAddressCommand command) {
		List<MembershipJpaEntity> membershipJpaEntities = findMembershipPort.findMembershipListByAddress(
			new Membership.MembershipAddress(command.getAddressName()));
		List<Membership> memberships = new ArrayList<>();

		for (MembershipJpaEntity membershipJpaEntity : membershipJpaEntities) {
			memberships.add(MembershipConvertor.entityToDomain(membershipJpaEntity));
		}
		return memberships;
	}
}
