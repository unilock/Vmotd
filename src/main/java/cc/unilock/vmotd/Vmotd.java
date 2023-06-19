package cc.unilock.vmotd;

import cc.unilock.vmotd.command.VmotdCommand;
import cc.unilock.vmotd.config.Config;
import cc.unilock.vmotd.config.Loader;
import cc.unilock.vmotd.util.Constants;
import cc.unilock.vmotd.util.Libraries;
import cc.unilock.vmotd.util.Placeholders;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "vmotd",
        name = "vmotd",
        description = "/vmotd",
        version = Constants.VERSION,
        authors = {"unilock"},
        dependencies = {@Dependency(id = "miniplaceholders", optional = true)}
)
public final class Vmotd {
    private final ProxyServer proxy;
    private final Logger logger;
    private final Path path;
    private Config config;

    @Inject
    public Vmotd(ProxyServer proxy, Logger logger, @DataDirectory Path path) {
        this.logger = logger;
        this.proxy = proxy;
        this.path = path;

        logger.info("hello from vmotd");
    }

    @Subscribe
    void onProxyInitialization(final ProxyInitializeEvent event) {
        Libraries.load(this, logger, path, proxy.getPluginManager());
        this.config = Loader.loadConfig(logger, path);
        if (this.config == null) {
            return;
        }
        VmotdCommand.register(this);
        logger.info("vmotd initialized");
    }

    @Subscribe
    void onServerConnected(final ServerConnectedEvent event) {
        if (event.getPreviousServer().isEmpty()) {
            Player player = event.getPlayer();

            String username = player.getUsername();
            String servername = event.getServer().getServerInfo().getName();
            int online = event.getServer().getPlayersConnected().size();

            player.sendMessage(Placeholders.motdComponent(this.config, this.proxy.getPluginManager(), username, servername, online));
        }
    }

    public ProxyServer proxy() {
        return this.proxy;
    }

    public Config config() {
        return this.config;
    }
}
