package tk.spop.util.jpa;

import java.lang.reflect.Field;

import javax.persistence.*;
import javax.persistence.metamodel.Attribute;

import lombok.SneakyThrows;

public class JpaUtil {

    public JpaProvider getProvider(EntityManager entityManager) {
        String name = entityManager.getClass().getName();
        for (JpaProvider provider : JpaProvider.values()) {
            if (name.startsWith(provider.getPattern())) {
                return provider;
            }
        }
        return null;
    }

    public static String getSql(Query query, JpaProvider provider) {

        switch (provider) {

            case HIBERNATE:
                return query.unwrap(org.hibernate.Query.class).getQueryString();

            case ECLIPSELINK:
                return query.unwrap(org.eclipse.persistence.internal.jpa.JPAQuery.class).getDatabaseQuery().getSQLString();

            case OPENJPA:
                return query.unwrap(org.apache.openjpa.persistence.QueryImpl.class).getQueryString();

            case DATANUCLEUS:
            case OBJECTDB:
            case OTHER:
                // TODO
                return "";

        }
        return null;
    }

    /**
     * TODO: Clears the second level cache.
     */
    public void cacheEvict() {
        // entityManager.getEntityManagerFactory().getCache().evictAll();
    }

    // TODO: Vaia castanya...
    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static <T> Attribute<T, ?> getAttribute(Class<T> entityClass, String name) {
        Class<?> clss = Class.forName(entityClass.getName() + "_");
        Field field = clss.getField(name);
        return (Attribute<T, ?>) field.get(null);
    }

}