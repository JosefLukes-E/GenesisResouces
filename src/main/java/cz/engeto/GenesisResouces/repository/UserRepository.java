package cz.engeto.GenesisResouces.repository;

import cz.engeto.GenesisResouces.dto.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByPersonId(String personId);

    @Modifying
    @Transactional
    @Query(value="update engeto.users u set u.name = :name, u.surname= :surname where u.id = :id",nativeQuery = true)
    void updateNameSurname(@Param(value="id") Long id, @Param(value="name") String name,@Param(value="surname") String surname );
}
