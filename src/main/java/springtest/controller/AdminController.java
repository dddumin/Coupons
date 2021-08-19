package springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springtest.exceptions.IncorrectHashException;
import springtest.model.Company;
import springtest.model.Customer;
import springtest.service.AdminService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class AdminController extends ClientController {
    private AdminService adminService;

    //свой метод авторизации
    //свой метод регистрации

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    @PostMapping(path = "/{login}/{password}")
    public boolean login(HttpServletResponse response, @PathVariable String login, @PathVariable String password) {
        String hash = this.adminService.login(login, password);
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

    @PostMapping(path = "/company", consumes = "application/json")
    public void addCompany(@CookieValue("hash") String hash, @RequestBody Company company) {
        try {
            this.adminService.addCompany(hash, company);
        } catch (IncorrectHashException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), e.getMessage(), e);
        }
    }

    @PutMapping(path = "/company", consumes = "application/json")
    public void updateCompany(@CookieValue("hash") String hash, @RequestBody Company company) {
        try {
            this.adminService.updateCompany(hash, company);
        } catch (IncorrectHashException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), e.getMessage(), e);
        }
    }

    @DeleteMapping(path = "/company/{id}")
    public void deleteCompany(@CookieValue("hash") String hash, @PathVariable Long id) {
        try {
            this.adminService.deleteCompany(hash, id);
        } catch (IncorrectHashException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), e.getMessage(), e);
        }
    }

    @GetMapping(path = "/company/{id}", produces = "application/json")
    public Company getCompany(@CookieValue("hash") String hash, @PathVariable Long id) {
        try {
            return this.adminService.getOneCompany(hash, id);
        } catch (IncorrectHashException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), e.getMessage(), e);
        }

    }

    @GetMapping(path = "/companies", produces = "application/json")
    public List<Company> listCompanies( HttpServletResponse response, @CookieValue("hash") String hash) {
        try {
            return this.adminService.getAllCompanies(hash);
        } catch (IncorrectHashException e) {
            response.setStatus(500);
            throw new ResponseStatusException(HttpStatus.valueOf(500), e.getMessage(), e);
        }
    }

    @PostMapping(path = "/customer", consumes = "application/json")
    public void addCustomer(@CookieValue("hash") String hash, @RequestBody Customer customer) {
        try {
            this.adminService.addCustomer(hash, customer);
        } catch (IncorrectHashException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), e.getMessage(), e);
        }
    }

    @PutMapping(path = "/customer", consumes = "application/json")
    public void updateCustomer(@CookieValue("hash") String hash, @RequestBody Customer customer) {
        try {
            this.adminService.updateCustomer(hash, customer);
        } catch (IncorrectHashException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), e.getMessage(), e);
        }
    }

    @DeleteMapping(path = "/customer/{id}")
    public void deleteCustomer(@CookieValue("hash") String hash, @PathVariable Long id) {
        try {
            this.adminService.deleteCustomer(hash, id);
        } catch (IncorrectHashException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.valueOf(500), e.getMessage(), e);
        }
    }

    @GetMapping(path = "/customers", produces = "application/json")
    public List<Customer> listCustomer(@CookieValue("hash") String hash) {
        try {
            return this.adminService.getAllCustomers(hash);
        } catch (IncorrectHashException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), e.getMessage(), e);
        }
    }

    @GetMapping(path = "/customer/{id}", produces = "application/json")
    public Customer getCustomer(@CookieValue("hash") String hash, @PathVariable Long id) {
        try {
            return this.adminService.getOneCustomer(hash, id);
        } catch (IncorrectHashException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), e.getMessage(), e);
        }
    }
}
