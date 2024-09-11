package com.fastcampuspay.remittance.application.service;

import java.util.List;

import com.fastcampuspay.common.UseCase;
import com.fastcampuspay.remittance.adapter.out.persistence.RemittanceRequestMapper;
import com.fastcampuspay.remittance.application.port.in.FindRemittanceCommand;
import com.fastcampuspay.remittance.application.port.in.FindRemittanceUseCase;
import com.fastcampuspay.remittance.application.port.out.FindRemittancePort;
import com.fastcampuspay.remittance.domain.RemittanceRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindRemittanceService implements FindRemittanceUseCase {
	private final FindRemittancePort findRemittancePort;
	private final RemittanceRequestMapper mapper;

	@Override
	public List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command) {
		//
		findRemittancePort.findRemittanceHistory(command);
		return null;
	}
}
