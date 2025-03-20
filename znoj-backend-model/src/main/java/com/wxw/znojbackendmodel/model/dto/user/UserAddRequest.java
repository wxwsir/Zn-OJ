package com.wxw.znojbackendmodel.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建用户请求
 *
 * @author <a href="https://github.com/liwxw">程序员鱼皮</a>
 * @from <a href="https://wxw.icu">编程导航知识星球</a>
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户角色: user, admin
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}