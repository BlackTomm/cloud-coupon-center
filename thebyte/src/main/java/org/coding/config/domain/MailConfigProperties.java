package org.coding.config.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Create by blacktom on 2023/11/27
 */

@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailConfigProperties {
    private String hostName;
    private int port;
    private String from;

    private List<String> recipients;
    private Map<String, String> headers;
    private MailCrendential mailCrendential;
}
