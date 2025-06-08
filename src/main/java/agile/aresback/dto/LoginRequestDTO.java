package agile.aresback.dto;

import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@Data
public class LoginRequestDTO {
    private String username;
    private String password;

}
