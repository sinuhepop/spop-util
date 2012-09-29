package sample.repository.jpa;

import org.springframework.stereotype.Repository;

import sample.repository.TestRepository;


@Repository
public class JpaTestRepository implements TestRepository {

    @Override
    public String get() {
        return this.getClass().getSimpleName();
    }

}
