package cc.carm.outsource.plugin.coreprotectaddon;

import cc.carm.lib.easyplugin.EasyPlugin;
import cc.carm.lib.mineconfiguration.bukkit.MineConfiguration;
import cc.carm.outsource.plugin.coreprotectaddon.command.QueryCommands;
import cc.carm.outsource.plugin.coreprotectaddon.conf.PluginConfig;
import cc.carm.outsource.plugin.coreprotectaddon.conf.PluginMessages;
import org.bukkit.event.Listener;

public class Main extends EasyPlugin implements Listener {

    private static Main instance;

    public Main() {
        Main.instance = this;
    }

    protected MineConfiguration configuration;

    @Override
    protected void load() {

        log("加载配置文件...");
        this.configuration = new MineConfiguration(this, PluginConfig.class, PluginMessages.class);

    }

    @Override
    protected boolean initialize() {

        log("注册命令...");
        registerCommand("coreprotectquery", new QueryCommands(this));

        return true;
    }

    public static void info(String... messages) {
        getInstance().log(messages);
    }

    public static void severe(String... messages) {
        getInstance().error(messages);
    }

    public static void debugging(String... messages) {
        getInstance().debug(messages);
    }

    public static Main getInstance() {
        return instance;
    }

}
