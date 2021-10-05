package it.unicam.cs.asdl2021.mp2TeseiTest;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class that solves an instance of the the El Mamun's Caravan problem using
 * dynamic programming.
 * 
 * Template: Daniele Marchei and Luca Tesei, Implementation: Piermichele Rosati
 * - piermichele.rosati@studenti.unicam.it
 *
 */
public class ElMamunCaravanSolver {

	// the expression to analyse
	private final Expression expression;

	// table to collect the optimal solution for each sub-problem,
	// protected just for Junit Testing purposes
	protected Integer[][] table;

	// table to record the chosen optimal solution among the optimal solution of
	// the sub-problems, protected just for JUnit Testing purposes
	protected Integer[][] tracebackTable;

	// flag indicating that the problem has been solved at least once
	private boolean solved;

	/**
	 * Create a solver for a specific expression.
	 * 
	 * @param expression The expression to work on
	 * @throws NullPointerException if the expression is null
	 */
	public ElMamunCaravanSolver(Expression expression) {
		if (expression == null)
			throw new NullPointerException("Creazione di solver con expression null");
		this.expression = expression;
		// Allocazione matrice soluzioni ottime dei sottoproblemi
		this.table = new Integer[expression.size()][expression.size()];
		// Allocazione matrice per eseguire il traceBack
		this.tracebackTable = new Integer[expression.size()][expression.size()];
		// All' inizio il problema non è stato risolto
		this.solved = false;
	}

	/**
	 * Returns the expression that this solver analyse.
	 * 
	 * @return the expression of this solver
	 */
	public Expression getExpression() {
		return this.expression;
	}

	/**
	 * Solve the problem on the expression of this solver by using a given objective
	 * function.
	 * 
	 * @param function The objective function to be used when deciding which
	 *                 candidate to choose
	 * @throws NullPointerException if the objective function is null
	 */
	public void solve(ObjectiveFunction function) {
		// Se la funzione specificata è nulla
		if (function == null)
			throw new NullPointerException("ERRORE: la funzione specificata è nulla!");
		// Inizializzazione diagonale principale della matrice
		for (int i = 0; i < this.expression.size(); i++) {
			for (int j = 0; j < this.expression.size(); j++) {
				// Se e[i] è una cifra
				if (i == j && this.expression.get(i).getType() == ItemType.DIGIT)
					this.table[i][j] = (Integer) this.expression.get(i).getValue();
			}
		}
		// Costruzione della soluzione ottima per tutti i sottoproblemi m[i][j] in
		// maniera bottom-up.
		for (int z = 0; z < this.expression.size(); z = z + 2) {
			for (int i = 0; i < this.expression.size() - 2 - z; i = i + 2) {
				int j = i + 2 + z;
				// Ad ogni ciclo devo prendere la soluzione ottima
				List<Integer> tmp = new ArrayList<Integer>();
				for (int k = 0; i + k + 2 <= j; k = k + 2) {
					// Aggiunta dei sottoproblemi alla lista temporanea
					// Se l' operatore è la somma
					if (this.expression.get(i + k + 1).getValue().equals("+"))
						tmp.add(this.table[i][i + k] + this.table[i + k + 2][j]);
					// Altrimenti è la moltiplicazione
					else
						tmp.add(this.table[i][i + k] * this.table[i + k + 2][j]);
					// Calcolo della soluzione ottima
					this.table[i][j] = function.getBest(tmp);
					// Bisogna moltiplicare per 2 l' indice ritornato da getBestIndex perchè a
					// questo metodo viene passata una lista ogni iterazione k del ciclo, quindi
					// all' iterazione 0 tmp(0), all' iterazione 2 tmp(1), e così via per k = 0, 2,
					// 4, ... In pratica l' indice ritornato moltiplicato per 2 è uguale a k
					this.tracebackTable[i][j] = function.getBestIndex(tmp) * 2;
				}
			}
		}
		// Almeno un problema è stato risolto da questo solver
		this.solved = true;
	}

	/**
	 * Returns the current optimal value for the expression of this solver. The
	 * value corresponds to the one obtained after the last solving (which used a
	 * particular objective function).
	 * 
	 * @return the current optimal value
	 * @throws IllegalStateException if the problem has never been solved
	 */
	public int getOptimalSolution() {
		// Se almeno un problema non è stato ancora risolto da questo solver
		if (!this.solved)
			throw new IllegalStateException("ERRORE: il problema non è stato ancora risolto!");
		// Altrimenti se il problema è stato risolto ritorno la soluzione ottima
		return this.table[0][this.expression.size() - 1];
	}

	/**
	 * Returns an optimal parenthesization corresponding to an optimal solution of
	 * the expression of this solver. The parenthesization corresponds to the
	 * optimal value obtained after the last solving (which used a particular
	 * objective function).
	 * 
	 * If the expression is just a digit then the parenthesization is the expression
	 * itself. If the expression is not just a digit then the parethesization is of
	 * the form "(<parenthesization>)". Examples: "1", "(1+2)", "(1*(2+(3*4)))"
	 * 
	 * @return the current optimal parenthesization for the expression of this
	 *         solver
	 * @throws IllegalStateException if the problem has never been solved
	 */
	public String getOptimalParenthesization() {
		// Se almeno un problema non è stato ancora risolto da questo solver
		if (!this.solved)
			throw new IllegalStateException("ERRORE: il problema non è stato ancora risolto!");
		// Altrimenti se il problema è stato risolto richiamo il traceBack per (0,N-1),
		// ovvero per la soluzione ottima
		return this.traceBack(0, this.expression.size() - 1);
	}

	/**
	 * Determines if the problem has been solved at least once.
	 * 
	 * @return true if the problem has been solved at least once, false otherwise.
	 */
	public boolean isSolved() {
		return this.solved;
	}

	@Override
	public String toString() {
		return "ElMamunCaravanSolver for " + expression;
	}

	// TODO implementare: inserire eventuali metodi privati per rendere
	// l'implementazione più modulare

	/**
	 * Funzione di comodo che esegue il traceBack della soluzione ottima.
	 * 
	 * @param i l' indice [i] della table
	 * 
	 * @param j l' indice [j] della table
	 * 
	 * @return la stringa contenente la parantesizzazione che da il risultato in
	 *         base alla funzione in questione(MaximumFunction oppure
	 *         MinimumFunction)
	 */
	private String traceBack(int i, int j) {
		// Se [i][i] allora ritorno e[i]
		if (i == j)
			return this.expression.get(i).getValue().toString();
		// Altrimenti eseguo il traceBack per i sottoproblemi m[i][i + k ] e[i + k + 1]
		// m[i + k + 2][j]
		return "(" + this.traceBack(i, i + this.tracebackTable[i][j])
				+ this.expression.get(i + this.tracebackTable[i][j] + 1).getValue()
				+ this.traceBack(i + this.tracebackTable[i][j] + 2, j) + ")";
	}

}
