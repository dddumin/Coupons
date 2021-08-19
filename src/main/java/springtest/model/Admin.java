package springtest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import springtest.util.StringUtil;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "admins")
public class Admin {
    // hash генерировать при авторизации и перезаписывать при каждой.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String login;

    @NonNull
    @Column(nullable = false)
    private String password;

    private String hash;

    @Column(nullable = false)
    private String salt = StringUtil.generateSalt();

    public Admin(@NonNull String login, @NonNull String password) {
        this.login = login;
        this.password = DigestUtils.md5Hex(DigestUtils.md5Hex(password) + DigestUtils.md5Hex(this.salt));
    }
}
