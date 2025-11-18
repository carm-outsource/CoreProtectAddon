package cc.carm.outsource.plugin.coreprotectaddon.command;

import cc.carm.lib.easyplugin.command.CommandHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class QueryCommands extends CommandHandler {

    public QueryCommands(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public Void noArgs(CommandSender sender) {
        return null;
    }

    @Override
    public Void noPermission(CommandSender sender) {
        return null;
    }

    @Override
    public boolean hasPermission(@NotNull CommandSender sender) {
        return sender.hasPermission("coreprotectaddon.command.query");
    }


}
