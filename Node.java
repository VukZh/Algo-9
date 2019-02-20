package avltreeapp;

public class Node {

    int key;
    int height;
    int balance;
    Node left;
    Node right;
    Node parent;

    Node(int k, Node prnt) {
        key = k;
        parent = prnt;
        height = 1;
    }

}
