package com.obelov.health;

import com.obelov.repository.BookRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class LibraryHealthIndicator implements HealthIndicator {
	private final BookRepository bookRepository;

	public LibraryHealthIndicator(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public Health health() {
		if (!bookRepository.isEmpty()) {
			return Health.up().build();
		}
		return Health.down().withDetail("Library status", "empty").build();
	}
}