package springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springtest.exceptions.IncorrectHashException;
import springtest.model.Category;
import springtest.model.Company;
import springtest.model.Coupon;
import springtest.service.CompanyService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CompanyController extends ClientController{
    private CompanyService companyService;

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    @PostMapping(path = "/{login}/{password}")
    public boolean login(HttpServletResponse response, @PathVariable String login, @PathVariable String password) {
        String hash = this.companyService.login(login, password);
        if (hash != null) {
            // create a cookie
            Cookie cookie = new Cookie("hash", hash);

            // expires in 30 minutes
            cookie.setMaxAge(30 * 60);

            // optional properties
            //cookie.setSecure(true);
            //cookie.setHttpOnly(true);
            cookie.setPath("/");

            // add cookie to response
            response.addCookie(cookie);

            return true;
        }
        return false;
    }

    @PostMapping(consumes = "application/json")
    public void addCoupon(@CookieValue("hash") String hash, @RequestBody Coupon coupon) throws IncorrectHashException {
        this.companyService.addCoupon(hash, coupon);
    }

    @PutMapping(consumes = "application/json")
    public void updateCoupon(@CookieValue("hash") String hash, @RequestBody Coupon coupon) throws IncorrectHashException {
        this.companyService.updateCoupon(hash, coupon);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCoupon(@CookieValue("hash") String hash, @PathVariable Long id) throws IncorrectHashException {
        this.companyService.deleteCoupon(hash, id);
    }

    @GetMapping(path = "/coupon/{id}", produces = "application/json")
    public Coupon getCoupon(@CookieValue("hash") String hash, @PathVariable Long id) throws IncorrectHashException {
        return this.companyService.getCouponById(hash, id);
    }

    @GetMapping(value = "/coupons/category/{category}", produces = "application/json")
    public List<Coupon> getCouponsByCategory(@CookieValue("hash") String hash, @PathVariable Category category) throws IncorrectHashException {
        return this.companyService.getCompanyCoupons(hash, category);
    }

    @GetMapping(value = "/coupons" , produces = "application/json")
    public List<Coupon> getCoupons(@CookieValue("hash") String hash) throws IncorrectHashException {
        return this.companyService.getCompanyCoupons(hash);
    }

    @GetMapping(value = "/coupons/{price}" , produces = "application/json")
    public List<Coupon> getCoupons(@CookieValue("hash") String hash, @PathVariable Double price) throws IncorrectHashException {
        return this.companyService.getCompanyCoupons(hash, price);
    }

    @GetMapping(produces = "application/json")
    public Company getCompany(@CookieValue("hash") String hash) throws IncorrectHashException {
        return this.companyService.getCompanyDetails(hash);
    }
}
