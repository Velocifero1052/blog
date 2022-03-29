package ru.bagration.spring.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.bagration.spring.dto.PublicationDto;
import ru.bagration.spring.entity.Publication;
import ru.bagration.spring.repository.AuthorRepository;
import ru.bagration.spring.repository.PublicationRepository;
import ru.bagration.spring.repository.ThemeRepository;
import ru.bagration.spring.service.PublicationService;
import ru.bagration.spring.utils.ResponseData;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.bagration.spring.utils.ResponseData.ok;

@Service
@RequiredArgsConstructor
public class PublicationServiceImplementation implements PublicationService {

    private final PublicationRepository publicationRepository;
    private final ThemeRepository themeRepository;
    private final AuthorRepository authorRepository;

    public ResponseData getAllPublications(){

        var publications = publicationRepository.findAll();
        var dtoS = publications.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ok(dtoS);
    }

    public ResponseEntity<?> getData(String authorName, String themeName, Pageable pageable){
        var authorNameParam = "%" + authorName.trim().toLowerCase() + "%";
        var themeNameParam = "%" + themeName.trim().toLowerCase() + "%";

        var authorIds = authorRepository.findIdsByName(authorNameParam);
        var themeIds = themeRepository.getIdsByName(themeNameParam);

        var publicationsPage = publicationRepository
                .findAll(getFilteringSpecification(authorIds, themeIds), pageable);

        return ResponseEntity.ok(ok(publicationsPage));
    }


    private Specification<Publication> getFilteringSpecification(List<Long> authorIds, List<Long> themeIds){
        return ((root, query, criteriaBuilder) -> {
            /*
            * select * from publications where author_id in (1, 2, 3, 4, 5) and theme_id in (1, 2, 3, 4);
            */
            var predicates = new ArrayList<Predicate>();
            var authorPredicates = new ArrayList<Predicate>();
            var themePredicates = new ArrayList<Predicate>();

            for(var id: authorIds){
                authorPredicates.add(
                        criteriaBuilder.equal(root.get("authorId"), id)
                );
            }

            predicates.add(criteriaBuilder.or(authorPredicates.toArray(new Predicate[0])));

            for(var id: themeIds){
                themePredicates.add(
                        criteriaBuilder.equal(root.get("themeId"), id)
                );
            }
            predicates.add(criteriaBuilder.or(themePredicates.toArray(new Predicate[0])));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    private PublicationDto mapToDto(Publication publication){
        var dto = new PublicationDto();
        dto.setId(publication.getPublicId());
        dto.setAuthor("Michael Jackson");
        dto.setAuthorId(UUID.randomUUID().toString());
        dto.setTitle(publication.getTitle());
        dto.setTheme("War");
        dto.setContent(publication.getContent());
        return dto;
    }

}
