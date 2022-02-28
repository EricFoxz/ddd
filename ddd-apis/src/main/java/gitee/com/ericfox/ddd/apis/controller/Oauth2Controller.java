package gitee.com.ericfox.ddd.apis.controller;

import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.http.adapter.jakarta.JakartaRequestAdapter;
import com.fujieid.jap.http.adapter.jakarta.JakartaResponseAdapter;
import com.fujieid.jap.oauth2.OAuthConfig;
import com.fujieid.jap.oauth2.Oauth2GrantType;
import com.fujieid.jap.oauth2.Oauth2ResponseType;
import com.fujieid.jap.oauth2.Oauth2Strategy;
import gitee.com.ericfox.ddd.application.service.ApplicationOauth2Service;
import gitee.com.ericfox.ddd.common.toolkit.coding.IdUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.URLUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/oauth2")
public class Oauth2Controller {
    @Resource
    private ApplicationOauth2Service applicationOauth2Service;

    @RequestMapping("/login/jai")
    @SneakyThrows
    public ModelAndView renderAuth(HttpServletRequest request, HttpServletResponse response) {
        Oauth2Strategy oauth2Strategy = new Oauth2Strategy(applicationOauth2Service, new JapConfig());
        OAuthConfig config = new OAuthConfig();
        config.setPlatform("jai")
                .setState(IdUtil.fastSimpleUUID())
                .setClientId("xx")
                .setClientSecret("xx")
                .setCallbackUrl("http://sso.jap.com:8443/oauth2/login/jai")
                .setAuthorizationUrl("xx")
                .setTokenUrl("xx")
                .setUserinfoUrl("xx")
                .setScopes(new String[]{"read", "write"})
                .setResponseType(Oauth2ResponseType.CODE)
                .setGrantType(Oauth2GrantType.AUTHORIZATION_CODE);
        JapResponse japResponse = oauth2Strategy.authenticate(config, new JakartaRequestAdapter(request), new JakartaResponseAdapter(response));
        if (!japResponse.isSuccess()) {
            return new ModelAndView(new RedirectView("/?error=" + URLUtil.encode(japResponse.getMessage())));
        }
        if (japResponse.isRedirectUrl()) {
            return new ModelAndView(new RedirectView((String) japResponse.getData()));
        } else {
            // 登录成功，需要对用户数据进行处理
            // ...
            System.out.println(japResponse.getData());
            return new ModelAndView(new RedirectView("/"));
        }
    }
}
