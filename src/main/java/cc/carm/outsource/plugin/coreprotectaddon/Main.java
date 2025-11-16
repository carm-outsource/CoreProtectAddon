package cc.carm.outsource.plugin.coreprotectaddon;

import cc.carm.lib.easyplugin.EasyPlugin;
import org.bukkit.event.Listener;

public class Main extends EasyPlugin implements Listener {

    private static Main instance;

    public Main() {
        Main.instance = this;
    }


    @Override
    protected boolean initialize() {
        registerListener(this);

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
