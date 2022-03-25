package gitee.com.ericfox.ddd.application.framework.model.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@ToString(callSuper = true)
public class GrantedAuthorityImpl implements GrantedAuthority {
    private String authority;
}
