package springtest.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springtest.exceptions.IncorrectHashException;
import springtest.model.Category;
import springtest.model.Coupon;
import springtest.model.Customer;
import springtest.repository.CouponRepository;
import springtest.repository.CustomerRepository;
import springtest.util.StringUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CouponRepository couponRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public void setCouponRepository(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public String login(String login, String password) {
        Customer customer = this.customerRepository.findByLogin(login);
        if (customer != null) {
            String saltPass = DigestUtils.md5Hex(DigestUtils.md5Hex(password) + DigestUtils.md5Hex(customer.getSalt()));
            if (saltPass.equals(customer.getPassword())) {
                String hash = StringUtil.generateHash();
                customer.setHash(hash);
                this.customerRepository.save(customer);
                return hash;
            }
        }
        return null;
    }

    @Override
    public void purchaseCoupon(String hash, Coupon coupon) throws IncorrectHashException {
        Customer customer = this.customerRepository.findByHash(hash);
        if (customer != null) {
            //для теста
            Coupon coupon1 = this.couponRepository.findById(coupon.getId()).get();
            coupon1.addCustomer(customer);
            this.couponRepository.save(coupon1);

        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public List<Coupon> getCustomerCoupons(String hash, Category category) throws IncorrectHashException {
        Customer customer = this.customerRepository.findByHash(hash);
        if (customer != null) {
            return customer.getCoupons().stream().filter(coupon -> coupon.getCategory().equals(category)).collect(Collectors.toList());
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public List<Coupon> getCustomerCoupons(String hash) throws IncorrectHashException {
        Customer customer = this.customerRepository.findByHash(hash);
        if (customer != null) {
            return customer.getCoupons();
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public List<Coupon> getCustomerCoupons(String hash, double maxPrice) throws IncorrectHashException {
        Customer customer = this.customerRepository.findByHash(hash);
        if (customer != null) {
            return customer.getCoupons().stream().filter(coupon -> coupon.getPrice() <= maxPrice).collect(Collectors.toList());
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public Customer getCustomerDetails(String hash) throws IncorrectHashException {
        Customer customer = this.customerRepository.findByHash(hash);
        if (customer != null) {
            return customer;
        }
        throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }
}
