package com.obelov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obelov.RestApplication;
import com.obelov.model.Book;
import com.obelov.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(RestApplication.class)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class BookControllerUnitTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JacksonTester<Book> jacksonTester;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@MockBean
	private BookRepository bookRepository;

	@Test
	public void findById() throws Exception {
		//Given
		int bookId = 1;
		given(bookRepository.findById(bookId)).willReturn(new Book());
		//Then
		ResultActions resultActions = mockMvc.perform(get("/book/" + bookId))
				.andDo(print());
		//When
		resultActions.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}

	@Test
	public void findById_invalidId_bookNotFound() throws Exception {
		//Given
		int bookId = 1000;
		given(bookRepository.findById(bookId)).willReturn(null);
		//Then
		ResultActions resultActions = mockMvc.perform(get("/book/" + bookId))
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
}


