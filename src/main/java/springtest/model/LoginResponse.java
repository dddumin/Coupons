package springtest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class LoginResponse {
    @NonNull
    private boolean auth;
    @NonNull
    private String type;
}
