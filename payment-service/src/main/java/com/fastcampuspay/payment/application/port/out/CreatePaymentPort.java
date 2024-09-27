package com.fastcampuspay.payment.application.port.out;

import java.util.List;

import com.fastcampuspay.payment.domain.Payment;

public interface CreatePaymentPort {
	Payment createPayment(String requestMembershipId, String requestPrice, String franchiseId, String franchiseFeeRate);

	List<Payment> getNormalStatusPayments();

	void changePaymentRequestStatus(String paymentId, int status);
}
