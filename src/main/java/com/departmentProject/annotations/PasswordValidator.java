package com.departmentProject.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValidation, String> {
	@Override
	public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
		if (password == null) {
			return false;
		} else {
			boolean check = isValidPassword(password);
			return check;
		}
	}

	/**
	 * checking for length
	 * if greater than 10 will return false
	 * if password is empty return false
	 * if null will return false
	 */

	private boolean isValidPassword(String password) {
		boolean hasUpperCase = false;
		boolean hasLowerCase = false;
		boolean hasSpecial = false;
		String specialChars = "!@#$%^&*()-+";

		if (password.length() < 10 || password.isEmpty() || password == null) {
			return false;
		}
		for (char c : password.toCharArray()) {
			if (Character.isUpperCase(c)) {
				hasUpperCase = true;
			} else if (Character.isLowerCase(c)) {
				hasLowerCase = true;
			} else if (specialChars.indexOf(c) >= 0) {
				hasSpecial = true;
			}
			if (hasSpecial && hasLowerCase && hasUpperCase) {
				return true;
			}
		}
		return hasLowerCase && hasSpecial && hasUpperCase;
	}
}
