import java.util.NoSuchElementException;
import java.util.Scanner;

public class QueueKthElement<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int n;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next = null;
        private Node<Item> prev = null;
    }

    private QueueKthElement() {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
        return last == null;
    }

    public int size() {
        return n;
    }

    //Adds to front of list
    public void enqueue(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();  // "Suddar" ut gamla "last"
        first.item = item;

        if (isEmpty()) {
            last = first;
        } else { //Double linked list
            first.next = oldfirst;
            oldfirst.prev = first;
        }
        n++;
    }

    //Dequeues the kth element
    public void dequeue(int k) {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");

        //Special case when k == 1 or k == n (length of list)
        if (k == 1) { //Update first
            if (n == 1) {
                first = last = null;
                n = 0;
                return;
            }
            first = first.next;
            first.prev = null;
            n--;
            return;

        } else if (k == n) { //Update last
            last = last.prev;
            last.next = null;
            n--;
            return;
        }

        //Initiates pointers
        Node<Item> current = first;
        Node<Item> prevToCurrent;
        Node<Item> nextToCurrent;

        //Goes until current points to the kth element
        while (k > 1) {
            current = current.next;
            k--;
        }

        //Updates pointers
        nextToCurrent = current.next;
        prevToCurrent = current.prev;
        current = null;
        nextToCurrent.prev = prevToCurrent;
        prevToCurrent.next = nextToCurrent;
        n--;

        if (isEmpty()) last = null;   // to avoid loitering
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        Node<Item> current = first;

        if (size() == 1) {
            sb.append("[" + current.item + "]");
            return sb.toString();
        }
        System.out.println("Current queue: ");
        while (current.next != null) {
            sb.append("[" + current.item + "], ");
            current = current.next;
        }
        sb.append("[" + current.item + "]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        QueueKthElement<String> list = new QueueKthElement<String>();
        boolean running = true;
        while (running) {
            System.out.println("1: Add");
            System.out.println("2: Delete kth");
            System.out.println("Everything else: exit");
            int i = in.nextInt();
            switch (i) {
                case (1):
                    System.out.print("Enter item to front: ");
                    list.enqueue(in.next());
                    break;
                case (2):
                    System.out.print("Enter element to remove (kth): ");
                    int input = in.nextInt();
                    if (input <= 0 || input > list.n) {
                        System.out.println("Element out of bounds, try again.");
                        break;
                    }
                    list.dequeue(input);
                    break;
                default:
                    running = false;
            }
            if (list.n > 0) {
                System.out.println(list.print());
            } else {
                System.out.println("Empty List!");
            }
            System.out.println();
        }
        System.out.println("Bye!");
    }

}