package tk.spop.util.page;

import java.util.*;

import lombok.Data;

@Data
public class PageImpl<T> implements Page<T> {

    private final List<T> items;
    private final long number;
    private final int size;
    private final long totalSize;

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }

    @Override
    public int getActualSize() {
        return items.size();
    }

    @Override
    public long getTotalPages() {
        return 1 + (totalSize - 1) / size;
    }

    @Override
    public boolean isFirst() {
        return number == 0;
    }

    @Override
    public boolean isLast() {
        return number >= getTotalPages();
    }

    @Override
    public long getFirstItem() {
        return number * size;
    }

    @Override
    public long getLastItem() {
        return number * size + getActualSize() - 1;
    }

}
