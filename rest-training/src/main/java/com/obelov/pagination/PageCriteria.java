package com.obelov.pagination;

public class PageCriteria {
	private final int page;

	private final int size;

	public PageCriteria(int page, int size) {
		this.page = page;
		this.size = size;
	}

	public int getPage() {
		return page;
	}

	public int getSize() {
		return size;
	}
}