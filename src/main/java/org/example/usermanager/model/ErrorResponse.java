
package org.example.usermanager.model;

import org.springframework.http.HttpStatus;

public class ErrorResponse implements Response{

	private HttpStatus status;
	private String errorMessage;

	public ErrorResponse(){}

	public ErrorResponse(HttpStatus status, String errorMessage) {
		this.status = status;
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return String.format("{\"status\"=%s, \"errorMessage\"=%s}", this.status.toString(), this.errorMessage);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
}
