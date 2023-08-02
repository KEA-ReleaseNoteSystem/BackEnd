package kakao99.backend.issue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kakao99.backend.common.ResponseMessage;
import kakao99.backend.entity.Issue;
import kakao99.backend.issue.controller.IssueController;
import kakao99.backend.issue.controller.IssueForm;
import kakao99.backend.issue.repository.IssueRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(IssueController.class)
public class IssueControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    IssueRepository issueRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void 이슈_생성() throws Exception {
        final Long projectId = 1L;
        final String title = "22이게 맞나 테스트";
        final String writerName = "박도영";
        final String type = "진행중?";
        final String description = "테스트입니다.";
        final Long userId = 1L;

        final String url = (String) "/api/project/"+ projectId +"/issue";
//        final String url2 = "/api/test";

        IssueForm issueForm = IssueForm.builder()
                .title(title)
                .writerName(writerName)
                .type(type)
                .description(description)
                .userId(userId)
                .build();

        final String requestBody = objectMapper.writeValueAsString(issueForm);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isOk());

    }

    @AfterEach
    public void resetTestData() {
        Long maxId = issueRepository.findMaxId();
        System.out.println("maxId = " + maxId);
        issueRepository.deleteById(maxId);
    }

}
