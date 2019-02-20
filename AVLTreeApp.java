package avltreeapp;

public class AVLTreeApp {

    public static void main(String[] args) {

        AVLTree avlTree = new AVLTree();
        avlTree.insert(71);
        avlTree.insert(33);
        avlTree.insert(65);
        avlTree.insert(60);
        avlTree.insert(75);
        avlTree.insert(5);
        avlTree.insert(40);
        avlTree.insert(61);
        avlTree.insert(27);
        avlTree.insert(31);
        avlTree.insert(32);
        avlTree.insert(14);
        avlTree.insert(9);
        avlTree.insert(15);
        avlTree.insert(41);
        avlTree.insert(62);
        avlTree.insert(27);
        avlTree.insert(33);
        avlTree.insert(32);
        avlTree.insert(14);
        avlTree.insert(9);
        avlTree.insert(15);

//        avlTree.find(65);
        avlTree.displayAVLTree();
//        avlTree.displayHeight();
//        avlTree.displayBalance();
        avlTree.delete(65);        
//        avlTree.find(65);
        avlTree.displayAVLTree();
    }
}
