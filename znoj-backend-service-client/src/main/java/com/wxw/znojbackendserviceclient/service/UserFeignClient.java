package com.wxw.znojbackendserviceclient.service;

import com.wxw.znojbackendcommon.common.ErrorCode;
import com.wxw.znojbackendcommon.exception.BusinessException;
import com.wxw.znojbackendmodel.model.entity.User;
import com.wxw.znojbackendmodel.model.enums.UserRoleEnum;
import com.wxw.znojbackendmodel.model.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

import static com.wxw.znojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;

/**
 *
 * @author wxw
 * Feign客户端----用户服务
 * @date 2024
 */
@FeignClient(name = "znoj-backend-user-service", path = "/api/user/inner")
public interface UserFeignClient {

    public static final Logger logger = LoggerFactory.getLogger(UserFeignClient.class);

    /**
     * 根据 id 获取用户
     * @param userId
     * @return
     */
    @GetMapping("/get/id")
    User getById(@RequestParam("userId") long userId);

    /**
     * 根据 id 获取用户列表
     * @param idList
     * @return
     */
    @GetMapping("/get/ids")
    List<User> listByIds(@RequestParam("idList") Collection<Long> idList);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    default User getLoginUser(HttpServletRequest request){
        HttpSession session = request.getSession(false); // 不创建新 Session
        logger.info("Session: " + session);
        logger.info("Session ID: " + request.getSession().getId());
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        logger.info("User object from session: " + userObj);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    default boolean isAdmin(User user){
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * 获取脱敏的用户信息
     * @param user
     * @return
     */
    default UserVO getUserVO(User user){
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

}
