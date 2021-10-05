package it.unicam.cs.asdl2021.mp2TeseiTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Un oggetto di questa classe singoletto è un attore che trova le componenti
 * fortemente connesse in un grafo orientato che viene passato come parametro.
 * 
 * @author Template: Luca Tesei, Implementation: Piermichele Rosati -
 *         piermichele.rosati@studenti.unicam.it
 *
 */
public class StronglyConnectedComponentsFinder<L> {

	/*
	 * NOTA: per tutti i metodi che ritornano un set utilizzare la classe HashSet<E>
	 * per creare l'insieme risultato. Questo garantisce un buon funzionamento dei
	 * test JUnit che controllano l'uguaglianza tra insiemi
	 */

	// Per registrare informazioni temporali di ogni nodo
	private int time;

	/**
	 * Dato un grafo orientato determina l'insieme di tutte le componenti fortemente
	 * connesse dello stesso.
	 * 
	 * @param g un grafo orientato
	 * @return l'insieme di tutte le componenti fortemente connesse di g dove ogni
	 *         componente fortemente connessa è rappresentata dall'insieme dei nodi
	 *         che la compongono.
	 * @throws IllegalArgumentException se il grafo passato non è orientato
	 * @throws NullPointerException     se il grafo passato è nullo
	 */
	public Set<Set<GraphNode<L>>> findStronglyConnectedComponents(Graph<L> g) {
		// Se il grafo specificato è nullo
		if (g == null)
			throw new NullPointerException("ERRORE: il grafo specificato è nullo!");
		// Se il grafo specificato è non orientato
		if (!g.isDirected())
			throw new IllegalArgumentException("ERRORE: il grafo specificato è non orientato!");
		// Chiamo la DFS sul grafo specificato
		this.DFS(g);
		// Calcolo il grafo trasposto del grafo specificato
		Graph<L> gT = this.generaGrafoTrasposto(g);
		// Chiamo la DFS sul grafo trasposto per generare le componenti fortemente
		// connesse
		Set<Set<GraphNode<L>>> scc = new HashSet<Set<GraphNode<L>>>();
		this.DFSGrafoTrasposto(gT, scc);
		// Ritorno le componenti fortemente connesse
		return scc;
	}

	// TODO implementare: inserire eventuali metodi privati per rendere
	// l'implementazione più modulare

	/**
	 * Esegue la visita in profondità di un certo grafo. Setta i seguenti valori
	 * associati ai nodi: tempo di scoperta, tempo di completamento, predecessore.
	 * 
	 * @param g il grafo da visitare.
	 */
	private void DFS(Graph<L> g) {
		// Inizializzo i colori e i predecessori di ogni nodo
		for (GraphNode<L> u : g.getNodes()) {
			u.setColor(GraphNode.COLOR_WHITE);
			u.setPrevious(null);
		}
		this.time = 0;
		// Visito in profontià ogni nodo ancora non scoperto
		for (GraphNode<L> u : g.getNodes()) {
			if (u.getColor() == GraphNode.COLOR_WHITE)
				this.DFSVisit(g, u);
		}
	}

	/**
	 * Esegue la visita in profondità di un certo grafo trasposto. A differenza
	 * della DFS, setta solamente i nodi predecessori. Scorre i nodi in ordine
	 * decrescente rispetto i tempi di completamento dei nodi (calcolati eseguendo
	 * la DFS nel grafo originale). Prende come parametro il set delle componenti
	 * fortemente connesse, ovvero un set contenente tutti set, in cui ogni set
	 * "interno" è una componente fortemente connessa.
	 * 
	 * @param g   il grafo trasposto da visitare
	 * 
	 * @param scc l' insieme delle componenti fortemente connesse del grafo
	 */
	private void DFSGrafoTrasposto(Graph<L> gT, Set<Set<GraphNode<L>>> scc) {
		// Inizializzo i colori e i predecessori di ogni nodo
		for (GraphNode<L> u : gT.getNodes()) {
			u.setColor(GraphNode.COLOR_WHITE);
			u.setPrevious(null);
		}
		// Ordinamento nodi in ordine decrescente rispetto ai tempi di completamento
		ArrayList<GraphNode<L>> nodiOrdinati = this.ordinaNodi(gT.getNodes());
		// Per ogni nodo u ancora non scoperto creo un un set, che sarà la componente
		// fortemente connessa a cui il nodo appartiene
		for (GraphNode<L> u : nodiOrdinati) {
			if (u.getColor() == GraphNode.COLOR_WHITE) {
				Set<GraphNode<L>> set = new HashSet<GraphNode<L>>();
				this.DFSVisitGrafoTrasposto(gT, u, set);
				// Quando il nodo è stato visitato lo aggiungo alla sua componente fortemente
				// connessa
				scc.add(set);
			}

		}
	}

	/**
	 * Esegue la DFS ricorsivamente sul nodo specificato.
	 * 
	 * @param g il grafo
	 * 
	 * @param u il nodo su cui parte la DFS
	 */
	private void DFSVisit(Graph<L> g, GraphNode<L> u) {
		// Il nodo u viene appena scoperto
		this.time++;
		u.setEnteringTime(time);
		u.setColor(GraphNode.COLOR_GREY);
		// Ispezione dell' arco (u,v)
		for (GraphNode<L> v : g.getAdjacentNodesOf(u)) {
			// Se il nodo non è stato scoperto, lo visito
			if (v.getColor() == GraphNode.COLOR_WHITE) {
				v.setPrevious(u);
				this.DFSVisit(g, v);
			}
		}
		// Visita completata
		u.setColor(GraphNode.COLOR_BLACK);
		this.time++;
		u.setExitingTime(this.time);
	}

	/**
	 * Esegue la DFS di un grafo trasposto ricorsivamente sul nodo specificato.
	 * 
	 * @param gT  il grafo trasposto
	 * 
	 * @param u   il nodo su cui parte la DFS
	 * 
	 * @param set la componente fortemente connessa del nodo u
	 */
	private void DFSVisitGrafoTrasposto(Graph<L> gT, GraphNode<L> u, Set<GraphNode<L>> set) {
		/// Il nodo u viene appena scoperto
		u.setColor(GraphNode.COLOR_GREY);
		// Ispezione dell' arco (u,v)
		for (GraphNode<L> v : gT.getAdjacentNodesOf(u)) {
			// Se il nodo non è stato scoperto, lo visito
			if (v.getColor() == GraphNode.COLOR_WHITE) {
				v.setPrevious(u);
				this.DFSVisitGrafoTrasposto(gT, v, set);
			}
		}
		// Visita completata
		u.setColor(GraphNode.COLOR_BLACK);
		// Il nodo viene aggiungo alla sua componente fortemente connessa
		set.add(u);
	}

	/**
	 * Genera il grafo trasposto del grafo passato come parametro.
	 * 
	 * @param g il grafo
	 * 
	 * @return gT il grafo trasposto di g
	 * 
	 */
	private Graph<L> generaGrafoTrasposto(Graph<L> g) {
		// Per ogni vertice cambio la direzione nel verso opposto
		Graph<L> gT = new MapAdjacentListDirectedGraph<L>();
		// Per ogni nodo u, scorro tutti i suoi nodi v adiacenti
		// Aggiungo il nodo v adiacente a u e associo a v l' arco (v,u)
		for (GraphNode<L> u : g.getNodes()) {
			gT.addNode(u);
			for (GraphNode<L> v : g.getAdjacentNodesOf(u)) {
				gT.addNode(v);
				GraphEdge<L> arco = new GraphEdge<>(v, u, true);
				gT.addEdge(arco);
			}
		}
		// Ritorno il grafo trasposto
		return gT;
	}

	/**
	 * Funzione di comodo che ordina i nodi in ordine decrescente rispetto i tempi
	 * di completamento dopo la DFS sul grafo. Come algoritmo di ordinamento è stato
	 * scelto l' InsertionSort, che in questo caso ordina i tempi di completamento
	 * dei nodi in ordine decrescente.
	 * 
	 * @param nodiDaOrdinare il set di nodi da ordinare in ordine decrescente
	 *                       rispetto ai tempi di completamento
	 * 
	 * @return nodiOrdinati un arrayList di nodi ordinati in ordine decrescente
	 *         rispetto ai tempi di completamento
	 * 
	 * 
	 */
	private ArrayList<GraphNode<L>> ordinaNodi(Set<GraphNode<L>> nodiDaOrdinare) {
		// ArrayList temporaneo contente i nodi da ordinare
		ArrayList<GraphNode<L>> nodiOrdinati = new ArrayList<GraphNode<L>>();
		nodiOrdinati.addAll(nodiDaOrdinare);
		// InsertionSort in ordina decrescente rispetto ai tempi di completamento
		for (int i = 1; i < nodiOrdinati.size(); i++) {
			GraphNode<L> tmp = nodiOrdinati.get(i);
			int j = i - 1;
			while (j >= 0 && nodiOrdinati.get(j).getExitingTime() < tmp.getExitingTime()) {
				nodiOrdinati.set(j + 1, nodiOrdinati.get(j));
				j--;
			}
			if (j != i - 1)
				nodiOrdinati.set(j + 1, tmp);
		}
		// Nodi ordinati in ordine decrescente rispetto ai tempi di completamento di
		// ogni nodo
		return nodiOrdinati;
	}

}
