package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${platform}.config",
        "classpath:${platform}.items.config",
        "system:properties"
})
public interface LaunchConfig extends Config {
    @Key("url.base")
    String getBaseUrl();

    @Key("server")
    String getServerAddress();

}