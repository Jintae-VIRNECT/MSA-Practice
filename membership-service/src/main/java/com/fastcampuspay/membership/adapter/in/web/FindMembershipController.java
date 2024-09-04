package com.fastcampuspay.membership.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampuspay.membership.application.port.in.FindMembershipCommand;
import com.fastcampuspay.membership.application.port.in.FindMembershipUserCase;
import com.fastcampuspay.membership.common.WebAdapter;
import com.fastcampuspay.membership.domain.Membership;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindMembershipController {
	private final FindMembershipUserCase findMembershipUserCase;

	@GetMapping("/membership/{membershipId}")
	public ResponseEntity<Membership> findMembershipByMemberId(@PathVariable String membershipId) {
		//request -> command
		FindMembershipCommand command = FindMembershipCommand.builder()
			.membershipId(membershipId)
			.build();

		return ResponseEntity.ok(findMembershipUserCase.findMembership(command));
	}
}
