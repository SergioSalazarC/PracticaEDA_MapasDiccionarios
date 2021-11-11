
package dictionary;

import javax.management.openmbean.InvalidKeyException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mayte
 * @param <K>
 * @param <V>
 */
public class MyDictionary<K,V> implements Dictionary<K,V> {

    List<Entry<K,V>>[] tabla;
    int cap;
    int size;

    public MyDictionary(){
        cap=20;
        size=0;
        tabla= new List[20];
    }

    /**
     * @param <T> Key type
     * @param <U> Value type
     *
     */
    private class HashEntry<T, U> implements Entry<T, U> {

        protected T key;
        protected U value;

        public HashEntry(T k, U v) {
            key = k;
            value = v;
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
            U oldValue = value;
            value = val;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {

            if (o.getClass() != this.getClass()) {
                return false;
            }

            HashEntry<T, U> ent;
            try {
                ent = (HashEntry<T, U>) o;
            } catch (ClassCastException ex) {
                return false;
            }
            return (ent.getKey().equals(this.key))
                    && (ent.getValue().equals(this.value));
        }

        /**
         * Entry visualization.
         */
        @Override
        public String toString() {
            return "(" + key + "," + value + ")";
        }
    }
    
    private class HashDictionaryIterator<T, U> implements Iterator<Entry<T, U>> {

        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Entry<T, U> next() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        
        
    }
    
    
    /**
     * Hash function applying MAD method to default hash code.
     *
     * @param key Key
     * @return
     */
    private int hashValue(K key) {
        return (key.hashCode()%cap);
    }
    
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size==0;
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalStateException {
        int index = this.hashValue(key);
        if(this.tabla[index]==null){
            this.tabla[index]=new LinkedList<>();
        }
        Entry<K,V> entrada = new HashEntry<>(key,value);
        tabla[index].add(entrada);
        size++;
        if(size/cap > 0.75){
            rehash();
        }
        return entrada;
    }

    @Override
    public Entry<K, V> find(K key) throws IllegalStateException {
        if(key==null){
            throw new InvalidKeyException();
        }
        Entry<K,V> sol = null;
        int index = this.hashValue(key);
        if(this.tabla[index]==null){
            return sol;
        }
        for(Entry s : this.tabla[index]){
            if(s.getKey().equals(key)){
                sol=s;
                break;
            }
        }
        return sol;
    }

    @Override
    public Iterable<Entry<K, V>> findAll(K key) throws IllegalStateException {
        List<Entry<K,V>> sol = new LinkedList<>();
        int index = this.hashValue(key);
        if(this.tabla[index]==null){
            return sol;
        }
        for(Entry s : this.tabla[index]){
            if(s.getKey().equals(key)){
                sol.add(s);
                break;
            }
        }
        return sol;
    }

    @Override
    public Entry<K, V> remove(Entry<K, V> e) throws IllegalStateException {
        Entry<K,V> sol = null;
        int index = this.hashValue(e.getKey());
        if(this.tabla[index]==null){
            return sol;
        }
        if(this.tabla[index].remove(e)){
            sol=e;
        }
        size--;
        return sol;

    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        List<Entry<K,V>> lista = new LinkedList<>();
        for(int i=0;i<cap;i++){
            if(this.tabla[i]==null)continue;
            for(Entry<K,V> s : this.tabla[i]){
                lista.add(s);
            }
        }
        return lista;
    }
    
    @Override
    public Iterator<Entry<K, V>> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Doubles the size of the hash table and rehashes all the entries.
     */
    private void rehash() {
        int lastcap=cap;
        cap=cap*2;
        List<Entry<K,V>>[] nueva = new LinkedList[cap];
        for(int i=0;i<lastcap;i++){
            if(tabla[i]==null)continue;
            for(Entry<K,V> s: tabla[i]){
                int index = this.hashValue(s.getKey());
                if(nueva[index]==null){
                    nueva[index]=new LinkedList<>();
                }
                nueva[index].add(s);
            }
        }
        this.tabla=nueva;

    }
}
