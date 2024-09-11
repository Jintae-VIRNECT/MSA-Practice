package com.fastcampuspay.remittance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RemittanceRequest { // 송금 요청에 대한 상태 클래스.
	@Getter
	private final String remittanceRequestId;
	@Getter
	private final String remittanceFromMembershipId;
	@Getter
	private final String toBankName;
	@Getter
	private final String toBankAccountNumber;
	@Getter
	private int remittanceType; // 0: membership(내부 고객), 1: bank (외부 은행 계좌)
	// 송금요청 금액
	@Getter
	private int amount;
	@Getter
	private String remittanceStatus;

	public static RemittanceRequest generateRemittanceRequest(
		RemittanceRequestId remittanceRequestId,
		RemittanceFromMembershipId remittanceFromMembershipId,
		ToBankName toBankName,
		ToBankAccountNumber toBankAccountNumber,
		RemittanceType remittanceType,
		Amount amount,
		RemittanceStatus remittanceRequestStatus
	) {
		return new RemittanceRequest(
			remittanceRequestId.remittanceRequestId,
			remittanceFromMembershipId.remittanceFromMembershipId,
			toBankName.toBankName,
			toBankAccountNumber.toBankAccountNumber,
			remittanceType.remittanceType,
			amount.amount, remittanceRequestStatus.remittanceStatus
		);
	}

	@Value
	public static class RemittanceRequestId {
		String remittanceRequestId;

		public RemittanceRequestId(String value) {
			this.remittanceRequestId = value;
		}
	}

	@Value
	public static class RemittanceFromMembershipId {
		String remittanceFromMembershipId;

		public RemittanceFromMembershipId(String value) {
			this.remittanceFromMembershipId = value;
		}
	}

	@Value
	public static class ToBankName {
		String toBankName;

		public ToBankName(String value) {
			this.toBankName = value;
		}
	}

	@Value
	public static class ToBankAccountNumber {
		String toBankAccountNumber;

		public ToBankAccountNumber(String value) {
			this.toBankAccountNumber = value;
		}
	}

	@Value
	public static class RemittanceType {
		int remittanceType;

		public RemittanceType(int value) {
			this.remittanceType = value;
		}
	}

	@Value
	public static class Amount {
		int amount;

		public Amount(int value) {
			this.amount = value;
		}
	}

	@Value
	public static class RemittanceStatus {
		String remittanceStatus;

		public RemittanceStatus(String value) {
			this.remittanceStatus = value;
		}
	}
}
