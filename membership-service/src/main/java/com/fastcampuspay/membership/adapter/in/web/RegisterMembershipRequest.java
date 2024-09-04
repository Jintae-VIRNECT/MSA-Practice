package com.fastcampuspay.membership.adapter.in.web;

import lombok.Data;

@Data
public class RegisterMembershipRequest {
	private String name;
	private String email;
	private String address;
	private boolean isCorp;
}
