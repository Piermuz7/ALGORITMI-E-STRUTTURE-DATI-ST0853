package it.unicam.cs.asdl2021.mp1TeseiTest;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Class that provides an implementation of a "dynamic" min-priority queue based
 * on a ternary heap. "Dynamic" means that the priority of an element already
 * present in the queue may be decreased, so possibly this element may become
 * the new minumum element. The elements that can be inserted may be of any
 * class implementing the interface <code>PriorityQueueElement</code>. This
 * min-priority queue does not have capacity restrictions, i.e., it is always
 * possible to insert new elements and the number of elements is unbound.
 * Duplicated elements are permitted while <code>null</code> elements are not
 * permitted.
 * 
 * @author Template: Luca Tesei, Implementation: Piermichele Rosati -
 *         piermichele.rosati@studenti.unicam.it
 *
 */
public class TernaryHeapMinPriorityQueue {

	/*
	 * ArrayList for representing the ternary heap. Use all positions, including
	 * position 0 (the JUnit tests will assume so). You have to adapt child/parent
	 * indexing formulas consequently.
	 */
	private ArrayList<PriorityQueueElement> heap;

	/**
	 * Create an empty queue.
	 */
	public TernaryHeapMinPriorityQueue() {
		this.heap = new ArrayList<PriorityQueueElement>();
	}

	/**
	 * Return the current size of this queue.
	 * 
	 * @return the number of elements currently in this queue.
	 */
	public int size() {
		return this.heap.size();
	}

	/**
	 * Add an element to this min-priority queue. The current priority associated
	 * with the element will be used to place it in the correct position in the
	 * ternary heap. The handle of the element will also be set accordingly.
	 * 
	 * @param element the new element to add
	 * @throws NullPointerException if the element passed is null
	 */
	public void insert(PriorityQueueElement element) {
		// Se l' elemento passato è null
		if (element == null)
			throw new NullPointerException("ERRORE: l' elemento passato è null!");
		// L' elemento viene inserito in base alla priorità
		this.heap.add(element);
		int i = this.heap.size() - 1;
		this.heap.get(i).setHandle(i);
		while ((i > 0) && (this.heap.get(this.parentIndex(i)).getPriority() > this.heap.get(i).getPriority())) {
			// Scambio il figlio con il padre
			PriorityQueueElement tmp = this.heap.get(i);
			this.heap.set(i, this.heap.get(this.parentIndex(i)));
			this.heap.set(this.parentIndex(i), tmp);
			// L' handle del nuovo figlio è i
			this.heap.get(i).setHandle(i);
			// L' handle del nuovo padre è il padre del nuovo figlio
			this.heap.get(this.parentIndex(i)).setHandle(this.parentIndex(i));
			// L' indice successivo è quello del padre del nuovo padre
			i = this.parentIndex(i);
		}
	}

	/**
	 * Returns the current minimum element of this min-priority queue without
	 * extracting it. This operation does not affect the ternary heap.
	 * 
	 * @return the current minimum element of this min-priority queue
	 * 
	 * @throws NoSuchElementException if this min-priority queue is empty
	 */
	public PriorityQueueElement minimum() {
		// Se il minHeap è vuoto
		if (this.heap.isEmpty())
			throw new NoSuchElementException("ERRORE: il min-Heap è vuoto!");
		// Altrimenti ritorno la radice
		return this.heap.get(0);
	}

	/**
	 * Extract the current minimum element from this min-priority queue. The ternary
	 * heap will be updated accordingly.
	 * 
	 * @return the current minimum element
	 * @throws NoSuchElementException if this min-priority queue is empty
	 */
	public PriorityQueueElement extractMinimum() {
		// Se il minHeap è vuoto
		if (this.heap.isEmpty())
			throw new NoSuchElementException("ERRORE: il min-Heap è vuoto!");
		// Altrimenti se c'è almeno un nodo il minimo è la radice
		PriorityQueueElement min = this.minimum();
		this.heap.set(0, this.heap.get(this.size() - 1));
		// La nuova radice ha handle 0
		this.heap.get(0).setHandle(0);
		this.heap.remove(this.size() - 1);
		this.minHeapify(0);
		return min;
	}

	/**
	 * Decrease the priority associated to an element of this min-priority queue.
	 * The position of the element in the ternary heap must be changed accordingly.
	 * The changed element may become the minimum element. The handle of the element
	 * will also be changed accordingly.
	 * 
	 * @param element     the element whose priority will be decreased, it must
	 *                    currently be inside this min-priority queue
	 * @param newPriority the new priority to assign to the element
	 * 
	 * @throws NoSuchElementException   if the element is not currently present in
	 *                                  this min-priority queue
	 * @throws IllegalArgumentException if the specified newPriority is not strictly
	 *                                  less than the current priority of the
	 *                                  element
	 */
	public void decreasePriority(PriorityQueueElement element, double newPriority) {
		// Se l' elemento specificato non è presente nel minheap
		if (!this.heap.contains(element))
			throw new NoSuchElementException("ERRORE: il min-heap non contiene l' elemento specificato!");
		// Se la priorità specificata è > di quella dell' elemento specificato
		if (newPriority > element.getPriority())
			throw new IllegalArgumentException(
					"ERRORE: la nuova priorità è maggiore di quella dell' elemento specificato!");
		// Setto la nuova priorità
		this.heap.get(this.heap.indexOf(element)).setPriority(newPriority);
		int i = this.heap.indexOf(element);
		// Se la priorità del padre è > di quella del figlio, li scambio
		while ((i > 0) && (this.heap.get(this.parentIndex(i)).getPriority() > this.heap.get(i).getPriority())) {
			// Scambio il figlio con il padre
			PriorityQueueElement tmp = this.heap.get(i);
			this.heap.set(i, this.heap.get(this.parentIndex(i)));
			this.heap.set(this.parentIndex(i), tmp);
			// L' handle del nuovo figlio è i
			this.heap.get(i).setHandle(i);
			// L' handle del nuovo padre è il padre del nuovo figlio
			this.heap.get(this.parentIndex(i)).setHandle(this.parentIndex(i));
			// L' indice successivo è quello del padre del nuovo padre
			i = this.parentIndex(i);
		}

	}

	/**
	 * Erase all the elements from this min-priority queue. After this operation
	 * this min-priority queue is empty.
	 */
	public void clear() {
		this.heap.clear();
	}

	/*
	 * This method is only for JUnit testing purposes.
	 */
	protected ArrayList<PriorityQueueElement> getTernaryHeap() {
		return this.heap;
	}

	/**
	 * Funzione di comodo per calcolare l'indice del figlio sinistro del nodo in
	 * posizione i. Si noti che la posizione 0 è significativa e contiene sempre la
	 * radice dello heap. Ritorna l' indice del figlio sinistro del padre avente
	 * indice i, -1 se non ha il figlio sinistro.
	 * 
	 * @return l' indice del figlio sinitro del padre in posizione i oppure -1 se
	 *         non ha il figlio sinistro
	 * 
	 */
	private int leftIndex(int i) {
		int leftIndex = (i * 3) + 1;
		if (leftIndex < this.size())
			return leftIndex;
		// Se non ha il figlio sinistro
		return -1;
	}

	/**
	 * Funzione di comodo per calcolare l'indice del figlio centrale del nodo in
	 * posizione i. Si noti che la posizione 0 è significativa e contiene sempre la
	 * radice dello heap.Ritorna l' indice del figlio centrale del padre avente
	 * indice i, -1 se non ha il figlio centrale.
	 * 
	 * 
	 * @return l' indice del figlio centrale del padre in posizione i oppure -1 se
	 *         non ha il figlio centrale.
	 * 
	 */
	private int centerIndex(int i) {
		int centerIndex = (i * 3) + 2;
		if (centerIndex < this.size())
			return centerIndex;
		// Se non ha il figlio centrale
		return -1;
	}

	/**
	 * Funzione di comodo per calcolare l'indice del figlio destro del nodo in
	 * posizione i. Si noti che la posizione 0 è significativa e contiene sempre la
	 * radice dello heap. Ritorna l' indice del figlio destro del padre avente
	 * indice i, -1 se non ha il figlio destro.
	 * 
	 * @return l' indice del figlio destro del padre in posizione i oppure -1 se non
	 *         ha il figlio destro.
	 * 
	 */
	private int rightIndex(int i) {
		int rightIndex = (i * 3) + 3;
		if (rightIndex < this.size())
			return rightIndex;
		// Se non ha il figlio destro
		return -1;
	}

	/**
	 * Funzione di comodo per calcolare l'indice del genitore del nodo in posizione
	 * i. Si noti che la posizione 0 è significativa e contiene sempre la radice
	 * dello heap. Ritorna l' indice del padre del figlio avente indice i.
	 * 
	 * @return l' indice del padre del figlio in posizione i.
	 * 
	 */
	private int parentIndex(int i) {
		return (i - 1) / 3;
	}

	/*
	 * Ricostituisce un min-heap a partire dal nodo in posizione i assumendo che i
	 * suoi sottoalberi sinistro e destro (se esistono) siano min-heap.
	 */
	private void minHeapify(int i) {
		// Se il minHeap è vuoto
		if (this.heap.isEmpty())
			return;
		// Figli del nodo i
		int figlioSinitro = this.leftIndex(i);
		int figlioCentrale = this.centerIndex(i);
		int figlioDestro = this.rightIndex(i);
		int min;

		if ((figlioSinitro <= this.size() && (figlioSinitro != -1))
				&& (this.heap.get(figlioSinitro).getPriority() < (this.heap.get(i).getPriority())))
			min = figlioSinitro;
		else
			min = i;

		if ((figlioCentrale <= this.size() && (figlioCentrale != -1))
				&& (this.heap.get(figlioCentrale).getPriority() < (this.heap.get(min).getPriority())))
			min = figlioCentrale;

		if ((figlioDestro <= this.size() && (figlioDestro != -1))
				&& (this.heap.get(figlioDestro).getPriority() < (this.heap.get(min).getPriority())))
			min = figlioDestro;

		PriorityQueueElement tmp = this.heap.get(i);
		if (min != i) {
			this.heap.set(i, this.heap.get(min));
			this.heap.get(i).setHandle(i);
			this.heap.set(min, tmp);
			this.heap.get(min).setHandle(min);
			this.minHeapify(min);
		}
	}

}
