package ecomes.iteecomest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class ResourceHandler implements WebMvcConfigurer {
    @Value("${file.storage-location}")
    private String fileLocation;

    @Value("${file.client-path}")
    private String filePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(filePath+"/**")
                .addResourceLocations("file:" + fileLocation);
    }
}
