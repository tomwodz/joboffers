package pl.tomwodz.joboffers.feature;

import pl.tomwodz.joboffers.domain.clientoffer.dto.JobOfferResponse;

import java.util.List;

public interface SampleResponseIntegrationTest {

    default String bodyWithZeroOffersJson() {
        return "[]";
    }

    default String bodyWithTwoOffersJson() {
        return """
                [
                {
                         "title": "Junior Java Developer",
                         "company": "BlueSoft Sp. z o.o.",
                         "salary": "7 000 - 9 000 PLN",
                         "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre"
                },
                {
                        "title": "Junior Java Developer",
                        "company": "NIX Tech Kft.",
                        "salary": "6 169 - 12 339 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-nix-tech-kft-budapest-d1wuebdj"
                }
                ]
                """.trim();
    }

    default String bodyWithFourOffersJson() {
        return """
                [
                   {
                            "title": "Junior Java Developer",
                            "company": "BlueSoft Sp. z o.o.",
                            "salary": "7 000 - 9 000 PLN",
                            "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre"
                   },
                   {
                           "title": "Junior Java Developer",
                           "company": "NIX Tech Kft.",
                           "salary": "6 169 - 12 339 PLN",
                           "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-nix-tech-kft-budapest-d1wuebdj"
                   },
                   {
                            "title": "2023 Technology Program BNY Mellon",
                            "company": "BNY Mellon",
                            "salary": "5 833 - 7 500 PLN",
                            "offerUrl": "https://nofluffjobs.com/pl/job/2023-technology-program-bny-mellon-bny-mellon-remote-ezutwncf"
                   },
                   {
                            "title": "Junior Java Full Stack Developer",
                            "company": "Broadridge",
                            "salary": "9 000 - 11 000 PLN",
                            "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-full-stack-developer-broadridge-gdansk-af8ukogy"
                   }
                   ]
                   """.trim();
    }




}
