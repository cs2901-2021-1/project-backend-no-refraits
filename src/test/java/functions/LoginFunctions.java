package functions;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.entities.Login;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class LoginFunctions {
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Login getLoginMockData() {
        return new Login("esteban.villacorta@utec.edu.pe", "111014891633982592683");
    }

    public static Login fakeLoginMockData() {
        return new Login("absolutely.fake@fake.edu.pe", "111014891633982592683");
    }

    public static MvcResult simulateLogin(MockMvc mvc) throws Exception {
        final var login = getLoginMockData();
        return mvc.perform(MockMvcRequestBuilders.post("/token/generate-token")
                .content(asJsonString(login))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    public static MvcResult simulateLoginFail(MockMvc mvc) throws Exception {
        final var login = fakeLoginMockData();
        return mvc.perform(MockMvcRequestBuilders.post("/token/generate-token")
                .content(asJsonString(login))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }
}
