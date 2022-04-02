package ru.bagration.spring.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.bagration.spring.dto.AuthorDto;
import ru.bagration.spring.dto.AuthorListDto;
import ru.bagration.spring.entity.Author;
import ru.bagration.spring.exception.definition.BadRequestException;
import ru.bagration.spring.repository.AuthorRepository;
import ru.bagration.spring.repository.PublicationRepository;
import ru.bagration.spring.repository.ThemeRepository;
import ru.bagration.spring.service.AuthorService;
import ru.bagration.spring.utils.Utility;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import static ru.bagration.spring.utils.ResponseData.response;
import static ru.bagration.spring.utils.ResponseData.responseBadRequest;

@Service
@RequiredArgsConstructor
public class AuthorServiceImplementation implements AuthorService {

    private final AuthorRepository authorRepository;
    private final PublicationRepository publicationRepository;
    private final ThemeRepository themeRepository;

    public ResponseEntity<?> createAuthor(String firstName, String lastName){

        if(authorRepository.existsByFirstNameIgnoreCaseOrLastNameIgnoreCase(firstName, lastName))
            throw new BadRequestException("such author already exists", HttpStatus.CONFLICT);

        var author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setPublicId(Utility.generateUuid());
        authorRepository.save(author);
        return response("author was successfully created");
    }

    public ResponseEntity<?> getAuthorsList(String name, String firstName, String lastName,
                                            Boolean detailed, Pageable pageable){
        var authorsList = authorRepository
                .findAll(getFilteringSpecification(name, firstName, lastName, detailed), pageable)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return response(authorsList);
    }

    public ResponseEntity<?> getDetailsById(String id){
        var authorOptional = authorRepository.findByPublicId(id);
        if(authorOptional.isPresent()){
            return response(mapToAuthorDto(authorOptional.get()));
        }else return responseBadRequest("no such author");
    }

    private AuthorListDto mapToDto(Author author){
        var dto = new AuthorListDto();
        dto.setId(author.getPublicId());
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());
        return dto;
    }

    private AuthorDto mapToAuthorDto(Author author){
        var dto = new AuthorDto();
        dto.setId(author.getPublicId());
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());
        dto.setNumberOfPublications(
                publicationRepository
                        .countAllByAuthorId(
                                author.getId()
                        ));

        var perTheme = new HashMap<String, Long>();
        var themeIds = publicationRepository.findUniqueThemeIdsByAuthorId(author.getId());

        for(var themeId: themeIds){
            var themeName = (String)themeRepository.findNameById(themeId);
            var count = publicationRepository.countAllByAuthorIdAndThemeId(author.getId(), themeId);
            perTheme.put(themeName, count);
        }
        dto.setPerTheme(perTheme);
        return dto;
    }

    private Specification<Author> getFilteringSpecification(String name, String firstName, String lastName, Boolean detailed){
        return ((root, query, criteriaBuilder) -> {
            if(!detailed) {
                var queryString = "%" + name.toLowerCase() + "%";

                var firstNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("firstName")),
                        queryString);

                var lastNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("lastName")),
                        queryString);

                return criteriaBuilder.or(firstNamePredicate, lastNamePredicate);
            }
            else{
                var predicates = new ArrayList<Predicate>();

                if(firstName != null){
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
                            "%" + firstName.toLowerCase() + "%"));
                }

                if(lastName != null){
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
                            "%" + lastName.toLowerCase() + "%"));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        });
    }

}
