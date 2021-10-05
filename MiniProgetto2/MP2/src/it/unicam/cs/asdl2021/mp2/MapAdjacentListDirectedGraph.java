package it.unicam.cs.asdl2021.mp2TeseiTest;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementazione della classe astratta {@code Graph<L>} che realizza un grafo
 * orientato. Non sono accettate etichette dei nodi null e non sono accettate
 * etichette duplicate nei nodi (che in quel caso sono lo stesso nodo).
 * 
 * Per la rappresentazione viene usata una variante della rappresentazione con
 * liste di adiacenza. A differenza della rappresentazione standard si usano
 * strutture dati più efficienti per quanto riguarda la complessità in tempo
 * della ricerca se un nodo è presente (pseudocostante, con tabella hash) e se
 * un arco è presente (pseudocostante, con tabella hash). Lo spazio occupato per
 * la rappresentazione risultà tuttavia più grande di quello che servirebbe con
 * la rappresentazione standard.
 * 
 * Le liste di adiacenza sono rappresentate con una mappa (implementata con
 * tabelle hash) che associa ad ogni nodo del grafo i nodi adiacenti. In questo
 * modo il dominio delle chiavi della mappa è l'insieme dei nodi, su cui è
 * possibile chiamare il metodo contains per testare la presenza o meno di un
 * nodo. Ad ogni chiave della mappa, cioè ad ogni nodo del grafo, non è
 * associata una lista concatenata dei nodi collegati, ma un set di oggetti
 * della classe GraphEdge<L> che rappresentano gli archi uscenti dal nodo: in
 * questo modo la rappresentazione riesce a contenere anche l'eventuale peso
 * dell'arco (memorizzato nell'oggetto della classe GraphEdge<L>). Per
 * controllare se un arco è presente basta richiamare il metodo contains in
 * questo set. I test di presenza si basano sui metodi equals ridefiniti per
 * nodi e archi nelle classi GraphNode<L> e GraphEdge<L>.
 * 
 * Questa classe non supporta le operazioni di rimozione di nodi e archi e le
 * operazioni indicizzate di ricerca di nodi e archi.
 * 
 * @author Template: Luca Tesei, Implementation: Piermichele Rosati -
 *         piermichele.rosati@studenti.unicam.it
 *
 * @param <L> etichette dei nodi del grafo
 */
public class MapAdjacentListDirectedGraph<L> extends Graph<L> {

	/*
	 * Le liste di adiacenza sono rappresentate con una mappa. Ogni nodo viene
	 * associato con l'insieme degli archi uscenti. Nel caso in cui un nodo non
	 * abbia archi uscenti è associato con un insieme vuoto. La variabile istanza è
	 * protected solo per scopi di test JUnit.
	 */
	protected final Map<GraphNode<L>, Set<GraphEdge<L>>> adjacentLists;

	/*
	 * NOTA: per tutti i metodi che ritornano un set utilizzare la classe HashSet<E>
	 * per creare l'insieme risultato. Questo garantisce un buon funzionamento dei
	 * test JUnit che controllano l'uguaglianza tra insiemi
	 */

	/**
	 * Crea un grafo vuoto.
	 */
	public MapAdjacentListDirectedGraph() {
		// Inizializza la mappa con la mappa vuota
		this.adjacentLists = new HashMap<GraphNode<L>, Set<GraphEdge<L>>>();
	}

	@Override
	public int nodeCount() {
		// Il numero di nodi del grafo è la dimensione del set di key della map
		return this.adjacentLists.keySet().size();
	}

	@Override
	public int edgeCount() {
		// // Conto gli archi del grafo (eventuali cappi inclusi)
		return archiDelGrafo().size();
	}

	@Override
	public void clear() {
		this.adjacentLists.clear();
	}

	@Override
	public boolean isDirected() {
		// Questa classe implementa grafi orientati
		return true;
	}

	@Override
	public Set<GraphNode<L>> getNodes() {
		// Ritorna il set di nodi del grafo. Ritorna un set vuoto se il grafo non ha
		// nodi
		return this.adjacentLists.keySet();
	}

	@Override
	public boolean addNode(GraphNode<L> node) {
		// Se il nodo specificato è nullo
		if (node == null)
			throw new NullPointerException("ERRORE: tentativo di aggiungere un nodo nullo al grafo!");
		// Se il grafo non contiene il nodo lo aggiungo
		if (!this.containsNode(node)) {
			// All' inizio il nodo aggiunto al grafo non ha nodi adiacenti, quindi neanche
			// archi associati ad esso: avrà un set vuoto di archi associato ad esso
			Set<GraphEdge<L>> archi = new HashSet<GraphEdge<L>>();
			this.adjacentLists.put(node, archi);
			// Ho aggiunto il nodo con successo
			return true;
		}
		// Altrimenti non lo aggiungo
		return false;
	}

	@Override
	public boolean removeNode(GraphNode<L> node) {
		if (node == null)
			throw new NullPointerException("Tentativo di rimuovere un nodo null");
		throw new UnsupportedOperationException("Rimozione dei nodi non supportata");
	}

	@Override
	public boolean containsNode(GraphNode<L> node) {
		// Se il nodo specificato è nullo
		if (node == null)
			throw new NullPointerException("ERRORE: tentativo di ricerca di un nodo nullo!");
		// Altrimenti controllo nel set dei nodi se il nodo specificato è presente
		// oppure no
		return this.getNodes().contains(node);
	}

	@Override
	public GraphNode<L> getNodeOf(L label) {
		// Se l' etichetta specificata è nulla
		if (label == null)
			throw new NullPointerException("ERRORE: tentativo di restituire un nodo avente etichetta nulla!");
		// Scorro il set dei nodi
		for (GraphNode<L> nodo : this.getNodes())
			// Confronto l' etichetta specificata con quella del nodo attuale nel set dei
			// nodi
			if (label.equals(nodo.getLabel()))
				return nodo;
		// Se non trovo nessuna etichetta nel set dei nodi
		return null;
	}

	@Override
	public int getNodeIndexOf(L label) {
		if (label == null)
			throw new NullPointerException("Tentativo di ricercare un nodo con etichetta null");
		throw new UnsupportedOperationException("Ricerca dei nodi con indice non supportata");
	}

	@Override
	public GraphNode<L> getNodeAtIndex(int i) {
		throw new UnsupportedOperationException("Ricerca dei nodi con indice non supportata");
	}

	@Override
	public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
		// Se il nodo specificato è nullo
		if (node == null)
			throw new NullPointerException("ERRORE: tentativo di restituire nodi adiacenti a un nodo nullo!");
		// Se il nodo specificato non esiste nel grafo
		if (!this.containsNode(node))
			throw new IllegalArgumentException("ERRORE: il nodo specificato non esiste in questo grafo!");
		// Altrimenti il nodo esiste
		Set<GraphNode<L>> nodiAdiacenti = new HashSet<GraphNode<L>>();
		// Scorro tutti gli archi (u,v) del nodo u e aggiungo l' arco v al set da
		// ritornare
		for (GraphEdge<L> arco : this.getEdgesOf(node))
			nodiAdiacenti.add(arco.getNode2());
		return nodiAdiacenti;
	}

	@Override
	public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
		// Se il grafo non è orientato
		if (!this.isDirected())
			throw new UnsupportedOperationException("ERRORE: il grafo non è orientato!");
		// Se il nodo specificato è nullo
		if (node == null)
			throw new NullPointerException("ERRORE: tentativo di restituire nodi predecessori a un nodo nullo!");
		// Se il nodo specificato non esiste nel grafo
		if (!this.containsNode(node))
			throw new IllegalArgumentException("ERRORE: il nodo specificato non esiste in questo grafo!");
		// Altrimenti restituisco il set di nodi che entrano nel nodo specificato
		// Per il nodo u scorro il set degli archi (u,v) e confronto il nodo v con
		// quello specificato
		Set<GraphNode<L>> nodiPredecessori = new HashSet<GraphNode<L>>();
		for (GraphNode<L> nodo : this.getNodes())
			for (GraphEdge<L> arco : this.getEdgesOf(nodo))
				// Se nell' arco (u,v) v è uguale al nodo specificato
				if (node.equals(arco.getNode2()))
					// Aggiungo u al set perchè è u che entra in v, e quindi nel nodo specificato
					nodiPredecessori.add(arco.getNode1());
		// Ritorno il set dei nodi predecessori
		return nodiPredecessori;
	}

	@Override
	public Set<GraphEdge<L>> getEdges() {
		// Ritorna il set di archi di questo grafo (eventuali cappi inclusi)
		return archiDelGrafo();
	}

	@Override
	public boolean addEdge(GraphEdge<L> edge) {
		// Se l' arco specificato è nullo
		if (edge == null)
			throw new NullPointerException("ERRORE: tentativo di aggiungere un arco nullo al grafo!");
		// Se l' arco è orientato e questo grafo non è orientato
		if (edge.isDirected() && !this.isDirected())
			throw new IllegalArgumentException("ERRORE: l' arco è orientato ma il grafo è non orientato!");
		// Se l' arco non è orientato e questo grafo è orientato
		if (!edge.isDirected() && this.isDirected())
			throw new IllegalArgumentException("ERRORE: l' arco è orientato ma il grafo è non orientato!");
		// Se l'arco esiste già nel grafo
		if (this.containsEdge(edge))
			return false;
		// Altrimenti lo aggiungo al set del nodo in questione
		return this.adjacentLists.get(edge.getNode1()).add(edge);
	}

	@Override
	public boolean removeEdge(GraphEdge<L> edge) {
		throw new UnsupportedOperationException("Rimozione degli archi non supportata");
	}

	@Override
	public boolean containsEdge(GraphEdge<L> edge) {
		// Se l' arco specificato è nullo
		if (edge == null)
			throw new NullPointerException("ERRORE: tentativo di ricerca di un arco nullo nel grafo!");
		// Se almeno uno dei due nodi specificati nell' arco non esiste
		if (!this.containsNode(edge.getNode1()))
			throw new IllegalArgumentException("ERRORE: il nodo " + edge.getNode1() + " non esiste in questo grafo!");
		if (!this.containsNode(edge.getNode2()))
			throw new IllegalArgumentException("ERRORE: il nodo " + edge.getNode2() + " non esiste in questo grafo!");
		// Cerco nel set degli archi (u,v) del nodo u se l' arco specificato è presente
		return this.getEdgesOf(edge.getNode1()).contains(edge);
	}

	@Override
	public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
		// Se il nodo specificato è nullo
		if (node == null)
			throw new NullPointerException("ERRORE: tentativo di restituire gli archi associati a un nodo nullo!");
		// Se il nodo specificato non esiste nel grafo
		if (!this.containsNode(node))
			throw new IllegalArgumentException("ERRORE: il nodo specificato non esiste!");
		// Restituisco il set degli archi uscenti del nodo specificato
		return this.adjacentLists.get(node);
	}

	@Override
	public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
		// Se il grafo non è orientato
		if (!this.isDirected())
			throw new UnsupportedOperationException("ERRORE: il grafo non è orientato!");
		// Se il nodo specificato è nullo
		if (node == null)
			throw new NullPointerException("ERRORE: tentativo di restituire gli archi entranti in un nodo nullo!");
		// Se il nodo specificato non esiste nel grafo
		if (!this.containsNode(node))
			throw new IllegalArgumentException("ERRORE: il nodo specificato non esiste!");
		// Per ogni nodo scorro gli archi ad esso associati
		// Confronto, per ogni arco (u,v) il nodo v con il nodo specificato, se uguale
		// lo aggiungo al set da ritornare
		Set<GraphEdge<L>> archiEntranti = new HashSet<GraphEdge<L>>();
		for (GraphNode<L> nodo : this.getNodes())
			for (GraphEdge<L> arco : this.getEdgesOf(nodo))
				if (node.equals(arco.getNode2()))
					archiEntranti.add(arco);
		return archiEntranti;
	}

	/**
	 * Funzione di comodo che ritorna il set di archi di questo grafo. Questo metodo
	 * è utilizzato per ritornare il set di archi di questo grafo, dunque anche per
	 * ritornare il numero di archi di questo grafo
	 * 
	 * @return il set di archi di questo grafo
	 * 
	 */
	private Set<GraphEdge<L>> archiDelGrafo() {
		Set<GraphEdge<L>> archi = new HashSet<GraphEdge<L>>();
		// Scorro il set dei nodi e aggiungo tutti gli archi di tutti i nodi al set da
		// ritornare
		for (GraphNode<L> nodo : this.getNodes())
			archi.addAll(this.getEdgesOf(nodo));
		return archi;
	}
}
