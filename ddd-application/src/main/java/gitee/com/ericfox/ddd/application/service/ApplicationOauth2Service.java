package gitee.com.ericfox.ddd.application.service;

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.oauth2.token.AccessToken;
import com.xkcoding.json.JsonUtil;
import gitee.com.ericfox.ddd.common.toolkit.trans.SimpleCondition;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserService;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
@Slf4j
public class ApplicationOauth2Service implements JapUserService {
    @Resource
    private SysUserService sysUserService;

    /**
     * 根据第三方平台标识（platform）和第三方平台的用户 uid 查询数据库
     *
     * @param platform 第三方平台标识
     * @param uid      第三方平台的用户 uid
     * @return JapUser
     */
    @Override
    public JapUser getByPlatformAndUid(String platform, String uid) {
        SysUserEntity entity = new SysUserEntity();
        entity.set_condition(SimpleCondition.newInstance().equals(SysUser.STRUCTURE.uuid, uid));
        SysUserEntity sysUserEntity = sysUserService.findFirst(entity);
        if (sysUserEntity == null) {
            log.error("用户不存在platform:{}, uid: {}", platform, uid);
            return null;
        }
        JapUser japUser = new JapUser();
        japUser.setUsername(entity.getUsername());
        japUser.setUserId(entity.getId().toString());
        //japUser.setPassword(entity.get)
        return japUser;
    }

    /**
     * 创建并获取第三方用户，相当于第三方登录成功后，将授权关系保存到数据库
     * （开发者业务系统中 oauth2 user -> sys user 的绑定关系）
     *
     * @param platform  第三方平台标识
     * @param userInfo  第三方返回的用户信息
     * @param tokenInfo token 信息，可以强制转换为 com.fujieid.jap.oauth2.token.AccessToken
     * @return JapUser
     */
    @Override
    public JapUser createAndGetOauth2User(String platform, Map<String, Object> userInfo, Object tokenInfo) {
        // FIXME 业务端可以对 tokenInfo 进行保存或其他操作
        AccessToken accessToken = (AccessToken) tokenInfo;
        System.out.println(JsonUtil.toJsonString(accessToken));
        // FIXME 注意：此处仅作演示用，不同的 oauth 平台用户id都不一样，
        // 此处需要开发者自己分析第三方平台的用户信息，提取出用户的唯一ID
        String uid = (String) userInfo.get("userId");
        // 查询绑定关系，确定当前用户是否已经登录过业务系统
        JapUser japUser = this.getByPlatformAndUid(platform, uid);
        if (null == japUser) {
            // 保存用户
            SysUserEntity sysUserEntity = new SysUserEntity();
            sysUserService.register(sysUserEntity);
            japUser = new JapUser();
            japUser.setAdditional(userInfo);
            //userData.add(japUser);
        }
        return japUser;
    }
}
