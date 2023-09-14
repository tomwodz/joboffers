package pl.tomwodz.joboffers.domain.loginandregister;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {

    Long id;

    String username;

    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
