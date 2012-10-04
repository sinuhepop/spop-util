package sample.repository;

import tk.spop.util.spring.dynamic.Dynamic;

@Dynamic(decider = "persistenceDecider")
public interface TestRepository {

    String get();

}
