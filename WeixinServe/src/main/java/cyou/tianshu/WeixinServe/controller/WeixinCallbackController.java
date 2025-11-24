package cyou.tianshu.WeixinServe.controller;

import cyou.tianshu.WeixinServe.serve.WxCpServiceManager;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/")
public class WeixinCallbackController {

    @Autowired
    private WxCpServiceManager wxCpServiceManager;

    /**
     * 企业微信回调验证接口
     * 对应 URL: http://api.3dept.com/
     */
    @GetMapping
    public String verifyCallback(
            @RequestParam("msg_signature") String msgSignature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr) {

        log.info("收到企业微信验证请求: signature={}, timestamp={}, nonce={}", 
                msgSignature, timestamp, nonce);

        try {
            WxCpService wxCpService = wxCpServiceManager.getWxCpService();
            
            // 验证URL并解密echostr
            String plainText = wxCpService.getWxCpMessageRouter()
                    .getMessageCryptoService()
                    .verifyURL(msgSignature, timestamp, nonce, echostr);
            
            log.info("验证成功，返回明文: {}", plainText);
            
            // 原样返回解密后的明文（不能加引号，不能带bom头，不能带换行符）
            return plainText;
            
        } catch (Exception e) {
            log.error("企业微信验证失败", e);
            return "error";
        }
    }

    /**
     * 接收企业微信消息（验证通过后使用）
     */
    @PostMapping
    public String receiveMessage(
            @RequestParam("msg_signature") String msgSignature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            HttpServletRequest request) throws IOException {

        String requestBody = getRequestBody(request);
        log.info("收到企业微信消息: {}", requestBody);

        try {
            WxCpService wxCpService = wxCpServiceManager.getWxCpService();
            
            // 解密消息
            WxCpXmlMessage message = WxCpXmlMessage.fromEncryptedXml(
                    requestBody, 
                    wxCpService.getWxCpConfigStorage(), 
                    timestamp, 
                    nonce, 
                    msgSignature);
            
            log.info("解密后的消息: {}", message.toString());
            
            // 处理消息逻辑
            return processMessage(message);
            
        } catch (Exception e) {
            log.error("处理企业微信消息失败", e);
            return "error";
        }
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private String processMessage(WxCpXmlMessage message) {
        // 根据消息类型处理不同的业务逻辑
        switch (message.getMsgType()) {
            case "text":
                return processTextMessage(message);
            case "event":
                return processEventMessage(message);
            default:
                return "success";
        }
    }

    private String processTextMessage(WxCpXmlMessage message) {
        String content = message.getContent();
        log.info("收到文本消息: {}", content);
        
        // 这里可以添加你的业务逻辑
        // 例如：自动回复、处理用户指令等
        
        return "success";
    }

    private String processEventMessage(WxCpXmlMessage message) {
        String event = message.getEvent();
        log.info("收到事件消息: {}", event);
        
        // 处理不同类型的事件
        switch (event) {
            case "subscribe":
                // 处理关注事件
                break;
            case "unsubscribe":
                // 处理取消关注事件
                break;
            case "click":
                // 处理菜单点击事件
                break;
        }
        
        return "success";
    }
}