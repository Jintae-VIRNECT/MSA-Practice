package com.fastcampuspay.membership.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampuspay.common.WebAdapter;
import com.fastcampuspay.membership.application.port.in.ModifyMembershipCommand;
import com.fastcampuspay.membership.application.port.in.ModifyMembershipUserCase;
import com.fastcampuspay.membership.domain.Membership;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class ModifyMembershipController {
	private final ModifyMembershipUserCase modifyMembershipUserCase;

	@PutMapping("/membership/modify/{membershipId}")
	public ResponseEntity<Membership> modifyMembershipByMemberId(
		@PathVariable String membershipId,
		@RequestBody ModifyMembershipRequest request
	) {
		//request -> command
		ModifyMembershipCommand command = ModifyMembershipCommand.from(request);
		return ResponseEntity.ok(modifyMembershipUserCase.modifyMembership(command));
	}
}
