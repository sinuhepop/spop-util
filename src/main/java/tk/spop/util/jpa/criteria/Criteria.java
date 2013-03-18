package tk.spop.util.jpa.criteria;

import java.util.*;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.*;

import lombok.Getter;
import tk.spop.util.page.*;

@Getter
public class Criteria<T> implements Iterable<T> {

    public static final int DEFAULT_FETCH_SIZE = 1000;

    private final EntityManager entityManager;
    private final Class<T> entityClass;
    private final CriteriaBuilder builder;
    private final CriteriaQuery<T> query;
    private final Root<T> entityRoot;

    private boolean negateNext = false;

    public Criteria(Class<T> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
        this.builder = entityManager.getCriteriaBuilder();
        this.query = builder.createQuery(entityClass);
        this.entityRoot = query.from(entityClass);
    }

    @Override
    public Iterator<T> iterator() {
        return new CriteriaIterator<>(this, DEFAULT_FETCH_SIZE);
    }

    public T get() {
        List<T> list = list(0, 2);
        if (list.size() == 1) {
            return list.get(0);
        }
        if (list.size() == 0) {
            return null;
        }
        throw new NonUniqueResultException();
    }

    public List<T> list() {
        TypedQuery<T> query = entityManager.createQuery(getQuery());
        return query.getResultList();
    }

    public List<T> list(long firstResult, int maxResults) {
        TypedQuery<T> query = entityManager.createQuery(getQuery());
        query.setFirstResult((int) firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    public long count() {
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<T> entityRoot = countQuery.from(entityClass);
        countQuery.select(builder.count(entityRoot));
        countQuery.where(query.getRestriction());
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    public Page<T> page(long number, int size) {
        long offset = number * size;
        List<T> list = list(offset, size);
        long count;
        if (size < list.size()) {
            count = offset + list.size();
        } else {
            count = count();
        }
        return new PageImpl<>(list, number, size, count);
    }

    public int delete() {
        CriteriaDelete<T> criteria = builder.createCriteriaDelete(entityClass);
        criteria.from(entityClass);
        criteria.where(query.getRestriction());
        Query query = entityManager.createQuery(criteria);
        return query.executeUpdate();
    }

    public int update() {
        CriteriaUpdate<T> criteria = builder.createCriteriaUpdate(entityClass);
        criteria.from(entityClass);
        criteria.where(query.getRestriction());
        // criteria.set(null, null);
        Query query = entityManager.createQuery(criteria);
        return query.executeUpdate();
    }

    // public List<K> getIds() {
    // CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    // CriteriaQuery<K> idQuery = builder.createQuery(IdUtil.getIdClass(entityClass));
    // Root<T> entityRoot = idQuery.from(entityClass);
    // @SuppressWarnings("unchecked") Path<K> path = (Path<K>) entityRoot.get(Identifiable.ID_PROPERTY);
    // idQuery.select(path);
    // idQuery.where(query.getRestriction());
    // return entityManager.createQuery(idQuery).getResultList();
    // }
    //
    // public int delete() {
    // List<K> ids = getIds();
    // EntityType<T> type = entityManager.getMetamodel().entity(entityClass);
    // return entityManager.createQuery("delete from " + type.getName() +
    // " where id in :ids").setParameter(":ids", ids).executeUpdate();
    // }

    // **********************************************************************
    // **** Restrictions
    // **********************************************************************

    public Criteria<T> add(Predicate predicate) {

        Predicate current = query.getRestriction();
        if (current != null) {
            predicate = builder.and(current, predicate);
        }

        if (negateNext) {
            predicate = predicate.not();
            negateNext = false;
        }
        query.where(predicate);

        return this;
    }

    // **********************************************************************
    // **** Comparations
    // **********************************************************************

    public <S> Criteria<T> eq(SingularAttribute<T, S> field, S value) {
        Predicate predicate;
        if (value == null) {
            predicate = builder.isNull(entityRoot.get(field));
        } else {
            predicate = builder.equal(entityRoot.get(field), value);
        }
        return add(predicate);
    }

    public <S extends Comparable<? super S>> Criteria<T> gt(SingularAttribute<T, ? extends S> field, S value) {
        return add(builder.greaterThan(entityRoot.get(field), value));
    }

    public <S extends Comparable<? super S>> Criteria<T> ge(SingularAttribute<T, ? extends S> field, S value) {
        return add(builder.greaterThanOrEqualTo(entityRoot.get(field), value));
    }

    public <S extends Comparable<? super S>> Criteria<T> lt(SingularAttribute<T, ? extends S> field, S value) {
        return add(builder.lessThanOrEqualTo(entityRoot.get(field), value));
    }

    public <S extends Comparable<? super S>> Criteria<T> le(SingularAttribute<T, ? extends S> field, S value) {
        return add(builder.lessThanOrEqualTo(entityRoot.get(field), value));
    }

    public <S extends Comparable<? super S>> Criteria<T> between(SingularAttribute<T, ? extends S> field, S from, S to) {
        return add(builder.between(entityRoot.get(field), from, to));
    }

    // **********************************************************************
    // **** Other restrictions
    // **********************************************************************

    public Criteria<T> like(SingularAttribute<T, String> field, String pattern) {
        return add(builder.like(entityRoot.get(field), pattern));
    }

    public Criteria<T> ilike(SingularAttribute<T, String> field, String pattern) {
        return add(builder.like(builder.upper(entityRoot.get(field)), pattern.toUpperCase()));
    }

    public Criteria<T> or(Predicate predicate) {
        return add(builder.or(predicate));
    }

    public <S> Criteria<T> in(SingularAttribute<T, S> field, List<S> list) {
        return add(entityRoot.get(field).in(list));
    }

    // **********************************************************************
    // **** Order
    // **********************************************************************

    public Criteria<T> asc(SingularAttribute<T, ?> field) {
        addOrder(builder.asc(entityRoot.get(field)));
        return this;
    }

    public Criteria<T> desc(SingularAttribute<T, ?> field) {
        addOrder(builder.desc(entityRoot.get(field)));
        return this;
    }

    public Criteria<T> addOrder(Order order) {
        List<Order> orderList = getOrderList(query);
        orderList.add(order);
        query.orderBy(orderList);
        return this;
    }

    // **********************************************************************
    // **** Other operations
    // **********************************************************************

    public Criteria<T> not() {
        negateNext = true;
        return this;
    }

    @SuppressWarnings("unchecked")
    public Criteria<T> fetch(Attribute<? super T, ?> field) {
        if (field instanceof SingularAttribute) {
            entityRoot.fetch((SingularAttribute<T, ?>) field);
        } else {
            entityRoot.fetch((PluralAttribute<T, ?, ?>) field);
        }
        return this;
    }

    public Criteria<T> merge(Criteria<T> criteria) {

        this.add(criteria.getQuery().getRestriction());

        for (Fetch<T, ?> fetch : criteria.getEntityRoot().getFetches()) {
            fetch(fetch.getAttribute());
        }

        List<Order> orderList = getOrderList(criteria.getQuery());
        orderList.addAll(query.getOrderList());
        query.orderBy(orderList);

        return this;
    }

    // *********************
    private List<Order> getOrderList(CriteriaQuery<T> query) {
        List<Order> orderList = query.getOrderList();
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        return orderList;
    }

    // TODO: order in all queries. pagination problems
    // TODO: toString??
    // TODO: getSql()
}
