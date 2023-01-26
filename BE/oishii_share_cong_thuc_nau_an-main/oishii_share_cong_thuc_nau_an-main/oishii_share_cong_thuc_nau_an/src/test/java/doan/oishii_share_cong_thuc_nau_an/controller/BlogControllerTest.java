package doan.oishii_share_cong_thuc_nau_an.controller;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import doan.oishii_share_cong_thuc_nau_an.common.vo.MessageVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.SaveBlogRequest;
import doan.oishii_share_cong_thuc_nau_an.entities.Account;
import doan.oishii_share_cong_thuc_nau_an.exception.NotFoundException;
import doan.oishii_share_cong_thuc_nau_an.exception.StatusCode;
import doan.oishii_share_cong_thuc_nau_an.repositories.AccountRepository;
import doan.oishii_share_cong_thuc_nau_an.repositories.BlogRepository;
import doan.oishii_share_cong_thuc_nau_an.service.BlogService;

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
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
@SpringBootTest
@AutoConfigureMockMvc
//@ContextConfiguration
//@ExtendWith( SpringExtension.class)
//@WebMvcTest(LoginController.class)
//@AutoConfigureMockMvc(addFilters = false)
public class BlogControllerTest {



    @MockBean
    AccountRepository accountRepository;

    @MockBean
    BlogService blogService;

    @MockBean
    BlogRepository blogRepository;



    @InjectMocks
    private BlogController blogController;


    @Autowired
    private MockMvc mockMvc;



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
    void  testSaveBlog1() throws Exception{
        Account account = new Account("tu123", "123", "tu@gmail.com", "tule");
        String uri = "/saveBlog";
        SaveBlogRequest saveBlogRequest = new SaveBlogRequest();
        saveBlogRequest.setBlogId(1);
        saveBlogRequest.setTitle("title");
        saveBlogRequest.setContent("content");



        String inputJson = mapToJson(saveBlogRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).
                andExpect(jsonPath("$.message").value("Bạn không có quyền sử dụng chức năng này"));


    }

    @Test
    @WithMockUser(username = "tu123",roles = {"ADMIN"})
    void  testSaveBlog2() throws Exception{
        Account account = new Account("tu123", "123", "tu@gmail.com", "tule");
        String uri = "/saveBlog";
        SaveBlogRequest saveBlogRequest = new SaveBlogRequest();
        saveBlogRequest.setTitle("title");
        saveBlogRequest.setContent("content");


        MessageVo messageVo = new MessageVo("lưu bài viết thành công", "success");

        Mockito.when(accountRepository.findAccountByUserName("tu123")).thenReturn(account);
        Mockito.when(blogService.createBlog(saveBlogRequest.getTitle(),saveBlogRequest.getContent(),account))
                        .thenReturn(new ResponseEntity(messageVo, HttpStatus.OK));

        String inputJson = mapToJson(saveBlogRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

         mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.messContent").value("lưu bài viết thành công"))
                .andExpect(jsonPath("$.messType").value("success"));


    }

    @Test
    @WithMockUser(username = "tu123",roles = {"ADMIN"})
    void  testSaveBlog3() throws Exception{
        Account account = new Account("tu123", "123", "tu@gmail.com", "tule");
        String uri = "/saveBlog";
        SaveBlogRequest saveBlogRequest = new SaveBlogRequest();
        saveBlogRequest.setBlogId(1000);
        saveBlogRequest.setTitle("title");
        saveBlogRequest.setContent("content");

        Mockito.when(accountRepository.findAccountByUserName("tu123")).thenReturn(account);
        Mockito.when(blogService.updateBlog(saveBlogRequest.getBlogId(),saveBlogRequest.getTitle(),saveBlogRequest.getContent(),account))
                .thenThrow(new NotFoundException(StatusCode.Not_Found, "blog này không tồn tại"));

        String inputJson = mapToJson(saveBlogRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound()).
                andExpect(jsonPath("$.message").value("blog này không tồn tại"));


    }

    @Test
    @WithMockUser(username = "tu123",roles = {"ADMIN"})
    void  testSaveBlog4() throws Exception{
        Account account = new Account("tu123", "123", "tu@gmail.com", "tule");
        String uri = "/saveBlog";
        SaveBlogRequest saveBlogRequest = new SaveBlogRequest();
        saveBlogRequest.setBlogId(1);
        saveBlogRequest.setTitle("title");
        saveBlogRequest.setContent("content");

        MessageVo messageVo = new MessageVo("cập nhật bài viết thành công", "success");

        Mockito.when(accountRepository.findAccountByUserName("tu123")).thenReturn(account);
        Mockito.when(blogService.updateBlog(saveBlogRequest.getBlogId(),saveBlogRequest.getTitle(),saveBlogRequest.getContent(),account))
                .thenReturn(new ResponseEntity(messageVo, HttpStatus.OK));

        String inputJson = mapToJson(saveBlogRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.messContent").value("cập nhật bài viết thành công"))
                .andExpect(jsonPath("$.messType").value("success"));


    }

    @Test
    @WithMockUser(username = "tu123",roles = {"ADMIN"})
    void  testSaveBlog5() throws Exception{
        Account account = new Account("tu123", "123", "tu@gmail.com", "tule");
        String uri = "/saveBlog";
        SaveBlogRequest saveBlogRequest = new SaveBlogRequest();
        saveBlogRequest.setContent("content");
        saveBlogRequest.setTitle("");


        String inputJson = mapToJson(saveBlogRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.messContent").value("xin hãy điền tiêu đề cho bài viết"))
                .andExpect(jsonPath("$.messType").value("error"));


    }

    @Test
    @WithMockUser(username = "tu123",roles = {"ADMIN"})
    void  testSaveBlog6() throws Exception{
        Account account = new Account("tu123", "123", "tu@gmail.com", "tule");
        String uri = "/saveBlog";
        SaveBlogRequest saveBlogRequest = new SaveBlogRequest();
        saveBlogRequest.setContent("content");


        String inputJson = mapToJson(saveBlogRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.messContent").value("xin hãy điền tiêu đề cho bài viết"))
                .andExpect(jsonPath("$.messType").value("error"));


    }

    @Test
    @WithMockUser(username = "tu123",roles = {"ADMIN"})
    void  testSaveBlog7() throws Exception{
        Account account = new Account("tu123", "123", "tu@gmail.com", "tule");
        String uri = "/saveBlog";
        SaveBlogRequest saveBlogRequest = new SaveBlogRequest();
        saveBlogRequest.setTitle("title");
        saveBlogRequest.setContent("");


        String inputJson = mapToJson(saveBlogRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.messContent").value("xin hãy điền nội dung cho bài viết"))
                .andExpect(jsonPath("$.messType").value("error"));


    }

    @Test
    @WithMockUser(username = "tu123",roles = {"ADMIN"})
    void  testSaveBlog8() throws Exception{
        Account account = new Account("tu123", "123", "tu@gmail.com", "tule");
        String uri = "/saveBlog";
        SaveBlogRequest saveBlogRequest = new SaveBlogRequest();
        saveBlogRequest.setTitle("title");



        String inputJson = mapToJson(saveBlogRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.messContent").value("xin hãy điền nội dung cho bài viết"))
                .andExpect(jsonPath("$.messType").value("error"));


    }





}
