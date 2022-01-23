package gitee.com.ericfox.ddd.infrastructure.general.config;

import gitee.com.ericfox.ddd.infrastructure.general.config.env.CustomProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.Resource;

/**
 * SpringSecurity配置类
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private CustomProperties customProperties;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers().frameOptions().sameOrigin();
        httpSecurity
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, customProperties.getApi().getStaticSources())
                .permitAll()
                .antMatchers("/apis/**").permitAll()
                .antMatchers("/sys/**").permitAll()
                .and()
                .logout()
                .permitAll();
        httpSecurity
                .csrf()
                .disable()
                .sessionManagement()
                .disable();
    }
}
