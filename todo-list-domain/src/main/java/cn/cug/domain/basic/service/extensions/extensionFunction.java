package cn.cug.domain.basic.service.extensions;

import cn.cug.domain.basic.model.entity.todoItemEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import sun.util.resources.cldr.fa.CalendarData_fa_AF;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
public class extensionFunction implements IExtensionFunction{

    private final String JSON_FILE = "todo.json";

    private ObjectMapper objectMapper = new ObjectMapper() ;

    @Override
    public void saveToFile(List<todoItemEntity> list) {
        try{
            objectMapper.writeValue(new File(JSON_FILE), list);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    @Override
    public List<todoItemEntity> loadFromFile() {
        return Collections.emptyList();
    }

    @Override
    public List<todoItemEntity> searchTodoItem(String info) {
        return Collections.emptyList();
    }
}
