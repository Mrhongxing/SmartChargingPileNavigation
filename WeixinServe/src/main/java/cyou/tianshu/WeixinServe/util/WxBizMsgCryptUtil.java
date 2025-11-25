package cyou.tianshu.WeixinServe.util;

import cyou.tianshu.WeixinServe.aes.WXBizMsgCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j

public class WxBizMsgCryptUtil {
    
    private WXBizMsgCrypt wxcpt;
    
    public WxBizMsgCryptUtil(String token, String encodingAesKey, String corpId) {
        try {
            this.wxcpt = new WXBizMsgCrypt(token, encodingAesKey, corpId);
        } catch (Exception e) {
            log.error("初始化企业微信消息加解密工具失败", e);
            throw new RuntimeException("初始化企业微信消息加解密工具失败", e);
        }
    }
    
    /**
     * 验证URL并解密echostr
     * @param msgSignature 消息签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 加密字符串
     * @return 解密后的明文消息
     */
    public String verifyURL(String msgSignature, String timestamp, String nonce, String echostr) {
        try {
            log.info("开始验证URL: signature={}, timestamp={}, nonce={}", 
                    msgSignature, timestamp, nonce);
            
            String plainText = wxcpt.VerifyURL(msgSignature, timestamp, nonce, echostr);
            
            log.info("URL验证成功，解密后的明文: {}", plainText);
            return plainText;
            
        } catch (Exception e) {
            log.error("URL验证失败", e);
            throw new RuntimeException("URL验证失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 解密消息
     * @param msgSignature 消息签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param encryptedMsg 加密的消息XML
     * @return 解密后的明文XML
     */
    public String decryptMsg(String msgSignature, String timestamp, String nonce, String encryptedMsg) {
        try {
            return wxcpt.DecryptMsg(msgSignature, timestamp, nonce, encryptedMsg);
        } catch (Exception e) {
            log.error("消息解密失败", e);
            throw new RuntimeException("消息解密失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 加密回复消息
     * @param replyMsg 回复的明文XML
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @return 加密后的消息XML
     */
    public String encryptMsg(String replyMsg, String timestamp, String nonce) {
        try {
            return wxcpt.EncryptMsg(replyMsg, timestamp, nonce);
        } catch (Exception e) {
            log.error("消息加密失败", e);
            throw new RuntimeException("消息加密失败: " + e.getMessage(), e);
        }
    }
}