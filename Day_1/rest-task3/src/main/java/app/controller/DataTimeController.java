package app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("current")
public class DataTimeController {

	@GetMapping("time")
	public String getTime() {
		return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}

	@GetMapping("date")
	public String getDate() {
		return LocalDate.now().toString();
	}
}
