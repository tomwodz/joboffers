package pl.tomwodz.joboffers.infrastructure.clientoffer.http;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import pl.tomwodz.joboffers.domain.clientoffer.ClientOfferQuery;
import pl.tomwodz.joboffers.domain.clientoffer.dto.JobOfferResponse;

import java.util.Collections;
import java.util.List;

@Log4j2
@AllArgsConstructor
public class ClientOfferRestTemplate implements ClientOfferQuery {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<JobOfferResponse> fetchOffers() {
        log.info("Started connecting to outside client");
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        try {
            String urlForService = getUrlForService("/offers");
            final String url = UriComponentsBuilder.fromHttpUrl(urlForService).toUriString();
            ResponseEntity<List<JobOfferResponse>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    });
            response.getBody();
            return verificationBodyResponse(response.getBody());
        }
        catch (ResourceAccessException | IllegalArgumentException e) {
            log.error("Error connecting to outside client " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }

    private List<JobOfferResponse> verificationBodyResponse(List<JobOfferResponse> listBodyResponse) {
        if(listBodyResponse != null){
            log.info("Success connecting. Returned body: " + listBodyResponse);
            return listBodyResponse;
        }
        log.info("List job offers was null.");
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

}
