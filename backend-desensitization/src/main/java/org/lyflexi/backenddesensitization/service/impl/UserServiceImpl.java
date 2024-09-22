package org.lyflexi.backenddesensitization.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lyflexi.backenddesensitization.model.UserPo;
import org.lyflexi.backenddesensitization.service.UserService;
import org.lyflexi.backenddesensitization.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author hasee
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-09-22 19:47:44
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPo>
    implements UserService{

}




