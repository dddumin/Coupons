package springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springtest.exceptions.IncorrectHashException;
import springtest.model.Company;
import springtest.model.Coupon;
import springtest.service.ShopService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/shop")
public class ShopController {
    private ShopService shopService;

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping(path = "/companies", produces = "application/json")
    public List<Company> getCompanies(@CookieValue("hash") String hash) throws IncorrectHashException {
        return this.shopService.getAllCompanies(hash);
    }

    @GetMapping(path = "/coupons", produces = "application/json")
    public List<Coupon> getCoupons(@CookieValue("hash") String hash) throws IncorrectHashException {
        return this.shopService.getCoupons(hash);
    }

    @GetMapping(path = "/coupon/{id}", produces = "application/json")
    public Coupon getCoupon(@CookieValue("hash") String hash, @PathVariable Long id) throws IncorrectHashException {
        return this.shopService.getCoupon(hash, id);
    }

    @GetMapping(path = "/company/{id}", produces = "application/json")
    public Company getCompany(@CookieValue("hash") String hash, @PathVariable Long id) throws IncorrectHashException {
        return this.shopService.getCompany(hash, id);
    }


}
