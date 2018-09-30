package com.obelov.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Book {
	private int id;

	@NotBlank
	private String author;

	@NotBlank
	@JsonProperty("title")
	@XmlElement(name = "title")
	private String name;

	@Min(value = 1900)
	private int year;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Book)) return false;
		Book book = (Book) o;
		return id == book.id &&
				year == book.year &&
				Objects.equals(author, book.author) &&
				Objects.equals(name, book.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, author, name, year);
	}

	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", author='" + author + '\'' +
				", name='" + name + '\'' +
				", year=" + year +
				'}';
	}
}
