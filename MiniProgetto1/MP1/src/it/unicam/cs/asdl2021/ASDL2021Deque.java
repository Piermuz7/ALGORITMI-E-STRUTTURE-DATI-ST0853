/**
 * 
 */
package it.unicam.cs.asdl2021.mp1;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the Java SE Double-ended Queue (Deque) interface
 * (<code>java.util.Deque</code>) based on a double linked list. This deque does
 * not have capacity restrictions, i.e., it is always possible to insert new
 * elements and the number of elements is unbound. Duplicated elements are
 * permitted while <code>null</code> elements are not permitted. Being
 * <code>Deque</code> a sub-interface of
 * <code>Queue<code>, this class can be used also as an implementaion of a <code>Queue</code>
 * and of a <code>Stack</code>.
 * 
 * The following operations are not supported:
 * <ul>
 * <li><code>public <T> T[] toArray(T[] a)</code></li>
 * <li><code>public boolean removeAll(Collection<?> c)</code></li>
 * <li><code>public boolean retainAll(Collection<?> c)</code></li>
 * <li><code>public boolean removeFirstOccurrence(Object o)</code></li>
 * <li><code>public boolean removeLastOccurrence(Object o)</code></li>
 * </ul>
 * 
 * @author Template: Luca Tesei, Implementation: Piermichele Rosati -
 *         piermichele.rosati@studenti.unicam.it
 *
 */
public class ASDL2021Deque<E> implements Deque<E> {

	/*
	 * Numero attuale di elementi in questa deque.
	 */
	private int size;

	/*
	 * Puntatore al primo nodo di questa deque.
	 */
	private Node<E> first;

	/*
	 * Puntatore all' ultimo nodo di questa deque.
	 */
	private Node<E> last;

	/*
	 * Modifiche relative a questa deque.
	 * 
	 */
	private int numeroModifiche;

	/**
	 * Costruisce una deque vuota.
	 */
	public ASDL2021Deque() {
		// Inizializzo le variabili di istanza
		// All' inizio questa deque è vuota
		this.size = 0;
		// I puntatori di testa e coda puntano a null
		this.first = null;
		this.last = null;
		// All' inizio non ci sono modifiche
		this.numeroModifiche = 0;
	}

	/**
	 * 
	 * Ritorna true se questa deque non contiene elementi.
	 * 
	 * @return true se questa deque non contiene elementi, false altrimenti.
	 */
	@Override
	public boolean isEmpty() {
		// Controllo se la dimensione di questa deque è = 0, allora è vuota
		if (this.size == 0)
			return true;
		// Altrimenti non è vuota
		return false;
	}

	/**
	 * 
	 * Ritorna un array contenente tutti gli elementi di questa deque ordinati come
	 * nella deque.
	 * 
	 * @return un array contenente tutti gli elementi di questa deque.
	 * 
	 */
	@Override
	public Object[] toArray() {
		// Allocazione array con dimensione pari alla dimensione di questa deque
		Object[] array = new Object[this.size];
		// Iteratore per scorrere la deque
		Itr i = new Itr();
		// Contatore per scorrere l'array
		int j = 0;
		while (i.hasNext()) {
			// Copio l' oggetto contenuto nel nodo attuale, nell' array
			array[j] = i.next();
			j++;
		}
		return array;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException("This class does not implement this service.");
	}

	/**
	 * 
	 * Ritorna true se questa deque contiene tutti gli elementi della collezione
	 * specificata.
	 * 
	 * @param c la collection da controllare per l' appartenenza a questa deque.
	 * 
	 * @return true se questa deque contiene tutti gli elementi appartenenti alla
	 *         collezione specificata, false altrimenti.
	 * 
	 * @throws NullPointerException se la collection passata contiene uno o più
	 *                              elementi null perchè questa deque non permette
	 *                              elementi null, o se la collection è null.
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		// Se la collection è null
		if (c == null)
			throw new NullPointerException("ERRORE: la collection passata è nulla!");
		// Se la lista è vuota
		if (this.isEmpty())
			return false;
		// Iteratore per scorrere la collection passata
		Iterator<?> i = c.iterator();
		E item;
		while (i.hasNext()) {
			// Casting necessario: i.next potrebbe essere di qualsiasi tipo
			item = (E) i.next();
			// Se la collection passata contiene almeno un elemento null
			if (item == null)
				throw new NullPointerException("ERRORE: la collection passata contiene almeno un elemento nullo!");
			// Altrimenti vedo se questa deque non contiene l' item della collection
			if (!this.contains(item))
				return false;
		}
		// Se esce dal while, li contiene tutti
		return true;
	}

	/**
	 * Aggiunge a questa deque tutti gli elementi della collezione specificata.
	 * 
	 * @param c la collection che contiene gli elementi da aggiungere a questa
	 *          deque.
	 * 
	 * @return true se questa deque è cambiata dopo la chiamata al metodo, false
	 *         altrimenti.
	 * 
	 * @throws NullPointerException se la collection passata è null, o contiene
	 *                              elementi null.
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		// Se la collection passata è null
		if (c == null)
			throw new NullPointerException("ERRORE: la collection passata è nulla!");
		// Se la collection passata permette elementi null e contiene almeno un elemento
		// null
		if (c.contains(null))
			throw new NullPointerException("ERRORE: la collection passata contiene almeno un elemento nullo!");
		// Iteratore per scorrere la collection passata
		Iterator<?> i = c.iterator();
		// Se non aggiungo elementi
		if (!i.hasNext())
			return false;
		// Se li aggiungo
		while (i.hasNext()) {
			// Se la deque è vuota
			if (this.isEmpty()) {
				this.first = new Node<E>(null, (E) i.next(), null);
				this.last = this.first;
			} else {
				this.last.next = new Node<E>(this.last, (E) i.next(), null);
				this.last = this.last.next;
			}
			this.size++;
			this.numeroModifiche++;
		}
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("This class does not implement this service.");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("This class does not implement this service.");
	}

	/**
	 * 
	 * Rimuove tutti gli elementi di questa deque. Questa deque sarà vuota dopo la
	 * chiamata al metodo.
	 * 
	 */
	@Override
	public void clear() {
		// Inizializzo i puntatori a null
		this.first = null;
		this.last = null;
		// La dimensione diventa 0
		this.size = 0;
		this.numeroModifiche++;
	}

	/**
	 * 
	 * Inserisce l' elemento specificato nella testa di questa deque se non vengono
	 * violate le capacità restrittive di questa deque. Quando vengono usate
	 * capacità restrittive, generalmente è preferibile usare il metodo
	 * offerFirst(E).
	 * 
	 * @param e l' oggetto di tipo E da aggiungere nella testa di questa deque.
	 * 
	 * @throws NullPointerException se l'oggetto passato è null.
	 * 
	 */
	@Override
	public void addFirst(E e) {
		this.push(e);
	}

	/**
	 * 
	 * Inserisce l' elemento specificato nella coda di questa deque se non vengono
	 * violate le capacità restrittive di questa deque. Quando vengono usate
	 * capacità restrittive, generalmente è preferibile usare il metodo
	 * offerLast(E).
	 * 
	 * @param e l'oggetto da aggiungere in coda.
	 * 
	 * @throws NullPointerException se l'oggetto passato è nullo.
	 * 
	 */
	@Override
	public void addLast(E e) {
		this.add(e);
	}

	/**
	 * Inserisce l' elemento specificato nella testa di questa deque se non vengono
	 * violate le capacità restrittive di questa deque. Quando vengono usate
	 * capacità restrittive, generalmente questo metodo è preferibile al metodo
	 * addFirst(E), il quale può fallire nell' inserimento e in tal caso, lancia l'
	 * eccezione.
	 * 
	 * @param e l'oggetto da aggiungere in testa
	 * 
	 * @return true se l' elemento specificato viene aggiunto a questa deque, false
	 *         altrimenti.
	 * 
	 * @throws NullPointerException se l' elemento specificato è null perchè questa
	 *                              deque non accetta elementi null.
	 * 
	 */
	@Override
	public boolean offerFirst(E e) {
		this.push(e);
		return true;
	}

	/**
	 * Inserisce l' elemento specificato nella coda di questa deque possibilmente
	 * senza violare le capacità restrittive. Quando vengono usate capacità
	 * restrittive, generalmente è preferibile usare il metodo addFirst(E).
	 * 
	 * @param e l'oggetto da aggiungere in testa
	 * 
	 * @return true se l' elemento specificato viene aggiunto a questa deque, false
	 *         altrimenti.
	 * 
	 * @throws NullPointerException se l' elemento specificato è null perchè questa
	 *                              deque non accetta elementi null.
	 * 
	 */
	@Override
	public boolean offerLast(E e) {
		this.add(e);
		return true;
	}

	/**
	 * 
	 * Ritorna e rimuove la testa di questa deque. Questo metodo differisce da
	 * pollFirst, perchè lancia un' eccezione se questa deque è vuota.
	 * 
	 * @return la testa di questa deque.
	 * 
	 * @throws NoSuchElementException se la deque è vuota.
	 * 
	 */
	@Override
	public E removeFirst() {
		return this.remove();
	}

	/**
	 * 
	 * Ritorna e rimuove la coda di questa deque. Questo metodo differisce da
	 * pollLast, perchè lancia un' eccezione se questa deque è vuota.
	 * 
	 * @return la coda di questa deque.
	 * 
	 * @throws NoSuchElementException se la deque è vuota.
	 * 
	 */
	@Override
	public E removeLast() {
		// Se la deque è vuota
		if (this.isEmpty())
			throw new NoSuchElementException("ERRORE: la deque è vuota!");
		// Item della coda da eliminare
		E item = this.last.item;
		// La coda diventa il nodo precedente e punta a null
		this.last = this.last.prev;
		// Vedo se la deque è composta da un solo elemento
		if (this.first.next == null)
			this.first = null;
		else
			this.last.next = null;
		this.size--;
		this.numeroModifiche++;
		return item;
	}

	/**
	 * 
	 * Ritorna e rimuove la testa di questa deque, oppure ritorna null se questa
	 * deque è vuota.
	 * 
	 * @return la testa di questa deque, oppure null se questa deque è vuota.
	 * 
	 */
	@Override
	public E pollFirst() {
		return this.poll();
	}

	/**
	 * 
	 * Ritorna e rimuove la coda di questa deque, oppure ritorna null se questa
	 * deque è vuota.
	 * 
	 * @return la coda di questa deque, oppure null se questa deque è vuota.
	 * 
	 */
	@Override
	public E pollLast() {
		// Se la deque è vuota
		if (this.isEmpty())
			return null;
		// Item della coda da eliminare
		E item = this.last.item;
		// La coda diventa il nodo precedente e punta a null
		this.last = this.last.prev;
		// Vedo se la deque è composta da un solo elemento
		if (this.first.next == null)
			this.first = null;
		else
			this.last.next = null;
		this.size--;
		this.numeroModifiche++;
		return item;
	}

	/**
	 * 
	 * Ritorna, ma non rimuove, la testa di questa deque. Questo metodo differisce
	 * dal metodo peekFirst(), perchè lancia un eccezione se questa deque è vuota.
	 *
	 * 
	 * @return la testa di questa deque.
	 * 
	 * @throws NoSuchElementException se questa deque è vuota.
	 * 
	 */
	@Override
	public E getFirst() {
		return this.element();
	}

	/**
	 * 
	 * Ritorna, ma non rimuove, la coda di questa deque. Questo metodo differisce
	 * dal metodo peekLast(), perchè lancia un eccezione se questa deque è vuota.
	 *
	 * 
	 * @return la coda di questa deque.
	 * 
	 * @throws NoSuchElementException se questa deque è vuota.
	 * 
	 */
	@Override
	public E getLast() {
		// Controllo se la deque è vuota
		if (this.isEmpty())
			throw new NoSuchElementException("ERRORE: la deque è vuota!");
		// Altrimenti ritorno la coda di questa deque
		return this.last.item;
	}

	/**
	 * 
	 * Ritorna, ma non rimuove, la testa di questa deque, oppure ritorna null se
	 * questa deque è vuota.
	 *
	 * 
	 * @return la testa di questa deque oppure null se questa deque è vuota.
	 * 
	 */
	@Override
	public E peekFirst() {
		return this.peek();
	}

	/**
	 * 
	 * Ritorna, ma non rimuove, la coda di questa deque, oppure ritorna null se
	 * questa deque è vuota.
	 *
	 * 
	 * @return la coda di questa deque oppure null se questa deque è vuota.
	 * 
	 */
	@Override
	public E peekLast() {
		// Controllo se la deque è vuota
		if (this.isEmpty())
			return null;
		// Altrimenti ritorno la coda di questa deque
		return this.last.item;
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		throw new UnsupportedOperationException("This class does not implement this service.");
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		throw new UnsupportedOperationException("This class does not implement this service.");
	}

	/**
	 * 
	 * Inserisce l' elemento specificato, nella deque in coda, possibilmente senza
	 * violare le capacità restrittive. Ritorna true in caso di successo. Quando
	 * vengono usate capacità ristrettive per questa deque, generalmente questo
	 * metodo è preferibile al metodo offer(). Questo metodo è equivalente al metodo
	 * addLast(E).
	 * 
	 * @param e l'oggetto da aggiungere in coda.
	 * 
	 * @return true se questa deque è cambiata dopo la chiamata al metodo.
	 * 
	 * @throws NullPointerException se l'oggetto passato è null perchè questa deque
	 *                              non accetta elementi null.
	 * 
	 */
	@Override
	public boolean add(E e) {
		// Se l' oggetto passato è null
		if (e == null)
			throw new NullPointerException("ERRORE: l' oggetto passato è nullo!");
		// Nuovo nodo da aggiungere
		Node<E> newNode = new Node<E>(null, e, null);
		// Se la lista e' vuota
		if (this.first == null)
			// Il primo nodo è la testa
			this.first = newNode;
		else {
			// Altrimenti aggancio il nodo precedente e il nodo successivo all' ultimo nodo
			newNode.prev = this.last;
			this.last.next = newNode;
		}
		// E l'ultimo nodo aggiunto è la coda
		this.last = newNode;
		// La lista viene modificata
		this.size++;
		this.numeroModifiche++;
		return true;
	}

	/**
	 * Inserisce l' elemento specificato nella deque in coda, possibilmente senza
	 * violare le capacità restrittive. Ritorna true in caso di successo e ritorna
	 * false se non c'è spazio disponibile in questa deque. Quando vengono usate
	 * capacità ristrettive per questa deque, generalmente questo metodo è
	 * preferibile al metodo add(E), che può fallire nell' inserimento lanciando un
	 * eccezione. Questo metodo è equivalente al metodo offerLast(E).
	 * 
	 * @param e l' oggetto da aggiungere.
	 * 
	 * @return true se questo oggetto viene aggiunto a questa deque, false
	 *         altrimenti.
	 * 
	 * @throws NullPointerException, se l'oggetto specificato è null perchè questa
	 *                               deque non ammette oggetti null.
	 * 
	 */
	@Override
	public boolean offer(E e) {
		return this.add(e);
	}

	/**
	 * 
	 * Ritorna e rimuove la testa di questa deque. Questo metodo differisce dal
	 * metodo poll(), perchè lancia un' eccezione se questa deque è vuota. Questo
	 * metodo è equivalente al metodo removeFirst().
	 * 
	 * @return la testa di questa deque.
	 * 
	 * @throws NoSuchElementException se la deque è vuota.
	 * 
	 */
	@Override
	public E remove() {
		// Controllo se la deque è vuota
		if (this.isEmpty())
			throw new NoSuchElementException("ERRORE: la deque è vuota!");
		// Altrimenti rimuovo la testa
		E item;
		// Mi salvo l'item della testa
		item = this.first.item;
		// Se la lista ha un solo nodo, la deque diventa vuota
		if (this.first == this.last)
			this.last = null;
		// La nuova testa diventa il nodo successivo alla vecchia testa
		this.first = this.first.next;
		// e se questa deque non è vuota, il nodo precedente al primo deve essere null
		if (this.first != null)
			this.first.prev = null;
		this.size--;
		this.numeroModifiche++;
		return item;
	}

	/**
	 * 
	 * Ritorna e rimuove la testa di questa deque , oppure ritorna null se questa
	 * deque è vuota. Questo metodo è equivalente al metodo pollFirst().
	 * 
	 * @return la testa di questa deque, oppure null se questa deque è vuota.
	 * 
	 * 
	 */
	@Override
	public E poll() {
		// Se la deque è vuota ritorno null
		if (this.isEmpty())
			return null;
		else {
			// Altrimenti rimuovo la testa
			E item;
			// Mi salvo l'item della testa
			item = this.first.item;
			// Se la lista ha un solo nodo, la deque diventa vuota
			if (this.first == this.last)
				this.last = null;
			// La nuova testa diventa il nodo successivo alla vecchia testa
			this.first = this.first.next;
			// e se questa deque non è vuota, il nodo precedente al primo deve essere null
			if (this.first != null)
				this.first.prev = null;
			this.size--;
			this.numeroModifiche++;
			return item;
		}

	}

	/**
	 * Ritorna, ma non rimuove, la testa di questa deque. Questo metodo differisce
	 * dal metodo peek(), perchè lancia un' eccezione se questa deque è vuota.
	 * Questo metodo è equivalente al metodo getFirst().
	 * 
	 * @return la testa di questa deque.
	 * 
	 * @throws NoSuchElementException se questa deque è vuota.
	 * 
	 */
	@Override
	public E element() {
		// Se questa deque è vuota lancio l' eccezione
		if (this.isEmpty())
			throw new NoSuchElementException("ERRORE: la deque è vuota!");
		// Altrimenti ritorno la testa
		return this.first.item;
	}

	/**
	 * Ritorna, ma non rimuove, la testa delle deque, oppure ritorna null se questa
	 * deque è vuota. Questo metodo è equivalente al metodo peekFirst().
	 * 
	 */
	@Override
	public E peek() {
		// Controllo se questa deque è vuota
		if (this.isEmpty())
			return null;
		// Altrimenti ritorno la testa
		return this.first.item;
	}

	/**
	 * Inserisce l' elemento specificato nella testa di questa deque se non vengono
	 * violate le capacità restrittive di questa deque. Questo metodo è equivalente
	 * al metodo addFirst(E).
	 * 
	 * @throws NullPointerException se l'elemento specificato è null perchè questa
	 *                              deque non accetta elementi null
	 * 
	 */
	@Override
	public void push(E e) {
		// Se l' oggetto passato è null
		if (e == null)
			throw new NullPointerException("ERRORE: l' oggetto passato è nullo!");
		// Nuovo nodo da aggiungere
		Node<E> newNode = new Node<E>(null, e, null);
		// Se la deque è vuota, l' inizio sarà anche la fine
		if (this.first == null)
			this.last = newNode;
		// Aggancio il nuovo nodo in testa
		newNode.next = this.first;
		this.first = newNode;
		// La deque viene modificata
		this.size++;
		this.numeroModifiche++;
	}

	/**
	 * 
	 * Ritorna e rimuove la testa di questa deque.Questo metodo è equivalente al
	 * metodo removeFirst().
	 * 
	 * @return la testa di questa deque.
	 * 
	 * @throws NoSuchElementException se la deque è vuota.
	 * 
	 */
	@Override
	public E pop() {
		return this.remove();
	}

	/**
	 * 
	 * Rimuove la prima occorrenza dell' elemento specificato di questa deque. Se
	 * questa deque non contiene elementi, allora non cambia. Ritorna true se questa
	 * deque conteneva l' elemento specificato, false altrimenti. Questo metodo è
	 * equivalente al metodo removeFirstOccurrence(Object).
	 * 
	 * @param o l' elemento che, se presenta, deve essere rimosso da questa deque.
	 * 
	 * @return true se un elemento viene rimosso come risultato della chiamata a
	 *         questo metodo.
	 * 
	 * @throws NullPointerException se l' elemento specificato è null perchè questa
	 *                              deque non accetta elementi null.
	 * 
	 */
	@Override
	public boolean remove(Object o) {
		// Se l' oggetto passato è null
		if (o == null)
			throw new NullPointerException("ERRORE: l' oggetto passato è nullo!");
		// Controllo se la deque è vuota
		if (this.isEmpty())
			throw new NoSuchElementException("ERRORE: la deque è vuota!");
		// Se lo contiene
		Itr i = new Itr();
		while (i.hasNext()) {
			if (o.equals(i.next())) {
				// Se elimino la testa
				if (i.lastReturned == this.first) {
					this.first = i.lastReturned.next;
					// Se la testa è anche la coda
					if (i.lastReturned == this.last)
						this.last = i.lastReturned.next;
					else
						this.first.prev = null;
				}
				// Se elimino in mezzo o alla fine
				else {
					// Il nodo precedente al nodo da eliminare punta al nodo successivo a quello da
					// eliminare
					i.lastReturned.prev.next = i.lastReturned.next;
					// Se è la coda, il nodo precedente diventa la coda e punta a null
					if (i.lastReturned == this.last) {
						this.last = i.lastReturned.prev;
						this.last.next = null;
					}
					// Se non è la coda, collego il nodo successivo a quello da eliminare con il
					// precedente
					else
						i.lastReturned.next.prev = i.lastReturned.prev;
				}
				// Se l' ho trovato modifico la deque e ritorno true
				this.size--;
				this.numeroModifiche++;
				return true;
			}
		}
		// Altrimenti non lo contiene
		return false;
	}

	/**
	 * Ritorna true se questa deque contiene l' elemento specificato.
	 * 
	 * @param o l' oggetto ricercato contenuto in questa deque.
	 * 
	 * @return true se questa deque contiene l' oggetto specificato, false
	 *         altrimenti.
	 * 
	 * @throws NullPointerException l'oggetto specificato è null.
	 * 
	 */
	@Override
	public boolean contains(Object o) {
		// Controllo se l' oggetto passato è null
		if (o == null)
			throw new NullPointerException("ERRORE: l' oggetto passato è nullo!");
		// Se la deque è vuota non lo contiene
		if (this.isEmpty())
			return false;
		// Altrimenti controllo se è contenuto nella deque
		Itr i = new Itr();
		while (i.hasNext())
			// Se l' oggetto viene trovato all' interno della deque
			if (o.equals(i.next()))
				return true;
		// Se non viene trovato
		return false;
	}

	/**
	 * 
	 * Ritorna il numero di elementi in questa deque.
	 * 
	 * @return il numero di elementi di questa deque. Se questa deque contiene più
	 *         elementi di Integer.MAX_VALUE , ritorna Integer.MAX_VALUE
	 */
	@Override
	public int size() {
		// Controllo se la dimensione della deque è minore della costante ritorno la
		// size della deque
		if (this.size < Integer.MAX_VALUE)
			return this.size;
		// Altrimenti ritorno la costante
		return Integer.MAX_VALUE;
	}

	/*
	 * Class for representing the nodes of the double-linked list used to implement
	 * this deque. The class and its members/methods are protected instead of
	 * private only for JUnit testing purposes.
	 */
	protected static class Node<E> {
		protected E item;

		protected Node<E> next;

		protected Node<E> prev;

		protected Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}

	/**
	 * Ritorna un iteratore per scorrere gli elementi di questa deque nel proprio
	 * ordine. Gli elementi verranno ritornati in ordine dalla testa alla coda.
	 * 
	 * @return un iteratore per scorrere gli elementi di questa deque nel proprio
	 *         ordine.
	 */
	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}

	/*
	 * Class for implementing an iterator for this deque. The iterator is fail-fast:
	 * it detects if during the iteration a modification to the original deque was
	 * done and, if so, it launches a <code>ConcurrentModificationException</code>
	 * as soon as a call to the method <code>next()</code> is done.
	 */
	private class Itr implements Iterator<E> {
		// Modifiche attese relative alle modifiche della Deque
		private int numeroModificheAtteso;
		// Ultimo nodo ritornato
		private Node<E> lastReturned;

		Itr() {
			this.numeroModificheAtteso = ASDL2021Deque.this.numeroModifiche;
			this.lastReturned = null;
		}

		public boolean hasNext() {
			// Se non ho ancora scorso la deque
			if (this.lastReturned == null) {
				// Se c'è almeno un elemento nella deque
				if (ASDL2021Deque.this.first != null)
					return true;
				return false;
			}
			// Controllo se il riferimento al nodo successivo è nullo
			if (this.lastReturned.next != null)
				return true;
			return false;
		}

		public E next() {
			// Se la deque viene cambiata
			if (this.numeroModificheAtteso != ASDL2021Deque.this.numeroModifiche)
				throw new ConcurrentModificationException("ERRORE: la Deque è stata cambiata");
			// Controllo se c'è un elemento successivo
			if (!this.hasNext())
				throw new NoSuchElementException("ERRORE: non c'è un elemento successivo");
			// Se all'inizio il cursore è null lo inizializzo con il primo nodo della deque
			if (this.lastReturned == null)
				this.lastReturned = ASDL2021Deque.this.first;
			// Altrimenti scorro la deque
			else
				this.lastReturned = this.lastReturned.next;
			// Ritorno l'oggetto contenuto nel nodo
			return this.lastReturned.item;
		}
	}

	/**
	 * 
	 * Ritorna un iteratore per scorrere gli elementi di questa deque nell' ordine
	 * sequenziale inverso. Gli elementi verranno ritornati in ordine dalla coda
	 * alla tasta.
	 * 
	 * @return un iteratore per scorrere gli elementi di questa deque nell' ordine
	 *         sequenziale inverso.
	 * 
	 */
	@Override
	public Iterator<E> descendingIterator() {
		return new DescItr();
	}

	/*
	 * Class for implementing a descendign iterator for this deque. The iterator is
	 * fail-safe: it detects if during the iteration a modification to the original
	 * deque was done and, if so, it launches a
	 * <code>ConcurrentModificationException</code> as soon as a call to the method
	 * <code>next()</code> is done.
	 */
	private class DescItr implements Iterator<E> {
		// Modifiche attese relative alle modifiche della Deque
		private int numeroModificheAtteso;
		// Ultimo nodo ritornato
		private Node<E> lastReturned;

		DescItr() {
			this.numeroModificheAtteso = ASDL2021Deque.this.numeroModifiche;
			this.lastReturned = null;
		}

		public boolean hasNext() {
			// Se non ho ancora scorso la deque
			if (this.lastReturned == null) {
				// Se c'è almeno un elemento nella deque
				if (ASDL2021Deque.this.last != null)
					return true;
				return false;
			}
			// Controllo se il riferimento al nodo successivo è nullo
			if (this.lastReturned.prev != null)
				return true;
			return false;
		}

		public E next() {
			// Se la deque viene cambiata
			if (this.numeroModificheAtteso != ASDL2021Deque.this.numeroModifiche)
				throw new ConcurrentModificationException("ERRORE: la Deque è stata cambiata");
			// Controllo se c'è un elemento successivo
			if (!this.hasNext())
				throw new NoSuchElementException("ERRORE: non c'è un elemento successivo");
			// Se all'inizio il cursore è null lo inizializzo con l'ultimo nodo della deque
			if (this.lastReturned == null)
				this.lastReturned = ASDL2021Deque.this.last;
			// Altrimenti scorro la deque
			else
				this.lastReturned = this.lastReturned.prev;
			// Ritorno l'oggetto contenuto nel nodo
			return this.lastReturned.item;
		}

	}

	// TODO implement: possibly add private methods for implementation purposes

	/*
	 * This method is only for JUnit testing purposes.
	 */
	protected Node<E> getFirstNode() {
		return this.first;
	}

	/*
	 * This method is only for JUnit testing purposes.
	 */
	protected Node<E> getLastNode() {
		return this.last;
	}

}
