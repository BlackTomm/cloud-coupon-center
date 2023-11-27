package org.coding.coupon.template.config;

import org.coding.coupon.template.config.domain.MailCrendential;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;


/**
 * Description:ConfigurationProperties使用<br/>
 * <a href="https://juejin.cn/post/7090528037497913375">「Spring」@ConfigurationProperties——从基础到源码</a><br/>
 * <a href="https://www.baeldung.com/properties-with-spring">Properties with Spring and Spring Boot</a>
 * Create by blacktom on 2023/11/27
 */
@Configuration
@ConfigurationProperties(prefix = "mail")
@PropertySource({"classpath:mailconfig.properties"})
public class MailConfigProperties {
    private String hostName;
    private int port;
    private String from;

    private List<String> recipients;
    private Map<String, String> headers;
    private MailCrendential mailCrendential;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public MailCrendential getMailCrendential() {
        return mailCrendential;
    }

    public void setMailCrendential(MailCrendential mailCrendential) {
        this.mailCrendential = mailCrendential;
    }
}
