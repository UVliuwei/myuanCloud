package com.myuan.user.config;
/*
 * @author liuwei
 * @date 2018/3/1 18:34
 * 验证码
 */

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        DefaultKaptcha captchaProducer =new DefaultKaptcha();
        Properties properties =new Properties();
        properties.setProperty("kaptcha.border","no");
        properties.setProperty("kaptcha.border.color","105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color","12,34,56");
        properties.setProperty("kaptcha.image.width","110");
        properties.setProperty("kaptcha.image.height","38");
        properties.setProperty("kaptcha.textproducer.font.size","30");
        properties.setProperty("kaptcha.session.key","code");
        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
        properties.setProperty("kaptcha.textproducer.char.space","5");
        properties.setProperty("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy");
        properties.setProperty("kaptcha.textproducer.char.length","4");
        properties.setProperty("kaptcha.textproducer.font.names","宋体,楷体,微软雅黑");
        Config config=new Config(properties);
        captchaProducer.setConfig(config);
        return  captchaProducer;
    }
}
