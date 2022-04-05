package ru.bagration.spring.service;

public interface ErrorMessageService {

    String findByKeyAndLang(String key, String lang);

}
