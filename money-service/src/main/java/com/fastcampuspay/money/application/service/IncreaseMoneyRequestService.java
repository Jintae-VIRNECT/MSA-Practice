package com.fastcampuspay.money.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;

import com.fastcampuspay.common.CountDownLatchManager;
import com.fastcampuspay.common.RechargingMoneyTask;
import com.fastcampuspay.common.SubTask;
import com.fastcampuspay.common.UseCase;
import com.fastcampuspay.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.fastcampuspay.money.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import com.fastcampuspay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.fastcampuspay.money.adapter.out.persistence.MemberMoneyMapper;
import com.fastcampuspay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.fastcampuspay.money.application.port.in.CreateMemberMoneyCommand;
import com.fastcampuspay.money.application.port.in.CreateMemberMoneyPort;
import com.fastcampuspay.money.application.port.in.CreateMemberMoneyUseCase;
import com.fastcampuspay.money.application.port.in.FindMemberMoneyListByMembershipIdsCommand;
import com.fastcampuspay.money.application.port.in.GetMemberMoneyPort;
import com.fastcampuspay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.fastcampuspay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.fastcampuspay.money.application.port.out.GetMemberMoneyListPort;
import com.fastcampuspay.money.application.port.out.IncreaseMoneyPort;
import com.fastcampuspay.money.application.port.out.SendRechargingMoneyTaskPort;
import com.fastcampuspay.money.domain.MemberMoney;
import com.fastcampuspay.money.domain.MoneyChangingRequest;

import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@Slf4j
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase, CreateMemberMoneyUseCase {

	private final IncreaseMoneyPort increaseMoneyPort;
	private final MoneyChangingRequestMapper mapper;
	private final MemberMoneyMapper memberMoneyMapper;
	private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
	private final CountDownLatchManager countDownLatchManager;
	private final CommandGateway commandGateway;
	private final CreateMemberMoneyPort createMemberMoneyPort;
	private final GetMemberMoneyPort getMemberMoneyPort;
	private final GetMemberMoneyListPort getMemberMoneyListPort;

	@Override
	public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

		// 머니의 충전.증액이라는 과정
		// 1. 고객 정보가 정상인지 확인 (멤버)

		// 2. 고객의 연동된 계좌가 있는지, 고객의 연동된 계좌의 잔액이 충분한지도 확인 (뱅킹)

		// 3. 법인 계좌 상태도 정상인지 확인 (뱅킹)

		// 4. 증액을 위한 "기록". 요청 상태로 MoneyChangingRequest 를 생성한다. (MoneyChangingRequest)

		// 5. 펌뱅킹을 수행하고 (고객의 연동된 계좌 -> 패캠페이 법인 계좌) (뱅킹)

		// 6-1. 결과가 정상적이라면. 성공으로 MoneyChangingRequest 상태값을 변동 후에 리턴
		// 성공 시에 멤버의 MemberMoney 값 증액이 필요해요
		getMoneyChangingRequest(command);

		// 6-2. 결과가 실패라면, 실패라고 MoneyChangingRequest 상태값을 변동 후에 리턴
		return null;
	}

	@Override
	public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {
		// Count 증가.
		countDownLatchManager.addCountDownLatch("rechargingMoneyTask");
		SubTask validateMembershipIdTask = SubTask.builder()
			.subTaskName("validateMembershipId")
			.membershipID(command.getTargetMembershipId())
			.taskType("membership")
			.status("ready")
			.build();

		SubTask validateBankingTask = SubTask.builder()
			.subTaskName("validateBanking")
			.membershipID(command.getTargetMembershipId())
			.taskType("banking")
			.status("ready")
			.build();

		List<SubTask> subTaskList = new ArrayList<>();
		subTaskList.add(validateMembershipIdTask);
		subTaskList.add(validateBankingTask);

		RechargingMoneyTask task = RechargingMoneyTask.builder()
			.taskID(UUID.randomUUID().toString())
			.taskName("rechargingMoneyTask")
			.subTaskList(subTaskList)
			.moneyAmount(command.getAmount())
			.membershipID(command.getTargetMembershipId())
			.toBankName("fastcampus") // 법인 계좌 은행 이름
			.build();

		// money increase 를 위한 task 생성, Produce
		sendRechargingMoneyTaskPort.sendRechargingMoneyTask(task);

		// block, wait....
		try {
			// Task 완료 이벤트 올 때까지 기다린다.
			countDownLatchManager.getCountDownLatch("rechargingMoneyTask").await();
			String result = countDownLatchManager.getDataForKey(task.getTaskID());
			if (result.equals("success")) {
				// 제대로 수행됨,, 머니 증액.
				System.out.println("success for async Money Recharging!!");
				return getMoneyChangingRequest(command);
			} else {
				return null;
			}
		} catch (InterruptedException e) {
			// 문제 발생 시 핸들링.
			throw new RuntimeException(e);
		}
	}

	@Nullable
	private MoneyChangingRequest getMoneyChangingRequest(IncreaseMoneyRequestCommand command) {
		MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
			new MemberMoney.MembershipId(command.getTargetMembershipId())
			, command.getAmount());

		if (memberMoneyJpaEntity != null) {
			return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
					new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
					new MoneyChangingRequest.MoneyChangingType(1),
					new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
					new MoneyChangingRequest.MoneyChangingStatus(1),
					new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
				)
			);
		}

		return null;
	}

	@Override
	public void createMemberMoney(CreateMemberMoneyCommand command) {
		MemberMoneyCreatedCommand axonCommand = new MemberMoneyCreatedCommand(command.getMembershipId());
		commandGateway.send(axonCommand).whenComplete((result, throwable) -> {
			if (throwable != null) {
				System.out.println("throwable = " + throwable);
				throwable.printStackTrace();
				throw new RuntimeException(throwable);
			} else {
				System.out.println("result = " + result);
				createMemberMoneyPort.createMemberMoney(
					new MemberMoney.MembershipId(command.getMembershipId()),
					new MemberMoney.MoneyAggregateIdentifier(result.toString())
				);
			}
		});
	}

	@Override
	public void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command) {
		log.info("머니 증가 요청 시작 service");
		MemberMoneyJpaEntity memberMoneyJpaEntity = getMemberMoneyPort.getMemberMoney(
			new MemberMoney.MembershipId(command.getTargetMembershipId())
		);
		String memberMoneyAggregateIdentifier = memberMoneyJpaEntity.getAggregateIdentifier();

		// Saga 의 시작을 나타내는 커맨드!
		// RechargingMoneyRequestCreateCommand
		commandGateway.send(new RechargingMoneyRequestCreateCommand(
				memberMoneyAggregateIdentifier,
				UUID.randomUUID().toString(),
				command.getTargetMembershipId(),
				command.getAmount()
			)
		).whenComplete(
			(result, throwable) -> {
				if (throwable != null) {
					throwable.printStackTrace();
					throw new RuntimeException(throwable);
				} else {
					System.out.println("result = " + result); // aggregateIdentifier
				}
			}
		);
		// String aggregateIdentifier = memberMoneyJpaEntity.getAggregateIdentifier();
		// // command
		// commandGateway.send(IncreaseMemberMoneyCommand.builder()
		// 		.aggregateIdentifier(aggregateIdentifier)
		// 		.membershipId(command.getTargetMembershipId())
		// 		.amount(command.getAmount()).build())
		// 	.whenComplete(
		// 		(result, throwable) -> {
		// 			if (throwable != null) {
		// 				throwable.printStackTrace();
		// 				throw new RuntimeException(throwable);
		// 			} else {
		// 				// Increase money -> money incr
		// 				System.out.println("increaseMoney result = " + result);
		// 				increaseMoneyPort.increaseMoney(
		// 					new MemberMoney.MembershipId(command.getTargetMembershipId())
		// 					, command.getAmount());
		// 			}
		// 		}
		// 	);
	}

	@Override
	public List<MemberMoney> findMemberMoneyListByMembershipIds(FindMemberMoneyListByMembershipIdsCommand command) {
		// 여러개의 membership Ids 를 기준으로, memberMoney 정보를 가져와야 해요.
		List<MemberMoneyJpaEntity> memberMoneyJpaEntityList = getMemberMoneyListPort.getMemberMoneyPort(command.getMembershipIds());
		List<MemberMoney> memberMoneyList = new ArrayList<>();

		for (MemberMoneyJpaEntity memberMoneyJpaEntity : memberMoneyJpaEntityList) {
			memberMoneyList.add(memberMoneyMapper.mapToDomainEntity(memberMoneyJpaEntity));
		}

		return memberMoneyList;
	}

}
