package kde.backend.repository;

import kde.backend.domain.Readdress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface ReaddressRepository extends JpaRepository<Readdress, Long> {
    List<Readdress> findByZip(String id);

    List<Readdress> findByAddrContaining(String addr);
}
