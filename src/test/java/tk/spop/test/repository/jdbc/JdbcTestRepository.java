package tk.spop.test.repository.jdbc;

import org.springframework.stereotype.Repository;

import tk.spop.test.repository.TestRepository;

@Repository
public class JdbcTestRepository implements TestRepository {
    
    @Override
    public String get() {
        return this.getClass().getSimpleName();
    }

}
