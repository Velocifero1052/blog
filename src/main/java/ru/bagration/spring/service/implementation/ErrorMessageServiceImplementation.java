package ru.bagration.spring.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bagration.spring.repository.ErrorMessageRepository;
import ru.bagration.spring.service.ErrorMessageService;

@Service
@RequiredArgsConstructor
public class ErrorMessageServiceImplementation implements ErrorMessageService {

    private final ErrorMessageRepository errorMessageRepository;

    public String findByKeyAndLang(String key, String lang){

        var errorMessageOptional = errorMessageRepository.findByKeyAndLang(key, lang);

        if(errorMessageOptional.isPresent()){
            var message = errorMessageOptional.get();
            return message.getValue();
        }else{
            return key;
        }

    }


}
