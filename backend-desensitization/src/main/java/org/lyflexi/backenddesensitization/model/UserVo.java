package org.lyflexi.backenddesensitization.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.lyflexi.backenddesensitization.annotation.Desensitive;
import org.lyflexi.backenddesensitization.enums.DesensitizationType;

import java.io.Serializable;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class UserVo implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户名
     */
    private String usename;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 性别，0男1女
     */
    private Integer gender;

    /**
     * 手机号
     */
    @Desensitive(DesensitizationType.PHONE)
    private String phone;

    /**
     * 身份证号
     */
    @Desensitive(DesensitizationType.ID_CARD)
    private String idCard;

    /**
     * 银行卡号
     */
    @Desensitive(DesensitizationType.BANK_CARD)
    private String bankCard;


}