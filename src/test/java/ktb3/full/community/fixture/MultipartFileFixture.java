package ktb3.full.community.fixture;

import org.springframework.mock.web.MockMultipartFile;

public class MultipartFileFixture {

    public static MockMultipartFile createImage() {
        return new MockMultipartFile("image", new byte[]{});
    }

    public static MockMultipartFile createWithOriginName(String originalFilename) {
        return new MockMultipartFile("image", originalFilename, "image", new byte[]{});
    }

    public static MockMultipartFile createProfileImage() {
        return new MockMultipartFile("profileImage", new byte[]{});
    }
}
