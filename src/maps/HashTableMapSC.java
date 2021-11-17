package maps;

import java.util.*;

/**
 * Separate chaining table implementation of hash tables. Note that all
 * "matching" is based on the equals method.
 *
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro
 * @param <K> The key
 * @param <V> The stored value
 */




public class HashTableMapSC<K, V> implements Map<K, V> {

    private static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }


    private List<HashEntry<K,V>>[] array;
    private int cap;
    private int prime;
    private int size;



    private class HashEntry<T, U> implements Entry<T, U> {

        private T key;
        private U value;

        public HashEntry(T k, U v) {
            this.key=k;
            this.value=v;
        }

        @Override
        public U getValue() {
            return value;
        }

        @Override
        public T getKey() {
            return key;
        }

        public U setValue(U val) {
            this.value=val;
            return this.value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HashEntry<T, U> hashEntry = (HashEntry<T,U>) o;
            return Objects.equals(key, hashEntry.key) && Objects.equals(value, hashEntry.value);
        }


        /**
         * Entry visualization.
         */
        @Override
        public String toString() {
            return key.toString()+" "+value.toString();
        }
    }

    private class HashTableMapIterator<T, U> implements Iterator<Entry<T, U>> {

        //Ejercicio 2.2
        public HashTableMapIterator(ArrayList<HashEntry<T, U>>[] map, int numElems) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        private void goToNextElement() {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public Entry<T, U> next() {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void remove() {
            // NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapKeyIterator<T, U> implements Iterator<T> {

        public HashTableMapKeyIterator(HashTableMapIterator<T, U> it) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void remove() {
            // NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapValueIterator<T, U> implements Iterator<U> {

        public HashTableMapValueIterator(HashTableMapIterator<T, U> it) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public U next() {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    /**
     * Creates a hash table
     */
    public HashTableMapSC() {
        this.size=0;
        this.array = new LinkedList[100];


        this.cap=100;
        this.prime=103;

    }

    /**
     * Creates a hash table.
     *
     * @param cap initial capacity
     */
    public HashTableMapSC(int cap) {
        this.size=0;
        this.array = new LinkedList[cap];

        this.cap=cap;
        int i=cap;
        while(!isPrime(i)){
            i++;
        }
        this.prime=i;

    }

    /**
     * Creates a hash table with the given prime factor and capacity.
     *
     * @param p prime number
     * @param cap initial capacity
     */
    public HashTableMapSC(int p, int cap) {
        this.size=0;
        this.array = new LinkedList[cap];

        this.cap=cap;
        this.prime=p;
    }

    /**
     * Hash function applying MAD method to default hash code.
     *
     * @param key Key
     * @return
     */
    protected int hashValue(K key) {

        int m = Objects.hashCode(key);
        return (m%this.prime)%this.cap;
    }

    /**
     * Returns the number of entries in the hash table.
     *
     * @return the size
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Returns whether or not the table is empty.
     *
     * @return true if the size is 0
     */
    @Override
    public boolean isEmpty() {
        return this.size==0;
    }

    /**
     * Returns the value associated with a key.
     *
     * @param key
     * @return value
     */
    @Override
    public V get(K key) {
        if(key==null) {
            throw new RuntimeException();
        }
        int hash = this.hashValue(key);
        List<HashEntry<K,V>> lista = this.array[hash];
        if(lista==null){
            return null;
        }
        if (lista.isEmpty()) return null;
        for(HashEntry<K,V> elm : lista){
            if(elm.getKey().equals(key)) return elm.getValue();
        }
        return null;


    }

    /**
     * Put a key-value pair in the map, replacing previous one if it exists.
     *
     * @param key
     * @param value
     * @return value
     */
    @Override
    public V put(K key, V value) throws IllegalStateException {
        if(key==null) {
            throw new RuntimeException();
        }
        this.size++;
        int hash = this.hashValue(key);
        if(this.array[hash]==null){
            this.array[hash]=new LinkedList<>();
        }
        List<HashEntry<K,V>> lista = this.array[hash];
        HashEntry<K,V> aux =null;
        for(HashEntry<K,V> elm : lista){
            if(elm.getKey().equals(key)){
                aux=elm;
                break;
            }
        }
        if(aux==null){
            lista.add(new HashEntry<>(key,value));
        }
        else{
            aux.setValue(value);
        }

        if(this.size*4 > this.cap*3){
            rehash(cap*2);
        }

        return value;
    }

    /**
     * Removes the key-value pair with a specified key.
     *
     * @param key
     * @return
     */
    @Override
    public V remove(K key) throws IllegalStateException {
        Iterator<HashEntry<K,V>> it = this.array[this.hashValue(key)].iterator();
        V val = null;
        while(it.hasNext()){
            HashEntry<K,V> next = it.next();
            if(next.getKey().equals(key)) {
                val = next.getValue();
                it.remove();

            }
        }
        this.size--;
        return val;

    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        LinkedList<Entry<K,V>> aux = new LinkedList<>();
        for(int i=0;i<this.cap;i++){
            List<HashEntry<K,V>> lista = this.array[i];
            if (lista==null)continue;
            for(HashEntry<K,V> entry : lista){
                aux.add(new HashEntry<>(entry.getKey(),entry.getValue()));
            }

        }
        return aux.iterator();
    }

    /**
     * Returns an iterable object containing all of the keys.
     *
     * @return
     */
    @Override
    public Iterable<K> keys() {
        LinkedList<K> aux = new LinkedList<>();
        for(int i=0;i<this.cap;i++){
            List<HashEntry<K,V>> lista = this.array[i];
            if (lista==null)continue;
            for(HashEntry<K,V> entry : lista){
                aux.add(entry.getKey());
            }

        }
        return aux;
    }

    /**
     * Returns an iterable object containing all of the values.
     *
     * @return
     */
    @Override
    public Iterable<V> values() {
        LinkedList<V> aux = new LinkedList<>();
        for(int i=0;i<this.cap;i++){
            List<HashEntry<K,V>> lista = this.array[i];
            if (lista==null)continue;
            for(HashEntry<K,V> entry : lista){
                aux.add(entry.getValue());
            }

        }
        return aux;
    }

    /**
     * Returns an iterable object containing all of the entries.
     *
     * @return
     */
    @Override
    public Iterable<Entry<K, V>> entries() {
        LinkedList<Entry<K,V>> aux = new LinkedList<>();
        for(int i=0;i<this.cap;i++){
            List<HashEntry<K,V>> lista = this.array[i];
            if (lista==null)continue;
            for(HashEntry<K,V> entry : lista){
                aux.add(new HashEntry<>(entry.getKey(),entry.getValue()));
            }

        }
        return aux;
    }

    /**
     * Determines whether a key is valid.
     *
     * @param k Key
     */
    protected boolean checkKey(K k) {
        int index = this.hashValue(k);
        List<HashEntry<K, V>> lista = this.array[index];
        if(lista==null) return false;
        else{
            for(HashEntry<K,V> ent : lista){
                if(ent.key.equals(k)) return true;
            }
        }
        return false;
    }

    /**
     * Increase/reduce the size of the hash table and rehashes all the entries.
     * @param newCap
     */
    protected void rehash(int newCap) {
        List<HashEntry<K,V>>[] tabla = new LinkedList[newCap];
        for(int i=0;i<this.cap;i++){
            tabla[i]=this.array[i];
        }
        this.array=tabla;
        cap=newCap;
    }
}
