package com.departmentProject.annotations;

import com.departmentProject.exceptions.ResourceNotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrimeNumberValidator implements ConstraintValidator<PrimeNumberValidation, Integer> {

	@Override
	public boolean isValid(Integer num, ConstraintValidatorContext constraintValidatorContext) {
		if (num == null) {
			throw new ResourceNotFoundException("Null value is not Allowed");
		}
		return false;
	}

	/**
	 * Check if a given number is a prime number.
	 * num the Number to check
	 * return true if the number isPrime else false
	 */

	private boolean isPrime(int num) {
		if (num == 1) return false;
		if (num == 2) return true;
		if (num % 2 == 0) return false;
		for (int i = 3; i <= Math.sqrt(num); i += 2) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}
}
