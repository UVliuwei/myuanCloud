package com.myuan.post.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

/*
 * @author liuwei
 * @date 2018/1/19 16:14
 *  用户类
 */
@Data
@Entity
public class MyUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5615703065557959847L;

    //自增长主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @Size(min = 3, max = 10, message = "用户名长度在3到10之间")
    private String name;

    private String sex;

    private String password;
    @Email
    @NotNull(message = "邮箱不能为空")
    @Column(unique = true)
    private String email;

    private String city;

    private String img;

    private Integer kiss;

    private String locked;
    @Length(max = 25, message = "签名不能超过25个字")
    private String description;

}
