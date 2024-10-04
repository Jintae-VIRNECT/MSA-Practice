package com.fastcampuspay.membership.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.fastcampuspay.common.UseCase;
import com.fastcampuspay.membership.adapter.out.persistence.MembershipConvertor;
import com.fastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.fastcampuspay.membership.application.port.in.AuthMembershipUseCase;
import com.fastcampuspay.membership.application.port.in.LoginMembershipCommand;
import com.fastcampuspay.membership.application.port.in.RefreshTokenCommand;
import com.fastcampuspay.membership.application.port.in.ValidateTokenCommand;
import com.fastcampuspay.membership.application.port.out.AuthMembershipPort;
import com.fastcampuspay.membership.application.port.out.FindMembershipPort;
import com.fastcampuspay.membership.application.port.out.ModifyMembershipPort;
import com.fastcampuspay.membership.domain.JwtToken;
import com.fastcampuspay.membership.domain.Membership;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
@Transactional
public class AuthMembershipService implements AuthMembershipUseCase {
	private final AuthMembershipPort authMembershipPort;
	private final FindMembershipPort findMembershipPort;
	private final ModifyMembershipPort modifyMembershipPort;

	@Override
	public JwtToken loginMembership(LoginMembershipCommand command) {

		String membershipId = command.getMembershipId();
		MembershipJpaEntity membershipJpaEntity = findMembershipPort.findMembership(
			new Membership.MembershipId(membershipId)
		);

		if (membershipJpaEntity.isValid()) {
			String jwtToken = authMembershipPort.generateJwtToken(
				new Membership.MembershipId(membershipId)
			);
			String refreshToken = authMembershipPort.generateRefreshToken(
				new Membership.MembershipId(membershipId)
			);

			// membership jpa 내에, refresh token 을 저장한다.
			modifyMembershipPort.modifyMembership(
				new Membership.MembershipId(membershipId),
				new Membership.MembershipName(membershipJpaEntity.getName()),
				new Membership.MembershipEmail(membershipJpaEntity.getEmail()),
				new Membership.MembershipAddress(membershipJpaEntity.getAddress()),
				new Membership.MembershipIsValid(membershipJpaEntity.isValid()),
				new Membership.MembershipIsCorp(membershipJpaEntity.isCorp()),
				new Membership.MembershipRefreshToken(refreshToken)
			);

			return JwtToken.generateJwtToken(
				new JwtToken.MembershipId(membershipId),
				new JwtToken.MembershipJwtToken(jwtToken),
				new JwtToken.MembershipRefreshToken(refreshToken)
			);
		}

		return null;
	}

	@Override
	public JwtToken refreshJwtTokenByRefreshToken(RefreshTokenCommand command) {
		String requestedRefreshToken = command.getRefreshToken();
		boolean isValid = authMembershipPort.validateJwtToken(requestedRefreshToken);

		if (isValid) {
			Membership.MembershipId membershipId = authMembershipPort.parseMembershipIdFromToken(requestedRefreshToken);
			String membershipIdString = membershipId.getIdValue();

			MembershipJpaEntity membershipJpaEntity = findMembershipPort.findMembership(membershipId);
			if (!membershipJpaEntity.getRefreshToken().equals(
				command.getRefreshToken()
			)) {
				return null;
			}

			// 고객의 refresh token 정보와, 요청받은 refresh token 정보가 일치하는지 확인 된 상태.
			if (membershipJpaEntity.isValid()) {
				String newJwtToken = authMembershipPort.generateJwtToken(
					new Membership.MembershipId(membershipIdString)
				);

				return JwtToken.generateJwtToken(
					new JwtToken.MembershipId(membershipIdString),
					new JwtToken.MembershipJwtToken(newJwtToken),
					new JwtToken.MembershipRefreshToken(requestedRefreshToken)
				);
			}
		}

		return null;
	}

	@Override
	public boolean validateJwtToken(ValidateTokenCommand command) {
		String jwtToken = command.getJwtToken();
		return authMembershipPort.validateJwtToken(jwtToken);
	}

	@Override
	public Membership getMembershipByJwtToken(ValidateTokenCommand command) {
		String jwtToken = command.getJwtToken();
		boolean isValid = authMembershipPort.validateJwtToken(jwtToken);

		if (isValid) {
			Membership.MembershipId membershipId = authMembershipPort.parseMembershipIdFromToken(jwtToken);
			String membershipIdString = membershipId.getIdValue();

			MembershipJpaEntity membershipJpaEntity = findMembershipPort.findMembership(membershipId);
			if (!membershipJpaEntity.getRefreshToken().equals(
				command.getJwtToken()
			)) {
				return null;
			}

			return MembershipConvertor.entityToDomain(membershipJpaEntity);
		}
		return null;
	}
}
