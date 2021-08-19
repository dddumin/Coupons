package springtest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.Cascade;
import springtest.util.StringUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String name;

    @NonNull
    @Column(nullable = false, unique = true)
    private String login;

    @NonNull
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    private String hash;

    @Column(nullable = false)
    @JsonIgnore
    private String salt = StringUtil.generateSalt();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Coupon> coupons = new ArrayList<>();

    public void setPassword(String password) {
        this.password = DigestUtils.md5Hex(DigestUtils.md5Hex(password) + DigestUtils.md5Hex(this.salt));
    }
}
