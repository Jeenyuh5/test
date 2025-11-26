package cn.cug.domain.basic.service.basic;

import cn.cug.domain.basic.model.entity.todoItemEntity;

import java.util.List;

public interface ITodoManager {

    /*
    * 添加待办事项
    * */
     void addTodo(String title , String description);

     /*
     * 删除待办事项
     * */
     boolean deleteTodo(int id);

     /*
     * 标记待办事项是否完成
     * */
     boolean markTodoStatus(int id);

     /*
     * 查看待办事项列表
     * */
     List<todoItemEntity> getTodoList();


}
