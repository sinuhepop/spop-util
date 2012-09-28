package tk.spop.util.spring.dynamic;

import java.util.*;
import java.util.Map.Entry;

import lombok.*;


@AllArgsConstructor
public class PersistenceDecider implements DynamicDecider {

    public enum Persistence {
        JPA, JDBC
    }

    @Setter
    private Persistence persistence = Persistence.JPA;


    @Override
    public <T> T choose(Map<String, T> beans) {
        for (Entry<String, T> entry : beans.entrySet()) {
            if (entry.getKey().toUpperCase().startsWith(persistence.name())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
