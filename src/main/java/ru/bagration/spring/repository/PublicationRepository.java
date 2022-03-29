package ru.bagration.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bagration.spring.entity.Publication;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long>, JpaSpecificationExecutor<Publication> {

    long countAllByAuthorId(long authorId);

    @Query("select distinct p.themeId from Publication p where p.authorId = :authorId")
    List<Long> findUniqueThemeIdsByAuthorId(long authorId);

    long countAllByAuthorIdAndThemeId(long authorId, long themeId);

}
