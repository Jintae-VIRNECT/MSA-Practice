package com.fastcampuspay.settlement.port.out;

import java.util.List;

import com.fastcampuspay.settlement.adapter.out.service.Payment;

public interface PaymentPort {
	List<Payment> getNormalStatusPayments(); // membershipId = franchiseId 간주.

	// 타겟 계좌, 금액
	void finishSettlement(Long paymentId);
}
