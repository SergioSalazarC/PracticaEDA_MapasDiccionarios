package maps;

/**
 * @param <K> The hey
 * @param <V> The stored value
 */
public class HashTableMapLP<K, V> extends AbstractHashTableMap<K, V> {

    public HashTableMapLP(int size) {
        super(size);
    }

    /**
     * Creates a hash table with prime factor 109345121 and capacity 1000.
     */
    public HashTableMapLP() {
        super();
    }

    //protected AbstractHashTableMap(int p, int cap)
    public HashTableMapLP(int p, int cap) {
        super(p, cap);
    }

    @Override
    protected int offset(int hashKey, int i) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
