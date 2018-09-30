package com.obelov.repository;

import java.util.List;

import com.obelov.pagination.Page;
import com.obelov.pagination.PageCriteria;
import com.obelov.model.Book;

public interface BookRepository {
	Book findById(int id);
	
	List<Book> findAll();
	
	void save(Book book);
	
	boolean delete(int id);

	Page searchBooks(PageCriteria pageCriteria);

	boolean isEmpty();
}
