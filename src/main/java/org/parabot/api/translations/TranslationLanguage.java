package org.parabot.api.translations;

import java.io.File;
import java.util.HashMap;

/**
 * @author JKetelaar
 */
public class TranslationLanguage {

    private String key;
    private String name;
    private HashMap<String, String> translations;

    public TranslationLanguage(String key, String name) {
        this.key = key;
        this.name = name;
        this.translations = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public HashMap<String, String> getTranslations() {
        if (translations.size() <= 0){
            translations = TranslationHelper.fileToHashmap(translations, this.key);
        }
        return translations;
    }

    public String getKey() {
        return key;
    }
}
