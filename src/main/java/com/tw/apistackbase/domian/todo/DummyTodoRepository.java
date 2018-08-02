package com.tw.apistackbase.domian.todo;

import com.tw.apistackbase.domian.common.InMemeryRepository;
import com.tw.apistackbase.domian.todo.model.Todo;
import org.springframework.stereotype.Repository;

/**
 * Created by jxzhong on 2017/7/3.
 */
@Repository
public class DummyTodoRepository extends InMemeryRepository<Todo, Long> {

    @Override
    protected <S extends Todo> Long getEntityId(S todo) {
        return todo.getId();
    }
}
