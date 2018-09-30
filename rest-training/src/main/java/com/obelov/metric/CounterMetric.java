package com.obelov.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CounterMetric {

	private final Counter bookDeleteCounter;
	private final Counter bookCreateCounter;

	public CounterMetric(MeterRegistry registry) {
		this.bookDeleteCounter = registry.counter("book.delete.counter");
		this.bookCreateCounter = registry.counter("book.create.counter");
	}

	public void handleDeleteBook() {
		this.bookDeleteCounter.increment();
	}

	public void handleCreateBook() {
		this.bookCreateCounter.increment();
	}

}