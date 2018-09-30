package com.obelov.controller;

import com.obelov.exception.BadIdException;
import com.obelov.model.Book;
import com.obelov.pagination.Page;
import com.obelov.pagination.PageCriteria;
import com.obelov.repository.BookRepository;
import io.micrometer.core.annotation.Timed;
import com.obelov.exception.BookNotFoundException;
import com.obelov.metric.CounterMetric;
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

	@Autowired
	private CounterMetric counterMetric;

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

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "book.getAll")
	public ResponseEntity<List<Book>> getAll(
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "0") int size
	) {
		Page pageResponse = bookRepository.searchBooks(new PageCriteria(page, size));

		return ResponseEntity.ok().header("X-TOTAL-COUNT", String.valueOf(pageResponse.getTotalCount()))
				.body(pageResponse.getBooks());
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

	@PostMapping(value = "", consumes = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE
	}, produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE
	})
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@Valid @RequestBody Book book) {
		bookRepository.save(book);
		counterMetric.handleCreateBook();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Integer id) {
		bookRepository.delete(id);
		counterMetric.handleDeleteBook();
	}
}
