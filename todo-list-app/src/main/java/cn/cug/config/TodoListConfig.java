package cn.cug.config;

import cn.cug.domain.basic.model.entity.TodoItemEntity;
import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class TodoListConfig {

    @Bean
    public List<TodoItemEntity> todoList(){
        return new ArrayList<>();
    }
}
