package tk.spop.util.spring.dynamic;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import sample.repository.*;
import sample.repository.PersistenceDecider.Implementation;
import tk.spop.util.AbstractTest;

public class DynamicTest extends AbstractTest {

    @Autowired
    private PersistenceDecider persistenceDecider;

    @Autowired
    private TestRepository repository;

    @Test
    public void dynamic() {
        persistenceDecider.setPersistence(Implementation.JPA);
        Assert.isTrue(repository.get().contains("Jpa"));
        persistenceDecider.setPersistence(Implementation.JDBC);
        Assert.isTrue(repository.get().contains("Jdbc"));
    }

}
