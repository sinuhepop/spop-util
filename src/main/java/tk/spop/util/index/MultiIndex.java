package tk.spop.util.index;

import java.util.*;

public class MultiIndex<T> {

    private final List<T> list = new ArrayList<>();
    private final Map<String, List<T>> indexMap = new HashMap<>();
    
    public MultiIndex(List<T> list, String... fields) {
        this.list.addAll(list);
        for (String field: fields) {
            addIndex(field);
        }
    }
    
    
    public void addIndex(String field) {
        
        
    }
    
    
}
