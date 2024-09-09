package com.fastcampuspay.money.domain;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyChangingRequest {
	@Getter
	private final String moneyChangingRequestId;

	// 어떤 고객의 증액/감액 요청을 요청했는지의 멤버 정보
	@Getter
	private final String targetMembershipId;

	// 그 요청이 증액 요청인지 / 감액 요청인지
	@Getter
	private final int changingType; // enum. 0: 증액, 1: 감액

	// 증액 또는 감액 요청의 금액
	@Getter
	private final int changingMoneyAmount;

	// 머니 변액 요청에 대한 상태
	@Getter
	private final int changingMoneyStatus; // enum. 0:요청, 1: 성공, 2: 실패

	@Getter
	private final String uuid;

	@Getter
	private final Date createdAt;

	public static MoneyChangingRequest generateMoneyChangingRequest(
		MoneyChangingRequestId moneyChangingRequestId,
		TargetMembershipId targetMembershipId,
		MoneyChangingType moneyChangingType,
		ChangingMoneyAmount changingMoneyAmount,
		MoneyChangingStatus moneyChangingStatus,
		String uuid
	) {
		return new MoneyChangingRequest(
			moneyChangingRequestId.getMoneyChangingRequestId(),
			targetMembershipId.getTargetMembershipId(),
			moneyChangingType.getMoneyChangingType(),
			changingMoneyAmount.getChangingMoneyAmount(),
			moneyChangingStatus.getChangingMoneyStatus(),
			uuid,
			new Date()
		);
	}

	@Value
	public static class MoneyChangingRequestId {
		String moneyChangingRequestId;

		public MoneyChangingRequestId(String value) {
			this.moneyChangingRequestId = value;
		}
	}

	@Value
	public static class TargetMembershipId {
		String targetMembershipId;

		public TargetMembershipId(String value) {
			this.targetMembershipId = value;
		}
	}

	@Value
	public static class MoneyChangingType {
		int moneyChangingType;

		public MoneyChangingType(int value) {
			this.moneyChangingType = value;
		}
	}

	@Value
	public static class ChangingMoneyAmount {
		int changingMoneyAmount;

		public ChangingMoneyAmount(int value) {
			this.changingMoneyAmount = value;
		}
	}

	@Value
	public static class MoneyChangingStatus {
		int changingMoneyStatus;

		public MoneyChangingStatus(int value) {
			this.changingMoneyStatus = value;
		}
	}

	@Value
	public static class Uuid {
		String uuid;

		public Uuid(String uuid) {
			this.uuid = uuid;
		}
	}
}
