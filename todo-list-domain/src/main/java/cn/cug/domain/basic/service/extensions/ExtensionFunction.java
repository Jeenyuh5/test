package cn.cug.domain.basic.service.extensions;

import cn.cug.domain.basic.adapter.repository.IExtensionRepository;
import cn.cug.domain.basic.model.entity.TodoItemEntity;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExtensionFunction implements IExtensionFunction{

    @Resource
    private List<TodoItemEntity> todoList;

    private final String JSON_FILE = "todo.json";

    @Resource
    private IExtensionRepository extensionRepository;




    @Override
    public void saveToFile(List<TodoItemEntity> list) {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH_mm_ss");
            String now = LocalDateTime.now().format(formatter);
            extensionRepository.saveFile(now+JSON_FILE, list);

        }catch (Exception e){
//            e.printStackTrace();
            log.error("保存数据到文件失败：{}", e.getMessage());
        }
    }

    @Override
    public List<TodoItemEntity> loadFromFile(String source) {
        File file = new File(source);
        if(!file.exists()){
            System.out.println("文件不存在！，加载失败");

            return todoList;
        }
        try {
            return extensionRepository.loadFile(file);
        } catch (Exception e) {
            log.error("加载数据失败：{}", e.getMessage());
            return todoList;
        }
    }

    @Override
    public List<TodoItemEntity> searchTodoItem( String info) {
        String lowerKeyword = info.toLowerCase();
        return todoList.stream()
                .filter(item -> item.getTitle().toLowerCase().contains(lowerKeyword)
                         ||(item.getDescription()!=null && item.getDescription().toLowerCase().contains(lowerKeyword)))
                        .collect(Collectors.toList());
    }
}
