/**
 * Program Name: NaturalMergeSort.java
 * Written By:   Zimin Guo
 * Date:         2016/03/29
 */
 import java.util.Random;
 import java.util.Scanner;

public class NaturalMergeSort {
	public static Node sort(Node first) {
		Node head = first; 	// first node of the second run
		Node tail;			// last node of the second run

		while (head != null) {
			while (head.getNext() != null && head.isLess(head.getNext())) { // finding the last node of the first sorted run
				head = head.getNext();
			}

			head = head.getNext(); // becomes the first node of the second sorted run
			if (head == null) break; // no more nodes left
			tail = head;

			while (tail != null && tail.getNext() != null && tail.isLess(tail.getNext())) { // finding the last node of the second sorted run
				tail = tail.getNext();
			}

			first = merge(first, head, tail);
			head = tail; // last node of (now first) sorted run; will cause first while loop to be skipped
		}

		return first;
	}

	private static Node merge(Node lo, Node mid, Node hi) {
		Node first, runner;			// runner node goes through the (so far) arranged list
		Node runner1 = lo; 			// node that goes through the first run
		Node runner2 = mid;			// node that goes through the second run
		Node last = hi.getNext();	// end boundary node

		if (runner2.isLess(runner1)) {	// using runner2.isLess to preserve stability; if equal, then use runner1
			first = runner2;
			runner2 = runner2.getNext();
		} else {
			first = runner1;
			runner1 = runner1.getNext();
		}

		runner = first;

		while (runner1 != mid || runner2 != last) { // loop terminates when both runners reach the end of their run
			if (runner2 != last && (runner1 == mid || runner2.isLess(runner1))) { // next lowest node is in second run
				runner.setNext(runner2);
				runner2 = runner2.getNext();
			} else {
				runner.setNext(runner1);
				runner1 = runner1.getNext();
			}

			runner = runner.getNext(); // "incrementing" runner
		}

		runner.setNext(last);
		return first;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("What size is the array? ");
			int size = Integer.parseInt(sc.nextLine());

			if (size <= 0)
				size = 15;

			Node first = createRandomLinkedList(size);
			System.out.print("Before: ");
			printLinkedList(first);
			first = NaturalMergeSort.sort(first);
			System.out.print("After: ");
			printLinkedList(first);

			System.out.println("Repeat? (0 to exit) ");

			if (Integer.parseInt(sc.nextLine()) == 0) break;
		}
	}

	private static Node createRandomLinkedList(int size) {
		Random random = new Random();
		Node first = new Node(Character.valueOf((char) (random.nextInt(26) + 65)));
		Node runner = first;

		for (int i = 1; i <= size - 1; i++) {
			runner.setNext(new Node(Character.valueOf((char) (random.nextInt(26) + 65))));
			runner = runner.getNext();
		}

		return first;
	}

	private static void printLinkedList(Node first) {
		Node runner = first;

		while (runner != null) {
			System.out.print(runner.getKey());
			runner = runner.getNext();
		}
		System.out.println();
	}
}
}
