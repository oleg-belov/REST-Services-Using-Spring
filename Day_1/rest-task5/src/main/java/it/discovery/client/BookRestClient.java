package it.discovery.client;

import it.discovery.model.Book;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class BookRestClient {
	private final RestTemplate restTemplate = new RestTemplate();
	private final String baseUrl;

	public BookRestClient(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public ResponseEntity<List<Book>> findAll() {
		return restTemplate.exchange(baseUrl, HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Book>>() {
				});
	}

	public ResponseEntity<Book> findById(int id) {
		return restTemplate.exchange(baseUrl + id, HttpMethod.GET,
				null, new ParameterizedTypeReference<Book>() {
				});
	}

	public Book save(Book book) {
		MultiValueMap<String, String> headers =
				new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		HttpEntity request = new HttpEntity<>(book, headers);
		return restTemplate.postForObject(baseUrl, request, Book.class);
	}

	public static void main(String[] args) {
		BookRestClient bookRestClient = new BookRestClient("http://localhost:8080/book/");

		creatNewBook(bookRestClient);

		ResponseEntity<List<Book>> response = bookRestClient.findAll();
		System.out.println(response);

		Book firstBook = response.getBody().get(0);
		Book createdBook = bookRestClient.findById(firstBook.getId()).getBody();

		Assert.isTrue(firstBook.equals(createdBook), "Must be same");


	}

	private static void creatNewBook(BookRestClient bookRestClient) {
		Book book = new Book();
		book.setAuthor("James Gosling");
		book.setName("Java");
		book.setYear(2000);
		bookRestClient.save(book);
	}
}
