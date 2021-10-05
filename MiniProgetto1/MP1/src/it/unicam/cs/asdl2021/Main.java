package it.unicam.cs.asdl2021.mp1;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) {
		/*
		 * BalancedParenthesesChecker b = new BalancedParenthesesChecker(); String s =
		 * " (( [( {\t (\t) [ ] } ) \n ] ) )"; System.out.println(b.check(s));
		 */

		/*
		 * ASDL2021Deque<Integer> deque = new ASDL2021Deque<Integer>(); deque.add(0);
		 * deque.add(1); deque.add(1); deque.add(2); deque.add(3);
		 * 
		 * Collection<Integer> c = new LinkedList<Integer>(); c.add(0); c.add(2);
		 * c.add(1); c.add(1); System.out.println(c.size());
		 * System.out.println(deque.containsAll(c));
		 */

		TernaryHeapMinPriorityQueue minheap = new TernaryHeapMinPriorityQueue();
		Job j0 = new Job("JOB1", 1.0);
		j0.setPriority(1);
		Job j1 = new Job("JOB2", 1.1);
		j1.setPriority(2);
		Job j2 = new Job("JOB3", 1.2);
		j2.setPriority(3);
		Job j3 = new Job("JOB4", 1.3);
		j3.setPriority(4);
		Job j4 = new Job("JOB5", 1.0);
		j4.setPriority(5);
		Job j5 = new Job("JOB6", 1.0);
		j5.setPriority(6);
		Job j6 = new Job("JOB7", 1.0);
		j6.setPriority(7);
		Job j7 = new Job("JOB8", 1.0);
		j7.setPriority(8);
		Job j8 = new Job("JOB9", 1.0);
		j8.setPriority(9);
		Job j9 = new Job("JOB10", 1.0);
		j9.setPriority(10);
		Job j10 = new Job("JOB11", 1.0);
		j10.setPriority(11);
		Job j11 = new Job("JOB12", 1.0);
		j11.setPriority(12);
		Job j12 = new Job("JOB13", 1.0);
		j12.setPriority(13);
		// minheap.insert(j0);
		/*
		 * minheap.insert(j1); minheap.insert(j2); minheap.insert(j3);
		 * minheap.insert(j4); minheap.insert(j5); minheap.insert(j6);
		 * minheap.insert(j7); minheap.insert(j8); minheap.insert(j9);
		 * minheap.insert(j10); minheap.insert(j11); minheap.insert(j12);
		 */
		j0.setPriority(2);
		j4.setPriority(5);
		j5.setPriority(6);
		j6.setPriority(7);
		minheap.insert(j0);
		minheap.insert(j4);
		minheap.insert(j5);
		minheap.insert(j6);
		ArrayList<PriorityQueueElement> a = minheap.getTernaryHeap();
		for (PriorityQueueElement p : a)
			System.out.println(p);
		// System.out.println("Minimo attuale: " + minheap.minimum());
		// System.out.println("Estraggo il minimo");
		// PriorityQueueElement m = minheap.extractMinimum();
		// System.out.println("Minimo estratto: " + m);
		// for (PriorityQueueElement p : a)
		// System.out.println(p);
		// System.out.println("Minimo attuale: " + minheap.minimum());
		Job e = j4;
		minheap.decreasePriority(e, 1);
		System.out.println("-------");
		for (PriorityQueueElement p : a)
			System.out.println(p);
	}

}
