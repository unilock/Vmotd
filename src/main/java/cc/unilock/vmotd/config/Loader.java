package cc.unilock.vmotd.config;

import org.slf4j.Logger;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Path;

public final class Loader {
    public static Config loadConfig(Logger logger, Path path) {
        final HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
                .defaultOptions(opts -> opts
                        .shouldCopyDefaults(true)
                        .header("""
                                vmotd | by unilock
                                Review the MiniMessage guide at:
                                https://docs.adventure.kyori.net/minimessage#format

                                Check the placeholders available at:
                                https://github.com/unilock/vmotd/wiki/Placeholders
                                """)
                )
                .path(path.resolve("config.conf"))
                .build();

        final Config config;
        try {
            final CommentedConfigurationNode node = loader.load();
            config = node.get(Config.class);
            node.set(Config.class, config);
            loader.save(node);
            return config;
        } catch (ConfigurateException exception) {
            logger.error("Could not load config.conf file", exception);
            return null;
        }
    }
}
