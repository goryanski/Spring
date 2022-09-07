package app.EasyFoodAPI.repositories;

import app.EasyFoodAPI.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Role, Integer> {
    Role findById(int id);
    Role findByName(String name);
}