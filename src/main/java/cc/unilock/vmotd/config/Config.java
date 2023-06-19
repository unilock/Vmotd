package cc.unilock.vmotd.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@SuppressWarnings("ALL")
@ConfigSerializable
public class Config {
    @Comment("Message to send to a player when they join the proxy")
    private String motd = "<rainbow>Hello <player>, welcome to <server>!</rainbow> (<online> players online)";

    public String getMotd() {
        return motd;
    }
}
