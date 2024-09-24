package com.fastcampuspay.membership.adapter.in.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampuspay.common.WebAdapter;
import com.fastcampuspay.membership.application.port.in.FindMembershipCommand;
import com.fastcampuspay.membership.application.port.in.FindMembershipListByAddressCommand;
import com.fastcampuspay.membership.application.port.in.FindMembershipUserCase;
import com.fastcampuspay.membership.domain.Membership;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindMembershipController {
	private final FindMembershipUserCase findMembershipUseCase;

	@GetMapping("/membership/{membershipId}")
	public ResponseEntity<Membership> findMembershipByMemberId(@PathVariable String membershipId) {
		//request -> command
		FindMembershipCommand command = FindMembershipCommand.builder()
			.membershipId(membershipId)
			.build();

		return ResponseEntity.ok(findMembershipUseCase.findMembership(command));
	}

	@GetMapping(path = "/membership/address/{addressName}")
	ResponseEntity<List<Membership>> findMembershipListByAddress(@PathVariable String addressName) {
		log.info("findMembershipListByAddress");
		FindMembershipListByAddressCommand command = FindMembershipListByAddressCommand.builder()
			.addressName(addressName)
			.build();
		return ResponseEntity.ok(findMembershipUseCase.findMembershipListByAddress(command));
	}
}
