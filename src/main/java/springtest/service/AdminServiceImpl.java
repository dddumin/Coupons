package springtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springtest.exceptions.IncorrectHashException;
import springtest.model.Admin;
import springtest.model.Company;
import springtest.model.Customer;
import springtest.repository.AdminRepository;
import springtest.repository.CompanyRepository;
import springtest.repository.CustomerRepository;
import springtest.util.StringUtil;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private AdminRepository adminRepository;
    private CompanyRepository companyRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public void setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Autowired
    public void setCompanyRepository(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public String login(String login, String password) {
        Admin admin = this.adminRepository.findByLoginAndPassword(login, password);
        if (admin != null) {
            String hash = StringUtil.generateHash();
            admin.setHash(hash);
            this.adminRepository.save(admin);
            return hash;
        }
        return null;
    }

    @Override
    public void addCompany(String hash, Company company) throws IncorrectHashException {
        if (this.adminRepository.findByHash(hash) != null) {
            this.companyRepository.save(company);
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public void updateCompany(String hash, Company company) throws IncorrectHashException {
        if (this.adminRepository.findByHash(hash) != null) {
            this.companyRepository.save(company);
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public void deleteCompany(String hash, Long companyId) throws IncorrectHashException {
        if (this.adminRepository.findByHash(hash) != null) {
            this.companyRepository.deleteById(companyId);
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public List<Company> getAllCompanies(String hash) throws IncorrectHashException {
        if (this.adminRepository.findByHash(hash) != null) {
            return this.companyRepository.findAll();
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public Company getOneCompany(String hash, Long companyId) throws IncorrectHashException {
        if (this.adminRepository.findByHash(hash) != null) {
            return this.companyRepository.findById(companyId).orElse(null);
        } else
            throw new IncorrectHashException("Авторизуйтесь чтобы выполнить запрос!!!");
    }

    @Override
    public void addCustomer(String hash, Customer customer) throws IncorrectHashException {
        if (this.adminRepository.findByHash(hash) != null) {
            this.customerRepository.save(customer);
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public void updateCustomer(String hash, Customer customer) throws IncorrectHashException {
        if (this.adminRepository.findByHash(hash) != null) {
            this.customerRepository.save(customer);
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public void deleteCustomer(String hash, Long customerId) throws IncorrectHashException {
        if (this.adminRepository.findByHash(hash) != null) {
            this.customerRepository.deleteById(customerId);
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public List<Customer> getAllCustomers(String hash) throws IncorrectHashException {
        if (this.adminRepository.findByHash(hash) != null) {
            return this.customerRepository.findAll();
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }

    @Override
    public Customer getOneCustomer(String hash, Long customerId) throws IncorrectHashException {
        if (this.adminRepository.findByHash(hash) != null) {
            return this.customerRepository.findById(customerId).orElse(null);
        } else
            throw new IncorrectHashException("Авторизуйтесь, чтобы выполнить запрос!!!");
    }
}
