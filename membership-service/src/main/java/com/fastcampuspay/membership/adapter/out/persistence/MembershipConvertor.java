package com.fastcampuspay.membership.adapter.out.persistence;

import com.fastcampuspay.membership.domain.Membership;

public class MembershipConvertor {

	public static Membership entityToDomain(MembershipJpaEntity membershipJpaEntity) {
		return Membership.generateMember(
			new Membership.MembershipId(membershipJpaEntity.getId() + ""),
			new Membership.MembershipName(membershipJpaEntity.getName()),
			new Membership.MembershipEmail(membershipJpaEntity.getEmail()),
			new Membership.MembershipAddress(membershipJpaEntity.getAddress()),
			new Membership.MembershipIsValid(membershipJpaEntity.isValid()),
			new Membership.MembershipIsCorp(membershipJpaEntity.isCorp())
		);
	}
}
