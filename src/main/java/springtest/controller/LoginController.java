package springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springtest.model.LoginResponse;
import springtest.service.AdminService;
import springtest.service.CompanyService;
import springtest.service.CustomerService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", maxAge = 3600)
public class LoginController {
    private AdminService adminService;
    private CompanyService companyService;
    private CustomerService customerService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(path = "/{login}/{password}", produces = "application/json")
    public LoginResponse login(HttpServletResponse response, @PathVariable String login, @PathVariable String password) {
        String hash = this.adminService.login(login, password);
        LoginResponse loginResponse = null;
        if (hash != null) {
            loginResponse = new LoginResponse(true, "admin");
        }
        if (hash == null) {
            hash = this.companyService.login(login, password);
            if (hash != null) {
                loginResponse = new LoginResponse(true, "company");
            }
        }
        if (hash == null) {
            hash = this.customerService.login(login, password);
            if (hash != null) {
                loginResponse = new LoginResponse(true, "customer");
            }
        }
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
            return loginResponse;
        }
        return null;
    }
}
