package com.fastcampuspay.remittance.application.port.out;

import java.util.List;

import com.fastcampuspay.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.fastcampuspay.remittance.application.port.in.FindRemittanceCommand;

public interface FindRemittancePort {

	List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command);
}
