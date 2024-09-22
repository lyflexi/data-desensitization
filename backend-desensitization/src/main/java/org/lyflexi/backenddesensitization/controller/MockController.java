package org.lyflexi.backenddesensitization.controller;

import org.lyflexi.backenddesensitization.component.DesensitiveComponent;
import org.lyflexi.backenddesensitization.model.UserPo;
import org.lyflexi.backenddesensitization.model.UserVo;
import org.lyflexi.backenddesensitization.result.Result;
import org.lyflexi.backenddesensitization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: lyflexi
 * @project: data-desensitization
 * @Date: 2024/9/22 19:59
 */
@RestController
@RequestMapping("/mock")
public class MockController {
    @Autowired
    UserService userService;
    @Autowired
    DesensitiveComponent desensitiveComponent;
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result<?> getUser(@PathVariable("id") Long id){
        UserPo po = userService.getById(id);
        UserVo userVo = new UserVo();
        desensitiveComponent.desensitize(po,userVo);
        return Result.success(userVo);
    }
}
