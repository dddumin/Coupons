package springtest.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springtest.exceptions.IncorrectHashException;
import springtest.model.*;
import springtest.repository.CompanyRepository;
import springtest.repository.CouponRepository;
import springtest.util.StringUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CouponRepository couponRepository;
    private CompanyRepository companyRepository;

    @Autowired
    public void setCompanyRepository(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Autowired
    public void setCouponRepository(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public String login(String login, String password) {
        Company company = this.companyRepository.findByLogin(login);
        if (company != null) {
            String saltPass = DigestUtils.md5Hex(DigestUtils.md5Hex(password) + DigestUtils.md5Hex(company.getSalt()));
            if (saltPass.equals(company.getPassword())) {
                String hash = StringUtil.generateHash();
                company.setHash(hash);
                this.companyRepository.save(company);
                return hash;
            }
        }
        return null;
    }

    @Override
    public void addCoupon(String hash, Coupon coupon) throws IncorrectHashException {
        Company company = this.companyRepository.findByHash(hash);
        if (company != null){
            coupon.setCompany(company);
            this.couponRepository.save(coupon);
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }


    @Override
    public void updateCoupon(String hash, Coupon coupon) throws IncorrectHashException {
        Company company = this.companyRepository.findByHash(hash);
        if (company != null){
            coupon.setCompany(company);
            this.couponRepository.save(coupon);
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public void deleteCoupon(String hash, Long couponId) throws IncorrectHashException {
        if (this.companyRepository.findByHash(hash) != null) {
            this.couponRepository.deleteById(couponId);
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public List<Coupon> getCompanyCoupons(String hash, Category category) throws IncorrectHashException {
        Company company = this.companyRepository.findByHash(hash);
        if (company != null) {

            return company.getCoupons().stream().filter(coupon -> coupon.getCategory().equals(category)).collect(Collectors.toList());
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public List<Coupon> getCompanyCoupons(String hash, double maxPrice) throws IncorrectHashException {
        Company company = this.companyRepository.findByHash(hash);
        if (company != null) {

            return company.getCoupons().stream().filter(coupon -> coupon.getPrice() <= maxPrice).collect(Collectors.toList());
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public List<Coupon> getCompanyCoupons(String hash) throws IncorrectHashException {
        Company company = this.companyRepository.findByHash(hash);
        if (company != null) {
            return company.getCoupons();
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public Coupon getCouponById(String hash, Long id) throws IncorrectHashException {
        Company company = this.companyRepository.findByHash(hash);
        if (company != null) {
            return company.getCoupons().stream().filter(coupon -> coupon.getId().equals(id)).findFirst().orElse(null);
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public Company getCompanyDetails(String hash) throws IncorrectHashException {
        Company company = this.companyRepository.findByHash(hash);
        if (company != null) {
            return company;
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

}
