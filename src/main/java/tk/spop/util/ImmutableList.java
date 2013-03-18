package tk.spop.util;

import java.util.*;

public class ImmutableList<T> implements Iterable<T> {

    private final Object[] array;

    public ImmutableList() {
        array = new Object[0];
    }

    public ImmutableList(Collection<T> collection) {
        array = collection.toArray();
    }

    @SafeVarargs
    private ImmutableList(Object[] old, T... elements) {
        array = new Object[old.length + elements.length];
        System.arraycopy(old, 0, array, 0, old.length);
        System.arraycopy(elements, 0, array, old.length, elements.length);
    }

    @SuppressWarnings("unchecked")
    public ImmutableList<T> with(T... elements) {
        return new ImmutableList<T>(array, elements);
    }

    public int size() {
        return array.length;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) array[index];
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int pos = -1;

            @Override
            public boolean hasNext() {
                return pos < array.length;
            }

            @SuppressWarnings("unchecked")
            @Override
            public T next() {
                pos++;
                return (T) array[pos];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }
}
