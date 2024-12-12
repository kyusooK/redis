package kyusootest.config.redis;

public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    Cookie[] cookies = attributes.getRequest().getCookies();
                    // Start Generation Here
                    for (Cookie cookie : cookies) {
                        if ("JSESSIONID".equals(cookie.getName())) {
                            template.header(
                                "Cookie",
                                "JSESSIONID=" + cookie.getValue()
                            );
                            break;
                        }
                    }
                }
            }
        };
    }
}
