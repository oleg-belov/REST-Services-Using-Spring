package com.obelov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obelov.RestApplication;
import com.obelov.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(RestApplication.class)
@AutoConfigureMockMvc
public class BookControllerTest {
	@Autowired
	private MockMvc mockMvc;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Test
	public void findById_invalidId_bookNotFound() throws Exception {
		//Given
		//Then
		ResultActions resultActions = mockMvc.perform(get("/book/1000"))
				.andDo(print());
		//When
		resultActions.andExpect(status().isNotFound());
	}

	@Test
	public void findById_nonNumericId_returnBadRequest() throws Exception {
		//Given
		//Then
		ResultActions resultActions = mockMvc.perform(get("/book/aaa"))
				.andDo(print());
		//When
		resultActions.andExpect(status().isBadRequest());
	}

	@Test
	public void save_bookAuthorEmpty_returnBadRequest() throws Exception {
		//Given
		Book book = new Book();
		book.setName("Java");
		book.setYear(2010);
		//Then
		ResultActions resultActions = mockMvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(MAPPER.writeValueAsString(book)))
				.andDo(print());
		//When
		resultActions.andExpect(status().isBadRequest());
	}

}


