package gitee.com.ericfox.ddd.apis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/oauth2")
public class Oauth2Controller {
    //TODO 用户权限
    /*@Resource
    private ApplicationFrameworkOauth2Service applicationFrameworkOauth2Service;

    @RequestMapping("/login/jai")
    @SneakyThrows
    public ModelAndView renderAuth(HttpServletRequest request, HttpServletResponse response) {
        Oauth2Strategy oauth2Strategy = new Oauth2Strategy(applicationFrameworkOauth2Service, new JapConfig());
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
    }*/
}
