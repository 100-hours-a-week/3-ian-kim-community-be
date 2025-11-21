package ktb3.full.community.common.config;

import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.path.base}")
    private String fileBasePath;

    @Value("${file.path.image}")
    private String fileImagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileImagePath + "/**")
                .addResourceLocations("file:" + fileBasePath + fileImagePath + "/");
    }
}
