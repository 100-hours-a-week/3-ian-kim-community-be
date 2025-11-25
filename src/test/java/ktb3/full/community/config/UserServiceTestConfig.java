package ktb3.full.community.config;

import ktb3.full.community.security.config.PasswordEncoderConfig;
import ktb3.full.community.service.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@Import({UserService.class, ImageUploadServiceStubConfig.class, PasswordEncoderConfig.class})
@TestConfiguration
public class UserServiceTestConfig {
}
