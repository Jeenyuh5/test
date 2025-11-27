package cn.cug.infrastructure.adapter.repository;

import cn.cug.domain.basic.adapter.repository.IExtensionRepository;
import cn.cug.domain.basic.model.entity.TodoItemEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Repository
public class ExtensionRepository implements IExtensionRepository {

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    @Override
    public void saveFile(String source ,List<TodoItemEntity> list) throws IOException {
        objectMapper.writeValue(new File(source), list);
        System.out.println("已保存" + list.size() + "条待办事项");
    }

    @Override
    public List<TodoItemEntity> loadFile(File file) throws IOException {

       return objectMapper.readValue(file, new TypeReference<List<TodoItemEntity>>() {
        });
    }
}
