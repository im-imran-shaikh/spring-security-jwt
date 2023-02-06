package in.learnjavaskills.springsecurity.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExists extends RuntimeException 
{
	private static final long serialVersionUID = 1L;
	
	private String message;
	private HttpStatus httpStatus;
	
	public UserAlreadyExists(String message, HttpStatus httpStatus)
	{
		super(message);
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	
}
