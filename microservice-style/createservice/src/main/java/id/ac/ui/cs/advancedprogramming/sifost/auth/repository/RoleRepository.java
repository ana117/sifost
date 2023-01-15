package id.ac.ui.cs.advancedprogramming.sifost.auth.repository;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public List<Role> findByName(String name);
}
