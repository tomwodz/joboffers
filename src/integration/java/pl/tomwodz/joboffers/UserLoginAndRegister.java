package pl.tomwodz.joboffers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.tomwodz.joboffers.domain.loginandregister.dto.RegistrationResultDto;
import pl.tomwodz.joboffers.infrastructure.loginandregister.controller.dto.JwtResponseDto;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AllArgsConstructor
@Component
public class UserLoginAndRegister {

    public MockMvc mockMvc;

    public ObjectMapper objectMapper;

    public  String userRegisterAndLogin() throws Exception {
    //someUser was registered with somePassword
    //given
    //when
    ResultActions registerAction = mockMvc.perform(post("/register")
            .content("""
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );
    //then
    MvcResult mvcResultRegister = registerAction.andExpect(status().isCreated()).andReturn();
    String registerActionResultResponse = mvcResultRegister.getResponse().getContentAsString();
    RegistrationResultDto registrationResultDto = objectMapper.readValue(registerActionResultResponse, new TypeReference<>() {});
    assertAll(
            () -> assertThat(registrationResultDto.username()).isEqualTo("someUser"),
            () -> assertThat(registrationResultDto.registered()).isTrue(),
            () -> assertThat(registrationResultDto.id()).isNotNull());

    //login
    //given
    //when
    ResultActions successLoginRequest = mockMvc.perform(post("/token")
            .content("""
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    //then
    MvcResult mvcResult = successLoginRequest.andExpect(status().isOk()).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    JwtResponseDto jwtResponse = objectMapper.readValue(response, new TypeReference<>() {});
    String token = jwtResponse.token();
    assertAll(
            () -> assertThat(jwtResponse.username()).isEqualTo("someUser"),
            () -> assertThat(token).matches(Pattern.compile("^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$")));
    return jwtResponse.token();

}
}
