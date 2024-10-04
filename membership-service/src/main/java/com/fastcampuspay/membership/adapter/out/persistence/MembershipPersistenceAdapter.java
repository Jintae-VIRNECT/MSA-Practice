package com.fastcampuspay.membership.adapter.out.persistence;

import java.util.List;

import com.fastcampuspay.common.PersistenceAdapter;
import com.fastcampuspay.membership.application.port.out.FindMembershipPort;
import com.fastcampuspay.membership.application.port.out.ModifyMembershipPort;
import com.fastcampuspay.membership.application.port.out.RegisterMembershipPort;
import com.fastcampuspay.membership.domain.Membership;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {
	private final SpringDataMembershipRepository membershipRepository;

	@Override
	public MembershipJpaEntity createMembership(
		Membership.MembershipName membershipName,
		Membership.MembershipEmail membershipEmail,
		Membership.MembershipAddress membershipAddress,
		Membership.MembershipIsValid membershipIsValid,
		Membership.MembershipIsCorp membershipIsCorp
	) {
		return membershipRepository.save(
			MembershipJpaEntity.of(
				membershipName.getNameValue(),
				membershipEmail.getEmailValue(),
				membershipAddress.getAddressValue(),
				membershipIsValid.isValidValue(),
				membershipIsCorp.isCorpValue(),
				""
			)
		);
	}

	@Override
	public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {
		return membershipRepository.findById(Long.parseLong(membershipId.getIdValue())).get();
	}

	@Override
	public List<MembershipJpaEntity> findMembershipListByAddress(Membership.MembershipAddress membershipAddress) {
		// 관악구, 서초구, 강남구 중 하나
		String address = membershipAddress.getAddressValue();
		return membershipRepository.findByAddress(address);
	}

	@Override
	public MembershipJpaEntity modifyMembership(
		Membership.MembershipId membershipId, Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail,
		Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp,
		Membership.MembershipRefreshToken membershipRefreshToken
	) {
		MembershipJpaEntity entity = membershipRepository.getById(Long.parseLong(membershipId.getIdValue()));
		entity.setName(membershipName.getNameValue());
		entity.setEmail(membershipEmail.getEmailValue());
		entity.setAddress(membershipAddress.getAddressValue());
		entity.setValid(membershipIsValid.isValidValue());
		entity.setCorp(membershipIsCorp.isCorpValue());
		entity.setRefreshToken(membershipRefreshToken.getRefreshToken());
		return membershipRepository.save(entity);
	}
}
