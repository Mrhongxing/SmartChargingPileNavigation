package cyou.tianshu.WeixinServe.controller;

import cyou.tianshu.WeixinServe.serve.WxCpServiceManager;
import cyou.tianshu.WeixinServe.util.WxBizMsgCryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/")
public class WeixinCallbackController {

    @Autowired
    private WxCpServiceManager wxCpServiceManager;

    /**
     * 企业微信回调验证接口
     * 使用官方提供的 VerifyURL 函数进行验证
     */
    @GetMapping
    public String verifyCallback(
            @RequestParam("msg_signature") String msgSignature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr) {

        log.info("收到企业微信验证请求: signature={}, timestamp={}, nonce={}, echostr={}", 
                msgSignature, timestamp, nonce, echostr);

        try {
            WxBizMsgCryptUtil cryptUtil = wxCpServiceManager.getWxBizMsgCryptUtil();
            
            // 使用企业微信官方提供的 VerifyURL 函数进行验证和解密
            String plainText = cryptUtil.verifyURL(msgSignature, timestamp, nonce, echostr);
            
            log.info("验证成功，返回明文: {}", plainText);
            
            // 原样返回解密后的明文（不能加引号，不能带bom头，不能带换行符）
            return plainText;
            
        } catch (Exception e) {
            log.error("企业微信验证失败", e);
            // 验证失败时返回错误，企业微信会重试
            return "error";
        }
    }

    /**
     * 接收企业微信消息
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
            WxBizMsgCryptUtil cryptUtil = wxCpServiceManager.getWxBizMsgCryptUtil();
            
            // 解密消息
            String decryptedMsg = cryptUtil.decryptMsg(msgSignature, timestamp, nonce, requestBody);
            log.info("解密后的消息: {}", decryptedMsg);
            
            // 处理消息逻辑
            return processMessage(decryptedMsg);
            
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

    private String processMessage(String decryptedMsg) {
        // 这里解析解密后的XML消息并处理
        // 示例：如果是文本消息，可以回复"success"表示处理成功
        log.info("处理解密后的消息: {}", decryptedMsg);
        
        // 暂时返回success，表示消息处理成功
        return "success";
    }
}