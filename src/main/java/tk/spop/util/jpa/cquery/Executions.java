package tk.spop.util.jpa.cquery;

import java.util.List;

import javax.persistence.*;
import javax.persistence.criteria.*;

import lombok.RequiredArgsConstructor;
import tk.spop.util.page.*;

@RequiredArgsConstructor
public class Executions {

    private final EntityManager entityManager;

    protected static <T> CriteriaQuery<T> getQuery(CQuery<T> criteria) {
        return null;
    }

    public <T> List<T> list(CQuery<T> criteria) {
        CriteriaQuery<T> criteriaQuery = getQuery(criteria);
        TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public <T> long count(CQuery<T> criteria) {
        CriteriaQuery<T> criteriaQuery = getQuery(criteria);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<T> entityRoot = countQuery.from(criteria.getEntityClass());
        countQuery.select(builder.count(entityRoot));
        countQuery.where(criteriaQuery.getRestriction());
        long count = entityManager.createQuery(countQuery).getSingleResult();
        return count;
    }

    public <T> int delete(CQuery<T> criteria) {
        CriteriaQuery<T> criteriaQuery = getQuery(criteria);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> criteriaDelete = builder.createCriteriaDelete(criteria.getEntityClass());
        criteriaDelete.from(criteria.getEntityClass());
        criteriaDelete.where(criteriaQuery.getRestriction());
        Query query = entityManager.createQuery(criteriaDelete);
        return query.executeUpdate();
    }

    public <T> T get(CQuery<T> criteria) {
        List<T> list = list(criteria, 0, 2);
        if (list.size() == 1) {
            return list.get(0);
        }
        if (list.size() == 0) {
            return null;
        }
        throw new NonUniqueResultException();
    }

    public <T> List<T> list(CQuery<T> criteria, int firstResult, int maxResults) {
        CriteriaQuery<T> criteriaQuery = getQuery(criteria);
        TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((int) firstResult);
        typedQuery.setMaxResults(maxResults);
        return typedQuery.getResultList();
    }

    public <T> Page<T> page(CQuery<T> criteria, int number, int size) {
        int offset = number * size;
        List<T> list = list(criteria, offset, size);
        long count;
        if (size < list.size()) {
            count = offset + list.size();
        } else {
            count = count(criteria);
        }
        return new PageImpl<T>(list, number, size, count);
    }

    public <T> int update(CQuery<T> criteria) {
        CriteriaQuery<T> criteriaQuery = getQuery(criteria);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<T> criteriaUpdate = builder.createCriteriaUpdate(criteria.getEntityClass());
        criteriaUpdate.from(criteria.getEntityClass());
        criteriaUpdate.where(criteriaQuery.getRestriction());
        // criteria.set(null, null);
        Query query = entityManager.createQuery(criteriaUpdate);
        return query.executeUpdate();
    }
}
