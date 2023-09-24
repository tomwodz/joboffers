package pl.tomwodz.joboffers.cache.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import pl.tomwodz.joboffers.BaseIntegrationTest;
import pl.tomwodz.joboffers.UserLoginAndRegister;
import pl.tomwodz.joboffers.domain.offer.OfferFacade;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class RedisOffersCacheIntegrationTest extends BaseIntegrationTest {

    @Container
    private static final GenericContainer<?> REDIS;

    static {
        REDIS = new GenericContainer<>("redis").withExposedPorts(6379);
        REDIS.start();
    }

    @SpyBean
    OfferFacade offerFacade;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    UserLoginAndRegister userLoginAndRegister;

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.redis.port", () -> REDIS.getFirstMappedPort().toString());
        registry.add("spring.cache.type", () -> "redis");
        registry.add("spring.cache.redis.time-to-live", () -> "PT1S");
    }

    @Test
    public void ShouldSaveOffersToCacheAndThenInvalidateByTimeToLive() throws Exception {
        // step 1 and 2: register and login
        String jwtToken = userLoginAndRegister.userRegisterAndLogin();

        // step 3: should save to cache offers request
        //given
        //when
        mockMvc.perform(get("/offers")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //then
        verify(offerFacade, times(1)).findAllOffers();
        assertThat(cacheManager.getCacheNames().contains("jobOffers")).isTrue();

        // step 4: cache should be invalidated
        //given
        //when
        //then
        await()
                .atMost(Duration.ofSeconds(6))
                .pollInterval(Duration.ofSeconds(1))
                .untilAsserted(() -> {
                            mockMvc.perform(get("/offers")
                                    .header("Authorization", "Bearer " + jwtToken)
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                            );
                            verify(offerFacade, atLeast(3)).findAllOffers();
                        }
                );
    }

}
