package cn.cug.domain.basic.service.basic;

import cn.cug.domain.basic.model.entity.TodoItemEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoManagerService implements ITodoManager{
    @Resource
    private List<TodoItemEntity> todoList;

    private LocalDateTime createTime;


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
        todoList.add(TodoItemEntity.builder()
                .id(newId)
                .title(title)
                .description(description)
                .createTime(LocalDateTime.now())
                .build());
    }

    @Override
    public boolean deleteTodo(int id) {

        return todoList.removeIf(item -> item.getId() == id);
    }

    @Override
    public boolean markTodoStatus(int id) {
        for(TodoItemEntity item : todoList){
            if(item.getId() == id){
                item.setCompleted(!item.isCompleted());
                return true;
            }
        }
        return false;
    }

    @Override
    public List<TodoItemEntity> getTodoList() {
        return todoList;
    }
}
