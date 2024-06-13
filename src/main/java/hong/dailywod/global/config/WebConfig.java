package hong.dailywod.global.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hong.dailywod.global.interceptor.AdminAccessInterceptor;
import hong.dailywod.global.interceptor.UserAccessInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final UserAccessInterceptor userAccessInterceptor;

    // 사용자 api -> /api/~~
    // 관리자 api -> /admin-api/~~
    private static final List<String> PUBLIC = List.of("/api/v1/auth/**", "/admin-api/v1/auth/**");

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAccessInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(PUBLIC);

        registry.addInterceptor(new AdminAccessInterceptor())
                .addPathPatterns("/admin-api/**")
                .excludePathPatterns(PUBLIC);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081", "http://localhost:8082")
                .allowedMethods("*")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
