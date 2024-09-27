package com.fastcampuspay.payment.adapter.in.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampuspay.common.WebAdapter;
import com.fastcampuspay.payment.application.port.in.FinishSettlementCommand;
import com.fastcampuspay.payment.application.port.in.RequestPaymentCommand;
import com.fastcampuspay.payment.application.port.in.RequestPaymentUseCase;
import com.fastcampuspay.payment.domain.Payment;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestPaymentController {
	private final RequestPaymentUseCase requestPaymentUseCase;

	@PostMapping(path = "/payment/request")
	Payment requestPayment(PaymentRequest request) {
		return requestPaymentUseCase.requestPayment(
			new RequestPaymentCommand(
				request.getRequestMembershipId(),
				request.getRequestPrice(),
				request.getFranchiseId(),
				request.getFranchiseFeeRate()
			)
		);
	}

	@GetMapping(path = "/payment/normal-status")
	List<Payment> getNormalStatusPayments() {
		return requestPaymentUseCase.getNormalStatusPayments();
	}

	@PostMapping(path = "/payment/finish-settlement")
	void finishSettlement(@RequestBody FinishSettlementRequest request) {
		System.out.println("request.getPaymentId() = " + request.getPaymentId());
		requestPaymentUseCase.finishPayment(
			new FinishSettlementCommand(
				request.getPaymentId()
			)
		);
	}
}
