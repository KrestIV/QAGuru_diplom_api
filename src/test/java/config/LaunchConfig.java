package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:${platform}.config",
        "system:properties"
})
public interface LaunchConfig extends Config {
    @Key("url.base")
    String getBaseUrl();

}