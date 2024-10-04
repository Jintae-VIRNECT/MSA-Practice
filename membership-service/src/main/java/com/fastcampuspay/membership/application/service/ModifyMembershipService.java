package com.fastcampuspay.membership.application.service;

import com.fastcampuspay.common.UseCase;
import com.fastcampuspay.membership.adapter.out.persistence.MembershipConvertor;
import com.fastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.fastcampuspay.membership.application.port.in.ModifyMembershipCommand;
import com.fastcampuspay.membership.application.port.in.ModifyMembershipUserCase;
import com.fastcampuspay.membership.application.port.out.ModifyMembershipPort;
import com.fastcampuspay.membership.domain.Membership;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ModifyMembershipService implements ModifyMembershipUserCase {
	private final ModifyMembershipPort modifyMembershipPort;

	@Override
	public Membership modifyMembership(ModifyMembershipCommand command) {
		MembershipJpaEntity jpaEntity = modifyMembershipPort.modifyMembership(
			new Membership.MembershipId(command.getMembershipId()),
			new Membership.MembershipName(command.getName()),
			new Membership.MembershipEmail(command.getEmail()),
			new Membership.MembershipAddress(command.getAddress()),
			new Membership.MembershipIsValid(command.isValid()),
			new Membership.MembershipIsCorp(command.isCorp()),
			new Membership.MembershipRefreshToken("")
		);

		return MembershipConvertor.entityToDomain(jpaEntity);
	}
}
