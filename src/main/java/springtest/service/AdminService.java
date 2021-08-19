package springtest.service;

import springtest.exceptions.IncorrectHashException;
import springtest.model.Company;
import springtest.model.Customer;

import java.util.List;

public interface AdminService extends ClientService {
    void addCompany(String hash, Company company) throws IncorrectHashException;
    void updateCompany(String hash, Company company) throws IncorrectHashException;
    void deleteCompany(String hash, Long companyId) throws IncorrectHashException;
    List<Company> getAllCompanies(String hash) throws IncorrectHashException;
    Company getOneCompany(String hash, Long companyId) throws IncorrectHashException;
    void addCustomer(String hash, Customer customer) throws IncorrectHashException;
    void updateCustomer(String hash, Customer customer) throws IncorrectHashException;
    void deleteCustomer(String hash, Long customerId) throws IncorrectHashException;
    List<Customer> getAllCustomers(String hash) throws IncorrectHashException;
    Customer getOneCustomer(String hash, Long customerId) throws IncorrectHashException;
}
