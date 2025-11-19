package cc.carm.outsource.plugin.coreprotectaddon.conf;

import cc.carm.lib.configuration.Configuration;
import cc.carm.lib.configuration.annotation.ConfigPath;
import cc.carm.lib.configuration.annotation.HeaderComments;
import cc.carm.lib.configuration.value.standard.ConfiguredValue;


@ConfigPath(root = true)
public interface PluginConfig extends Configuration {

    ConfiguredValue<Boolean> DEBUG = ConfiguredValue.of(Boolean.class, false);

    @HeaderComments("数据库相关配置")
    interface DATABASE extends Configuration {

        @ConfigPath("driver")
        ConfiguredValue<String> DRIVER_NAME = ConfiguredValue.of("com.mysql.cj.jdbc.Driver");

        ConfiguredValue<String> HOST = ConfiguredValue.of("127.0.0.1");
        ConfiguredValue<Integer> PORT = ConfiguredValue.of(3306);
        ConfiguredValue<String> DATABASE = ConfiguredValue.of("minecraft");
        ConfiguredValue<String> USERNAME = ConfiguredValue.of("root");
        ConfiguredValue<String> PASSWORD = ConfiguredValue.of("password");
        ConfiguredValue<String> EXTRA = ConfiguredValue.of("?sslMode=DISABLED&serverTimezone=Asia/Shanghai");

        static String buildJDBC() {
            return String.format("jdbc:mysql://%s:%s/%s%s",
                    HOST.getNotNull(), PORT.getNotNull(), DATABASE.getNotNull(), EXTRA.getNotNull()
            );
        }

        @HeaderComments("插件相关表的名称")
        interface TABLES extends Configuration {
            ConfiguredValue<String> USERS = ConfiguredValue.of(String.class, "co_user");
            ConfiguredValue<String> CHAT = ConfiguredValue.of(String.class, "co_chat");
            ConfiguredValue<String> COMMAND = ConfiguredValue.of(String.class, "co_command");
        }

    }

    @HeaderComments("查询相关配置")
    interface QUERY extends Configuration {
        @HeaderComments("每页显示的数据数量")
        ConfiguredValue<Integer> PAGE_SIZE = ConfiguredValue.of(15);

        @HeaderComments("查询结果是否按时间倒序排列（最新的在前）")
        ConfiguredValue<Boolean> REVERSE_ORDER = ConfiguredValue.of(true);

    }


}
