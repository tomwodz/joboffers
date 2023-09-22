package pl.tomwodz.joboffers.apivalidationerror;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.tomwodz.joboffers.BaseIntegrationTest;
import pl.tomwodz.joboffers.infrastructure.apivalidation.ApiValidationErrorResponseDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    @WithMockUser
    public void ShouldReturn400BadRequestAndValidationMessageWhenOfferRequestDtoToSaveHasEmptyAndNull() throws Exception {

        //given
        //when
        ResultActions perform = mockMvc.perform(post("/offers")
                .content(
                        """
                        {
                        "companyName": "",
                        "position": "",       
                        "offerUrl":""
                        }
                        """
                ).contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        //then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorResponseDto resultDto = objectMapper.readValue(result, new TypeReference<>() {
        });
        assertThat(resultDto.errors()).containsExactlyInAnyOrder(
                "Offer company name must not be empty",
                "Offer position must not be empty",
                "Offer salary must not be empty",
                "Offer salary must not be null",
                "Offer url must not be empty"
               );
    }

}
