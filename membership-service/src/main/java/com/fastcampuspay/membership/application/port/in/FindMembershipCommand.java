package com.fastcampuspay.membership.application.port.in;

import com.fastcampuspay.membership.common.SelfValidating;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class FindMembershipCommand extends SelfValidating<FindMembershipCommand> {

	private final String membershipId;

}
