package sample.repository.jdbc;

import org.springframework.stereotype.Repository;

import sample.repository.TestRepository;

@Repository
public class JdbcTestRepository implements TestRepository {
    
    @Override
    public String get() {
        return this.getClass().getSimpleName();
    }

}
