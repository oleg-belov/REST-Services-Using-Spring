package com.obelov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obelov.RestApplication;
import com.obelov.model.Book;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
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
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.mockMvc;
import org.springframework.http.HttpStatus;

@SpringJUnitWebConfig(RestApplication.class)
@AutoConfigureMockMvc
public class BookControllerTest {
	@Autowired
	private MockMvc mockMvc;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@BeforeEach
	public void setup() {
		RestAssuredMockMvc.mockMvc(mockMvc);
	}

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
		Book book = new Book();
		book.setName("Java");
		book.setYear(2010);

		given().contentType(ContentType.JSON).body(book)
				.when().post("/book")
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

}


