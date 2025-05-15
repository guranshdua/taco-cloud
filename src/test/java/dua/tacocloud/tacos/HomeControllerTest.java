package dua.tacocloud.tacos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testHomePage() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("Home"))
                .andExpect(content().string(containsString("Welcome to...")));
    }
    @Test
    public void testNotFoundPage() throws Exception{
        mockMvc.perform(get("/404"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testServerError() throws Exception{
        mockMvc.perform(get("/error"))
                .andExpect(status().is5xxServerError());
    }
}
