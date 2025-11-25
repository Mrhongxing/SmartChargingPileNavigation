package cyou.tianshu.WeixinServe.serve;

import cyou.tianshu.WeixinServe.data.WxCpProperties;
import cyou.tianshu.WeixinServe.util.WxBizMsgCryptUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Slf4j
@Service
public class WxCpServiceManager {

    @Autowired
    private WxCpProperties wxCpProperties;

    private WxCpService wxCpService;
    private WxBizMsgCryptUtil wxBizMsgCryptUtil;

    @PostConstruct
    public void init() {
        // 初始化企业微信服务
        WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
        config.setCorpId(wxCpProperties.getCorpId());
        config.setCorpSecret(wxCpProperties.getCorpSecret());
        config.setToken(wxCpProperties.getToken());
        config.setAesKey(wxCpProperties.getAesKey());

        wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(config);
        
        // 初始化消息加解密工具
        wxBizMsgCryptUtil = new WxBizMsgCryptUtil(
            wxCpProperties.getToken(),
            wxCpProperties.getAesKey(), 
            wxCpProperties.getCorpId()
        );
        
        log.info("企业微信服务初始化完成");
    }

    public WxCpService getWxCpService() {
        return wxCpService;
    }
    
    public WxBizMsgCryptUtil getWxBizMsgCryptUtil() {
        return wxBizMsgCryptUtil;
    }
}