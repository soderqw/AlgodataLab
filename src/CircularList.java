import java.util.NoSuchElementException;
import java.util.Scanner;

public class CircularList<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int n;

    //Nodes for single linked list
    public static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    //Initialize empty list
    public CircularList() {
        first = null;
        last  = null;
        n = 0;
    }

    //Checks if first or last pointer points to nothing
    public boolean isEmptyFirst() { return first == null; }
    private boolean isEmptyLast() { return last == null; }

    public int size() { return n; }

    public void add(Item item, boolean front) {
        Node<Item> holder;  //Pointer that points to the "old" first/last pointer

        //Adding to the front of the list
        if (front) {
            holder = first;
            first = new Node<Item>();
            first.item = item;

            if (isEmptyLast()) {
                last = first;
            } else {
                last.next = first;
                first.next = holder;
            }

        //Adding to the back of the list
        } else {
            holder = last;
            last = new Node<Item>();
            last.item = item;

            if (isEmptyFirst()) {
                first = last;
            } else {
                last.next = first;
                holder.next = last;
            }
        }
        n++;
    }

    public void delete(boolean front) {
        if (n == 1) {
            first = last = null;
            n--;
            return;
        }

        if (front) {
            if (isEmptyFirst()) throw new NoSuchElementException("Queue underflow");
            Item item = first.item;
            first = first.next;
            last.next = first;
            if (isEmptyFirst()) last = null;   // to avoid loitering
        } else {
            //Create two pointers to keep track of when we are at last element in list
            Node<Item> pLast = first.next;
            Node<Item> pSecondToLast = first;

            //Iterate until pLast (points at last element) points to the first element
            while (pLast.next != first) {
                pSecondToLast = pLast;
                pLast = pLast.next;
            }

            //Set the second last element to be the new last element, and update pointers accordingly
            last = pSecondToLast;
            last.next = first;
        }
        n--;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        Node<Item> current = first;

        if (size() == 1) {
            sb.append("[" + current.item + "]");
            return sb.toString();
        }

        while (current.next != first) {
            sb.append("[" + current.item + "], ");
            current = current.next;
        }
        sb.append("[" + current.item + "]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        CircularList<String> list = new CircularList<String>();
        boolean running = true;
        while (running) {
            System.out.println("1: Add front");
            System.out.println("2: Add back");
            System.out.println("3: Delete front");
            System.out.println("4: Delete back");
            System.out.println("Everything else: exit");
            int i = in.nextInt();
            switch(i) {
                case (1):
                    System.out.print("Enter item to front: ");
                    list.add(in.next(), true);
                    break;
                case (2):
                    System.out.print("Enter item to back: ");
                    list.add(in.next(), false);
                    break;
                case (3):
                    if (list.n > 0)
                        list.delete(true);
                    break;
                case (4):
                    if (list.n > 0)
                        list.delete(false);
                    break;
                default: running=false;
            }
            if (list.n > 0) {
                System.out.println(list.print());
            } else {
                System.out.println("Empty List!");
            }
            System.out.println();
        }
        System.out.println("(" + list.size() + " left on list)");
    }
}