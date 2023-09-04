package ddym_corp.quoridor.profile.domain;

import com.amazonaws.services.s3.AmazonS3Client;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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