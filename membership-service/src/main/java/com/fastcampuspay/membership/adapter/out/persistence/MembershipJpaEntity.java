package com.fastcampuspay.membership.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "membership")
@Entity
@Getter
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

	@Builder
	public MembershipJpaEntity(Long id, String name, String email, String address, boolean isValid, boolean isCorp) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.address = address;
		this.isValid = isValid;
		this.isCorp = isCorp;
	}

	public static MembershipJpaEntity of(String name, String email, String address, boolean isValid, boolean isCorp) {
		return MembershipJpaEntity.builder()
			.name(name)
			.email(email)
			.address(address)
			.isValid(isValid)
			.isCorp(isCorp)
			.build();
	}
}
