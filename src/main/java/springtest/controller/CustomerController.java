package springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springtest.exceptions.IncorrectHashException;
import springtest.model.Category;
import springtest.model.Coupon;
import springtest.model.Customer;
import springtest.service.CustomerService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/customer")
public class CustomerController extends ClientController{
    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    @PostMapping(path = "/{login}/{password}")
    public boolean login(HttpServletResponse response, @PathVariable String login, @PathVariable String password) {
        String hash = this.customerService.login(login, password);
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
    public void purchaseCoupon (@CookieValue("hash") String hash, @RequestBody Coupon coupon) throws IncorrectHashException {
        this.customerService.purchaseCoupon(hash, coupon);
    }

    @GetMapping(value = "/coupons/category/{category}", produces = "application/json")
    public List<Coupon> getCouponsByCategory(@CookieValue("hash") String hash, @PathVariable Category category) throws IncorrectHashException {
        return this.customerService.getCustomerCoupons(hash, category);
    }

    @GetMapping(value = "/coupons" , produces = "application/json")
    public List<Coupon> getCoupons(@CookieValue("hash") String hash) throws IncorrectHashException {
        return this.customerService.getCustomerCoupons(hash);
    }

    @GetMapping(value = "/coupons/{price}" , produces = "application/json")
    public List<Coupon> getCoupons(@CookieValue("hash") String hash, @PathVariable Double price) throws IncorrectHashException {
        return this.customerService.getCustomerCoupons(hash, price);
    }

    @GetMapping(produces = "application/json")
    public Customer getCustomer(@CookieValue("hash") String hash) throws IncorrectHashException {
        return this.customerService.getCustomerDetails(hash);
    }
}
