package com.example.todoapp.global.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
@Builder
public class CustomResponse {
	private int status;
	private String description;
	private String message;

	public static ResponseEntity<CustomResponse> toResponseEntity(StatusEnum e){
		return ResponseEntity
				.status(e.getHttpStatus())
				.body(CustomResponse.builder()
					.status(e.getHttpStatus().value())
					.description(e.name())
					.message(e.getMessage())
					.build());
	}

	public static ResponseEntity<CustomResponse> toResponseEntity(MethodArgumentNotValidException e){
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(CustomResponse.builder()
						.status(HttpStatus.BAD_REQUEST.value())
						.description("VALID_FAILED")
						.message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
						.build());
	}

	public static CustomResponse from(StatusEnum e){
		return CustomResponse.builder()
				.status(e.getHttpStatus().value())
				.description(e.name())
				.message(e.getMessage())
				.build();
	}
}
