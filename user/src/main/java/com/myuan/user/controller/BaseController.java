package com.myuan.user.controller;
/*
 * @author liuwei
 * @date 2018/1/27 12:35
 *
 */

import com.myuan.user.entity.MyResult;
import java.beans.PropertyEditorSupport;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class BaseController {

    /**
     * 表单验证 <liuwei> [2018/1/27 12:41]
     */
    protected MyResult validForm(BindingResult result) {
        String message = result.getFieldError().getDefaultMessage();
        return MyResult.error(message);
    }

    /**
     *  xss预防 <liuwei> [2018/2/27 9:00]
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }
            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
    }
}
