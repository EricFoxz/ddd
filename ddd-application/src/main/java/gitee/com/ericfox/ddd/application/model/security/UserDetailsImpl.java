package gitee.com.ericfox.ddd.application.model.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@ToString(callSuper = true)
public class UserDetailsImpl implements UserDetails {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 是否账号未锁定
     */
    private boolean accountNonLocked = false;
    /**
     * 是否账号未过期
     */
    private boolean accountNonExpired = false;
    /**
     * 是否证书未过期
     */
    private boolean credentialsNonExpired = false;
    /**
     * 是否可用
     */
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
