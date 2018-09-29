package it.discovery.controller;

import it.discovery.model.Book;
import it.discovery.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@GetMapping( value = "/{id}", produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE
	})
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Book getById(@PathVariable("id") Integer id) {
		Book book = bookRepository.findById(id);

		if (book == null) {
			createNewBook(id);
			book = bookRepository.findById(id);
		}

		return book;
	}

	private void createNewBook(Integer id) {
		final Book book = new Book();

		book.setId(id);
		book.setAuthor("Robert Martin");
		book.setName("Clean code");
		book.setYear(2018);

		bookRepository.save(book);
	}
}
