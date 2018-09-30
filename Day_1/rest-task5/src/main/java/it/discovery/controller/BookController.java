package it.discovery.controller;

import it.discovery.exception.BadIdException;
import it.discovery.exception.BookNotFoundException;
import it.discovery.model.Book;
import it.discovery.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@GetMapping(value = "/{id}", produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE
	})
	public ResponseEntity<Book> getById(@PathVariable("id") Integer id) {
		if (id <= 0) {
			throw new BadIdException(String.format("Book id {%d} must be greater than 0", id));
		}

		final Book book = bookRepository.findById(id);

		if (book == null) {
			throw new BookNotFoundException(String.format("Book with id {%d} is not found", id));
		}

		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Book> getAll() {
		List<Book> books = bookRepository.findAll();

		return books;
	}

	@PutMapping(value = "/{id}", consumes = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE
	}, produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE
	})
	public ResponseEntity<Book> update(@PathVariable("id") Integer id, @RequestBody Book newBook) {
		Book book = bookRepository.findById(id);

		if (book == null) {
			throw new BookNotFoundException(String.format("Book with id %d is not found", id));
		} else {
			newBook.setId(id);
			bookRepository.save(newBook);
			newBook = bookRepository.findById(id);
		}

		return new ResponseEntity<>(newBook, HttpStatus.OK);
	}

	@PostMapping(value = "/", consumes = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE
	}, produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE
	})
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@Valid @RequestBody Book book) {
		bookRepository.save(book);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Integer id) {
		bookRepository.delete(id);
	}
}
