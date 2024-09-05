package com.fastcampuspay.banking.application.port.out;

import com.fastcampuspay.banking.adapter.out.persistence.ExternalFirmbankingRequest;
import com.fastcampuspay.banking.adapter.out.persistence.FirmbankingResult;

public interface RequestExternalFirmbankingPort {
	FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request);
}