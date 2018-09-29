package it.discovery.conf;

import it.discovery.exception.BadIdException;
import it.discovery.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {BookNotFoundException.class})
	protected ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
		final ErrorInfo response = new ErrorInfo(ex, String.valueOf(HttpStatus.NOT_FOUND.value()));

		return new ResponseEntity(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = {BadIdException.class})
	protected ResponseEntity<Object> handleBadIdException(Exception ex, WebRequest request) {
		final ErrorInfo response = new ErrorInfo(ex, String.valueOf(HttpStatus.BAD_REQUEST.value()));

		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

	private class ErrorInfo {
		public final String timestamp;
		public final String status;
		public final String error;
		public final String message;

		public ErrorInfo(Exception ex, String status) {
			this.timestamp = LocalDateTime.now().toString();
			this.status = status;
			this.error = ex.getClass().getName();
			this.message = ex.getLocalizedMessage();
		}
	}
}
