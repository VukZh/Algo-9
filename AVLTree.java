package avltreeapp;

import java.util.Stack;

public class AVLTree {

    public Node rootAVLtree;

    public AVLTree() {
        rootAVLtree = null;
    }

    public Node find(int k) { // поиск
        Node curr = rootAVLtree;
        while (curr.key != k) {
            if (k < curr.key) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
            if (curr == null) {
                System.out.println("not find");
                return null;
            }
        }
        System.out.println("find");
        return curr;
    }

/////////////// ребалансировка
    private int height(Node n) {
        if (n == null) {
            return 0;
        }
        return n.height;
    }

    private void reHeight(Node node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    private void setBalance(Node node) {
        if (node != null) {
            reHeight(node);
            node.balance = height(node.right) - height(node.left);
        }
    }

    private void reBalance(Node node) {
        setBalance(node);
        if (node.balance == -2) { // выбор правых поворотов
            if (height(node.left.left) >= height(node.left.right)) {
                node = rightRotate(node);
            } else {
                node = rotateLeft_Right(node);
            }
        } else if (node.balance == 2) { // выбор левых поворотов
            if (height(node.right.right) >= height(node.right.left)) {
                node = leftRotate(node);
            } else {
                node = rotateRight_Left(node);
            }
        }
        if (node.parent != null) {
            reBalance(node.parent);
        } else {
            rootAVLtree = node;
        }
    }

////////////// малые повороты
    private Node leftRotate(Node x) { // RR     x
        Node y = x.right; //                   / \ 
        y.parent = x.parent; //              T1   y
        x.right = y.left; //                     / \
        if (x.right != null) { //               T2  T3
            x.right.parent = x;
        }
        y.left = x;
        x.parent = y;
        if (y.parent != null) {
            if (y.parent.right == x) {
                y.parent.right = y;
            } else {
                y.parent.left = y;
            }
        }
        setBalance(x);
        setBalance(y);
        return y;
    }

    private Node rightRotate(Node y) { // LL    y
        Node x = y.left;  //                   / \
        x.parent = y.parent; //              x   T3
        y.left = x.right;//                / \
        if (y.left != null) {//          T1  T2
            y.left.parent = y;
        }
        x.right = y;
        y.parent = x;
        if (x.parent != null) {
            if (x.parent.right == y) {
                x.parent.right = x;
            } else {
                x.parent.left = x;
            }
        }
        setBalance(y);
        setBalance(x);

        return x;
    }

////////////// большие повороты
    private Node rotateLeft_Right(Node node) { // LR
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }

    private Node rotateRight_Left(Node node) { // RL
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

////////////////// вставка
    public boolean insert(int key) {
        if (rootAVLtree == null) {
            rootAVLtree = new Node(key, null);
            return true;
        }
        Node n = rootAVLtree; // начинаем с корня
        while (true) { // выходим при таком же уже ключе в дереве
            if (n.key == key) {
//                System.out.println("repeat " + n.key);
                return false;
            }
            Node parent = n; // сохранение узла для вставки если дальше не будет потомка

            boolean isLeft = n.key > key; // в какую ветвь идет вставка
            if (isLeft) {
                n = n.left;
            } else {
                n = n.right;
            }

            if (n == null) { // прогоняем до низа дерева
                if (isLeft) {
                    parent.left = new Node(key, parent);
                } else {
                    parent.right = new Node(key, parent);
                }
                reBalance(parent);
                break;
            }
        }
        return true;
    }

////////////////////// удаление    
    private void delNode(Node node) {
        if (node.left == null && node.right == null) { //если нет потомков - убираем связи у родителя на этот узел
            if (node.parent == null) {
                rootAVLtree = null;
            } else {
                Node parent = node.parent;
                if (parent.left == node) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                reBalance(parent);
            }
            return;
        }

        if (node.left != null) { // ищем замену вместо удаляемого узла
            Node fndNxt = node.left; // налево самый максимальный
            while (fndNxt.right != null) {
                fndNxt = fndNxt.right;
            }
            node.key = fndNxt.key;
            delNode(fndNxt);
        } else {
            Node fndNxt = node.right; // направо самый минимальный
            while (fndNxt.left != null) {
                fndNxt = fndNxt.left;
            }
            node.key = fndNxt.key;
            delNode(fndNxt);
        }
    }

    public void delete(int key) {
        if (rootAVLtree == null) {
            return;
        }
        Node fnd = rootAVLtree;
        while (fnd != null) {
            Node node = fnd;
            if (key >= node.key) {
                fnd = node.right;
            } else {
                fnd = node.left;
            }
            if (key == node.key) { // узел найден
                delNode(node);
                return;
            }
        }
    }

// вывод дерева
    public void displayAVLTree() {
        Stack globalStack = new Stack();
        globalStack.push(rootAVLtree);
        int nBlanks = 32;
        boolean isRowEmpty = false;

        System.out.println("......................................................................");

        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int i = 0; i < nBlanks; i++) {
                System.out.print(" ");
            }

            while (globalStack.isEmpty() == false) {
                Node tmp = (Node) globalStack.pop();
                if (tmp != null) {
                    System.out.print(tmp.key);
                    localStack.push(tmp.left);
                    localStack.push(tmp.right);
                    if (tmp.left != null || tmp.right != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("  ");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int i = 0; i < nBlanks * 2 - 2; i++) {
                    System.out.print(" ");
                }
            }
            System.out.println("");
            nBlanks = nBlanks / 2;
            while (localStack.isEmpty() == false) {
                globalStack.push(localStack.pop());
            }
        }
        System.out.println("......................................................................");
    }

    public void displayHeight() { // вывод высот
        Stack globalStack = new Stack();
        globalStack.push(rootAVLtree);
        int nBlanks = 32;
        boolean isRowEmpty = false;

        System.out.println("...............................Height.................................");

        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int i = 0; i < nBlanks; i++) {
                System.out.print(" ");
            }

            while (globalStack.isEmpty() == false) {
                Node tmp = (Node) globalStack.pop();
                if (tmp != null) {
                    System.out.print(tmp.height);
                    localStack.push(tmp.left);
                    localStack.push(tmp.right);
                    if (tmp.left != null || tmp.right != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("  ");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int i = 0; i < nBlanks * 2 - 2; i++) {
                    System.out.print(" ");
                }
            }
            System.out.println("");
            nBlanks = nBlanks / 2;
            while (localStack.isEmpty() == false) {
                globalStack.push(localStack.pop());
            }
        }
        System.out.println("......................................................................");
    }

    public void displayBalance() { // вывод баланса
        Stack globalStack = new Stack();
        globalStack.push(rootAVLtree);
        int nBlanks = 32;
        boolean isRowEmpty = false;

        System.out.println("...............................Balance................................");

        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int i = 0; i < nBlanks; i++) {
                System.out.print(" ");
            }

            while (globalStack.isEmpty() == false) {
                Node tmp = (Node) globalStack.pop();
                if (tmp != null) {
                    System.out.print(tmp.balance);
                    localStack.push(tmp.left);
                    localStack.push(tmp.right);
                    if (tmp.left != null || tmp.right != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("  ");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int i = 0; i < nBlanks * 2 - 2; i++) {
                    System.out.print(" ");
                }
            }
            System.out.println("");
            nBlanks = nBlanks / 2;
            while (localStack.isEmpty() == false) {
                globalStack.push(localStack.pop());
            }
        }
        System.out.println("......................................................................");
    }

}
