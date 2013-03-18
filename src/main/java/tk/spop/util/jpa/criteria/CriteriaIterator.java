package tk.spop.util.jpa.criteria;

import java.util.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriteriaIterator<T> implements Iterator<T> {

    private final Criteria<T> criteria;
    private final int fetchSize;
    private final boolean flush;

    private List<T> list;
    private int page = -1;
    private int index = Integer.MAX_VALUE;
    private int deleted = 0;
    private T current;

    @Override
    public boolean hasNext() {
        if (index >= fetchSize-1) {
            if (flush) {
                criteria.getEntityManager().flush();
                criteria.getEntityManager().clear();
            }
            page++;
            list = criteria.list(page * fetchSize - deleted, fetchSize);
            index = -1;
        }
        return index < list.size();
    }

    @Override
    public T next() {

        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        index++;
        current = list.get(index);
        return current;
    }

    @Override
    public void remove() {
        try {
            criteria.getEntityManager().remove(current);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(e);
        }
        deleted++;
    }

}
