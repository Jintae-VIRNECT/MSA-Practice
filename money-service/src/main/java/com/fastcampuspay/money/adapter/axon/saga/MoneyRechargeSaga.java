package com.fastcampuspay.money.adapter.axon.saga;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.fastcampuspay.common.event.CheckRegisteredBankAccountCommand;
import com.fastcampuspay.common.event.CheckedRegisteredBankAccountEvent;
import com.fastcampuspay.common.event.RequestFirmbankingCommand;
import com.fastcampuspay.common.event.RequestFirmbankingFinishedEvent;
import com.fastcampuspay.common.event.RollbackFirmbankingFinishedEvent;
import com.fastcampuspay.money.adapter.axon.event.RechargingRequestCreatedEvent;
import com.fastcampuspay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.fastcampuspay.money.application.port.out.IncreaseMoneyPort;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Saga
@Slf4j
@NoArgsConstructor
public class MoneyRechargeSaga {

	private transient CommandGateway commandGateway;

	@Autowired
	public void setCommandGateway(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	@StartSaga
	@SagaEventHandler(associationProperty = "rechargingRequestId")
	public void handle(RechargingRequestCreatedEvent event) {
		System.out.println("RechargingRequestCreatedEvent Start saga");

		String checkRegisteredBankAccountId = UUID.randomUUID().toString();
		log.info("여기서 부터 사가 요청 시작? id : {} , aggregateIdentifier : {} ", checkRegisteredBankAccountId,
			event.getRegisteredBankAccountAggregateIdentifier()
		);
		SagaLifecycle.associateWith("checkRegisteredBankAccountId", checkRegisteredBankAccountId);

		// "충전 요청" 이 시작 되었다.

		// 뱅킹의 계좌 등록 여부 확인하기. (RegisteredBankAccount)
		// CheckRegisteredBankAccountCommand -> Check Bank Account
		// -> axon server -> Banking Service -> Common

		// 기본적으로 axon framework 에서, 모든 aggregate 의 변경은, aggregate 단위로 되어야만 한다.
		commandGateway.send(new CheckRegisteredBankAccountCommand(
				event.getRegisteredBankAccountAggregateIdentifier(),
				event.getRechargingRequestId(),
				event.getMembershipId(),
				checkRegisteredBankAccountId,
				event.getBankName(),
				event.getBankAccountNumber(),
				event.getAmount()
			)
		).whenComplete(
			(result, throwable) -> {
				if (throwable != null) {
					throwable.printStackTrace();
					System.out.println("CheckRegisteredBankAccountCommand Command failed");
				} else {
					log.info("뱅크 가입된 유저 확인 완료. result : {}", result);
				}
			}
		);
	}

	@SagaEventHandler(associationProperty = "checkRegisteredBankAccountId")
	public void handle(CheckedRegisteredBankAccountEvent event) {
		System.out.println("CheckedRegisteredBankAccountEvent saga: " + event.toString());
		boolean status = event.isChecked();
		if (status) {
			log.info("CheckedRegisteredBankAccountEvent event success, event : {}", event);
		} else {
			log.error("CheckedRegisteredBankAccountEvent event Failed, event : {}", event);
		}

		String requestFirmbankingId = UUID.randomUUID().toString();
		SagaLifecycle.associateWith("requestFirmbankingId", requestFirmbankingId);

		// 송금 요청
		// 고객 계좌 -> 법인 계좌
		commandGateway.send(new RequestFirmbankingCommand(
			requestFirmbankingId,
			event.getFirmbankingRequestAggregateIdentifier()
			, event.getRechargingRequestId()
			, event.getMembershipId()
			, event.getFromBankName()
			, event.getFromBankAccountNumber()
			, "fastcampus"
			, "123456789"
			, event.getAmount()
		)).whenComplete(
			(result, throwable) -> {
				if (throwable != null) {
					throwable.printStackTrace();
					System.out.println("RequestFirmbankingCommand Command failed");
				} else {
					System.out.println("RequestFirmbankingCommand Command success");
				}
			}
		);
	}

	@SagaEventHandler(associationProperty = "requestFirmbankingId")
	public void handle(RequestFirmbankingFinishedEvent event, IncreaseMoneyPort increaseMoneyPort) {

		log.info("RequestFirmbankingFinishedEvent saga: " + event.toString());
		boolean status = event.getStatus() == 0;
		if (status) {
			log.info("RequestFirmbankingFinishedEvent event success : status : {}", event.getStatus());
		} else {
			log.error("RequestFirmbankingFinishedEvent event Failed : status : {}", event.getStatus());
		}

		// DB Update 명령.
		MemberMoneyJpaEntity resultEntity = null;
		// increaseMoneyPort.increaseMoney(
		// 	new MemberMoney.MembershipId(event.getMembershipId())
		// 	, event.getMoneyAmount()
		// );

		if (resultEntity == null) {
			// 실패 시, 롤백 이벤트
			String rollbackFirmbankingId = UUID.randomUUID().toString();
			SagaLifecycle.associateWith("rollbackFirmbankingId", rollbackFirmbankingId);
			commandGateway.send(new com.fastcampuspay.common.event.RollbackFirmbankingRequestCommand(
				rollbackFirmbankingId
				, event.getRequestFirmbankingAggregateIdentifier()
				, event.getRechargingRequestId()
				, event.getMembershipId()
				, event.getToBankName()
				, event.getToBankAccountNumber()
				, event.getMoneyAmount()
			)).whenComplete(
				(result, throwable) -> {
					if (throwable != null) {
						throwable.printStackTrace();
						log.error("RollbackFirmbankingRequestCommand Command failed");
					} else {
						log.info("RollbackFirmbankingRequestCommand Command success , result : {}", result);
						SagaLifecycle.end();
					}
				}
			);
		} else {
			// 성공 시, saga 종료.
			SagaLifecycle.end();
		}
	}

	@EndSaga
	@SagaEventHandler(associationProperty = "rollbackFirmbankingId")
	public void handle(RollbackFirmbankingFinishedEvent event) {
		System.out.println("RollbackFirmbankingFinishedEvent saga: " + event.toString());
	}
}
