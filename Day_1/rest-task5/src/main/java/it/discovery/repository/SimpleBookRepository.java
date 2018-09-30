package it.discovery.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.discovery.pagination.Page;
import it.discovery.pagination.PageCriteria;
import org.springframework.stereotype.Repository;

import it.discovery.model.Book;

@Repository
public class SimpleBookRepository implements BookRepository {
	private final Map<Integer, Book> books = new HashMap<>();

	private int counter = 0;

	@Override
	public Book findById(int id) {
		return books.get(id);
	}

	@Override
	public List<Book> findAll() {
		return new ArrayList<>(books.values());
	}

	@Override
	public void save(Book book) {
		if (book.getId() == 0) {
			counter++;
			book.setId(counter);
			books.put(counter, book);
			System.out.println("*** Book with id=" + book.getId() + " was created");
		} else {
			books.put(book.getId(), book);
			System.out.println("*** Book with id=" + book.getId() + " was updated");
		}
	}

	@Override
	public boolean delete(int id) {
		if (!books.containsKey(id)) {
			return false;
		}

		books.remove(id);
		System.out.println("*** Book with id=" + id + " was deleted");
		return true;
	}

	@Override
	public Page searchBooks(PageCriteria pageCriteria) {
		return new Page(books.size(), new ArrayList<>(books.values())
				.subList(pageCriteria.getPage() * pageCriteria.getSize(),
						(pageCriteria.getPage() + 1) * pageCriteria.getSize()));
	}
}
