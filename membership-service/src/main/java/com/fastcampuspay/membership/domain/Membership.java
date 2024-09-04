package com.fastcampuspay.membership.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Membership {
	@Getter
	private final String id;
	@Getter
	private final String name;
	@Getter
	private final String email;
	@Getter
	private final String address;
	@Getter
	private final boolean isValid;
	@Getter
	private final boolean isCorp;

	public static Membership generateMember(
		MembershipId id,
		MembershipName name,
		MembershipEmail email,
		MembershipAddress address,
		MembershipIsValid isValid,
		MembershipIsCorp isCorp
	) {
		return new Membership(
			id.getIdValue(),
			name.getNameValue(),
			email.getEmailValue(),
			address.getAddressValue(),
			isValid.isValidValue(),
			isCorp.isCorpValue()
		);
	}

	@Value
	public static class MembershipId {
		String idValue;

		public MembershipId(String id) {
			this.idValue = id;
		}
	}

	@Value
	public static class MembershipName {
		String nameValue;

		public MembershipName(String name) {
			this.nameValue = name;
		}
	}

	@Value
	public static class MembershipEmail {
		String emailValue;

		public MembershipEmail(String email) {
			this.emailValue = email;
		}
	}

	@Value
	public static class MembershipAddress {
		String addressValue;

		public MembershipAddress(String address) {
			this.addressValue = address;
		}
	}

	@Value
	public static class MembershipIsValid {
		boolean isValidValue;

		public MembershipIsValid(boolean isValid) {
			this.isValidValue = isValid;
		}
	}

	@Value
	public static class MembershipIsCorp {
		boolean isCorpValue;

		public MembershipIsCorp(boolean isCorp) {
			this.isCorpValue = isCorp;
		}
	}
}
