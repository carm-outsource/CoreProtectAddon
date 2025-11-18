package cc.carm.outsource.plugin.coreprotectaddon.command.query;

import cc.carm.lib.easyplugin.command.SubCommand;
import cc.carm.lib.easysql.api.builder.TableQueryBuilder;
import cc.carm.outsource.plugin.coreprotectaddon.command.ParamReader;
import cc.carm.outsource.plugin.coreprotectaddon.command.QueryCommands;
import cc.carm.outsource.plugin.coreprotectaddon.utils.TimeFormatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Map;
import java.util.function.Consumer;

public class ChatQueryCommand extends SubCommand<QueryCommands> {

    public ChatQueryCommand(@NotNull QueryCommands parent, String identifier, String... aliases) {
        super(parent, identifier, aliases);
    }

    @Override
    public Void execute(JavaPlugin plugin, CommandSender sender, String[] args) throws Exception {



        return null;
    }


}
