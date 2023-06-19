package cc.unilock.vmotd.util;

import cc.unilock.vmotd.config.Config;
import com.velocitypowered.api.plugin.PluginManager;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public final class Placeholders {
    public static Component motdComponent(Config config, PluginManager pluginManager, @NotNull String player, @NotNull String server, @NotNull Integer online) {
        final TagResolver.Builder builder = TagResolver.builder().resolvers(
                Placeholder.unparsed("player", player),
                Placeholder.unparsed("server", server),
                Placeholder.unparsed("online", online.toString())
        );

        if (pluginManager.isLoaded("miniplaceholders")) {
            builder.resolver(MiniPlaceholders.getGlobalPlaceholders());
        }
        final TagResolver resolver = builder.build();

        return miniMessage().deserialize(config.getMotd(), resolver);
    }
}
