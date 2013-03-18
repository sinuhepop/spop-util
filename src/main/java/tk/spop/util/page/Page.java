package tk.spop.util.page;

import java.util.List;

public interface Page<T> extends Iterable<T> {

    /**
     * @return list of items contained in page
     */
    List<T> getItems();

    /**
     * @return the zero-based number of the current page
     */
    long getNumber();

    /**
     * @return number of elements this page can contain
     */
    int getSize();

    /**
     * @return actual number of items in current page. Can be less than page size.
     */
    int getActualSize();

    /**
     * @return number of total items, not only current page.
     */
    long getTotalSize();

    /**
     * @return number of total pages
     */
    long getTotalPages();

    /**
     * @return whether is first page
     */
    boolean isFirst();

    /**
     * @return whether is last page
     */
    boolean isLast();

    /**
     * @return index of first item in total items
     */
    long getFirstItem();
    
    /**
     * @return index of last item in total items
     */
    long getLastItem();

}
