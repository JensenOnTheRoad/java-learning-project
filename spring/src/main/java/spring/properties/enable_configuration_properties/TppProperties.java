package spring.properties.enable_configuration_properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "thirty.tpp")
public record TppProperties(
    String domain, String appKey, String appSecret, Long userId, String platform) {}
