package wind.laguna.mycloud5.responces;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    @JsonProperty("auth-token")
    private String jwtToken;
}
