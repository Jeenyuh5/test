package cn.cug.domain.commandline.service.todocmd;

import cn.cug.domain.basic.model.entity.TodoItemEntity;
import cn.cug.domain.basic.service.basic.TodoManagerService;
import cn.cug.domain.basic.service.extensions.ExtensionFunction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Scanner;

@Service
public class TodoCMDService implements CommandLineRunner {

    @Resource
    private TodoManagerService todoManager;

    @Resource
    private ExtensionFunction extensionFunction;

    @Resource
    private List<TodoItemEntity> todoList;

    private Scanner sc ;


    @Override
    public void run(String... args) throws Exception {
        sc = new Scanner(System.in);
        System.out.println("===== Spring Boot TODO List 命令行工具 =====");
        while (true) {
            printMenu();
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                handleChoice(choice);
            } catch (NumberFormatException e) {
                System.out.println("请输入有效的数字！");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n请选择操作：");
        System.out.println("1. 添加任务");
        System.out.println("2. 删除任务");
        System.out.println("3. 标记任务完成/未完成");
        System.out.println("4. 查看所有任务");
        System.out.println("5. 搜索任务");
        System.out.println("6.更多服务");
        System.out.println("7. 退出");
        System.out.print("输入选项（1-6）：");
    }

    /*
    * 选项处理
    * */
    private void handleChoice(int choice) {
        switch (choice) {
            case 1:
                addTodo();
                break;
            case 2:
                deleteTodo();
                break;
            case 3:
                toggleTodoStatus();
                break;
            case 4:
                showAllTodos();
                break;
            case 5:
                searchTodos();
                break;
            case 6:
                moreService();
                break;
            case 7:
                System.out.println("再见！");
                sc.close(); // 关闭Scanner
                System.exit(0); // 退出程序
                break;
            default:
                System.out.println("无效选项，请重新输入！");
        }
    }

    private void moreService() {
        System.out.println("\n请选择操作：");
        System.out.println("1. 保存待办事项到本地文件");
        System.out.println("2. 从本地文件中加载待办事项（会覆盖当前待办事项表）");
        System.out.print("输入选项（1-2）：");
        int choice =  Integer.parseInt(sc.nextLine().trim());
        handleExtensionChoice(choice);
    }

    private void handleExtensionChoice(int choice) {
        switch (choice) {
            case 1:
                saveFile();
                break;
            case 2:
                loadFile();
                break;
            default:
                System.out.println("无效选项，请重新输入！");
                break;
        }
    }



    private void loadFile() {
        System.out.println("请输入文件路径：");
        String path = sc.nextLine().trim();
        System.out.println(path);
        List<TodoItemEntity> loadedTodoList = extensionFunction.loadFromFile(path);

        /*仅在文件加载成功后覆盖todolist*/
        if(loadedTodoList != todoList){
            todoList.clear();
            todoList.addAll(loadedTodoList);
            System.out.println("已加载" + loadedTodoList.size() + "条待办事项");
        }

    }

    private void saveFile() {
        extensionFunction.saveToFile(todoList);

    }

    /*
    * 添加待办事项
    * */
    private void addTodo() {
        System.out.print("请输入任务标题（必填）：");
        String title = sc.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("标题不能为空！");
            return;
        }
        System.out.print("请输入任务描述（可选，直接回车跳过）：");
        String description = sc.nextLine().trim();


        todoManager.addTodo(title, description);
        System.out.println("任务添加成功！");
    }

    /*
    * 删除待办事项
    * */
    private void deleteTodo() {
        System.out.print("请输入要删除的任务ID：");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            boolean success = todoManager.deleteTodo(id);
            System.out.println(success ? "任务删除成功！" : "未找到该任务ID！");
        } catch (NumberFormatException e) {
            System.out.println("ID必须是数字！");
        }
    }

    /*
    * 标记待办事项完成/未完成
    * */
    private void toggleTodoStatus() {
        System.out.print("请输入要标记的任务ID：");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            boolean success = todoManager.markTodoStatus(id);
            System.out.println(success ? "任务状态已更新！" : "未找到该任务ID！");
        } catch (NumberFormatException e) {
            System.out.println("ID必须是数字！");
        }
    }

    /*
    * 查看所有待办事项
    * */
    private void showAllTodos() {
        List<TodoItemEntity> list = todoManager.getTodoList();
        if (list.isEmpty()) {
            System.out.println("当前没有任务！");
            return;
        }
        System.out.println("\n===== 任务列表 =====");
        for (TodoItemEntity item : list) {
            String status = item.isCompleted() ? "[✓] 已完成" : "[ ] 未完成";
            System.out.printf("ID: %d | %s | 标题：%s%n", item.getId(), status, item.getTitle());
            if (item.getDescription() != null && !item.getDescription().isEmpty()) {
                System.out.printf("   描述：%s%n", item.getDescription());
            }
            System.out.printf("   创建时间：%s%n", item.getCreateTime());
        }
    }

    /*
    * 搜索待办事项
    * */
    private void searchTodos() {
        System.out.print("请输入搜索关键词：");
        String info = sc.nextLine().trim();
        List<TodoItemEntity> results = extensionFunction.searchTodoItem(info);
        if (results.isEmpty()) {
            System.out.println("未找到匹配的任务！");
            return;
        }
        System.out.println("\n===== 搜索结果 =====");
        for (TodoItemEntity item : results) {
            String status = item.isCompleted() ? "[✓] 已完成" : "[ ] 未完成";
            System.out.printf("ID: %d | %s | 标题：%s%n", item.getId(), status, item.getTitle());
        }
    }
}
