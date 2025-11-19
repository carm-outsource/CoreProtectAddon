package cc.carm.outsource.plugin.coreprotectaddon.conf;

import cc.carm.lib.configuration.Configuration;
import cc.carm.lib.configuration.annotation.ConfigPath;
import cc.carm.lib.mineconfiguration.bukkit.value.ConfiguredMessage;


@ConfigPath(root = true)
public interface PluginMessages extends Configuration {

    ConfiguredMessage<String> COMMAND_USAGE = ConfiguredMessage.asString().defaults(
            "&e&lCoreProtectAddon &f查询命令帮助 &8(/coq)",
            "&8#&f chat &6user:<玩家> time:<时间范围> page:<页码> content:<内容>",
            "&8-&7 查询指定条件下的聊天记录。",
            "&8#&f command &6user:<玩家> time:<时间范围> page:<页码> content:<内容>",
            "&8-&7 查询指定条件下的聊天记录。",
            "&f&o 内容支持SQL正则表达式匹配，时间范围格式形如 1mo2d3h4m5s 或 10d-30d 。"
    ).build();

    ConfiguredMessage<String> NO_PERMISSION = ConfiguredMessage.asString().defaults(
            "&c&l抱歉！&f但您没有权限执行该命令。"
    ).build();

    ConfiguredMessage<String> UNKNOWN_USER = ConfiguredMessage.asString().defaults(
            "&e&l未知的用户。&f不存在任何与 &e%(player) &f关联的记录。"
    ).params("player").build();


    ConfiguredMessage<String> WRONG_TIME = ConfiguredMessage.asString().defaults(
            "&c&l时间格式错误。&f请输入正确的时间格式，形如 &e5mo4d3h2m1s &f表示从今往前的一段时间，或例如 &e10d-30d &f表示从10天前到30天前的时间段。"
    ).build();

    ConfiguredMessage<String> WRONG_PAGE = ConfiguredMessage.asString().defaults(
            "&c&l无效的页码。&f请输入正确的页码，应当为整数数字。"
    ).build();

    ConfiguredMessage<String> EMPTY = ConfiguredMessage.asString().defaults(
            "&f在指定的范围内没有找到任何相关记录。"
    ).build();

    ConfiguredMessage<String> EMPTY_PAGE = ConfiguredMessage.asString().defaults(
            "&f在指定的范围内，第 %(page) 页无相关记录。"
    ).params("page").build();

    ConfiguredMessage<String> COST = ConfiguredMessage.asString().defaults(
            "&e&l查询完成！&f本次查询共耗时 &e%(time)ms&f，获取到 &e%(count) &f条记录。"
    ).params("time", "count").build();

    ConfiguredMessage<String> PAGE = ConfiguredMessage.asString().defaults(
            "&e-------[ &6&l记录查询 &8(第&7%(page)&8页) &e]-------",
            "{&7- &r}#content#{0,0}",
            "&7&o可通过 page 参数继续查看下一页内容。"
    ).params("page").build();

    ConfiguredMessage<String> CONTENT = ConfiguredMessage.asString().defaults(
            "&7[&6%(time)&7] &b%(player) &f: %(message)"
    ).params("time", "player", "message").build();


}

