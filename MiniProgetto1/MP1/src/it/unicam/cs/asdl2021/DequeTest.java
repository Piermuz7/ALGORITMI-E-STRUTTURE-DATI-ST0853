package it.unicam.cs.asdl2021.mp1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

class DequeTest {

	@Test
	final void isEmptyTest() {
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Persona p4 = new Persona("Michele", "Rosati", 20);
		ASDL2021Deque<Persona> d = new ASDL2021Deque<>();
		assertTrue(d.isEmpty());
		assertTrue(d.add(p1));
		assertTrue(d.add(p2));
		assertTrue(d.add(p3));
		assertTrue(d.add(p4));
		assertThrows(NullPointerException.class, () -> d.add(null));
		assertFalse(d.isEmpty());
		assertTrue(d.size() == 4);
	}

	@Test
	final void toArrayTest() {
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Persona p4 = new Persona("Michele", "Rosati", 20);
		Persona[] expectedArray = { p1, p2, p3, p4 };
		ASDL2021Deque<Persona> d = new ASDL2021Deque<>();
		assertTrue(d.isEmpty());
		Persona[] p = new Persona[0];
		assertArrayEquals(p, d.toArray());
		assertTrue(d.add(p1));
		assertTrue(d.add(p2));
		assertTrue(d.add(p3));
		assertTrue(d.add(p4));
		assertArrayEquals(expectedArray, d.toArray());
	}

	@Test
	final void containsAllTest() {
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Persona p4 = new Persona("Michele", "Rosati", 20);
		Collection<String> stringhe = new LinkedList<String>();
		assertTrue(stringhe.isEmpty());
		assertTrue(stringhe.add("ciao"));
		assertTrue(stringhe.add("bella"));
		Collection<Persona> persone = new LinkedList<Persona>();
		assertTrue(persone.isEmpty());
		assertTrue(persone.add(p1));
		assertTrue(persone.add(p2));
		assertTrue(persone.add(p3));
		assertTrue(persone.add(p4));
		assertTrue(persone.add(null));
		assertTrue(persone.size() == 5);
		assertFalse(persone.isEmpty());
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(studenti.add(p1));
		assertTrue(studenti.add(p2));
		assertTrue(studenti.add(p3));
		ASDL2021Deque<Persona> d = new ASDL2021Deque<>();
		// Se la deque è vuota mi deve ritornare false
		assertFalse(d.containsAll(stringhe));
		assertThrows(NullPointerException.class, () -> d.containsAll(null));
		// assertTrue(persone.remove(null));
		assertTrue(d.isEmpty());
		// Se la deque è vuota mi deve ritornare false
		assertFalse(d.containsAll(persone));
		assertTrue(d.add(p1));
		assertTrue(d.add(p2));
		assertTrue(d.add(p3));
		assertTrue(d.add(p4));
		assertThrows(NullPointerException.class, () -> d.containsAll(persone));
		persone.remove(null);
		// Se la deque non è vuota deve ritornare false sulla collection di String
		assertFalse(d.isEmpty());
		assertFalse(d.containsAll(stringhe));
		// e deve ritornare true sulla collection di Persona
		assertTrue(d.containsAll(persone));
		// e deve ritornare true sulla collection di Studente
		assertTrue(d.containsAll(studenti));
	}

	@Test
	final void addAllTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Persona p4 = new Persona("Michele", "Rosati", 20);
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		studenti.add(null);
		studenti.add(p0);
		ASDL2021Deque<Persona> d = new ASDL2021Deque<>();
		d.add(p4);
		assertFalse(d.isEmpty());
		assertThrows(NullPointerException.class, () -> d.containsAll(null));
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertThrows(NullPointerException.class, () -> d.containsAll(studenti));
		studenti.remove(null);
		assertTrue(d.addAll(studenti));
	}

	@Test
	final void clearTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Persona p4 = null;
		Collection<Studente> studenti = new LinkedList<Studente>();
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		ASDL2021Deque<Persona> d = new ASDL2021Deque<>();
		assertTrue(d.isEmpty());
		d.clear();
		assertTrue(d.isEmpty());
		assertTrue(d.addAll(studenti));
		d.clear();
		assertTrue(d.isEmpty());
	}

	@Test
	final void addFirstTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertThrows(NullPointerException.class, () -> d.addFirst(p4));
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.peek() == p1);
		// Inserisco una nuova testa
		d.addFirst(p0);
		assertFalse(d.peek() == p1);
		assertTrue(d.peek() == p0);
		assertTrue(d.size() == 4);
		// Levo la testa
		d.pop();
		assertTrue(d.peek() == p1);
		assertTrue(d.size() == 3);
		// Levo tutti gli elementi estrando ogni volta la testa
		d.pop();
		assertTrue(d.size() == 2);
		assertTrue(d.peek() == p2);
		d.pop();
		assertTrue(d.size() == 1);
		assertTrue(d.peek() == p3);
		d.pop();
		assertTrue(d.isEmpty());
		d.addFirst(p3);
		assertTrue(d.size() == 1);
		assertTrue(d.peek() == p3);
		assertTrue(d.peekLast() == p3);
		d.addFirst(p2);
		assertTrue(d.size() == 2);
		assertTrue(d.peek() == p2);
		assertTrue(d.peekLast() == p3);
		d.addFirst(p1);
		assertTrue(d.size() == 3);
		assertTrue(d.peek() == p1);
		assertTrue(d.peekLast() == p3);
		d.addFirst(p0);
		assertTrue(d.size() == 4);
		assertTrue(d.peek() == p0);
		assertTrue(d.peekLast() == p3);
		d.clear();
		assertTrue(d.size() == 0);
		assertTrue(d.isEmpty());
	}

	@Test
	final void addLastTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.peekLast() == p3);
		// Inserisco una nuova coda
		d.addLast(p0);
		assertFalse(d.peekLast() == p3);
		assertTrue(d.peekLast() == p0);
		// Levo la coda
		d.pollLast();
		assertTrue(d.peekLast() == p3);
	}

	@Test
	final void offerFirstTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		studenti.add(p1);
		studenti.add(p0);
		studenti.add(p2);
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.peek() == p1);
		assertTrue(d.offerFirst(p3));
		assertThrows(NullPointerException.class, () -> d.offerFirst(p4));
		assertFalse(d.peek() == p1);
		assertTrue(d.peek() == p3);
		// rimuovo la testa
		assertTrue(d.poll() == p3);
		assertTrue(d.offerFirst(p2));
		assertTrue(d.peek() == p2);
	}

	@Test
	final void offerLastTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		studenti.add(p3);
		studenti.add(p1);
		studenti.add(p2);
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.peekLast() == p2);
		assertTrue(d.offerLast(p0));
		assertFalse(d.peekLast() == p2);
		assertTrue(d.peekLast() == p0);
		assertThrows(NullPointerException.class, () -> d.offerLast(p4));
	}

	@Test
	final void removeFirstTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		assertThrows(NoSuchElementException.class, () -> d.removeFirst());
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.removeFirst() == p0);
		assertFalse(d.peek() == p0);
		assertTrue(d.removeFirst() == p1);
		assertFalse(d.peek() == p1);
		assertTrue(d.removeFirst() == p2);
		assertFalse(d.peek() == p2);
		assertTrue(d.removeFirst() == p3);
		assertFalse(d.peek() == p3);
		assertTrue(d.isEmpty());
		assertThrows(NoSuchElementException.class, () -> d.removeFirst());
	}

	@Test
	final void removeLastTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		assertThrows(NoSuchElementException.class, () -> d.removeFirst());
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.removeLast() == p3);
		assertFalse(d.peekLast() == p3);
		assertTrue(d.removeLast() == p2);
		assertFalse(d.peekLast() == p2);
		assertTrue(d.removeLast() == p1);
		assertFalse(d.peekLast() == p1);
		assertTrue(d.removeLast() == p0);
		assertFalse(d.peekLast() == p0);
		assertTrue(d.isEmpty());
		assertThrows(NoSuchElementException.class, () -> d.removeLast());
	}

	@Test
	final void pollFirstTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		assertThrows(NoSuchElementException.class, () -> d.removeFirst());
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.pollFirst() == p0);
		assertFalse(d.peek() == p0);
		assertTrue(d.pollFirst() == p1);
		assertFalse(d.peek() == p1);
		assertTrue(d.pollFirst() == p2);
		assertFalse(d.peek() == p2);
		assertTrue(d.pollFirst() == p3);
		assertFalse(d.peek() == p3);
		assertTrue(d.isEmpty());
		assertTrue(d.pollFirst() == null);
	}

	@Test
	final void pollLastTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		assertThrows(NoSuchElementException.class, () -> d.removeFirst());
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.peekLast() == p3);
		assertTrue(d.pollLast() == p3);
		assertFalse(d.peekLast() == p3);
		assertTrue(d.pollLast() == p2);
		assertFalse(d.peekLast() == p2);
		assertTrue(d.pollLast() == p1);
		assertFalse(d.peekLast() == p1);
		assertTrue(d.pollLast() == p0);
		assertFalse(d.peekLast() == p0);
		assertTrue(d.isEmpty());
		assertTrue(d.pollLast() == null);
	}

	@Test
	final void getFirstTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		assertThrows(NoSuchElementException.class, () -> d.getFirst());
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.getFirst() == p0);
		assertTrue(d.removeFirst() == p0);
		assertFalse(d.getFirst() == p0);
		assertTrue(d.getFirst() == p1);
		assertTrue(d.removeFirst() == p1);
		assertFalse(d.getFirst() == p1);
		assertTrue(d.getFirst() == p2);
		assertTrue(d.removeFirst() == p2);
		assertFalse(d.getFirst() == p2);
		assertTrue(d.getFirst() == p3);
		assertTrue(d.removeFirst() == p3);
		assertThrows(NoSuchElementException.class, () -> d.getFirst());
		assertTrue(d.isEmpty());
	}

	@Test
	final void getLastTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		assertThrows(NoSuchElementException.class, () -> d.getLast());
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.getLast() == p3);
		assertTrue(d.removeLast() == p3);
		assertFalse(d.getLast() == p3);
		assertTrue(d.getLast() == p2);
		assertTrue(d.removeLast() == p2);
		assertFalse(d.getLast() == p2);
		assertTrue(d.getLast() == p1);
		assertTrue(d.removeLast() == p1);
		assertFalse(d.getLast() == p1);
		assertTrue(d.getLast() == p0);
		assertTrue(d.removeLast() == p0);
		assertThrows(NoSuchElementException.class, () -> d.getLast());
		assertTrue(d.isEmpty());
	}

	@Test
	final void peekFirstTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		assertTrue(d.peekFirst() == null);
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.peekFirst() == p0);
		assertTrue(d.pollFirst() == p0);
		assertFalse(d.peekFirst() == p0);
		assertTrue(d.peekFirst() == p1);
		assertTrue(d.pollFirst() == p1);
		assertFalse(d.peekFirst() == p1);
		assertTrue(d.peekFirst() == p2);
		assertTrue(d.pollFirst() == p2);
		assertFalse(d.peekFirst() == p2);
		assertTrue(d.peekFirst() == p3);
		assertTrue(d.pollFirst() == p3);
		assertFalse(d.peekFirst() == p3);
		assertTrue(d.isEmpty());
		assertTrue(d.peekFirst() == null);
	}

	@Test
	final void peekLastTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		assertTrue(d.peekLast() == null);
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.peekLast() == p3);
		assertTrue(d.pollLast() == p3);
		assertFalse(d.peekLast() == p3);
		assertTrue(d.peekLast() == p2);
		assertTrue(d.pollLast() == p2);
		assertFalse(d.peekLast() == p2);
		assertTrue(d.peekLast() == p1);
		assertTrue(d.pollLast() == p1);
		assertFalse(d.peekLast() == p1);
		assertTrue(d.peekLast() == p0);
		assertTrue(d.pollLast() == p0);
		assertFalse(d.peekLast() == p0);
		assertTrue(d.isEmpty());
		assertTrue(d.peekLast() == null);
	}

	@Test
	final void addTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		assertThrows(NullPointerException.class, () -> d.add(p4));
		assertTrue(d.add(p0));
		assertTrue(d.peek() == p0);
		assertTrue(d.peekLast() == p0);
		assertTrue(d.add(p1));
		assertTrue(d.peek() == p0);
		assertTrue(d.peekLast() == p1);
		assertTrue(d.add(p2));
		assertTrue(d.peek() == p0);
		assertTrue(d.peekLast() == p2);
		assertTrue(d.add(p3));
		assertTrue(d.peek() == p0);
		assertTrue(d.peekLast() == p3);
		assertTrue(d.size() == 4);
		assertTrue(d.removeFirst() == p0);
		assertTrue(d.size() == 3);
		assertTrue(d.add(p0));
		assertTrue(d.peek() == p1);
		assertTrue(d.peekLast() == p0);
		assertFalse(d.isEmpty());
	}

	@Test
	final void offerTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		assertThrows(NullPointerException.class, () -> d.offer(p4));
		assertTrue(d.offer(p0));
		assertTrue(d.peek() == p0);
		assertTrue(d.peekLast() == p0);
		assertTrue(d.offer(p1));
		assertTrue(d.peek() == p0);
		assertTrue(d.peekLast() == p1);
		assertTrue(d.offer(p2));
		assertTrue(d.peek() == p0);
		assertTrue(d.peekLast() == p2);
		assertTrue(d.offer(p3));
		assertTrue(d.peek() == p0);
		assertTrue(d.peekLast() == p3);
		assertTrue(d.size() == 4);
		assertTrue(d.removeFirst() == p0);
		assertTrue(d.size() == 3);
		assertTrue(d.offer(p0));
		assertTrue(d.peek() == p1);
		assertTrue(d.peekLast() == p0);
		assertFalse(d.isEmpty());
	}

	@Test
	final void removeTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		assertThrows(NoSuchElementException.class, () -> d.remove());
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.remove() == p0);
		assertFalse(d.peek() == p0);
		assertTrue(d.remove() == p1);
		assertFalse(d.peek() == p1);
		assertTrue(d.remove() == p2);
		assertFalse(d.peek() == p2);
		assertTrue(d.remove() == p3);
		assertFalse(d.peek() == p3);
		assertTrue(d.isEmpty());
		assertThrows(NoSuchElementException.class, () -> d.remove());
	}

	@Test
	final void pollTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		assertThrows(NoSuchElementException.class, () -> d.removeFirst());
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.poll() == p0);
		assertFalse(d.peek() == p0);
		assertTrue(d.poll() == p1);
		assertFalse(d.peek() == p1);
		assertTrue(d.poll() == p2);
		assertFalse(d.peek() == p2);
		assertTrue(d.poll() == p3);
		assertFalse(d.peek() == p3);
		assertTrue(d.isEmpty());
		assertTrue(d.poll() == null);
	}

	@Test
	final void elementTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		assertThrows(NoSuchElementException.class, () -> d.element());
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.element() == p0);
		assertTrue(d.removeFirst() == p0);
		assertFalse(d.element() == p0);
		assertTrue(d.element() == p1);
		assertTrue(d.removeFirst() == p1);
		assertFalse(d.element() == p1);
		assertTrue(d.element() == p2);
		assertTrue(d.removeFirst() == p2);
		assertFalse(d.element() == p2);
		assertTrue(d.element() == p3);
		assertTrue(d.removeFirst() == p3);
		assertThrows(NoSuchElementException.class, () -> d.element());
		assertTrue(d.isEmpty());
	}

	@Test
	final void peekTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		assertTrue(d.peek() == null);
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.peek() == p0);
		assertTrue(d.pollFirst() == p0);
		assertFalse(d.peek() == p0);
		assertTrue(d.peek() == p1);
		assertTrue(d.pollFirst() == p1);
		assertFalse(d.peek() == p1);
		assertTrue(d.peek() == p2);
		assertTrue(d.pollFirst() == p2);
		assertFalse(d.peek() == p2);
		assertTrue(d.peek() == p3);
		assertTrue(d.pollFirst() == p3);
		assertFalse(d.peek() == p3);
		assertTrue(d.isEmpty());
		assertTrue(d.peek() == null);
	}

	@Test
	final void pushTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertThrows(NullPointerException.class, () -> d.addFirst(p4));
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.peek() == p1);
		// Inserisco una nuova testa
		d.push(p0);
		assertFalse(d.peek() == p1);
		assertTrue(d.peek() == p0);
		assertTrue(d.size() == 4);
		// Levo la testa
		d.pop();
		assertTrue(d.peek() == p1);
		assertTrue(d.size() == 3);
		// Levo tutti gli elementi estrando ogni volta la testa
		d.pop();
		assertTrue(d.size() == 2);
		assertTrue(d.peek() == p2);
		d.pop();
		assertTrue(d.size() == 1);
		assertTrue(d.peek() == p3);
		d.pop();
		assertTrue(d.isEmpty());
		d.push(p3);
		assertTrue(d.size() == 1);
		assertTrue(d.peek() == p3);
		assertTrue(d.peekLast() == p3);
		d.push(p2);
		assertTrue(d.size() == 2);
		assertTrue(d.peek() == p2);
		assertTrue(d.peekLast() == p3);
		d.push(p1);
		assertTrue(d.size() == 3);
		assertTrue(d.peek() == p1);
		assertTrue(d.peekLast() == p3);
		d.push(p0);
		assertTrue(d.size() == 4);
		assertTrue(d.peek() == p0);
		assertTrue(d.peekLast() == p3);
		d.clear();
		assertTrue(d.size() == 0);
		assertTrue(d.isEmpty());
	}

	@Test
	final void popTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		assertThrows(NoSuchElementException.class, () -> d.pop());
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertFalse(d.isEmpty());
		assertTrue(d.pop() == p0);
		assertFalse(d.peek() == p0);
		assertTrue(d.pop() == p1);
		assertFalse(d.peek() == p1);
		assertTrue(d.pop() == p2);
		assertFalse(d.peek() == p2);
		assertTrue(d.pop() == p3);
		assertFalse(d.peek() == p3);
		assertTrue(d.isEmpty());
		assertThrows(NoSuchElementException.class, () -> d.pop());
	}

	@Test
	final void removeObjectTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = p0;
		Studente p5 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p6 = new Studente("Pinco", "Pallino", 64, "Unimi", "Matematica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		assertThrows(NoSuchElementException.class, () -> d.remove(p0));
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		studenti.add(p5);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertThrows(NullPointerException.class, () -> d.remove(p4));
		assertFalse(d.isEmpty());
		assertTrue(d.size() == 5);
		assertTrue(d.remove(p0));
		assertTrue(d.size() == 4);
		assertFalse(d.peek() == p0);
		assertTrue(d.contains(p3));
		// p6 non è presente, quindi deve ritornare false
		assertFalse(d.remove(p6));
		assertTrue(d.peek() == p1);
		assertTrue(d.size() == 4);
		assertTrue(d.remove(p3));
		assertTrue(d.peek() == p1);
		assertTrue(d.peekLast() == p5);
		assertTrue(d.size() == 3);
		d.clear();
		assertThrows(NoSuchElementException.class, () -> d.remove(p5));
	}

	@Test
	final void containsTest() {
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = p0;
		Studente p5 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p6 = new Studente("Pinco", "Pallino", 64, "Unimi", "Matematica");
		Studente p4 = null;
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		assertFalse(d.contains(p1));
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		studenti.add(p5);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertThrows(NullPointerException.class, () -> d.contains(p4));
		assertFalse(d.isEmpty());
		assertTrue(d.size() == 5);
		assertFalse(d.contains(p6));
		assertTrue(d.contains(p0));
		assertTrue(d.remove(p0));
		assertTrue(d.size() == 4);
		// p3 = p0, quindi deve ritornare true
		assertTrue(d.contains(p0));
		assertTrue(d.contains(p1));
		assertTrue(d.remove(p1));
		assertTrue(d.size() == 3);
		assertFalse(d.contains(p1));
		assertTrue(d.contains(p2));
		assertTrue(d.remove(p2));
		assertTrue(d.size() == 2);
		assertFalse(d.contains(p2));
		assertTrue(d.contains(p3));
		assertTrue(d.remove(p3));
		assertTrue(d.size() == 1);
		assertFalse(d.contains(p3));
		assertTrue(d.contains(p5));
		assertTrue(d.remove(p5));
		assertTrue(d.size() == 0);
		assertFalse(d.contains(p0));
		assertFalse(d.contains(p1));
		assertFalse(d.contains(p2));
		assertFalse(d.contains(p3));
		assertThrows(NullPointerException.class, () -> d.contains(p4));
		assertFalse(d.contains(p5));
		assertFalse(d.contains(p6));
		assertTrue(d.isEmpty());
	}

	@Test
	final void sizeTest() {
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		assertTrue(d.isEmpty());
		d.add(p0);
		assertFalse(d.isEmpty());
		assertTrue(d.size() < Integer.MAX_VALUE);
		for (int i = 0; i < Integer.MAX_VALUE + 1; i++)
			d.add(p0);
		assertFalse(d.size() == Integer.MAX_VALUE);
	}

	@Test
	final void ItrTest() {
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Iterator<Studente> i0 = d.iterator();
		assertFalse(i0.hasNext());
		assertThrows(NoSuchElementException.class, () -> i0.next());
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertTrue(i0.hasNext());
		assertThrows(ConcurrentModificationException.class, () -> i0.next());
		Iterator<Studente> i1 = d.iterator();
		assertTrue(d.size() == 4);
		while (i1.hasNext()) {
			if (p3.equals(i1.next())) {
				d.remove(p2);
				assertFalse(d.contains(p2));
				assertThrows(ConcurrentModificationException.class, () -> i1.next());
			} else
				i1.next();

		}
		assertTrue(d.size() == 4);
	}

	@Test
	final void DescItr() {
		ASDL2021Deque<Studente> d = new ASDL2021Deque<Studente>();
		Iterator<Studente> i0 = d.descendingIterator();
		assertFalse(i0.hasNext());
		assertThrows(NoSuchElementException.class, () -> i0.next());
		Studente p0 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p1 = new Studente("Piermichele", "Rosati", 20, "Unicam", "Informatica");
		Studente p2 = new Studente("Mario", "Rossi", 64, "Unibo", "Informatica");
		Studente p3 = new Studente("Giovanni", "Verdi", 20, "Unicam", "Fisica");
		Collection<Studente> studenti = new LinkedList<Studente>();
		assertTrue(studenti.isEmpty());
		assertTrue(d.isEmpty());
		studenti.add(p0);
		studenti.add(p1);
		studenti.add(p2);
		studenti.add(p3);
		assertFalse(studenti.isEmpty());
		d.addAll(studenti);
		assertTrue(i0.hasNext());
		assertThrows(ConcurrentModificationException.class, () -> i0.next());
		Iterator<Studente> i1 = d.descendingIterator();
		assertTrue(d.size() == 4);
		while (i1.hasNext()) {
			if (p0.equals(i1.next())) {
				d.remove(p2);
				assertFalse(d.contains(p2));
				assertThrows(ConcurrentModificationException.class, () -> i1.next());
			} else
				i1.next();

		}
		assertTrue(d.size() == 4);
	}
}
