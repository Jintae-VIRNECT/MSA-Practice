package com.fastcampuspay.payment.domain;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment {

	@Getter
	private Long paymentId;
	@Getter
	private String requestMembershipId;
	@Getter
	private int requestPrice;
	@Getter
	private String franchiseId;
	@Getter
	private String franchiseFeeRate;
	@Getter
	private int paymentStatus;
	@Getter
	private Date approvedAt;

	public static Payment generatePayment(
		PaymentId paymentId,
		RequestMembershipId requestMembershipId,
		RequestPrice requestPrice,
		FranchiseId franchiseId,
		FranchiseFeeRate franchiseFeeRate,
		PaymentStatus paymentStatus,
		ApprovedAt approvedAt
	) {
		return new Payment(
			paymentId.getPaymentId(),
			requestMembershipId.getRequestMembershipId(),
			requestPrice.getRequestPrice(),
			franchiseId.getFranchiseId(),
			franchiseFeeRate.getFranchiseFeeRate(),
			paymentStatus.getPaymentStatus(),
			approvedAt.getApprovedAt()
		);
	}

	@Value
	public static class PaymentId {
		long paymentId;

		public PaymentId(long value) {
			this.paymentId = value;
		}
	}

	@Value
	public static class RequestMembershipId {
		String requestMembershipId;

		public RequestMembershipId(String value) {
			this.requestMembershipId = value;
		}
	}

	@Value
	public static class RequestPrice {
		int requestPrice;

		public RequestPrice(int value) {
			this.requestPrice = value;
		}
	}

	@Value
	public static class FranchiseId {
		String franchiseId;

		public FranchiseId(String value) {
			this.franchiseId = value;
		}
	}

	@Value
	public static class FranchiseFeeRate {
		String franchiseFeeRate;

		public FranchiseFeeRate(String value) {
			this.franchiseFeeRate = value;
		}
	}

	@Value
	public static class PaymentStatus {
		int paymentStatus;

		public PaymentStatus(int value) {
			this.paymentStatus = value;
		}
	}

	@Value
	public static class ApprovedAt {
		Date approvedAt;

		public ApprovedAt(Date value) {
			this.approvedAt = value;
		}
	}

}
