
package itinerary;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import material.Pair;

/**
 *
 * @author mayte
 */
public class Organize {

    HashMap<String,String> billetes;
    HashSet<String> repetido;

    
    public Organize (List<Pair<String,String>> lista){
        billetes=new HashMap<>();
        repetido=new HashSet<>();

        for(Pair<String,String> billete: lista){
            String inicio = billete.getFirst();
            String fin = billete.getSecond();
            billetes.put(inicio,fin);
            if(repetido.contains(inicio)){
                repetido.remove(inicio);
            }
            else{
                repetido.add(inicio);
            }
            if(repetido.contains(fin)){
                repetido.remove(fin);
            }
            else{
                repetido.add(fin);
            }
        }
    }
    
    /**
     * Returns the itinerary to travel or thrown an exception
     * @return 
     */
    public List<String> itineratio(){
        List<String> lista = new LinkedList<>();

        /*
        En el conjunto rpeetido solo habra dos string, el incio y final del trayecto, pues son los dos unicos string que aparecen una vez
         */
        String inicio=null;
        /*
        Puede parecer que estamos recorriendo el conjunto, pero en realidad solo comprobamos cual de los dos elementos es el incio
        Sabemos al 100% que solo habra dos elementos por tanto es correcto
         */
        for(String p:repetido){
            if(billetes.containsKey(p)){
                inicio=p;
                break;
            }
        }
        lista.add(inicio);


        //Sabemos que el bucle se hara n veces, con n el numero de billetes
        for(int i=0;i<billetes.size();i++){
            String k=billetes.get(inicio);
            lista.add(k);
            inicio=k;
        }

        return lista;
    }

}
