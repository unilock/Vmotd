package cc.unilock.vmotd.command;

import cc.unilock.vmotd.Vmotd;
import cc.unilock.vmotd.util.Placeholders;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;

public final class VmotdCommand {
    public static void register(Vmotd plugin) {
        final LiteralCommandNode<CommandSource> vmotdCommand = LiteralArgumentBuilder
                .<CommandSource>literal("vmotd")
//              .requires(src -> src.hasPermission("vmotd.command"))
                .executes(ctx -> sendMotd(plugin, ctx))
                .build();

        final CommandManager manager = plugin.proxy().getCommandManager();
        final BrigadierCommand command = new BrigadierCommand(vmotdCommand);
        final CommandMeta meta = manager.metaBuilder(command)
                .plugin(plugin)
                .build();
        manager.register(meta, command);
    }

    private static int sendMotd(final Vmotd plugin, final CommandContext<CommandSource> ctx) {
        CommandSource source = ctx.getSource();

        String username;
        String servername;
        int online;
        if (source instanceof Player player) {
            username = player.getUsername();
            servername = player.getCurrentServer().orElseThrow().getServerInfo().getName();
            online = player.getCurrentServer().orElseThrow().getServer().getPlayersConnected().size();
        } else {
            username = "console";
            servername = "velocity";
            online = plugin.proxy().getPlayerCount();
        }

        source.sendMessage(Placeholders.motdComponent(plugin.config(), plugin.proxy().getPluginManager(), username, servername, online));

        return Command.SINGLE_SUCCESS;
    }
}
