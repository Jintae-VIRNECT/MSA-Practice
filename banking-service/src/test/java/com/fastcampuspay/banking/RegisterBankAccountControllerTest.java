package com.fastcampuspay.banking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fastcampuspay.banking.adapter.in.web.RegisterBankAccountRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterBankAccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("계좌 등록 성공")
	void shouldRegisterBankAccountSuccessfully() throws Exception {
		RegisterBankAccountRequest request = new RegisterBankAccountRequest("1", "국민은행", "1234567890", true);

		// When & Then
		mockMvc.perform(MockMvcRequestBuilders.post("/banking/account/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
