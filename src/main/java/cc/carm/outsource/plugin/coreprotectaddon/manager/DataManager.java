package cc.carm.outsource.plugin.coreprotectaddon.manager;

import cc.carm.lib.easysql.EasySQL;
import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.outsource.plugin.coreprotectaddon.Main;
import cc.carm.outsource.plugin.coreprotectaddon.conf.PluginConfig;
import cc.carm.outsource.plugin.coreprotectaddon.data.UserKey;

import java.sql.ResultSet;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class DataManager {

    protected SQLManager sqlManager;

    public DataManager() throws Exception {
        try {
            Main.info("尝试连接到数据库...");
            this.sqlManager = EasySQL.createManager(
                    PluginConfig.DATABASE.DRIVER_NAME.getNotNull(), PluginConfig.DATABASE.buildJDBC(),
                    PluginConfig.DATABASE.USERNAME.getNotNull(), PluginConfig.DATABASE.PASSWORD.getNotNull()
            );
            this.sqlManager.setDebugMode(() -> Main.getInstance().isDebugging());
        } catch (Exception exception) {
            throw new Exception("无法连接到数据库，请检查配置文件。", exception);
        }
    }


    public void shutdown() {
        if (this.sqlManager == null) return;
        Main.info("关闭数据库连接...");
        EasySQL.shutdownManager(sqlManager);
        this.sqlManager = null;
    }


    public CompletableFuture<Optional<UserKey>> getUser(long uid) {
        return this.sqlManager.createQuery().inTable(PluginConfig.DATABASE.TABLES.USERS.resolve())
                .addCondition("id", uid).setLimit(1)
                .build().executeFuture(query -> {
                    ResultSet rs = query.getResultSet();
                    if (rs.next()) {
                        long id = rs.getLong("id");
                        String uuidStr = rs.getString("uuid");
                        String name = rs.getString("name");
                        return Optional.of(new UserKey(id, java.util.UUID.fromString(uuidStr), name));
                    } else return Optional.empty();
                });
    }


}
