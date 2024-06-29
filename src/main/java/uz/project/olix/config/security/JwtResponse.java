package uz.project.olix.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String accessToken;
    private LocalDateTime dateTime=LocalDateTime.now();

    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
