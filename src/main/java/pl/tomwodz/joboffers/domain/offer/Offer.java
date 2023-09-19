package pl.tomwodz.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "offers")
class Offer {

    @Id
    private String id;
    @Field("company") private String companyName;
    @Field("position") private String position;
    @Field("salary") private String salary;
    @Field("offerUrl") @Indexed(unique = true) private String offerUrl;

    public Offer(String companyName, String position, String salary, String offerUrl) {
        this.companyName = companyName;
        this.position = position;
        this.salary = salary;
        this.offerUrl = offerUrl;
    }
}
