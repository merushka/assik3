import java.util.ArrayList;
import java.util.Objects;
public class MyHashTable <K, V>{
    private class HashNode<K, V>{
        private K key;
        private V val;
        private HashNode<K, V> next = null;
        final int hashCode;
        public HashNode(){
            hashCode = 0;
        }
        public HashNode(K key, V val, int hashCode){
            this.key = key;
            this.val = val;
            this.hashCode = hashCode;
        }
        @Override
        public String toString() {
            return "{" + key + " " + val + "}";
        }
    }
    private ArrayList<HashNode<K,V>> chainArray = new ArrayList<>();
    private int M = 11;
    private int size = 0;
    public MyHashTable() {
        for (int i = 0; i < M; i++) chainArray.add(null);
    }
    public MyHashTable(int M) {
        this.M = M;
        for (int i = 0; i < M; i++){
            chainArray.add(null);
        }
    }

    private int hash(K key){
        return Objects.hashCode(key);
    }

    private int getIndex(K key){
        int hash;
        hash = hash(key);
        int index;
        index = hash % M;
        if (index >= 0) {
            return index;
        }
        index *= -1;
        return index;
    }
    public void put(K key, V val){
        int index = getIndex(key);
        int hash = hash(key);

        HashNode<K, V> head = chainArray.get(index);

        while(head != null){
            if (head.key.equals(key) && head.hashCode == hash){
                head.val = val;
                return;
            }
            head = head.next;
        }
        size++;
        head = chainArray.get(index);
        HashNode<K, V> newNode = new HashNode<>(key, val, hash);
        newNode.next = head;
        chainArray.set(index, newNode);

        if ( (double) (M / size) > 0.6){
            System.out.println("LOAD FACTOR " + key + " " + val);
            M *= 2;
            ArrayList<HashNode<K, V>> oldList = chainArray;
            chainArray = new ArrayList<>();
            size = 0;
            for (int i = 0; i < M; i ++){
                chainArray.add(null);
            }
            int i;
            for (i = 0; i < oldList.size(); i++){
                HashNode<K, V> start = oldList.get(i);
                while(start != null){
                    put(start.key, start.val);
                    start = start.next;
                }
            }
        }

    }

    public void print(){
        int i;
        for (i = 0; i < M; i ++){
            HashNode<K, V> head = chainArray.get(i);
            if (head != null){
                while(head != null){
                    System.out.print("{" + head.key + "," + head.val + "}  ");
                    head = head.next;
                }
                System.out.println();
            }
        }
    }


    public V get(K key){
        int index;
        index = getIndex(key);
        int hash;
        hash = hash(key);
        HashNode<K, V> head = chainArray.get(index);

        while(head != null){
            if (head.key.equals(key) && head.hashCode == hash){
                return head.val;
            }
            head = head.next;
        }
        return null;
    }

    public V remove(K key){
        int index;
        index = getIndex(key);
        int hash;
        hash = hash(key);
        HashNode<K, V> head = chainArray.get(index);
        HashNode<K, V> prev = null;
        while(head != null){
            if (head.key.equals(key) && hash == head.hashCode) break;
            prev = head;
            head = head.next;
        }
        if (head == null) return null;

        size--;

        if(prev != null) prev.next = head.next;
        else chainArray.set(index, head.next);
        return head.val;
    }

