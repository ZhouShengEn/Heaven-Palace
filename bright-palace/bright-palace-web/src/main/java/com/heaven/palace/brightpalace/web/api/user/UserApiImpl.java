package com.heaven.palace.brightpalace.web.api.user;

import com.heaven.palace.brightpalace.api.api.user.UserApi;
import com.heaven.palace.brightpalace.api.api.user.vo.UserInfoResVO;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;
import com.heaven.palace.brightpalace.domain.business.user.repository.UserRepository;
import com.heaven.palace.brightpalace.domain.factory.repository.MultiRepoFactory;
import com.heaven.palace.brightpalace.domain.factory.repository.context.RepoRegisterConst;
import com.heaven.palace.jasperpalace.base.ddd.PrimaryKey;
import com.heaven.palace.jasperpalace.base.response.GlobalRestResponse;
import com.heaven.palace.purplecloudpalace.util.MappingUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhoushengen
 * @Description: 用户api实现
 * @DateTime: 2024/2/5 16:09
 **/
@RestController
public class UserApiImpl implements UserApi {

    @Resource
    private MultiRepoFactory multiRepoFactory;
    @Override
    public GlobalRestResponse<UserInfoResVO> queryUserInfo(Long userId) {
        UserRepository userRepository = (UserRepository) multiRepoFactory.getMultiImplement(RepoRegisterConst.USER);
        List<UserAggregate> userAggregates = userRepository.selectUser(new UserAggregate().setId(new PrimaryKey(userId)));
        UserAggregate userAggregate = userAggregates.get(0);
        return GlobalRestResponse.success(MappingUtils.beanConvert(userAggregate, UserInfoResVO.class), "查询成功！");
    }
}
