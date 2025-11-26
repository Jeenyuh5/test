package cn.cug.domain.basic.service.basic;

import cn.cug.domain.basic.model.entity.todoItemEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class todoManagerService implements ITodoManager{

    private List<todoItemEntity> todoList;

//    private final String JSON_FILE = "todo.json";

    @Override
    public void addTodo(String title, String description) {
        /*
        * 根据待办表状态初始化待办事务id
        * */
        int newId = todoList.isEmpty()? 1 : todoList.get(todoList.size() - 1).getId() +1;

        /*
        * 表中添加待办事务
        * */
        todoList.add(todoItemEntity.builder()
                .id(newId)
                .title(title)
                .description(description)
                .build());
    }

    @Override
    public boolean deleteTodo(int id) {

        return todoList.removeIf(item -> item.getId() == id);
    }

    @Override
    public boolean markTodoStatus(int id) {
        for(todoItemEntity item : todoList){
            if(item.getId() == id){
                item.setCompleted(!item.isCompleted());
                return true;
            }
        }
        return false;
    }

    @Override
    public List<todoItemEntity> getTodoList() {
        return new ArrayList<>(todoList);
    }
}
