package com.fastcampuspay.membership.adapter.in.web;

import lombok.Data;

@Data
public class ModifyMembershipRequest {
	private String membershipId;
	private String name;
	private String email;
	private String address;
	private boolean isCorp;
	private boolean isValid;
}
