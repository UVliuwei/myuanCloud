package com.myuan.sign.entity;
/*
 * @author liuwei
 * @date 2018/1/19 17:35
 *  签到类
 */

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class MySign extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1662749780595220392L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //自增长主键
    private Long id;

    private Long userId;

    private Integer continueNum;

}
