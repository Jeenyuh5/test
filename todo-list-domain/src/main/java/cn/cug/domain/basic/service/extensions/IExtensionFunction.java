package cn.cug.domain.basic.service.extensions;

import cn.cug.domain.basic.model.entity.todoItemEntity;

import java.util.List;

public interface IExtensionFunction {

    /*
    * 保存到本地
    * */
    void saveToFile(List<todoItemEntity> list);

    /*
    * 从本地文件中加载
    * */
    List<todoItemEntity> loadFromFile();


    /*
    * 根据信息检索待办事项
    * */
    List<todoItemEntity> searchTodoItem(String info);
}
