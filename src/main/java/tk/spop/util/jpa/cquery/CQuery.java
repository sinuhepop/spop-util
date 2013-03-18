package tk.spop.util.jpa.cquery;

import lombok.Getter;

public class CQuery<T> {
    
    @Getter
    private final Class<T> entityClass;
    
    private final CQuery<T> previous;
    
    
    

}

