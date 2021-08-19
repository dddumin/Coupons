package springtest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import springtest.util.StringUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String firstName;

    @NonNull
    @Column(nullable = false)
    private String lastName;

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

    @ManyToMany
    @JoinTable(name = "customers_coupons", joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"customer_id", "coupon_id"})})
    private List<Coupon> coupons = new ArrayList<>();

    public void setPassword(String password) {
        this.password = DigestUtils.md5Hex(DigestUtils.md5Hex(password) + DigestUtils.md5Hex(this.salt));
    }
}
