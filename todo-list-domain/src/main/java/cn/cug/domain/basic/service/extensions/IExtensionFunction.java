package cn.cug.domain.basic.service.extensions;

import cn.cug.domain.basic.model.entity.TodoItemEntity;

import java.util.List;

public interface IExtensionFunction {

    /*
    * 保存到本地
    * */
    void saveToFile(List<TodoItemEntity> list);

    /*
    * 从本地文件中加载
    * */
    List<TodoItemEntity> loadFromFile(String source);


    /*
    * 根据信息检索待办事项
    * */
    List<TodoItemEntity> searchTodoItem(String info);
}
