package tk.spop.util.spring.dynamic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import sample.SampleConfig;
import sample.repository.PersistenceDecider;
import sample.repository.PersistenceDecider.Implementation;
import sample.repository.TestRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SampleConfig.class)
public class DynamicTest {

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
