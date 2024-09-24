package com.fastcampuspay.money.query.adapter.out.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Membership {
	private String id;
	private String name;
	private String email;
	private String address;
	private boolean isValid;
	private boolean isCorp;
	// private String timestamp;
}
