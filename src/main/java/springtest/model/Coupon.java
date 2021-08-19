package springtest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "coupons", uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "company_id"})})
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Category category;

    @NonNull
    @Column(nullable = false)
    private String title;

    @NonNull
    @Column(nullable = false)
    private String description;

    @NonNull
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @NonNull
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @NonNull
    @Column(nullable = false)
    private int amount;

    @NonNull
    @Column(nullable = false)
    private double price;

    @Lob
    @NonNull
    @Column(nullable = false)
    private String imagePath;


    //@NonNull
    @ManyToOne
    @JoinColumn(nullable = false, name = "company_id")
    private Company company;

    @ManyToMany
    @JoinTable(name = "customers_coupons", joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"customer_id", "coupon_id"})})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Customer> customers = new ArrayList<>();
    
    public void addCustomer(Customer customer) {
            this.customers.add(customer);
            this.amount--;
    }
}
