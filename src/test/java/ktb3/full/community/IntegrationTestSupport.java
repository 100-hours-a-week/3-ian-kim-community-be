package ktb3.full.community;

import ktb3.full.community.service.ImageUploadService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {

    @MockitoBean
    protected ImageUploadService imageUploadService;
}
