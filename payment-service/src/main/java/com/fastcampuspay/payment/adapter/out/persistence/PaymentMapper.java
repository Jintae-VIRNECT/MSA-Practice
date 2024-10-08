package com.fastcampuspay.payment.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.fastcampuspay.payment.domain.Payment;

@Component
public class PaymentMapper {
	public Payment mapToDomainEntity(PaymentJpaEntity paymentJpaEntity) {
		return Payment.generatePayment(
			new Payment.PaymentId(paymentJpaEntity.getPaymentId()),
			new Payment.RequestMembershipId(paymentJpaEntity.getRequestMembershipId()),
			new Payment.RequestPrice(paymentJpaEntity.getRequestPrice()),
			new Payment.FranchiseId(paymentJpaEntity.getFranchiseId()),
			new Payment.FranchiseFeeRate(paymentJpaEntity.getFranchiseFeeRate()),
			new Payment.PaymentStatus(paymentJpaEntity.getPaymentStatus()),
			new Payment.ApprovedAt(paymentJpaEntity.getApprovedAt())
		);
	}
}
