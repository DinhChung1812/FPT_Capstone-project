package doan.oishii_share_cong_thuc_nau_an.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import doan.oishii_share_cong_thuc_nau_an.common.utils.JwtUtils;
import doan.oishii_share_cong_thuc_nau_an.common.utils.LoginRequest;
import doan.oishii_share_cong_thuc_nau_an.common.utils.SignupRequest;
import doan.oishii_share_cong_thuc_nau_an.common.vo.MessageVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.SaveBlogRequest;
import doan.oishii_share_cong_thuc_nau_an.entities.Account;
import doan.oishii_share_cong_thuc_nau_an.repositories.AccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenControllerTest {

    @MockBean
    AccountRepository accountRepository;

    @InjectMocks
    AuthenController loginController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder encoder;


    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    WebApplicationContext webApplicationContext;


    @BeforeEach
    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }
    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

   

    @Test
//    @WithMockUser(username = "tu123",roles = {"ADMIN"})
    void  testRegisterUser1() throws Exception{
        String uri = "/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("tu202@gmail.com");
        signupRequest.setFullname("le tien tu");
        signupRequest.setUsername("tu202");
        signupRequest.setPassword("123");
        Account account = new Account(signupRequest.getUsername(), encoder.encode(signupRequest.getPassword()),
                signupRequest.getEmail(), signupRequest.getFullname());
        account.setRole("ROLE_USER");
        account.setStatus(1);


        MessageVo messageVo = new MessageVo("B???n ???? ????ng k?? th??nh c??ng", "info");

        Mockito.when(accountRepository.save(account)).thenReturn(account);

        String inputJson = mapToJson(signupRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.messContent").value("B???n ???? ????ng k?? th??nh c??ng"))
                .andExpect(jsonPath("$.messType").value("info"));

    }

    @Test
    void  testRegisterUser2() throws Exception{
        String uri = "/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("tu202@gmail.com");
        signupRequest.setFullname("le tien tu");
        signupRequest.setUsername("tu123");
        signupRequest.setPassword("123");


        Mockito.when(accountRepository.existsByUserName(signupRequest.getUsername())).thenReturn(true);


        String inputJson = mapToJson(signupRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).
                andExpect(jsonPath("$.messContent").value("T??n ng?????i d??ng ???? t???n t???i"))
                .andExpect(jsonPath("$.messType").value("error"));

    }

    @Test
    void  testRegisterUser3() throws Exception{
        String uri = "/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("tu202@gmail.com");
        signupRequest.setFullname("le tien tu");
        signupRequest.setUsername("tu123");
        signupRequest.setPassword("123");


        Mockito.when(accountRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);


        String inputJson = mapToJson(signupRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).
                andExpect(jsonPath("$.messContent").value("Email ???? t???n t???i"))
                .andExpect(jsonPath("$.messType").value("error"));

    }

    @Test
    void  testRegisterUser4() throws Exception{
        String uri = "/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("tu202@gmail.com");
        signupRequest.setFullname("le tien tu");
        signupRequest.setPassword("123");

        String inputJson = mapToJson(signupRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).
                andExpect(jsonPath("$.messContent").value("Ch??a nh???p t??n ng?????i d??ng"))
                .andExpect(jsonPath("$.messType").value("error"));

    }

    @Test
    void  testRegisterUser5() throws Exception{
        String uri = "/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("tu202@gmail.com");
        signupRequest.setFullname("le tien tu");
        signupRequest.setUsername("");
        signupRequest.setPassword("123");

        String inputJson = mapToJson(signupRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).
                andExpect(jsonPath("$.messContent").value("Ch??a nh???p t??n ng?????i d??ng"))
                .andExpect(jsonPath("$.messType").value("error"));

    }

    @Test
    void  testRegisterUser6() throws Exception{
        String uri = "/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("tu202@gmail.com");
        signupRequest.setFullname("le tien tu");
        signupRequest.setUsername("tu123");

        String inputJson = mapToJson(signupRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).
                andExpect(jsonPath("$.messContent").value("Ch??a nh???p m???t kh???u"))
                .andExpect(jsonPath("$.messType").value("error"));

    }

    @Test
    void  testRegisterUser7() throws Exception{
        String uri = "/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("tu202@gmail.com");
        signupRequest.setFullname("le tien tu");
        signupRequest.setUsername("tu123");
        signupRequest.setPassword("");

        String inputJson = mapToJson(signupRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).
                andExpect(jsonPath("$.messContent").value("Ch??a nh???p m???t kh???u"))
                .andExpect(jsonPath("$.messType").value("error"));

    }

    @Test
    void  testRegisterUser8() throws Exception{
        String uri = "/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setPassword("123");
        signupRequest.setFullname("le tien tu");
        signupRequest.setUsername("tu123");

        String inputJson = mapToJson(signupRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).
                andExpect(jsonPath("$.messContent").value("Ch??a nh???p email"))
                .andExpect(jsonPath("$.messType").value("error"));

    }

    void  testRegisterUser9() throws Exception{
        String uri = "/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setPassword("123");
        signupRequest.setFullname("le tien tu");
        signupRequest.setUsername("tu123");
        signupRequest.setEmail("");

        String inputJson = mapToJson(signupRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).
                andExpect(jsonPath("$.messContent").value("Ch??a nh???p email"))
                .andExpect(jsonPath("$.messType").value("error"));

    }

    @Test
    void  testRegisterUser10() throws Exception{
        String uri = "/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setPassword("123");
        signupRequest.setEmail("tu202@gmail.com");
        signupRequest.setUsername("tu123");

        String inputJson = mapToJson(signupRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).
                andExpect(jsonPath("$.messContent").value("Ch??a nh???p h??? t??n c???a b???n"))
                .andExpect(jsonPath("$.messType").value("error"));

    }

    @Test
    void  testRegisterUser11() throws Exception{
        String uri = "/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setPassword("123");
        signupRequest.setEmail("tu202@gmail.com");
        signupRequest.setUsername("tu123");
        signupRequest.setFullname("");

        String inputJson = mapToJson(signupRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).
                andExpect(jsonPath("$.messContent").value("Ch??a nh???p h??? t??n c???a b???n"))
                .andExpect(jsonPath("$.messType").value("error"));

    }
}
