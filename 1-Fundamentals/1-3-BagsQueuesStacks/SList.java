public class SList {
  public class IntNode {
    private int item;
    private IntNode next;

    public IntNode(int i, IntNode n) {
      item = i;
      next = n;
    }
  }

  private IntNode front;
  private int size;

  public SList() {
    front = null;
    size = 0;
  }


  public SList(int x) {
    front = new IntNode(x, null);
    size = 1;
  }

  public void insertFront(int x) {
    front = new IntNode(x, front);
    /**
    IntNode oldFront = front;
    front = new IntNode(x, oldFront);
    */
    size += 1;
  }

  public void insertBack(int x) {
    size += 1;

    if (front == null) {
      front = new IntNode(x, null);
      return;
    }

    IntNode p = front;

    /** Advance p to the end of the list */
    while (p.next != null) {
      p = p.next;
    }

    p.next = new IntNode(x, null);

  }

  public int getFront() {
    return front.item;
  }

  /*
  private static int IntNodeSize(IntNode n) {
    if (n.next == null) return 1;
    return 1 + IntNodeSize(n.next);

  }
  */

  public int size() {
    //return IntNodeSize(front);
    return size;
  }

  public static void main(String[] args) {
    //SList s1 = new SList(10);
    SList s1 = new SList();
    s1.insertFront(5);
    s1.insertFront(0);
    s1.insertBack(15);
    System.out.println(s1.size());
  }

}
