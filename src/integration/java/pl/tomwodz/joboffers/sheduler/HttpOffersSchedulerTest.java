package pl.tomwodz.joboffers.sheduler;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import pl.tomwodz.joboffers.BaseIntegrationTest;
import pl.tomwodz.joboffers.JobOffersApplication;
import pl.tomwodz.joboffers.domain.clientoffer.ClientOfferQuery;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = JobOffersApplication.class, properties = "scheduling.enabled=true")
public class HttpOffersSchedulerTest extends BaseIntegrationTest {

    @SpyBean
    ClientOfferQuery remoteOfferClient;

    @Test
    public void ShouldRunHttpClientOffersFetchingExactlyGivenTimes() {
        await().
                atMost(Duration.ofSeconds(3))
                .untilAsserted(() -> verify(remoteOfferClient, times(3)).fetchOffers());
    }
}
