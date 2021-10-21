import java.util.Stack;

public class BinaryTree<V extends Comparable<V>> {
    private Node<V> root;

    public BinaryTree(Node<V> root) {
        this.root = root;
    }

    public Node<V> getRoot() {
        return this.root;
    }

    public void printInorder() {
        printInOrderHelper(root);
    }
    private void printInOrderHelper(Node<V> node) {
        if (node.getLeft() == null && node.getRight()==null) {
            System.out.print(node.getValue() + " ");
        }
        else if (node.getLeft() == null) {
            printInOrderHelper(node.getRight());
            System.out.print(node.getValue() + " ");
        }
        else if (node.getRight()==null) {
            printInOrderHelper(node.getLeft());
            System.out.print(node.getValue() + " ");
        }
        else {
            printInOrderHelper(node.getLeft());
            System.out.print(node.getValue() + " ");
            printInOrderHelper(node.getRight());
        }
    }

    public void printPreorder(){
        printPreorderHelper(root);
    }
    private void printPreorderHelper(Node<V> node) {
        if (node.getLeft() == null && node.getRight()==null) {
            System.out.print(node.getValue() + " ");
        }
        else if (node.getLeft() == null) {
            printPreorderHelper(node.getRight());
            System.out.print(node.getValue() + " ");
        }
        else if (node.getRight()==null) {
            printPreorderHelper(node.getLeft());
            System.out.print(node.getValue() + " ");
        }
        else {
            System.out.print(node.getValue() + " ");
            printPreorderHelper(node.getLeft());
            printPreorderHelper(node.getRight());
        }
    }

    public void printPostorder() {
        printPostorderHelper(root);
    }
    private void printPostorderHelper(Node<V> node) {
        if (node.getLeft() == null && node.getRight()==null) {
            System.out.print(node.getValue() + " ");
        }
        else if (node.getLeft() == null) {
            printPostorderHelper(node.getRight());
            System.out.print(node.getValue() + " ");
        }
        else if (node.getRight()==null) {
            printPostorderHelper(node.getLeft());
            System.out.print(node.getValue() + " ");
        }
        else {
            printPostorderHelper(node.getLeft());
            printPostorderHelper(node.getRight());
            System.out.print(node.getValue() + " ");
        }
    }

    public void addInorder(V[] a) {
        addInorderHelper(root, a);
    }
    static int ptr1 = 0;
    private void addInorderHelper(Node<V> node, V[] a) {
        if (node.getLeft() == null && node.getRight()==null) {
            a[ptr1] = node.getValue();
            ptr1++;
        }
        else if (node.getLeft() == null) {
            addInorderHelper(node.getRight(), a);
            a[ptr1] = node.getValue();
            ptr1++;
        }
        else if (node.getRight()==null) {
            addInorderHelper(node.getLeft(), a);
            a[ptr1] = node.getValue();
            ptr1++;
        }
        else {
            addInorderHelper(node.getLeft(), a);
            a[ptr1] = node.getValue();
            ptr1++;
            addInorderHelper(node.getRight(), a);
        }
    }

    public V[] flatten() {
        V[] a = (V[]) new Comparable[5];
        addInorder(a);
        sort(a);
        return a;
    } // flatten

    // bubble sort
    // useful for flatten
    public void sort(Comparable[] a) {
        int i, j;
        Comparable temp;
        boolean swapped = true;
        for (i = 0; i < a.length && swapped; i++) {
            swapped = false;
            for (j = 1; j < a.length - i; j++) {
                if (a[j].compareTo(a[j-1]) < 0) {
                    swapped = true;
                    temp = a[j];
                    a[j] = a[j-1];
                    a[j-1] = temp;
                }
            }
        }
    }

    public void invert() {
        invertHelper(root);
    }

    public Node<V> invertHelper(Node<V> node) {
        if (node.getLeft() == null && node.getRight()==null) {
            return node;
        } else {
            Node<V> temp = node.getLeft();
            node.setLeft(node.getRight());
            node.setRight(temp);
            invertHelper(node.getRight());
            invertHelper(node.getLeft());
        }
        return node;
    }

    public boolean containsSubtree(BinaryTree<V> other) {
        Node<V> mainRoot = this.root;
        if (other == null) return true;
        Node<V> otherRoot = other.root;
        return containsSubtreeHelper(mainRoot, otherRoot);
    } // containsSubtree

    private boolean containsSubtreeHelper(Node<V> node1, Node<V> node2) {
        if (node1 == null) return false;
        if (node1.getValue() == node2.getValue()) {
            if (identicalTree(node1, node2)) return true;
        }
        return (containsSubtreeHelper(node1.getLeft(), node2) || containsSubtreeHelper(node1.getRight(), node2));
    } // containsSubtreeHelper

    private boolean identicalTree(Node<V> root1, Node<V> root2) {
        if (root1 == null && root2 == null) return true;
        if (root1 == null || root2 == null) return false;
        return (root1.getValue() == root2.getValue()
                && identicalTree(root1.getLeft(), root2.getLeft())
                && identicalTree(root1.getRight(), root2.getRight()));
    } // identicalTree

    public void merge(BinaryTree<V> other) {
        this.root = mergeHelper(this.root, other.root);
        return;
    }

    public Node<V> mergeHelper(Node<V> node1, Node<V> node2) {
        if (node1 == null && node2 == null) {
            return null;
        }
        else {
            if (node1 == null) {
                node2.setLeft(mergeHelper(null,node2.getLeft()));
                node2.setRight(mergeHelper(null,node2.getRight()));
                return node2;
            }
            else if (node2 == null) {
                node1.setLeft(mergeHelper(node1.getLeft(),null));
                node1.setRight(mergeHelper(node1.getRight(),null));
                return node1;
            }
            else {
                if (node1.getValue().compareTo(node2.getValue()) >= 0) {
                    node1.setLeft(mergeHelper(node1.getLeft(),node2.getLeft()));
                    node1.setRight(mergeHelper(node1.getRight(),node2.getRight()));
                    return node1;
                }
                else {
                    node2.setLeft(mergeHelper(node1.getLeft(),node2.getLeft()));
                    node2.setRight(mergeHelper(node1.getRight(),node2.getRight()));
                    return node2;
                }
            }
        }
    }

    // Main contains tests for each milestone.
    // Do not modify existing tests.
    // Un-comment tests by removing '/*' and '*/' as you move through the milestones.
    public static void main (String[] args) {
        // Tree given for testing on
        BinaryTree<Integer> p1Tree = new BinaryTree<Integer>(new Node<Integer>(1,
                new Node<Integer>(2,
                        new Node<Integer>(4, null, null),
                        new Node<Integer>(5, null, null)),
                new Node<Integer>(3, null, null)));

        // Milestone 1 (Traversing)
        System.out.println("--- Milestone 1 ---");
        System.out.print("Expected: 4 2 5 1 3" + System.lineSeparator() + "Actual: ");
        p1Tree.printInorder();
        System.out.println(System.lineSeparator());
        System.out.print("Expected: 1 2 4 5 3" + System.lineSeparator() + "Actual: ");
        p1Tree.printPreorder();
        System.out.println(System.lineSeparator());
        System.out.print("Expected: 4 5 2 3 1" + System.lineSeparator() + "Actual: ");
        p1Tree.printPostorder();
        System.out.println();

        // Milestone 2 (flatten) -- expected output: 1 2 3 4 5

        System.out.println(System.lineSeparator() + "--- Milestone 2 ---");
        System.out.print("Expected: 1 2 3 4 5" + System.lineSeparator() + "Actual: ");

        Comparable[] array_representation = p1Tree.flatten();
        for (int i = 0; i < array_representation.length; i++) {
            System.out.print(array_representation[i] + " ");
        }
        System.out.println();


        // Milestone 3 (invert)

        System.out.println(System.lineSeparator() + "--- Milestone 3 ---");

        p1Tree.invert();

        System.out.print("Expected: 3 1 5 2 4" + System.lineSeparator() + "Actual: ");
        p1Tree.printInorder();
        System.out.println(System.lineSeparator());
        System.out.print("Expected: 1 3 2 5 4" + System.lineSeparator() + "Actual: ");
        p1Tree.printPreorder();
        System.out.println(System.lineSeparator());
        System.out.print("Expected: 3 5 4 2 1" + System.lineSeparator() + "Actual: ");
        p1Tree.printPostorder();
        System.out.println();


        // Milestone 4 (containsSubtree)

        System.out.println(System.lineSeparator() + "--- Milestone 4 ---");

        p1Tree = new BinaryTree<Integer>(new Node<Integer>(1,
                new Node<Integer>(2,
                        new Node<Integer>(4, null, null),
                        new Node<Integer>(5, null, null)),
                new Node<Integer>(3, null, null)));
        BinaryTree<Integer> p2Tree = new BinaryTree<Integer>(new Node<Integer>(2,
                new Node<Integer>(4, null, null),
                new Node<Integer>(5, null, null)));
        BinaryTree<Integer> p3Tree = new BinaryTree<Integer>(new Node<Integer>(1,
                new Node<Integer>(2, null, null),
                new Node<Integer>(3, null, null)));
        BinaryTree<Integer> p4Tree = null;

        System.out.print("Expected: true" + System.lineSeparator() + "Actual: ");
        System.out.println(p1Tree.containsSubtree(p2Tree));
        System.out.println();

        System.out.print("Expected: false" + System.lineSeparator() + "Actual: ");
        System.out.println(p1Tree.containsSubtree(p3Tree));
        System.out.println();

        System.out.print("Expected: true" + System.lineSeparator() + "Actual: ");
        System.out.println(p1Tree.containsSubtree(p4Tree));



        // Milestone 5 (merge) *HONORS SECTION*
        System.out.println(System.lineSeparator() + "--- Milestone 5 ---");
        BinaryTree<Integer> p5Tree = new BinaryTree<>(new Node<>(10,
                new Node<>(7,
                        new Node<>(1, null, null),
                        new Node<>(5, null, null)),
                new Node<>(3,
                        new Node<>(9, null, null),
                        null)));
        BinaryTree<Integer> p6Tree = new BinaryTree<>(new Node<>(6,
                new Node<>(7,
                        new Node<>(-3, null, null),
                        new Node<>(6, null, null)),
                new Node<>(4,
                        null,
                            new Node<>(9,
                                new Node<>(4, null, null),
                                    null))));
        BinaryTree<Integer> p7Tree = new BinaryTree<>(new Node<>(10,
                new Node<>(7,
                        new Node<>(1, null, null),
                        new Node<>(6, null, null)),
                new Node<>(4,
                        new Node<>(9, null, null),
                        new Node<>(9,
                                new Node<>(4, null, null),
                                null))));
        p5Tree.merge(p6Tree);
        System.out.print("Expected: ");
        p7Tree.printPreorder();
        System.out.print("\nActual: ");
        p5Tree.printPreorder();
        System.out.println();
        System.out.print("Expected: true" + System.lineSeparator() + "Actual: ");
        System.out.println(p5Tree.containsSubtree(p7Tree));
        System.out.println();

    }
}