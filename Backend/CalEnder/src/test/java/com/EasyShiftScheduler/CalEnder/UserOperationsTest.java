package com.EasyShiftScheduler.CalEnder.Entities;

import com.EasyShiftScheduler.CalEnder.Helpers.UserOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.junit.jupiter.api.Assertions.*;

public class UserOperationsTest {
	private UserOperations userOperations;

	@BeforeTestClass
	public void setUp() {
		userOperations = new UserOperations();
	}

	// Valid password tests
	@Test
	public void testValidPassword() {
		assertTrue(userOperations.checkPasswordStrength("Password1!"));
	}

	@Test
	public void testValidPasswordComplex() {
		assertTrue(userOperations.checkPasswordStrength("MyP@ssw0rd"));
	}

	@Test
	public void testValidPasswordMinimumLength() {
		assertTrue(userOperations.checkPasswordStrength("Apass1!a"));
	}

	@Test
	public void testValidPasswordLongPassword() {
		assertTrue(userOperations.checkPasswordStrength("VeryLongPassword123!@#WithAllRequirements"));
	}

	// Password length tests
	@Test
	public void testPasswordTooShort() {
		assertFalse(userOperations.checkPasswordStrength("Pass1!a"));
	}

	@Test
	public void testEmptyPassword() {
		assertFalse(userOperations.checkPasswordStrength(""));
	}

	@Test
	public void testSingleCharacter() {
		assertFalse(userOperations.checkPasswordStrength("a"));
	}

	// Missing lowercase letter tests
	@Test
	public void testMissingLowercase() {
		assertFalse(userOperations.checkPasswordStrength("PASSWORD123!"));
	}

	@Test
	public void testMissingLowercaseEightChars() {
		assertFalse(userOperations.checkPasswordStrength("PASS1!AB"));
	}

	// Missing uppercase letter tests
	@Test
	public void testMissingUppercase() {
		assertFalse(userOperations.checkPasswordStrength("password123!"));
	}

	@Test
	public void testMissingUppercaseEightChars() {
		assertFalse(userOperations.checkPasswordStrength("pass1!ab"));
	}

	// Missing number tests
	@Test
	public void testMissingNumber() {
		assertFalse(userOperations.checkPasswordStrength("Password!@"));
	}

	@Test
	public void testMissingNumberEightChars() {
		assertFalse(userOperations.checkPasswordStrength("Pass!@ab"));
	}

	// Missing special character tests
	@Test
	public void testMissingSpecialCharacter() {
		assertFalse(userOperations.checkPasswordStrength("Password123"));
	}

	@Test
	public void testMissingSpecialCharEightChars() {
		assertFalse(userOperations.checkPasswordStrength("Pass1aab"));
	}

	// Multiple missing requirements tests
	@Test
	public void testMissingUppercaseAndNumber() {
		assertFalse(userOperations.checkPasswordStrength("password!@"));
	}

	@Test
	public void testMissingAllSpecialCharAndNumber() {
		assertFalse(userOperations.checkPasswordStrength("Passwordab"));
	}

	@Test
	public void testMissingLowercaseAndSpecialChar() {
		assertFalse(userOperations.checkPasswordStrength("PASSWORD123"));
	}

	// Edge case with allowed special characters
	@Test
	public void testWithDash() {
		assertTrue(userOperations.checkPasswordStrength("Pass-word1A"));
	}

	@Test
	public void testWithPlus() {
		assertTrue(userOperations.checkPasswordStrength("Pass+word1A"));
	}

	@Test
	public void testWithParenthesis() {
		assertTrue(userOperations.checkPasswordStrength("Pass(word1A"));
	}

	// Password with only special characters as the "special" requirement
	@Test
	public void testSpecialCharAtEnd() {
		assertTrue(userOperations.checkPasswordStrength("Abcdef1!"));
	}

	@Test
	public void testSpecialCharAtStart() {
		assertTrue(userOperations.checkPasswordStrength("!Abcdef1"));
	}

	@Test
	public void testSpecialCharInMiddle() {
		assertTrue(userOperations.checkPasswordStrength("Abc!def1"));
	}

	// Password with spaces (should fail as space is not in allowed special chars)
	@Test
	public void testPasswordWithSpace() {
		assertFalse(userOperations.checkPasswordStrength("Pass word1!"));
	}

	// All special characters from the allowed set
	@Test
	public void testAllSpecialCharacters() {
		assertTrue(userOperations.checkPasswordStrength("Pass!word1"));
		assertTrue(userOperations.checkPasswordStrength("Pass@word1"));
		assertTrue(userOperations.checkPasswordStrength("Pass#word1"));
		assertTrue(userOperations.checkPasswordStrength("Pass$word1"));
		assertTrue(userOperations.checkPasswordStrength("Pass%word1"));
		assertTrue(userOperations.checkPasswordStrength("Pass^word1"));
		assertTrue(userOperations.checkPasswordStrength("Pass&word1"));
		assertTrue(userOperations.checkPasswordStrength("Pass*word1"));
	}
}
