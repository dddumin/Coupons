package springtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springtest.model.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByHash(String hashCustomer);
    Customer findByLogin(String login);

}
