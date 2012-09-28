package tk.spop.util.spring.dynamic;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import tk.spop.test.repository.TestRepository;
import tk.spop.util.AbstractTest;
import tk.spop.util.spring.dynamic.PersistenceDecider.Persistence;


public class DynamicTest extends AbstractTest {

    @Autowired
    private PersistenceDecider decider;

    @Autowired
    private TestRepository repository;


    @Test
    public void dynamic() {
        decider.setPersistence(Persistence.JPA);
        Assert.isTrue(repository.get().contains("Jpa"));
        decider.setPersistence(Persistence.JDBC);
        Assert.isTrue(repository.get().contains("Jdbc"));
    }

}
