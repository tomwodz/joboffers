package pl.tomwodz.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class Offer {
    private Long id;
    private String companyName;
    private String position;
    private String salary;
    private String offerUrl;

    public Offer(String companyName, String position, String salary, String offerUrl) {
        this.companyName = companyName;
        this.position = position;
        this.salary = salary;
        this.offerUrl = offerUrl;
    }
}
