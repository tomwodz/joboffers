package pl.tomwodz.joboffers.feature;

import pl.tomwodz.joboffers.domain.clientoffer.dto.JobOfferResponse;

import java.util.List;

public interface SampleResponseIntegrationTest {

    default String bodyWithZeroOffersJson() {
        return "[]";
    }

    default String bodyWithThreeOffersJson() {
        return """
               [
               {
                        "title": "Junior Java Developer",
                        "company": "BlueSoft Sp. z o.o.",
                        "salary": "7 000 – 9 000 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre"
               },
               {
                        "title": "Java (CMS) Developer",
                        "company": "Efigence SA",
                        "salary": "16 000 – 18 000 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh"
               },
               {
                        "title": "Junior Java Developer",
                        "company": "Sollers Consulting",
                        "salary": "7 500 – 11 500 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-sollers-consulting-warszawa-s6et1ucc"
               }
               ]
               """.trim();
    }


}
