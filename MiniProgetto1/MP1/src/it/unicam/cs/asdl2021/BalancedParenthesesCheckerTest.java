package it.unicam.cs.asdl2021.mp1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BalancedParenthesesCheckerTest {

	@Test
	final void checkTest() {
		BalancedParenthesesChecker b = new BalancedParenthesesChecker();
		assertTrue(b.check(" (( [( {\t (\t) [ ] } ) \n ] ) ) "));
		assertTrue(b.check(" "));
		assertTrue(b.check("\n"));
		assertTrue(b.check("\t"));
		assertTrue(b.check(" \n   \t \t \n  "));
		assertTrue(b.check("\n\n\n\n\n \t \t \t \t  "));
		assertFalse(b.check("(([ )] )" ));
		assertFalse(b.check("( { } " ));
		assertFalse(b.check("}(([]))"));
		assertThrows(IllegalArgumentException.class, ()->b.check("( ( \n [(P)] \t ))"));
	}

}
