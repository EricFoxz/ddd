package gitee.com.ericfox.ddd.infrastructure.persistent.po.sys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class UserPo {
    @Id
    private Long id;
    private String username;
}
