package cyou.tianshu.WeixinServe.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "wechat.cp")
public class WxCpProperties {
    private String corpId;
    private String corpSecret;
    private String token;
    private String aesKey;
    private Integer agentId;
}