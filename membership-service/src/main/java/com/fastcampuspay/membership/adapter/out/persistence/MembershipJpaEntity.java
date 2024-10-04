package com.fastcampuspay.membership.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "membership")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class MembershipJpaEntity {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String email;
	private String address;
	private boolean isValid;
	private boolean isCorp;
	private String refreshToken;

	@Builder
	public MembershipJpaEntity(Long id, String name, String email, String address, boolean isValid, boolean isCorp, String refreshToken) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.address = address;
		this.isValid = isValid;
		this.isCorp = isCorp;
		this.refreshToken = refreshToken;
	}

	public static MembershipJpaEntity of(String name, String email, String address, boolean isValid, boolean isCorp, String refreshToken) {
		return MembershipJpaEntity.builder()
			.name(name)
			.email(email)
			.address(address)
			.isValid(isValid)
			.isCorp(isCorp)
			.refreshToken(refreshToken)
			.build();
	}
}
