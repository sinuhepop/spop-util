package tk.spop.test.repository.jpa;

import org.springframework.stereotype.Repository;

import tk.spop.test.repository.TestRepository;


@Repository
public class JpaTestRepository implements TestRepository {

    @Override
    public String get() {
        return this.getClass().getSimpleName();
    }

}
