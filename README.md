# CoreProtectAddon

CoreProtect 插件的外部拓展。

## 变量

```plain text


```

## 功能

### 1. 高级查询

在服务器中查询包含特定关键词记录，例如使用高风险指令的“玩家id”。

实现一个插件，能够根据管理员输入的查询参数（关键字段 + 时间范围（可选）+指定玩家（可选）），
自动组装 SQL 查询语句，从 CoreProtect 的数据库表 co_command 中检索匹配的指令记录，并返回执行过该关键词的全部玩家信息。

●查询目标表：co_command
○ time：主键（时间戳）
○ user：玩家数字 ID（整数，与玩家表对应）
○ message：玩家执行的完整指令（字符串，如 /fly speed 2）

管理输入了一个指令：
```
/coq user:Loliiiico time:3d action:commond include:op Loliiiico
```
插件会组装SQL查询语句，并根据配置文件coi的数据库进行查询 "op Loliiiico"

查询到了user_id=2的玩家输入过

接着查询
● 玩家映射表：co_user
○ id：数字主键
○ uuid：玩家 UUID
○ username：玩家名称（游戏名）

这样，我就找到了，输入过”op Loliiiico“的玩家是Loliiico，时间是”1745302851“


> 为什么不用api？，因为api我记得用不了，没有这个方法，而且coi自身指令只能够查询指定玩家的指令，不可以通过指令关键词，反查玩家。
我的需求特殊，需要能够通过指令关键词+玩家+时间，来统计出搜索结果，比如这个管理（玩家中选择的管理）在1周时间内执行的高危指令次数”/co rollback“次数等。
用于更好的管理服务器。





