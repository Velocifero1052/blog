package ru.bagration.spring.utils;

public class LangContext {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setLang(String lang) {
        CONTEXT.set(lang);
    }

    public static String getLang() {
        return CONTEXT.get();
    }

}
