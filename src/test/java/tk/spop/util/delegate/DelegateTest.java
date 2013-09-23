package tk.spop.util.delegate;

import org.junit.Test;

public class DelegateTest {

    @Test
    public void test() {
        A p1 = DelegateFactory.of(A.class);
        A p2 = p1.x();
        A p3 = p2.y("d", 3);
        System.out.println(p3);
    }

}
