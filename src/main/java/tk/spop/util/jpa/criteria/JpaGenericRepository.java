package tk.spop.util.jpa.criteria;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

//import es.kcsolutions.core.GenericRepository;
//import es.kcsolutions.util.IdUtil;
//import es.kcsolutions.util.Identifiable;
//import es.kcsolutions.util.ReflectionUtil;


@Repository
public class JpaGenericRepository /*<T extends Identifiable<K>, K> implements GenericRepository<T, K> */ {
/*
	@PersistenceContext
	protected EntityManager entityManager;

	protected final Class<T> entityClass;
	protected final Class<K> keyClass;

	@SuppressWarnings("unchecked")
	protected JpaGenericRepository() {
		ParameterizedType type = ReflectionUtil.getParameterizedType(getClass());
		this.entityClass = (Class<T>) type.getActualTypeArguments()[0];
		this.keyClass = IdUtil.getIdClass(entityClass);
	}

	public JpaGenericRepository(Class<T> entityClass) {
		this.entityClass = entityClass;
		this.keyClass = IdUtil.getIdClass(entityClass);
	}

	@Override
	public T get(K id) {
		return entityManager.find(entityClass, id);
	}

	@Override
	public List<T> get(List<K> idList) {
		Criteria<T, K> criteria = createCriteria();
		List<T> entityList = criteria.add(criteria.getEntityRoot().get(Identifiable.ID_PROPERTY).in(idList)).list();
		IdUtil.sort(entityList, idList);
		return entityList;
	}

	@Override
	public List<T> list() {
		return createCriteria().list();
	}

	@Override
	public T save(T entity) {
		if (entityManager.contains(entity)) {
			return entityManager.merge(entity);
		} else {
			entityManager.persist(entity);
			return entity;
		}
	}

	@Override
	public void remove(T entity) {
		entityManager.remove(entity);
	}

	protected Criteria<T, K> createCriteria() {
		return new Criteria<T, K>(entityClass, entityManager);
	}
*/
}
