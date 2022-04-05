package ru.bagration.spring.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.bagration.spring.dto.publication.PublicationDto;
import ru.bagration.spring.entity.Publication;
import ru.bagration.spring.exception.definition.BadRequestException;
import ru.bagration.spring.repository.AuthorRepository;
import ru.bagration.spring.repository.PublicationRepository;
import ru.bagration.spring.repository.ThemeRepository;
import ru.bagration.spring.service.PublicationService;
import ru.bagration.spring.utils.Utility;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static ru.bagration.spring.utils.ResponseData.response;


@Service
@RequiredArgsConstructor
public class PublicationServiceImplementation implements PublicationService {

    private final PublicationRepository publicationRepository;
    private final ThemeRepository themeRepository;
    private final AuthorRepository authorRepository;

    public ResponseEntity<?> getAllPublications(){

        var publications = publicationRepository.findAll();
        var dtoS = publications.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return response(dtoS);
    }

    public ResponseEntity<?> getData(String authorName, String themeName, Pageable pageable){
        var authorNameParam = "%" + authorName.trim().toLowerCase() + "%";
        var themeNameParam = "%" + themeName.trim().toLowerCase() + "%";

        var authorIds = authorRepository.findIdsByName(authorNameParam);
        var themeIds = themeRepository.getIdsByName(themeNameParam);

        var publicationsPage = publicationRepository
                .findAll(getFilteringSpecification(authorIds, themeIds), pageable);

        var data = new HashMap<String, Object>();
        data.put("page", publicationsPage.getNumber());
        data.put("totalPages", publicationsPage.getTotalPages());
        data.put("size", publicationsPage.getSize());
        data.put("totalElements", publicationsPage.getTotalElements());
        data.put("content", publicationsPage.getContent().stream().map(this::mapToDto).collect(Collectors.toList()));

        return response(data);
    }

    public ResponseEntity<?> createPublication(String authorId, String themeId, String title, String content){

        if(!authorRepository.existsByPublicId(authorId))
            throw new BadRequestException("no such author", HttpStatus.BAD_REQUEST);

        if(!themeRepository.existsByPublicId(themeId))
            throw new BadRequestException("no such theme", HttpStatus.BAD_REQUEST);

        var publication = new Publication();
        publication.setPublicId(Utility.generateUuid());
        publication.setAuthorId(authorRepository.getIdByPublicId(authorId));
        publication.setThemeId(themeRepository.getIdByPublicId(themeId));
        publication.setTitle(title);
        publication.setContent(content);

        publicationRepository.save(publication);

        return response(Collections.singletonMap("message", "success"));
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
        if(authorRepository.existsById(publication.getAuthorId())){
            var author = authorRepository.getById(publication.getAuthorId());
            dto.setAuthor(author.getFirstName() + " " + author.getLastName());
            dto.setAuthorId(author.getPublicId());
        }

        dto.setTitle(publication.getTitle());

        if(themeRepository.existsById(publication.getThemeId())){
            var theme = themeRepository.getById(publication.getThemeId());
            dto.setTheme(theme.getName());
            dto.setThemeId(theme.getPublicId());
        }

        dto.setContent(publication.getContent());
        return dto;
    }

}
