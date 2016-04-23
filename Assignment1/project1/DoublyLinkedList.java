/**
 * 1. Linked Lists - Dequeue
 * Created by ZiminGuo
 * Implement a nested class DoubleNode for building doubly-linked lists
 * Each node contains a reference to preceding and following items(null if none)
 * Implement methods:
 *  Insert at the beginning
 *  Insert at the end
 *  Remove from the beginning
 *  Remove from the end
 *  Insert before a given node
 *  Insert after a given node
 *  Remove a given node
 *  Move to the front
 *  Move to the end
 */

import java.util.Scanner;

public class DoublyLinkedList<Element> {
	
	private DoubleNode<Element> first;
	private DoubleNode<Element> last;

	public class DoubleNode<Element> {
		private DoubleNode<Element> previous;
		private DoubleNode<Element> next;
		private Element object;

		public DoubleNode() {
			this.previous = null;
			this.next = null;
			this.object = null;
		}

		public DoubleNode(Element e) {
			this.previous = null;
			this.next = null;
			this.object = e;
		}

		public void setPrevious(DoubleNode<Element> item) {
			this.previous = item;
		}

		public void setNext(DoubleNode<Element> item) {
			this.next = item;
		}

		public DoubleNode<Element> getPrevious() {
			return this.previous;
		}

		public DoubleNode<Element> getNext() {
			return this.next;
		}

		public Element getObject() {
			return this.object;
		}
	}

	public DoublyLinkedList() {
		this.first = null;
		this.last = null;
	}
	
	public void insertAtBeginning(Element e) {
		DoubleNode<Element> item = new DoubleNode<Element>(e);
		
		if(this.first != null) {
			item.setNext(this.first);
			this.first.setPrevious(item);
		}
		
		this.first = item;
		
		if (this.last == null) { // case of empty linked list
			this.last = item;
		}
	}
	
	public void insertAtEnd(Element e) {
		DoubleNode<Element> item = new DoubleNode<Element>(e);
		
		if(this.last != null) {
			item.setPrevious(this.last);
			this.last.setNext(item);
		}
		
		this.last = item;
		
		if (this.first == null) { // case of empty linked list
			this.first = item;
		}
	}
	
	public void insertBefore(Element givenData, Element newData) {
		DoubleNode<Element> item = new DoubleNode<Element>(newData);
		DoubleNode<Element> current = this.first;
		
		while (current != null && current.getObject() != givenData) {
			current = current.getNext();
		}
		
		if (current == null) { // case where no such node or empty linked list
			System.out.println("Node not found. Inserting at the end");
			this.insertAtEnd(newData);
		}
		
		else {
			if (current == this.first) {
				this.first = item;		
			}
			
			else { // if not the first item, then connect the previous item to the new item
				current.getPrevious().setNext(item);
				item.setPrevious(current.getPrevious());
			}
		
			current.setPrevious(item);
			item.setNext(current);
		}
	}
	
	public void insertAfter(Element givenData, Element newData) {
		DoubleNode<Element> item = new DoubleNode<Element>(newData);
		DoubleNode<Element> current = this.first;
		
		while (current != null && current.getObject() != givenData) {
			current = current.getNext();
		}
		
		if (current == null) { // case where no such node or empty linked list
			System.out.println("Node not found. Inserting at the end");
			this.insertAtEnd(newData);
		}
		
		else {
			if (current == this.last) {
				this.last = item;		
			}
			
			else { // if not the last item, then connect the next item to the new item
				current.getNext().setPrevious(item);
				item.setNext(current.getNext());
			}
		
			current.setNext(item);
			item.setPrevious(current);
		}
	}
	
	public Element removeBeginning() {
		if (this.first != null) {
			Element object = this.first.getObject();
			this.first = this.first.getNext();
			
			if (this.first == null) { // case where there is only one node, ie. no next
				this.last = null;
			}
			
			else {
				this.first.setPrevious(null);
			}
			
			return object;
		}
		
		System.out.println("The list is empty. Nothing removed.");
		return null;
	}
	
	public Element removeEnd() {
		if (this.last != null) {
			Element object = this.last.getObject();
			this.last = this.last.getPrevious();
			
			if (this.last == null) { // case where there is only one node, ie. no previous
				this.first = null;
			}
			
			else {
				this.last.setNext(null);
			}
			
			return object;
		}
		
		System.out.println("The list is empty. Nothing removed.");
		return null;
	}
	
	public Element remove(Element e) {
		DoubleNode<Element> current = this.first;
		
		while (current != null && current.getObject() != e) {
			current = current.getNext();
		}
		
		if (current == null) {
			System.out.println("Node not found.");
			return null;
		}
		
		if (current == this.first) {
			this.first = current.getNext();
		}
		
		else { // there is a node before current
			current.getPrevious().setNext(current.getNext());
		}
		
		if (current == this.last) {
			this.last = current.getPrevious();
		}
		
		else { // there is a node after current
			current.getNext().setPrevious(current.getPrevious());
		}
		
		return e;
	}
	
	public void moveFront(Element e) {
		DoubleNode<Element> current = this.first;
		
		while (current != null && current.getObject() != e) {
			current = current.getNext();
		}
		
		if (current == null) { // case where no such node or empty linked list
			System.out.println("Node not found.");
		}
		
		else if (current != this.first){
			current.getPrevious().setNext(current.getNext());
			
			if (current == this.last) {
				this.last = current.getPrevious();
			}
			
			else { // there is a node after current
				current.getNext().setPrevious(current.getPrevious());
			}
			
			current.setPrevious(null);
			current.setNext(this.first);
			this.first.setPrevious(current);
			this.first = current;
		}
	}
	
	public void moveEnd(Element e) {
		DoubleNode<Element> current = this.first;
		
		while (current != null && current.getObject() != e) {
			current = current.getNext();
		}
		
		if (current == null) { // case where no such node or empty linked list
			System.out.println("Node not found.");
		}
		
		else if (current != this.last){
			current.getNext().setPrevious(current.getPrevious());
			
			if (current == this.first) {
				this.first = current.getNext();
			}
			
			else { // there is a node before current
				current.getPrevious().setNext(current.getNext());
			}
			
			current.setPrevious(this.last);
			current.setNext(null);
			this.last.setNext(current);
			this.last = current;
		}
	}
	
	public void printNodes() {
		DoubleNode<Element> current = this.first;
		
		while (current != null) {
			System.out.print(current.getObject());
			System.out.print(" ");
			current = current.getNext();
		}
	}
	
	public void printNodesBackward() { // to check "previous" links
		DoubleNode<Element> current = this.last;
		
		while (current != null) {
			System.out.print(current.getObject());
			System.out.print(" ");
			current = current.getPrevious();
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean restart = true;

		while (restart) {
			int reference; // reference for inserting at a certain node
			int item;      // item for the actual node
			Integer removed;
			int choice = 1;
			DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();
			// Integer is an object; int is a primitive data type

			while (choice < 10) {
				System.out.println("Main Menu: ");
				System.out.println("1. Insert at the beginning");
				System.out.println("2. Insert at the end");
				System.out.println("3. Insert before a node");
				System.out.println("4. Insert after a node");
				System.out.println("5. Remove a node from the beginning");
				System.out.println("6. Remove a node from the end");
				System.out.println("7. Remove a node");
				System.out.println("8. Move a node to the beginning");
				System.out.println("9. Move a node to the end");
				System.out.println("10. Restart");
				System.out.println("11. Exit");
				System.out.println();
				System.out.print("Your choice: ");

				choice = sc.nextInt();

				switch(choice) {

					case 1: {
						System.out.print("Value of the node: ");
						item = sc.nextInt();
						list.insertAtBeginning(Integer.valueOf(item));
						break;
					}

					case 2: {
						System.out.print("Value of the node: ");
						item = sc.nextInt();
						list.insertAtEnd(Integer.valueOf(item));
						break;
					}

					case 3: {
						System.out.print("Value of the node: ");
						item = sc.nextInt();
						System.out.print("Insert before which node? ");
						reference = sc.nextInt();
						list.insertBefore(Integer.valueOf(reference), Integer.valueOf(item));
						break;
					}

					case 4: {
						System.out.print("Value of the node: ");
						item = sc.nextInt();
						System.out.print("Insert after which node? ");
						reference = sc.nextInt();
						list.insertAfter(Integer.valueOf(reference), Integer.valueOf(item));
						break;
					}

					case 5: {
						removed = list.removeBeginning();

						if (removed != null) {
							System.out.println("You have removed: " + removed.intValue());
						}

						break;
					}

					case 6: {
						removed = list.removeEnd();

						if (removed != null) {
							System.out.println("You have removed: " + removed.intValue());
						}

						break;
					}

					case 7: {
						System.out.print("Value of the node: ");
						item = sc.nextInt();
						removed = list.remove(Integer.valueOf(item));

						if (removed != null) {
							System.out.println("You have removed: " + removed.intValue());
						}

						break;
					}

					case 8: {
						System.out.print("Value of the node: ");
						item = sc.nextInt();
						list.moveFront(Integer.valueOf(item));
						break;
					}

					case 9: {
						System.out.print("Value of the node: ");
						item = sc.nextInt();
						list.moveEnd(Integer.valueOf(item));
						break;
					}

					case 11: {
						restart = false;
						break;
					}

				}

				if (choice < 10) {
					System.out.println("List: ");
					list.printNodes();
					System.out.println("\n");
					System.out.println("List backward: ");
					list.printNodesBackward();
					System.out.println("\n");
				}
			}
		}

	}
}

/* Program output
Main Menu:
1. Insert at the beginning
2. Insert at the end
3. Insert before a node
4. Insert after a node
5. Remove a node from the beginning
6. Remove a node from the end
7. Remove a node
8. Move a node to the beginning
9. Move a node to the end
10. Restart
11. Exit

Your choice: 1
Value of the node: 1
List:
1

List backward:
1

Main Menu:
1. Insert at the beginning
2. Insert at the end
3. Insert before a node
4. Insert after a node
5. Remove a node from the beginning
6. Remove a node from the end
7. Remove a node
8. Move a node to the beginning
9. Move a node to the end
10. Restart
11. Exit

Your choice: 2
Value of the node: 9
List:
1 9

List backward:
9 1

Main Menu:
1. Insert at the beginning
2. Insert at the end
3. Insert before a node
4. Insert after a node
5. Remove a node from the beginning
6. Remove a node from the end
7. Remove a node
8. Move a node to the beginning
9. Move a node to the end
10. Restart
11. Exit

Your choice:
3
Value of the node: 5
Insert before which node? 2
Node not found. Inserting at the end
List:
1 9 5

List backward:
5 9 1

Main Menu:
1. Insert at the beginning
2. Insert at the end
3. Insert before a node
4. Insert after a node
5. Remove a node from the beginning
6. Remove a node from the end
7. Remove a node
8. Move a node to the beginning
9. Move a node to the end
10. Restart
11. Exit

Your choice: 11
 */