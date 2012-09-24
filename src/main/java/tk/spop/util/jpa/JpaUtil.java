package tk.spop.util.jpa;

import javax.persistence.*;


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


    public String getSql(Query query, JpaProvider provider) {

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

}