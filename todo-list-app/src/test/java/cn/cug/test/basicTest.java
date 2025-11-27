package cn.cug.test;

import cn.cug.domain.basic.model.entity.TodoItemEntity;
import cn.cug.domain.basic.service.basic.TodoManagerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class basicTest {

    private TodoManagerService todoManager;


    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        // 初始化待办管理器
        todoManager = new TodoManagerService();

        // 通过反射初始化私有成员todoList（避免空指针）
        Field todoListField = TodoManagerService.class.getDeclaredField("todoList");
        todoListField.setAccessible(true);
        todoListField.set(todoManager, new java.util.ArrayList<>());
    }

    // 测试添加待办事项
    @Test
    public void addTodo_shouldAddItemWithCorrectId() {
        // 测试添加第一个待办（id=1）
        todoManager.addTodo("测试标题1", "测试描述1");
        List<TodoItemEntity> list = todoManager.getTodoList();
        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getId());
        assertEquals("测试标题1", list.get(0).getTitle());
        assertEquals("测试描述1", list.get(0).getDescription());

        // 测试添加第二个待办（id=2）
        todoManager.addTodo("测试标题2", "测试描述2");
        list = todoManager.getTodoList();
        assertEquals(2, list.size());
        assertEquals(2, list.get(1).getId());
    }

    // 测试删除待办事项
    @Test
    public void deleteTodo_shouldReturnCorrectResult() {
        // 先添加一个待办
        todoManager.addTodo("删除测试", "删除描述");
        assertEquals(1, todoManager.getTodoList().size());

        // 删除存在的id
        boolean deleteSuccess = todoManager.deleteTodo(1);
        assertTrue(deleteSuccess);
        assertEquals(0, todoManager.getTodoList().size());

        // 删除不存在的id
        boolean deleteFail = todoManager.deleteTodo(999);
        assertFalse(deleteFail);
    }

    // 测试标记待办状态
    @Test
    public void markTodoStatus_shouldToggleCompleted() {
        // 添加待办（默认completed=false）
        todoManager.addTodo("状态测试", "状态描述");
        TodoItemEntity item = todoManager.getTodoList().get(0);
        assertFalse(item.isCompleted());

        // 标记存在的id，状态翻转
        boolean markSuccess = todoManager.markTodoStatus(1);
        assertTrue(markSuccess);
        assertTrue(todoManager.getTodoList().get(0).isCompleted());

        // 再次标记，状态回退
        todoManager.markTodoStatus(1);
        assertFalse(todoManager.getTodoList().get(0).isCompleted());

        // 标记不存在的id
        boolean markFail = todoManager.markTodoStatus(999);
        assertFalse(markFail);
    }

    // 测试获取待办列表（返回拷贝，不影响原列表）
    @Test
    public void getTodoList_shouldReturnCopy() {
        // 添加待办
        todoManager.addTodo("拷贝测试", "拷贝描述");
        List<TodoItemEntity> returnedList = todoManager.getTodoList();

        // 修改返回的列表，原列表应不受影响
        returnedList.clear();
        assertEquals(1, todoManager.getTodoList().size());
    }
}
