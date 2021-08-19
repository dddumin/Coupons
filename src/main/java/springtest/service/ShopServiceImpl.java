package springtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springtest.exceptions.IncorrectHashException;
import springtest.model.Company;
import springtest.model.Coupon;
import springtest.model.Customer;
import springtest.repository.CompanyRepository;
import springtest.repository.CouponRepository;
import springtest.repository.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {
    private CustomerRepository customerRepository;
    private CompanyRepository companyRepository;
    private CouponRepository couponRepository;

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setCompanyRepository(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Autowired
    public void setCouponRepository(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public List<Company> getAllCompanies(String hash) throws IncorrectHashException {
        if (this.customerRepository.findByHash(hash) != null) {
            return this.companyRepository.findAll();
        }
        throw new  IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public List<Coupon> getCoupons(String hash) throws IncorrectHashException {
        if ( this.customerRepository.findByHash(hash) != null) {
            return this.couponRepository.findAll().stream().filter(coupon -> coupon.getAmount() > 0).collect(Collectors.toList());
        }
        throw new  IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    public Coupon getCoupon(String hash, Long id) throws IncorrectHashException {
        if (this.customerRepository.findByHash(hash) != null) {
            return this.couponRepository.findById(id).orElse(null);
        }
        throw new  IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public Company getCompany(String hash, Long id) throws IncorrectHashException {
        if (this.customerRepository.findByHash(hash) != null) {
            return this.companyRepository.findById(id).orElse(null);
        }
        throw new  IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }
}
