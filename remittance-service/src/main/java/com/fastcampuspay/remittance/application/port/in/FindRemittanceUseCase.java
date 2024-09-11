package com.fastcampuspay.remittance.application.port.in;

import java.util.List;

import com.fastcampuspay.remittance.domain.RemittanceRequest;

public interface FindRemittanceUseCase {
	List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command);
}
