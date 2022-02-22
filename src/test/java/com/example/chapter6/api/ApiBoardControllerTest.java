package com.example.chapter6.api;

import com.example.chapter6.model.BoardVO;
import com.example.chapter6.model.SearchHelper;
import com.example.chapter6.payload.request.LoginRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiBoardControllerTest {

    @Autowired
    MockMvc mvc;

    private String accessToken;
    private String tokenType;
    private String refreshToken;

    @Autowired
    WebApplicationContext context;

    @BeforeEach
    public void 로그인() throws Exception{
        this.mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8",true))
                .build();



        LoginRequest loginRequest = new LoginRequest("zxcv","1111");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(loginRequest);

        MvcResult result = mvc.perform(post("/api/member/login")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenType").exists())
                .andExpect(jsonPath("$.tokenType").value("Bearer "))
                .andExpect(jsonPath("$.refreshToken").exists())
                .andExpect(jsonPath("$.accessToken").exists())
                .andDo(print())
                .andReturn();

        this.accessToken = (String) new JacksonJsonParser().parseMap(result.getResponse().getContentAsString()).get("accessToken");
        this.tokenType = (String) new JacksonJsonParser().parseMap(result.getResponse().getContentAsString()).get("tokenType");
        this.refreshToken = (String) new JacksonJsonParser().parseMap(result.getResponse().getContentAsString()).get("refreshToken");
    }

    @Test
    @DisplayName(value = "게시물 목록")
   void boardList() throws Exception{

        SearchHelper searchHelper = new SearchHelper();
        searchHelper.setSrchCode(1000);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(searchHelper);

        mvc.perform(post("/api/board/list")
                        .header(HttpHeaders.AUTHORIZATION, tokenType+accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName(value="게시물 조회")
    void boardView() throws Exception {
        mvc.perform(get("/api/board/view/25")
                        .header(HttpHeaders.AUTHORIZATION, tokenType+accessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName(value="게시물 저장")
    void boardSave() throws Exception {

        BoardVO boardVO = new BoardVO();
        boardVO.setTitle("api write test");
        boardVO.setContent("api content test");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(boardVO);

        mvc.perform(post("/api/board/save")
                        .header(HttpHeaders.AUTHORIZATION, tokenType+accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName(value = "게시물 수정")
    void boardUpdate() throws Exception {
        BoardVO boardVO = new BoardVO();
        boardVO.setId(25);
        boardVO.setTitle("api write test");
        boardVO.setContent("api content test");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(boardVO);

        mvc.perform(put("/api/board/update")
                        .header(HttpHeaders.AUTHORIZATION, tokenType+accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName(value="게시물 삭제")
    void boardDelete() throws Exception{
        mvc.perform(delete("/api/board/delete/25")
                .header(HttpHeaders.AUTHORIZATION,tokenType+accessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName(value="ajax 파일 업로드")
    void ajax_파일_업로드() throws Exception{
        FileInputStream file = new FileInputStream("C:/Users/YooJin/Desktop/Craftables.png");
        MockMultipartFile multipartFile = new MockMultipartFile("file","Craftables.png","image/png",file);

        mvc.perform(MockMvcRequestBuilders.multipart("/api/board/ajaxFileUpload")
                        .file(multipartFile)
                        .header(HttpHeaders.AUTHORIZATION,tokenType+accessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }
}