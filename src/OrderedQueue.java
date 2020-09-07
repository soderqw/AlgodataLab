import java.util.Scanner;

public class OrderedQueue {
    private Node first;
    private Node last;
    private int n;

    private static class Node {
        private int item;
        private Node next = null;
    }

    //Initiate empty queue
    private OrderedQueue() {
        first = null;
        last = null;
        n = 0;
    }

    //Adds to front of list in ascending order
    public void enqueue(int item) {
        Node input = new Node();
        input.item = item;

        //If list is empty
        if (n == 0) {
            first = last = input;
            n++;
            return;
        }

        //We will iterate our list with this
        Node current = first;

        //Checks if input should be inserted at front
        if (input.item < first.item) {
            first = input;
            first.next = current;
            n++;
            return;
        }

        //Iterate until current is at right position in list
        while ((int) input.item > (int) current.item) {

            /*
             * Means this is the last iteration, so we insert
             * current at last position and update pointers
             * */
            if (current == last) {
                last = input;
                current.next = last;
                n++;
                return;
            }

            //Checking if we should continue iterating or not
            if (current.next.item < input.item) {
                //Iterating ->
                current = current.next;
            } else {
                /*
                * Example of breaking, inserting [2] into List: [1], [3]
                *
                *   [1]     ,    [3]
                *    |            |
                *  current      current.next
                *
                * current.next.item = 3
                * input.item = 2
                *
                * At this point we know 2 is bigger than 1 (Because we could enter the while loop),
                * and smaller than 3 (because we are in this else statement),
                * so we can break and insert.
                * */
                break;
            }
        }

        /*
        * Inserting here
        * */
        input.next = current.next;
        current.next = input;
        n++;
    }

    public void dequeueFront() {
        if (n == 1) {
            first = last = null;
            n = 0;
            return;
        }
        first = first.next;
        n--;
    }

    public void dequeueBack() {
        if (n == 1) {
            first = last = null;
            n--;
            return;
        }

        //Create two pointers to keep track of when we are at last element in list
        Node pLast = first.next;
        Node pSecondToLast = first;

        //Iterate until pLast (points at last element) points to the first element
        while (pLast != last) {
            pSecondToLast = pLast;
            pLast = pLast.next;
        }

        //Set the second last element to be the new last element, and update pointers accordingly
        last = pSecondToLast;
        last.next = null;
        n--;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        Node current = first;
        System.out.println("");

        if (n == 1) {
            sb.append("[" + current.item + "]");
            return sb.toString();
        }

        while (current.next != null) {
            sb.append("[" + current.item + "], ");
            current = current.next;
        }
        sb.append("[" + current.item + "]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        OrderedQueue list = new OrderedQueue();
        boolean running = true;
        while (running) {

            System.out.println("1: Add");
            System.out.println("2: Dequeue front");
            System.out.println("3: Dequeue back");
            System.out.println("Everything else: exit");
            System.out.print("\nEnter Action: ");

            int i = in.nextInt();
            switch (i) {

                case (1):
                    System.out.print("Enter number: ");
                    list.enqueue(in.nextInt());
                    break;

                case (2):
                    if (list.n == 0) {
                        break;
                    }
                    list.dequeueFront();
                    break;

                case (3):
                    if (list.n == 0) {
                        break;
                    }
                    list.dequeueBack();
                    break;

                default:
                    running = false;
            }

            if (list.n > 0) {
                System.out.println(list.print());
            } else {
                System.out.println("Empty List!\n");
            }
        }
        System.out.println("Bye!");
    }
}

