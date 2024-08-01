package wind.laguna.mycloud5.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String login;
    private String password;

    public String getUsername() {
        return login;
    }
}
