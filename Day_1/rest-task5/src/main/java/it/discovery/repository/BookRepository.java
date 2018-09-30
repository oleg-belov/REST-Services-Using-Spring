package it.discovery.repository;

import java.util.List;

import it.discovery.model.Book;
import it.discovery.pagination.Page;
import it.discovery.pagination.PageCriteria;

public interface BookRepository {
	Book findById(int id);
	
	List<Book> findAll();
	
	void save(Book book);
	
	boolean delete(int id);

	Page searchBooks(PageCriteria pageCriteria);
}
