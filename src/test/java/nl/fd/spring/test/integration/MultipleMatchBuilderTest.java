package nl.fd.spring.test.integration;

import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static nl.fd.hamcrest.jsoup.ElementWithOwnText.hasOwnText;
import static nl.fd.spring.test.integration.HtmlResultMatchers.html;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class MultipleMatchBuilderTest {

    @Controller
    public static class DummyController {

        @RequestMapping("/test")
        public void renderTestPage(HttpServletResponse response) throws IOException {
            response.getWriter().println("<html>" +
                    "<head><title>Title</title></head>" +
                    "<body><h1>It works!</h1></body>" +
                    "</html>");
        }
    }

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = standaloneSetup(new DummyController()).build();
    }

    @Test
    public void testMultipleMatch() throws Exception {
        mockMvc.perform(get("/test")).andExpect(
                html().withAllOf()
                        .title("Title")
                        .singleElement("h1", hasOwnText("It works!")));
    }
}