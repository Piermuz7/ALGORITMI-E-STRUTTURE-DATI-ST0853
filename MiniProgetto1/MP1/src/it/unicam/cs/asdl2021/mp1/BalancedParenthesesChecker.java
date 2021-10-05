package it.unicam.cs.asdl2021.mp1TeseiTest;

/**
 * An object of this class is an actor that uses an ASDL2021Deque<Character> as
 * a Stack in order to check that a sequence containing the following
 * characters: '(', ')', '[', ']', '{', '}' in any order is a string of balanced
 * parentheses or not. The input is given as a String in which white spaces,
 * tabs and newlines are ignored.
 * 
 * Some examples:
 * 
 * - " (( [( {\t (\t) [ ] } ) \n ] ) ) " is a string o balanced parentheses - "
 * " is a string of balanced parentheses - "(([)])" is NOT a string of balanced
 * parentheses - "( { } " is NOT a string of balanced parentheses - "}(([]))" is
 * NOT a string of balanced parentheses - "( ( \n [(P)] \t ))" is NOT a string
 * of balanced parentheses
 * 
 * @author Template: Luca Tesei, Implementation: Piermichele Rosati -
 *         piermichele.rosati@studenti.unicam.it
 *
 */
public class BalancedParenthesesChecker {

	// The stack is to be used to check the balanced parentheses
	private ASDL2021Deque<Character> stack;
	// Caratteri accettati nella stringa di cui controllarne il bilanciamento
	final static Character[] caratteriAccettati = { '(', ')', '[', ']', '{', '}', ' ', '\t', '\n' };

	/**
	 * Crea un nuovo checker.
	 */
	public BalancedParenthesesChecker() {
		this.stack = new ASDL2021Deque<Character>();
	}

	/**
	 * Check if a given string contains a balanced parentheses sequence of
	 * characters '(', ')', '[', ']', '{', '}' by ignoring white spaces ' ', tabs
	 * '\t' and newlines '\n'.
	 * 
	 * @param s the string to check
	 * @return true if s contains a balanced parentheses sequence, false otherwise
	 * @throws IllegalArgumentException if s contains at least a character different
	 *                                  form:'(', ')', '[', ']', '{', '}', white
	 *                                  space ' ', tab '\t' and newline '\n'
	 */
	public boolean check(String s) {
		// NOTE: the stack must be cleared first every time
		// this method is called. Then it has to be used to perform the check.

		// Pulizia dello stack
		this.stack.clear();
		// Se la stringa passata contiene caratteri diversi da (', ')', '[', ']', '{',
		// '}',
		// spazio ' ', tab '\t' e newline '\n' lancio l' eccezione
		if (this.containsDifferentCharacters(s))
			throw new IllegalArgumentException(
					"ERRORE: la stringa passata contiene caratteri diversi da quelli specificati!");

		int i = 0;
		while (i < s.length()) {
			// Se è una parentesi aperta (, [ o {, la aggiungo nello stack
			if ((s.charAt(i) == caratteriAccettati[0]) || (s.charAt(i) == caratteriAccettati[2])
					|| (s.charAt(i) == caratteriAccettati[4]))
				this.stack.add(s.charAt(i));
			// Altrimenti controllo se è una parentesi chiusa ), ] o }
			else {
				// Se lo stack è vuoto e inserisco una parentesi chiusa la stringa non è
				// bilanciata
				switch (s.charAt(i)) {
				// Se l'ultima parentesi aperta non è dello stesso tipo di quella chiusa, la
				// stringa non è bilanciata, altrimenti la rimuovo dallo stack
				case ')': {
					if (this.stack.peekLast() == caratteriAccettati[0])
						this.stack.pollLast();
					else
						return false;
					break;
				}
				case ']': {
					if (this.stack.peekLast() == caratteriAccettati[2])
						this.stack.pollLast();
					else
						return false;
					break;
				}
				case '}': {
					if (this.stack.peekLast() == caratteriAccettati[4])
						this.stack.pollLast();
					else
						return false;
					break;
				}
				// Se scorro un carattere tra ' ', '\t' o '\n', lo ignoro e scorro la stringa
				case ' ':
					break;
				case '\n':
					break;
				case '\t':
					break;
				}
			}
			i++;

		}

		// Se ho rimosso tutte le parentesi, allora la stringa passata è bilanciata,
		// altrimenti no
		if (this.stack.isEmpty())
			return true;
		return false;
	}

	/**
	 * Controlla se la stringa passata contiene caratteri diversi da quelli
	 * specificati in caratteriAccettati.
	 * 
	 * @param s la stringa da controllare.
	 * 
	 * @return true se contiene caratteri diversi da quelli specificati, false
	 *         altrimenti.
	 * 
	 */
	private boolean containsDifferentCharacters(String s) {
		int i = 0, j = 0;
		boolean flag = false;
		// Scorro la stringa specificata
		while ((i < s.length())) {
			flag = false;
			j = 0;
			// Scorro i caratteri accettati
			// se non ne trovo neanche uno di questi nella stringa ritorno false
			while ((j < caratteriAccettati.length) && (flag == false)) {
				if (s.toCharArray()[i] == caratteriAccettati[j])
					flag = true;
				j++;
			}
			if (!flag)
				return true;
			i++;
		}
		// Altrimenti la stringa viene accettata
		return false;
	}

}
