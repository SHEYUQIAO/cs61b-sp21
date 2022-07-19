package bstmap;

import java.util.Iterator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode {
        private K key;
        private V value;
        private int size;
        private BSTNode less, greater;

        public BSTNode(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    private BSTNode root;

    /**
     * Removes all the mappings from this map
     */
    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        return getNode(root, key) != null;
    }

    /**
     * return the value of the key
     */
    @Override
    public V get(K key) {
        BSTNode node = getNode(root, key);
        return node == null ? null : node.value;
    }

    /**
     * get the node where the key locates, returns null if not exists.
     */
    private BSTNode getNode(BSTNode node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return getNode(node.less, key);
        } else if (cmp > 0) {
            return getNode(node.greater, key);
        } else {
            return node;
        }
    }

    @Override
    public int size() {
        return root == null ? 0 : root.size;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private BSTNode put(BSTNode node, K key, V val) {
        if (node == null) {
            return new BSTNode(key, val, 1);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.less = put(node.less, key, val);
            node.size += 1;
        } else if (cmp > 0) {
            node.greater = put(node.greater, key, val);
            node.size += 1;
        } else {
            node.value = val;
        }
        return node;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        LinkedList<BSTNode> list = new LinkedList<>();
        list.addLast(root);
        while (!list.isEmpty()) {
            BSTNode node = list.removeFirst();
            if (node == null) {
                continue;
            }
            list.addLast(node.less);
            list.addLast(node.greater);
            set.add(node.key);
        }
        return set;
    }

    @Override
    public V remove(K key) {
        if (containsKey(key)) {
            V val = get(key);
            root = remove(root, key);
            return val;
        }
        return null;
    }

    private BSTNode remove(BSTNode node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.less = remove(node.less, key);
        } else if (cmp > 0) {
            node.greater = remove(node.greater,key);
        } else {
            //found the node going to be removed
            //if either child node is null, substitute with another child node.
            if (node.less == null) {
                return node.greater;
            }
            if (node.greater == null) {
                return node.less;
            }

            //if there are two nodes,
            //find the suitable one and put it in the current place
            BSTNode tmp = node;
            node = min(tmp.greater);
            node.greater = removeMin(tmp.greater);
            node.less = tmp.less;
        }
        node.size = 1 + size(node.less) + size(node.greater);
        return node;
    }

    private BSTNode min(BSTNode node) {
        if (node.less == null) {
            return node;
        }
        return min(node.less);
    }

    private BSTNode removeMin(BSTNode node) {
        if (node.less == null) {
            //
            return node.greater;
        }
        node.less = removeMin(node.less);
        node.size = 1 + size(node.less) + size(node.greater);
        return node;
    }

    private int size(BSTNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.less) + size(node.greater);
    }

    /**
     * removes the entry for the specified key only if it is currently mapped to
     * the specified value.
     */
    @Override
    public V remove(K key, V value) {
        if (get(key).equals(value)) {
            remove(root, key);
            return value;
        }
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     */
    @Override
    public Iterator<K> iterator() throws UnsupportedOperationException {
        return new BSTMapIter();
    }

    private class BSTMapIter implements Iterator<K> {
        LinkedList<BSTNode> list;

        public BSTMapIter() {
            list = new LinkedList<>();
            list.addLast(root);
        }

        @Override
        public boolean hasNext() {
            return !list.isEmpty();
        }

        @Override
        public K next() {
            BSTNode node = list.removeFirst();
            list.addLast(node.less);
            list.addLast(node.greater);
            return node.key;
        }

        /**
         * prints out your BSTMap in order of increasing Key
         */
        public void printInOrder() {
            printInOrder(root);
        }

        private void printInOrder(BSTNode node) {
            if (node == null) {
                return;
            }
            printInOrder(node.less);
            System.out.println(node.key + ", ");
            printInOrder(node.greater);
        }
    }
}
