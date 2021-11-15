package maps;
/**
 * @param <K> The hey
 * @param <V> The stored value
     */
public class HashTableMapQP<K, V> extends AbstractHashTableMap<K, V> {
    private int c1;
    private int c2;


    public HashTableMapQP(int size) {
        super(size);
        c1=(int)Math.floor(Math.random()*100 + 1);
        c2=(int)Math.floor(Math.random()*100 + 1);
    }

    public HashTableMapQP() {
        super();
    }

    public HashTableMapQP(int p, int cap) {
        super(p,cap);
    }

    @Override
    protected int offset(int hashKey, int longitud,int numprueb, int q) {
        int p=numprueb;
        return(hashKey+c1*p+c2*p*p)%longitud;
    }

}
