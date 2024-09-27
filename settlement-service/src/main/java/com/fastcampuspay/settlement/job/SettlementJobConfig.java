package com.fastcampuspay.settlement.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.fastcampuspay.settlement.tasklet.SettlementTasklet;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SettlementJobConfig extends DefaultBatchConfiguration {
	private final SettlementTasklet settlementTasklet;

	@Bean
	public Job settlementJob(JobRepository jobRepository, Step settlementStep) {
		return new JobBuilder("settlementJob", jobRepository) // JobBuilder 생성
			.start(settlementStep) // 첫 번째 단계 설정
			.build();
	}

	@Bean
	public Step settlementStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
		return new StepBuilder("settlementStep", jobRepository) // StepBuilder 생성
			.tasklet(settlementTasklet, platformTransactionManager) // Tasklet과 TransactionManager 설정
			.build();
	}
}
