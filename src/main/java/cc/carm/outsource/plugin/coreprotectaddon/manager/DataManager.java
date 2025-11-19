package cc.carm.outsource.plugin.coreprotectaddon.manager;

import cc.carm.lib.easysql.EasySQL;
import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.outsource.plugin.coreprotectaddon.Main;
import cc.carm.outsource.plugin.coreprotectaddon.conf.PluginConfig;
import cc.carm.outsource.plugin.coreprotectaddon.data.UserKey;
import cc.carm.outsource.plugin.coreprotectaddon.data.UserKeyType;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

public class DataManager {

    protected SQLManager sqlManager;
    protected Cache<String, UserKey> userCache = CacheBuilder.newBuilder()
            .expireAfterAccess(30, TimeUnit.MINUTES).build();

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

    public SQLManager sql() {
        return this.sqlManager;
    }

    public <K> @Nullable UserKey getUser(UserKeyType<K> type, K param) {
        String cacheKey = (type == UserKeyType.ID ? "#" : "") + param.toString();
        // Check cache
        if (this.userCache != null) {
            UserKey cached = this.userCache.getIfPresent(cacheKey);
            if (cached != null) return cached;
        }

        return sql().createQuery().inTable(PluginConfig.DATABASE.TABLES.USERS.resolve())
                .addCondition(type.dataKey(), param).setLimit(1)
                .build().execute(query -> {
                    ResultSet rs = query.getResultSet();
                    if (rs.next()) {
                        long id = rs.getLong(UserKeyType.ID.dataKey());
                        String uuidStr = rs.getString(UserKeyType.UUID.dataKey());
                        String name = rs.getString(UserKeyType.NAME.dataKey());
                        UserKey userKey = new UserKey(id, uuidStr == null ? null : java.util.UUID.fromString(uuidStr), name);
                        this.userCache.put(cacheKey, userKey);
                        return userKey;
                    } else return null;
                }, null, null);

    }

    public void cache(@NotNull UserKey key) {
        if (this.userCache != null) {
            this.userCache.put("#" + key.id(), key);
            if (key.uuid() != null) {
                this.userCache.put(key.uuid().toString(), key);
            }
            this.userCache.put(key.name(), key);
        }
    }


}
