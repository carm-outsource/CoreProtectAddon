package cc.carm.outsource.plugin.coreprotectaddon.command;

import cc.carm.lib.easyplugin.command.CommandHandler;
import cc.carm.outsource.plugin.coreprotectaddon.command.query.ChatQueryCommand;
import cc.carm.outsource.plugin.coreprotectaddon.command.query.CommandQueryCommand;
import cc.carm.outsource.plugin.coreprotectaddon.conf.PluginMessages;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class QueryCommands extends CommandHandler {

    public QueryCommands(@NotNull JavaPlugin plugin) {
        super(plugin);
        registerSubCommand(new ChatQueryCommand(this, "chat"));
        registerSubCommand(new CommandQueryCommand(this, "command"));
    }

    @Override
    public Void noArgs(CommandSender sender) {
        PluginMessages.COMMAND_USAGE.sendTo(sender);
        return null;
    }

    @Override
    public Void noPermission(CommandSender sender) {
        PluginMessages.NO_PERMISSION.sendTo(sender);
        return null;
    }

    @Override
    public boolean hasPermission(@NotNull CommandSender sender) {
        return sender.hasPermission("coreprotectaddon.command.query");
    }


}
