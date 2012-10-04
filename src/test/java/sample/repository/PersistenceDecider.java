package sample.repository;

import java.util.*;
import java.util.Map.Entry;

import lombok.Setter;

import org.springframework.stereotype.Component;

import tk.spop.util.spring.dynamic.DynamicDecider;

@Component
public class PersistenceDecider implements DynamicDecider {

    public enum Implementation {
        JPA,
        JDBC
    }

    @Setter
    private Implementation persistence = Implementation.JPA;

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
