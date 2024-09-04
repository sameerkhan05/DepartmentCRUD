package com.departmentProject.dto;


import com.departmentProject.annotations.PasswordValidation;
import com.departmentProject.annotations.PrimeNumberValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

	private Long id;

	@Size(min = 3, max = 12, message = "Title should be in range of [3:10]")
	@NotBlank(message = "Title can't be blank")
	private String title;
	@NotNull
	@PastOrPresent(message = "Future date are not accepted")
	private LocalDateTime createdAt;
	@PasswordValidation
	private String password;
	@PrimeNumberValidation
	private Integer primeNumber;
	@AssertTrue(message = "User should be active")
	@JsonProperty("isActive")
	private Boolean isActive;

}
