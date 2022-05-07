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


