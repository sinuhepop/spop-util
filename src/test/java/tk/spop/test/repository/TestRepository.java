package tk.spop.test.repository;

import tk.spop.util.spring.dynamic.Dynamic;


@Dynamic(decider = "decider")
public interface TestRepository {

    String get();

}
