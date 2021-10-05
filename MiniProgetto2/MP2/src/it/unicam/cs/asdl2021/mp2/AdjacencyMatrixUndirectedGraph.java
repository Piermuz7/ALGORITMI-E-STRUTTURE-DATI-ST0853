package it.unicam.cs.asdl2021.mp2TeseiTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe che implementa un grafo non orientato tramite matrice di adiacenza.
 * Non sono accettate etichette dei nodi null e non sono accettate etichette
 * duplicate nei nodi (che in quel caso sono lo stesso nodo).
 * 
 * I nodi sono indicizzati da 0 a nodeCount() - 1 seguendo l'ordine del loro
 * inserimento (0 è l'indice del primo nodo inserito, 1 del secondo e così via)
 * e quindi in ogni istante la matrice di adiacenza ha dimensione nodeCount() *
 * nodeCount(). La matrice, sempre quadrata, deve quindi aumentare di dimensione
 * ad ogni inserimento di un nodo. Per questo non è rappresentata tramite array
 * ma tramite ArrayList.
 * 
 * Gli oggetti GraphNode<L>, cioè i nodi, sono memorizzati in una mappa che
 * associa ad ogni nodo l'indice assegnato in fase di inserimento. Il dominio
 * della mappa rappresenta quindi l'insieme dei nodi.
 * 
 * Gli archi sono memorizzati nella matrice di adiacenza. A differenza della
 * rappresentazione standard con matrice di adiacenza, la posizione i,j della
 * matrice non contiene un flag di presenza, ma è null se i nodi i e j non sono
 * collegati da un arco e contiene un oggetto della classe GraphEdge<L> se lo
 * sono. Tale oggetto rappresenta l'arco. Un oggetto uguale (secondo equals) e
 * con lo stesso peso (se gli archi sono pesati) deve essere presente nella
 * posizione j, i della matrice.
 * 
 * Questa classe non supporta i metodi di cancellazione di nodi e archi, ma
 * supporta tutti i metodi che usano indici, utilizzando l'indice assegnato a
 * ogni nodo in fase di inserimento.
 * 
 * @author Template: Luca Tesei, Implementation: Piermichele Rosati -
 *         piermichele.rosati@studenti.unicam.it
 *
 */
public class AdjacencyMatrixUndirectedGraph<L> extends Graph<L> {
	/*
	 * Le seguenti variabili istanza sono protected al solo scopo di agevolare il
	 * JUnit testing
	 */

	// Insieme dei nodi e associazione di ogni nodo con il proprio indice nella
	// matrice di adiacenza
	protected Map<GraphNode<L>, Integer> nodesIndex;

	// Matrice di adiacenza, gli elementi sono null o oggetti della classe
	// GraphEdge<L>. L'uso di ArrayList permette alla matrice di aumentare di
	// dimensione gradualmente ad ogni inserimento di un nuovo nodo.
	protected ArrayList<ArrayList<GraphEdge<L>>> matrix;

	/*
	 * NOTA: per tutti i metodi che ritornano un set utilizzare la classe HashSet<E>
	 * per creare l'insieme risultato. Questo garantisce un buon funzionamento dei
	 * test JUnit che controllano l'uguaglianza tra insiemi
	 */

	/**
	 * Crea un grafo vuoto.
	 */
	public AdjacencyMatrixUndirectedGraph() {
		this.matrix = new ArrayList<ArrayList<GraphEdge<L>>>();
		this.nodesIndex = new HashMap<GraphNode<L>, Integer>();
	}

	@Override
	public int nodeCount() {
		// Il numero di nodi del grafo è il numero di righe (o colonne) della matrice
		// quadrata N x N
		return this.matrix.size();
	}

	@Override
	public int edgeCount() {
		// Conto gli archi sopra la diagonale principale (diagonale principale inclusa
		// per l' ammissione di eventuali cappi)
		return archiSopraLaDiagonalePrincipale().size();
	}

	@Override
	public void clear() {
		this.matrix = new ArrayList<ArrayList<GraphEdge<L>>>();
		this.nodesIndex = new HashMap<GraphNode<L>, Integer>();
	}

	@Override
	public boolean isDirected() {
		// Questa classe implementa un grafo non orientato
		return false;
	}

	@Override
	public Set<GraphNode<L>> getNodes() {
		// Ritorno il set delle chiavi della map, ovvero i nodi
		return this.nodesIndex.keySet();
	}

	@Override
	public boolean addNode(GraphNode<L> node) {
		// Se il nodo specificato è nullo
		if (node == null)
			throw new NullPointerException("ERRORE: tentativo di aggiungere un nodo nullo a questo grafo!");
		// Se il nodo già esiste in questo grafo non lo aggiungo
		if (this.containsNode(node))
			return false;
		// Altrimenti lo aggiungo nella map e nella matrice di adiacenza
		// Nella map gli associo il valore intero che corrisponde al numero di nodi
		// attuali (cioè all' ordine della matrice quadrata)
		this.nodesIndex.put(node, this.nodeCount());
		ArrayList<GraphEdge<L>> nuovaRiga = new ArrayList<GraphEdge<L>>();
		// Riempio la nuova riga di archi nulli
		for (int i = 0; i < this.matrix.size(); i++)
			nuovaRiga.add(null);
		// Aggiungo la riga alla matrice
		this.matrix.add(nuovaRiga);
		// Aggiorno tutte le righe (compresa la nuova riga) della matrice delle
		// adiacenze aggiungendo a ogni riga una colonna null
		for (ArrayList<GraphEdge<L>> riga : this.matrix)
			riga.add(null);
		// Nodo aggiunto con successo
		return true;
	}

	@Override
	public boolean removeNode(GraphNode<L> node) {
		throw new UnsupportedOperationException("Remove di nodi non supportata");
	}

	@Override
	public boolean containsNode(GraphNode<L> node) {
		// Se il nodo specificato è nullo
		if (node == null)
			throw new NullPointerException("ERRORE: tentativo di ricercare un nodo nullo in questo grafo!");
		// Cerco il nodo nel set dei nodi di questo grafo
		return this.getNodes().contains(node);
	}

	@Override
	public GraphNode<L> getNodeOf(L label) {
		// Se l' etichetta specificata è nulla
		if (label == null)
			throw new NullPointerException("ERRORE: tentativo di restituire un nodo associato a un' etichetta nullA!");
		// Scorro tutti i nodi di questo grafo e confronto l' etichetta di ogni nodo con
		// quella specificata
		for (GraphNode<L> nodo : this.getNodes())
			// Se le etichette sono uguali ritorno il nodo attuale
			if (label.equals(nodo.getLabel()))
				return nodo;
		// Altrimenti non ho trovato il nodo
		return null;
	}

	@Override
	public int getNodeIndexOf(L label) {
		// Se un nodo con l' etichetta specificata non esiste in questo grafo
		// Se viene passata un' etichetta nulla getNodeOf lancierà la
		// NullPointerException
		if (this.getNodeOf(label) == null)
			throw new IllegalArgumentException(
					"ERRORE: l' etichetta specificata non esiste in nessun nodo di questo grafo!");
		// Cerco l' etichetta del nodo e poi cerco nella map il valore della chiave,
		// cioè del nodo trovato
		return this.nodesIndex.get(getNodeOf(label));
	}

	@Override
	public GraphNode<L> getNodeAtIndex(int i) {
		// Se l' indice specificato è fuori dal range [0...N-1], dove N è la grandezza
		// della matrice
		if (i < 0 || i >= this.nodeCount())
			throw new IndexOutOfBoundsException(
					"ERRORE: l' indice specificato è < di 0 o > della grandezza della matrice");
		// Scorro ogni nodo nella map e confronto il proprio indice associato con quello
		// specificato
		GraphNode<L> nodoAllIndice = null;
		for (GraphNode<L> nodo : this.getNodes())
			if (indiceDelNodo(nodo) == i)
				nodoAllIndice = nodo;
		// Ritorno il nodo all' indice i
		return nodoAllIndice;
	}

	@Override
	public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
		// Se il nodo specificato è nullo
		if (node == null)
			throw new NullPointerException("ERRORE: tentativo di restituire nodi adiacenti a un nodo nullo!");
		// Se il nodo specificato non esiste
		if (!this.containsNode(node))
			throw new IllegalArgumentException(
					"ERRORE: tentativo di restituire nodi adiacenti a un nodo che non esiste in questo grafo!");
		Set<GraphNode<L>> nodiAdiacenti = new HashSet<GraphNode<L>>();
		// Scorro alla riga del nodo specificato, nella matrice e per mi salvo per ogni
		// arco (u,v) l' arco v nel set da ritornare
		for (GraphEdge<L> arco : this.matrix.get(this.indiceDelNodo(node)))
			if (arco != null) {
				// Confronto nell' arco (u,v) i nodi u e v con il nodo specificato
				// e mi salvo il nodo che è diverso da quello specificsato
				// Se u = node, mi salvo v
				if (node.equals(arco.getNode1()))
					nodiAdiacenti.add(arco.getNode2());
				// Altrimenti mi salvo u perchè v = node
				else
					nodiAdiacenti.add(arco.getNode1());
			}
		// Ritorno il set dei nodi adiacenti al nodo specificato
		return nodiAdiacenti;
	}

	@Override
	public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
		throw new UnsupportedOperationException("Operazione non supportata in un grafo non orientato");
	}

	@Override
	public Set<GraphEdge<L>> getEdges() {
		// Ritorno gli archi sopra la diagonale principale (diagonale principale inclusa
		// per l' ammissione di eventuali cappi)
		return archiSopraLaDiagonalePrincipale();
	}

	@Override
	public boolean addEdge(GraphEdge<L> edge) {
		// Se l' arco specificato è nullo
		if (edge == null)
			throw new NullPointerException("ERRORE: tentativo di aggiungere un arco nullo a questo grafo!");
		// Se l'arco è orientato e questo grafo non è orientato
		if (edge.isDirected() && !this.isDirected())
			throw new IllegalArgumentException(
					"ERRORE: l' arco specificato è orientato mentre questo grafo è non orientato!");
		// Se l'arco non è orientato e questo grafo è orientato
		if (!edge.isDirected() && this.isDirected())
			throw new IllegalArgumentException(
					"ERRORE: l' arco specificato non è orientato mentre questo grafo è orientato!");
		// Se l' arco non è già presente in questo grafo lo aggiungo
		if (!this.containsEdge(edge)) {
			// Prendo gli indici dei nodi nella map e inserisco gli archi nella matrice di
			// adiacenza
			int i = this.indiceDelNodo(edge.getNode1());
			int j = this.indiceDelNodo(edge.getNode2());
			// Aggiungo l' arco alla cella [i][j]
			this.matrix.get(i).set(j, edge);
			// Aggiungo l' arco alla cella [j][i]
			this.matrix.get(j).set(i, edge);
			return true;
		}
		// Altrimenti l' arco era già presente
		return false;
	}

	@Override
	public boolean removeEdge(GraphEdge<L> edge) {
		throw new UnsupportedOperationException("Operazione di remove non supportata in questa classe");
	}

	@Override
	public boolean containsEdge(GraphEdge<L> edge) {
		// Se l' arco specificato è nullo
		if (edge == null)
			throw new NullPointerException("ERRORE: tentativo di aggiungere un arco nullo a questo grafo!");
		// Se almeno uno dei due nodi specificati nell'arco non esiste
		if (!this.containsNode(edge.getNode1()))
			throw new IllegalArgumentException("ERRORE: il nodo " + edge.getNode1() + " non esiste in questo grafo!");
		if (!this.containsNode(edge.getNode2()))
			throw new IllegalArgumentException("ERRORE: il nodo " + edge.getNode2() + " non esiste in questo grafo!");
		// Altrimenti lo cerco nel set degli archi
		return this.getEdges().contains(edge);
	}

	@Override
	public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
		// Se il nodo specificato è nullo
		if (node == null)
			throw new NullPointerException("ERRORE: tentativo di restituire gli archi connessi a un nodo nullo!");
		// Se il nodo non esiste in questo grafo
		if (!this.containsNode(node))
			throw new IllegalArgumentException("ERRORE: il nodo specificato non esiste!");
		// Altrimenti ritorno il set degli archi connessi al nodo specificato
		Set<GraphEdge<L>> archiConnessi = new HashSet<GraphEdge<L>>();
		int i = this.indiceDelNodo(node);
		for (GraphEdge<L> arco : this.matrix.get(i))
			if (arco != null)
				archiConnessi.add(arco);
		// Ritorno il set degli archi connessi al nodo specificato
		return archiConnessi;
	}

	@Override
	public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
		throw new UnsupportedOperationException("Operazione non supportata in un grafo non orientato");
	}

	/**
	 * Funzione di comodo che ritorna il set di archi di questo grafo. Questo metodo
	 * è utilizzato per ritornare il set di archi di questo grafo, dunque anche per
	 * ritornare il numero di archi di questo grafo
	 * 
	 * @return il set di archi di questo grafo
	 */
	private Set<GraphEdge<L>> archiSopraLaDiagonalePrincipale() {
		Set<GraphEdge<L>> archi = new HashSet<GraphEdge<L>>();
		// Scorro sopra la diagonale principale e ogni volta che trovo un arco lo
		// aggiungo al set di archi da ritornare
		for (int i = 0; i < this.matrix.size(); i++)
			for (int j = i; j < this.matrix.size(); j++)
				if (this.matrix.get(i).get(j) != null)
					archi.add(this.matrix.get(i).get(j));
		return archi;
	}

	/**
	 * Funzione di comodo che ritorna l' indice del nodo specificato
	 * 
	 * @param node il nodo di cui ritornare l' indice
	 * 
	 * @return l' indice del nodo nella map, e ovviamente nella matrice
	 */
	private int indiceDelNodo(GraphNode<L> node) {
		// Ritorno l' indice del nodo associato nella map
		return this.nodesIndex.get(node);
	}

}
