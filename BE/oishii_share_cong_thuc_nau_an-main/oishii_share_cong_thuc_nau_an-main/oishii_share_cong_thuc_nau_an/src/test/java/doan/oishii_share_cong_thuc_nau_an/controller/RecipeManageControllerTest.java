package doan.oishii_share_cong_thuc_nau_an.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishFormulaVo;
import doan.oishii_share_cong_thuc_nau_an.service.DishServive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeManageControllerTest {

    @MockBean
    private DishServive dishServive;

    @InjectMocks
    private RecipeManageController recipeManageController;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

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
    @WithMockUser(username = "tu123",roles = {"ADMIN"})
    void  testGetAllRecipe1() throws Exception{
        Integer pageIndex = 1;
        String searchData = null;
        Integer pageSize = 10;
        String uri = "/admin/listRecipe";
        List<DishFormulaVo> dishFormulaVoList = new ArrayList<>();
        dishFormulaVoList.add(new DishFormulaVo(1,"cá kho", "11/23/2022", 1, "cá kho ngon", "mod1", "cá kho ngon"));
        dishFormulaVoList.add(new DishFormulaVo(2,"thịt kho", "11/23/2022", 2, "thịt kho ngon", "mod1", "thịt kho ngon"));
        dishFormulaVoList.add(new DishFormulaVo(3,"rau luộc", "11/23/2022", 3, "rau luộc ngon", "mod1", "rau luộc ngon"));
        Page<DishFormulaVo> dishFormulaVopage = new PageImpl<>(dishFormulaVoList);

        Mockito.when(dishServive.getAllRecipe(searchData,pageIndex-1,pageSize )).thenReturn(dishFormulaVopage);

//        String inputJson = mapToJson(saveBlogRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(uri).
                accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.dishFormulaVoList[0].dishName").value("cá kho"))
                .andExpect(jsonPath("$.dishFormulaVoList[1].dishName").value("thịt kho"))
                .andExpect(jsonPath("$.dishFormulaVoList[2].dishName").value("rau luộc"))
                .andExpect(jsonPath("$.pageIndex").value("1"));;

    }

    @Test
    @WithMockUser(username = "tu123",roles = {"USER"})
    void  testGetAllRecipe2() throws Exception{
        String uri = "/admin/listRecipe";
        List<DishFormulaVo> dishFormulaVoList = new ArrayList<>();
        dishFormulaVoList.add(new DishFormulaVo(1,"cá kho", "11/23/2022", 1, "cá kho ngon", "mod1", "cá kho ngon"));
        dishFormulaVoList.add(new DishFormulaVo(2,"thịt kho", "11/23/2022", 2, "thịt kho ngon", "mod1", "thịt kho ngon"));
        dishFormulaVoList.add(new DishFormulaVo(3,"rau luộc", "11/23/2022", 3, "rau luộc ngon", "mod1", "rau luộc ngon"));
        Page<DishFormulaVo> dishFormulaVopage = new PageImpl<>(dishFormulaVoList);

        Mockito.when(dishServive.getAllRecipe(null,0,10 )).thenReturn(dishFormulaVopage);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(uri).
                accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).
                andExpect(jsonPath("$.message").value("Bạn không có quyền sử dụng chức năng này"));

    }

    @Test
    void  testGetAllRecipe3() throws Exception{
        String uri = "/admin/listRecipe";
        List<DishFormulaVo> dishFormulaVoList = new ArrayList<>();
        dishFormulaVoList.add(new DishFormulaVo(1,"cá kho", "11/23/2022", 1, "cá kho ngon", "mod1", "cá kho ngon"));
        dishFormulaVoList.add(new DishFormulaVo(2,"thịt kho", "11/23/2022", 2, "thịt kho ngon", "mod1", "thịt kho ngon"));
        dishFormulaVoList.add(new DishFormulaVo(3,"rau luộc", "11/23/2022", 3, "rau luộc ngon", "mod1", "rau luộc ngon"));
        Page<DishFormulaVo> dishFormulaVopage = new PageImpl<>(dishFormulaVoList);

        Mockito.when(dishServive.getAllRecipe(null,0,10 )).thenReturn(dishFormulaVopage);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(uri).
                accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).
                andExpect(jsonPath("$.message").value("Bạn không có quyền sử dụng chức năng này"));

    }

    @Test
    @WithMockUser(username = "tu123",roles = {"ADMIN"})
    void  testGetAllRecipe4() throws Exception{
        Integer pageIndex = 2;
        String searchData = null;
        Integer pageSize = 10;
        String uri = "/admin/listRecipe?pageIndex="+pageIndex;
        List<DishFormulaVo> dishFormulaVoList = new ArrayList<>();
        dishFormulaVoList.add(new DishFormulaVo(4,"ếch rang muối", "11/23/2022", 4, "rau luộc ngon", "mod1", "rau luộc ngon"));
        dishFormulaVoList.add(new DishFormulaVo(5,"gà luộc", "11/23/2022", 5, "rau luộc ngon", "mod1", "rau luộc ngon"));
        dishFormulaVoList.add(new DishFormulaVo(6,"bò xào", "11/23/2022", 6, "rau luộc ngon", "mod1", "rau luộc ngon"));
        Page<DishFormulaVo> dishFormulaVopage = new PageImpl<>(dishFormulaVoList);

        Mockito.when(dishServive.getAllRecipe(searchData,pageIndex-1,pageSize )).thenReturn(dishFormulaVopage);

//        String inputJson = mapToJson(saveBlogRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(uri).
                accept(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.dishFormulaVoList[0].dishName").value("ếch rang muối"))
                .andExpect(jsonPath("$.pageIndex").value("2"));

    }

    @Test
    @WithMockUser(username = "tu123",roles = {"ADMIN"})
    void  testGetAllRecipe5() throws Exception{
        Integer pageIndex = 2;
        String searchData = "";
        Integer pageSize = 10;
        String uri = "/admin/listRecipe?pageIndex="+pageIndex+"&searchData=";
        List<DishFormulaVo> dishFormulaVoList = new ArrayList<>();
        dishFormulaVoList.add(new DishFormulaVo(4,"ếch rang muối", "11/23/2022", 4, "rau luộc ngon", "mod1", "rau luộc ngon"));
        dishFormulaVoList.add(new DishFormulaVo(5,"gà luộc", "11/23/2022", 5, "rau luộc ngon", "mod1", "rau luộc ngon"));
        dishFormulaVoList.add(new DishFormulaVo(6,"bò xào", "11/23/2022", 6, "rau luộc ngon", "mod1", "rau luộc ngon"));
        Page<DishFormulaVo> dishFormulaVopage = new PageImpl<>(dishFormulaVoList);

        Mockito.when(dishServive.getAllRecipe(searchData,pageIndex-1,pageSize )).thenReturn(dishFormulaVopage);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(uri).
                accept(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.dishFormulaVoList[0].dishName").value("ếch rang muối"))
                .andExpect(jsonPath("$.pageIndex").value("2"));

    }

    @Test
    @WithMockUser(username = "tu123",roles = {"ADMIN"})
    void  testGetAllRecipe6() throws Exception{
        Integer pageIndex = 2;
        String searchData = "gà";
        Integer pageSize = 10;
        String uri = "/admin/listRecipe?pageIndex="+pageIndex+"&searchData="+searchData;
        List<DishFormulaVo> dishFormulaVoList = new ArrayList<>();

        dishFormulaVoList.add(new DishFormulaVo(5,"gà luộc", "11/23/2022", 5, "rau luộc ngon", "mod1", "rau luộc ngon"));

        Page<DishFormulaVo> dishFormulaVopage = new PageImpl<>(dishFormulaVoList);

        Mockito.when(dishServive.getAllRecipe(searchData,pageIndex-1,pageSize )).thenReturn(dishFormulaVopage);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(uri).
                accept(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.dishFormulaVoList[0].dishName").value("gà luộc"))
                .andExpect(jsonPath("$.pageIndex").value("2"));

    }

    @Test
    @WithMockUser(username = "tu123",roles = {"ADMIN"})
    void  testGetAllRecipe7() throws Exception{
        String searchData = "gà";
        Integer pageSize = 10;
        String uri = "/admin/listRecipe?searchData="+searchData+"&pageIndex=";
        List<DishFormulaVo> dishFormulaVoList = new ArrayList<>();

        dishFormulaVoList.add(new DishFormulaVo(5,"gà luộc", "11/23/2022", 5, "rau luộc ngon", "mod1", "rau luộc ngon"));

        Page<DishFormulaVo> dishFormulaVopage = new PageImpl<>(dishFormulaVoList);

        Mockito.when(dishServive.getAllRecipe(searchData,0,pageSize )).thenReturn(dishFormulaVopage);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(uri).
                accept(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.dishFormulaVoList[0].dishName").value("gà luộc"))
                .andExpect(jsonPath("$.pageIndex").value("1"));

    }
}
