package it.discovery.pagination;

import it.discovery.model.Book;

import java.util.List;

public class Page {

	private final int totalCount;

	private final List<Book> books;

	public Page(int totalCount, List<Book> books) {
		this.totalCount = totalCount;
		this.books = books;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public List<Book> getBooks() {
		return books;
	}
}