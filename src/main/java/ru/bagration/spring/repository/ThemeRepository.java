package ru.bagration.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bagration.spring.entity.Theme;

import java.util.List;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    @Query("select t.name from Theme t where t.id = :id")
    Object findNameById(Long id);

    @Query("select t.id from Theme t where lower(t.name) like :name")
    List<Long> getIdsByName(String name);

    boolean existsByNameIgnoreCase(String name);

}
