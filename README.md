# TODO List 项目说明文档

## 1. 技术选型
- **编程语言**：Java，理由：最熟悉的语言，生态成熟、快速开发。
- **框架/库**：框架：Springboot;库：Jackson、lombok等。
   理由：springboot快速开发与简化配置，内嵌服务器Tomcat为后续扩展为web项目提供支持；
   Jackson 支持复杂类型（如LocalDateTime时间类型、自定义实体TodoItemEntity）的序列化；
   Lombok 简化实体类的编写，减少样板代码。
- **项目管理工具**：基于Maven的开源脚手架，理由：预设规范的项目目录，加速开发。
- **数据库/存储**：本地文件系统存储，后续可以升级为数据库存储（如MySQL）。


## 2. 项目结构设计
- 整体架构说明（前端/后端/数据库关系，或命令行工具的模块划分）。
- 目录结构示例：
```  
todo-list-app
├── data/log/             # 日志文件目录
├── pom.xml               # 模块依赖配置
└── src/
├── main/
│   ├── java/cn/cug/
│   │   ├── Application.java   # Spring Boot主应用类
│   │   ├── config/            # 配置类
│   │         └── TodoListConfig.java  #注册单例todoList
│   │   
│   └── resources/    # 配置文件
└── test/             # 测试代码


todo-list-domain
├── pom.xml
└── src/main/java/cn/cug/domain/
├── basic/            # 基础业务功能
│   ├── adapter/      # 适配器接口定义
│   │   ├── repository/
│   │   │   └── IExtensionRepository.java  # 扩展功能存储接口
│   │   └── port/
│   ├── model/        # 领域模型
│   │   ├── entity/
│   │   │   └── TodoItemEntity.java  # 待办事项实体
│   │   ├── aggregate/
│   │   └── valobj/
│   └── service/      # 业务服务
│       ├── basic/
│       │   ├── ITodoManager.java    # 待办管理接口
│       │   └── TodoManagerService.java  # 待办管理实现
│       └── extensions/
│           ├── IExtensionFunction.java  # 扩展功能接口
│           └── ExtensionFunction.java   # 扩展功能实现
└── commandline/      # 命令行相关功能

todo-list-infrastructure
├── pom.xml
└── src/main/java/cn/cug/infrastructure/
├── adapter/          # 适配器实现
│   ├── port/
│   └── repository/
│       └── ExtensionRepository.java  # 文件存储实现
├── dao/              # 数据访问对象,后续可扩展为数据库存储
└── gateway/          # 网关，后续可用于扩展微服务
``` 


## 3. 需求细节与决策
- 描述是否必填？如何处理空输入？：标题必填不能为空，描述为选填，在加载文件中针对非法输入（包括空输入）进行判断，非法输入则直接返回内存中的todolist。
- 已完成的任务在 UI 或 CLI 中如何显示？：在UI中已完成的任务会显示为已完成状态，在CLI中会显示为已完成状态。
- 任务排序逻辑（默认按创建时间，后续可以根据用户需求添加按优先级排序）。
- 如果涉及分类功能（如工作/学习/生活）：后续可以在todo-list-types层中定义枚举类（如工作/学习/生活），在TodoItemEntity中添加type字段，用于存储任务分类。
    然后在domain层中的服务层中添加分类服务，例如按分类查询。

## 4. AI 使用说明
- 是否使用 AI 工具？
   答：使用了Trae、ChatGPT。
- 使用 AI 的环节：
  - 利用Trae和GPT生成单元测试代码，GPT生成的测试代码质量较高，覆盖了主要功能。
  - Bug 定位
  - 文档初稿编写
- AI 输出如何修改：AI生成的单元测试使用了Junit5的@BeforeEach注解，与我的Junit4冲突，修改为@Before”。
                AI生成的代码忽略了Jackson库默认不支持LocalDateTime类型的序列化问题，手动配置序列化器。

## 5. 运行与测试方式
- 本地运行方式（安装依赖、启动命令）。
  1.直接在todo-list-APP层运行Application类下的main方法启动springboot服务。
- 已测试过的环境：Java8、Windows11
- 已知问题与不足：web页面由于时间问题为实现，仅采用控制台方式实现todolist应用。
               且删除功能，无法用用空闲itemid，后续可维护1个set集合用于存储已删除id，以及一个id最大值变量，用于复用空闲id。

## 6. 总结与反思
- 如果有更多时间，你会如何改进？
  答：如果有更多时间，我会优化为web项目，实现在网页上操作todolist应用。数据的持久化可以改为数据库存储，更方便实现按用户、功能进行分类查询。
     此外，可以将检索功能实现为一个MCP服务，利用AI来快速检索以及分析待办事项。
- 你觉得这个实现的最大亮点是什么？  
  答：最大亮点是使用DDD架构，将应用分为领域层、应用层、基础设施层，各层职责清晰，解耦度高，可扩展性好。领域层的服务代码
  专注于业务逻辑的实现，而应用层则负责启动服务，以及配置类的自动装配。基础设施层则负责实现数据存储、通信等底层功能。
  以及利用AI工具（如Trae、ChatGPT）以及开源maven脚手架快速构建项目以及代码，提高开发效率。
