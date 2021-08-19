package springtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springtest.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByHash(String hash);
    Company findByLogin(String login);
}
