package cn.cug.domain.basic.adapter.repository;

import cn.cug.domain.basic.model.entity.TodoItemEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IExtensionRepository {

    void saveFile(String source ,List<TodoItemEntity> list) throws IOException;

    List<TodoItemEntity> loadFile(File file) throws IOException;
}
