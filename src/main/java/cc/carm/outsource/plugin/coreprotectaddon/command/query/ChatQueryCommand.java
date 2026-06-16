package cc.carm.outsource.plugin.coreprotectaddon.command.query;

import cc.carm.lib.easyplugin.command.SubCommand;
import cc.carm.lib.easysql.api.builder.TableQueryBuilder;
import cc.carm.outsource.plugin.coreprotectaddon.Main;
import cc.carm.outsource.plugin.coreprotectaddon.command.CommandParameter;
import cc.carm.outsource.plugin.coreprotectaddon.command.QueryCommands;
import cc.carm.outsource.plugin.coreprotectaddon.conf.PluginConfig;
import cc.carm.outsource.plugin.coreprotectaddon.conf.PluginMessages;
import cc.carm.outsource.plugin.coreprotectaddon.data.UserKey;
import cc.carm.outsource.plugin.coreprotectaddon.data.UserKeyType;
import cc.carm.outsource.plugin.coreprotectaddon.manager.DataManager;
import cc.carm.outsource.plugin.coreprotectaddon.utils.TimeFormatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ChatQueryCommand extends SubCommand<QueryCommands> {

    public ChatQueryCommand(@NotNull QueryCommands parent, String identifier, String... aliases) {
        super(parent, identifier, aliases);
    }

    @Override
    public Void execute(JavaPlugin plugin, CommandSender sender, String[] args) throws Exception {
        DataManager data = Main.getDataManager();
        CommandParameter parameter = CommandParameter.parse(args);
        long s1 = System.currentTimeMillis();

        Main.getInstance().getScheduler().runAsync(() -> {

            TableQueryBuilder query = data.sql().createQuery().inTable(PluginConfig.DATABASE.TABLES.CHAT.resolve());
            String timeString = parameter.get("time", "t");
            if (timeString != null) {
                Duration[] interval = TimeFormatUtils.parseInterval(timeString);
                if (interval == null) {
                    PluginMessages.WRONG_TIME.sendTo(sender);
                    return;
                }

                long a = System.currentTimeMillis() - interval[0].toMillis();
                if (interval[1].isZero()) {
                    query.addCondition("time", ">=", a / 1000);
                } else {
                    long b = System.currentTimeMillis() - interval[1].toMillis();
                    query.addCondition("time", ">=", Math.min(a, b) / 1000);
                    query.addCondition("time", "<=", Math.max(a, b) / 1000);
                }

            }

            String content = parameter.get("content", "c", "message", "m");
            if (content != null) {
                query.addCondition("message", "REGEXP", content);
            }

            String pageString = parameter.get("page", "p");
            int page = 1;
            int pageSize = PluginConfig.QUERY.PAGE_SIZE.resolve();
            if (pageString != null) {
                try {
                    page = Integer.parseInt(pageString);
                    if (page < 1) page = 1;
                } catch (NumberFormatException ignored) {
                    PluginMessages.WRONG_PAGE.sendTo(sender);
                    return;
                }
            }

            String username = parameter.get("user", "u");
            if (username != null) {
                // 先利用用户名查询用户ID
                UserKey key = data.getUser(UserKeyType.NAME, username);
                if (key == null) {
                    PluginMessages.UNKNOWN_USER.sendTo(sender, username);
                    return;
                }
                query.addCondition("user", key.id());
            }


            query.setPageLimit((page - 1) * pageSize, pageSize);
            query.orderBy("time", !PluginConfig.QUERY.REVERSE_ORDER.resolve());

            List<ChatRecord> list = query.build().execute(sql -> {
                ResultSet rs = sql.getResultSet();
                List<ChatRecord> result = new ArrayList<>();

                while (rs.next()) {
                    long time = rs.getLong("time");
                    long userId = rs.getLong("user");
                    String message = rs.getString("message");
                    result.add(new ChatRecord(time, data.getUser(UserKeyType.ID, userId), message));
                }

                return result;
            }, new ArrayList<>(), null);

            if (list.isEmpty()) {
                if (page == 1) {
                    PluginMessages.EMPTY.sendTo(sender);
                } else {
                    PluginMessages.EMPTY_PAGE.sendTo(sender, page);
                }
            } else {
                PluginMessages.COST.prepare((System.currentTimeMillis() - s1), list.size()).to(sender);
                List<String> contents = new ArrayList<>();
                for (ChatRecord record : list) {
                    contents.addAll(PluginMessages.CONTENT.prepare(
                            TimeFormatUtils.datetime(record.time()),
                            (record.user() == null ? "§c未知用户" : "§b" + record.user().name()).replace("$", "\\$"),
                            record.message().replace("$", "\\$")
                    ).parse(sender));
                }
                PluginMessages.PAGE.prepare(page).insert("content", contents).to(sender);
            }
        });
        return null;
    }

    record ChatRecord(long time, @Nullable UserKey user, String message) {
    }


}
