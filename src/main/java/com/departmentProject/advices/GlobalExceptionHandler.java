package com.departmentProject.advices;

import com.departmentProject.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<?>> handleDepartmentNotFound(ResourceNotFoundException exception){
		ApiError apiError = ApiError.builder()
				.status(HttpStatus.NOT_FOUND)
				.message(exception.getMessage())
				.build();
		return buildErrorResponseEntity(apiError);
	}
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<?>> hadelInputValidation(MethodArgumentNotValidException exception){
		List<String> errors = exception
				.getBindingResult()
				.getAllErrors()
				.stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());
		ApiError apiError = ApiError.builder()
				.status(HttpStatus.BAD_REQUEST)
				.message("Input validation error")
				.subError(errors)
				.build();
		return buildErrorResponseEntity(apiError);
	}
	private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError){
		return new ResponseEntity<>(new ApiResponse<>(apiError),apiError.getStatus());
	}
}
