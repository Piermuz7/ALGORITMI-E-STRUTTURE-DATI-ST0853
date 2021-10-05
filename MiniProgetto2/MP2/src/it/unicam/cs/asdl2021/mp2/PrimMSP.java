package it.unicam.cs.asdl2021.mp2TeseiTest;

/**
 * 
 * Classe singoletto che implementa l'algoritmo di Prim per trovare un Minimum
 * Spanning Tree di un grafo non orientato, pesato e con pesi non negativi.
 * 
 * L'algoritmo usa una coda di min priorità tra i nodi implementata dalla classe
 * TernaryHeapMinPriorityQueue. I nodi vengono visti come PriorityQueueElement
 * poiché la classe GraphNode<L> implementa questa interfaccia. Si noti che
 * nell'esecuzione dell'algoritmo è necessario utilizzare l'operazione di
 * decreasePriority.
 * 
 * @author Template: Luca Tesei, Implementation: Piermichele Rosati -
 *         piermichele.rosati@studenti.unicam.it
 * 
 * @param <L> etichette dei nodi del grafo
 *
 */
public class PrimMSP<L> {

	/*
	 * Coda di priorità che va usata dall'algoritmo. La variabile istanza è
	 * protected solo per scopi di testing JUnit.
	 */
	protected TernaryHeapMinPriorityQueue queue;

	/**
	 * Crea un nuovo algoritmo e inizializza la coda di priorità con una coda vuota.
	 */
	public PrimMSP() {
		this.queue = new TernaryHeapMinPriorityQueue();
	}

	/**
	 * Utilizza l'algoritmo goloso di Prim per trovare un albero di copertura minimo
	 * in un grafo non orientato e pesato, con pesi degli archi non negativi. Dopo
	 * l'esecuzione del metodo nei nodi del grafo il campo previous deve contenere
	 * un puntatore a un nodo in accordo all'albero di copertura minimo calcolato,
	 * la cui radice è il nodo sorgente passato.
	 * 
	 * @param g un grafo non orientato, pesato, con pesi non negativi
	 * @param s il nodo del grafo g sorgente, cioè da cui parte il calcolo
	 *          dell'albero di copertura minimo. Tale nodo sarà la radice
	 *          dell'albero di copertura trovato
	 * 
	 * @throw NullPointerException se il grafo g o il nodo sorgente s sono nulli
	 * @throw IllegalArgumentException se il nodo sorgente s non esiste in g
	 * @throw IllegalArgumentException se il grafo g è orientato, non pesato o con
	 *        pesi negativi
	 */
	public void computeMSP(Graph<L> g, GraphNode<L> s) {
		// Se il grafo specificato è nullo
		if (g == null)
			throw new NullPointerException(
					"ERRORE: tentativo di calcolare il minimo albero di copertura di un grafo nullo!");
		// Se il nodo sorgente specificato è nullo
		if (s == null)
			throw new NullPointerException(
					"ERRORE: tentativo di calcolare il minimo albero di copertura di un grafo a partire da un nodo sorgente nullo!");
		// Se il nodo sorgente specificato non esiste nel grafo
		if (!g.containsNode(s))
			throw new IllegalArgumentException(
					"ERRORE: il nodo sorgente specificato non esiste nel grafo specificato!");
		// Se il grafo specificato è orientato
		if (g.isDirected())
			throw new IllegalArgumentException("ERRORE: il grafo specificato è orientato!");
		// Se il grafo specificato non è pesato
		if (!this.isWeighed(g))
			throw new IllegalArgumentException("ERRORE: il grafo specificato non è pesato!");
		// Se il grafo specificato ha pesi negativi
		if (!this.hasAllPositiveWeighs(g))
			throw new IllegalArgumentException("ERRORE: il grafo pesato specificato ha dei pesi negativi!");

		// Non controllo se il grafo specificato non è connesso perchè, in tal caso Prim
		// restituirà il MST della componente connessa in cui si trova il nodo sorgente

		// Inizializzazione nodi del grafo
		for (GraphNode<L> nodo : g.getNodes()) {
			// Inizializzazione nodo sorgente con padre nullo e priorità (peso minimo) a 0
			if (s.equals(nodo))
				nodo.setPriority(0);
			// Inizializzazione altri nodi con padre nullo e priorità a infinito
			else
				nodo.setPriority(Integer.MAX_VALUE);
			nodo.setPrevious(null);
			// Tutti i nodi nella coda all' inizio sono bianchi, cioè non ancora estratti
			nodo.setColor(GraphNode.COLOR_WHITE);
			// Inizialiazzione coda di min-priorità
			this.queue.insert(nodo);
		}
		// Scorro la coda di min-priorità fino a quando non è vuota
		while (this.queue.size() != 0) {
			// Estraggo il nodo con priorità minima e lo coloro di nero
			GraphNode<L> tmp = (GraphNode<L>) this.queue.extractMinimum();
			tmp.setColor(GraphNode.COLOR_BLACK);
			// Scopro i nodi adiacenti del nodo estratto dalla coda
			for (GraphNode<L> nodo : g.getAdjacentNodesOf(tmp))
				// Se il nodo adiacente al nodo estratto appartiene alla coda e il peso
				// (priorità) per arrivarci è minore di quello attualmente presente nella coda
				if (nodo.getColor() == GraphNode.COLOR_WHITE && this.pesoArco(g, tmp, nodo) < nodo.getPriority()) {
					// Aggiorno il padre e la priorità
					nodo.setPrevious(tmp);
					this.queue.decreasePriority(nodo, this.pesoArco(g, tmp, nodo));
				}
		}

	}

	// TODO implementare: inserire eventuali metodi privati per rendere
	// l'implementazione più modulare

	/**
	 * Verifica se il grafo specificato è pesato.
	 * 
	 * @param g il grafo di cui verfificare se è pesato
	 * 
	 * @return true se il grafo è pesato, false altrimenti
	 * 
	 */
	private boolean isWeighed(Graph<L> g) {
		// Verifico se almeno uno tra tutti gli archi del grafo non ha il peso
		for (GraphEdge<L> arco : g.getEdges())
			if (Double.isNaN(arco.getWeight()))
				return false;
		// Altrimenti il grafo è pesato
		return true;
	}

	/**
	 * Verifica se il grafo specificato pesato ha tutti i pesi positivi.
	 * 
	 * @param g il grafo pesato di cui verfificare se ha tutti i pesi positivi
	 * 
	 * @return true se il grafo ha tutti i pesi positivi, false altrimenti
	 * 
	 */
	private boolean hasAllPositiveWeighs(Graph<L> g) {
		// Verifico se almeno uno tra tutti gli archi del grafo ha peso negativo
		for (GraphEdge<L> arco : g.getEdges())
			if (arco.getWeight() < 0)
				return false;
		// Altrimenti il grafo ha tutti i pesi positivi
		return true;
	}

	/**
	 * Funzione di comodo che calcola il peso dell' arco (u,v) di due nodi u,v
	 * esistenti in un grafo non orientato. Sarebbe stato più corretto implementare
	 * questo metodo nella classe del grafo non orientato, ma potendo aggiungere
	 * solamente metodi private, l' ho aggiunto in questo classe.
	 * 
	 * @param g il grafo pesato
	 * 
	 * @param u nodo dell' arco (u,v)
	 * 
	 * @param v nodo dell' arco (u,v)
	 * 
	 * @return il peso dell' arco (u,v)
	 * 
	 */
	private double pesoArco(Graph<L> g, GraphNode<L> u, GraphNode<L> v) {
		// Arco temporaneo per restituire il peso
		GraphEdge<L> tmp = new GraphEdge<L>(u, v, false);
		// Scorro tutti gli archi e setto il peso dell' arco temporaneo formato dai nodi
		// u, v specificati
		for (GraphEdge<L> arco : g.getEdgesOf(u))
			if (arco.equals(tmp))
				tmp.setWeight(arco.getWeight());
		// Ritorno il peso dell' arco (u,v)
		return tmp.getWeight();
	}
}
