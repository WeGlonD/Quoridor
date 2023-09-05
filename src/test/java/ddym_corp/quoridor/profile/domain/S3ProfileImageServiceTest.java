package ddym_corp.quoridor.profile.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class S3ProfileImageServiceTest {

    @Test
    void upload() throws IOException {
//        FileInputStream fileInputStream = new FileInputStream("src/test/resources/test_img.jpg");
//        MockMultipartFile mockMultipartFile = new MockMultipartFile("test_img", "test_img.jpg", "jpg", fileInputStream);
//
//        when(profileImageService.upload(mockMultipartFile, 50L)).thenReturn(profileImageRepository.findOne(50L));
//
//        System.out.println("url = " + profileImageRepository.findOne(50L));
    }

    @Test
    void get() {
    }

    @Test
    void delete() {
    }
}