package org.parabot.api.translations;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.parabot.api.io.WebUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * @author JKetelaar
 */
public class TranslationHelper {
    public static final String DEFAULT_LANGUAGE = "en";
    private static HashMap<String, TranslationLanguage> availableLanguages;
    private static String currentLanguage = null;

    static {
        availableLanguages = new HashMap<>();
        availableLanguages.put("en", new TranslationLanguage("en", "English"));
        availableLanguages.put("nl", new TranslationLanguage("nl", "Dutch"));
        availableLanguages.put("pt", new TranslationLanguage("pt", "Portuguese"));
    }

    public static File getTranslationFile(String languageKey){
        try {
            return new File(TranslationHelper.class.getResource("/storage/languages/strings_" + languageKey + ".json").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, String> fileToHashmap(HashMap<String, String> hashMap, File file){
        try {
            JSONObject object = (JSONObject) WebUtil.getJsonParser().parse(new FileReader(file));
            for (Object s : object.keySet()){
                hashMap.put((String) s, (String) object.get(s));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    public static TranslationLanguage getLanguage(String key){
        for (String lang : availableLanguages.keySet()) {
            if (lang.equalsIgnoreCase(key)){
                return availableLanguages.get(lang);
            }
        }
        return null;
    }

    public static HashMap<String, TranslationLanguage> getAvailableLanguages() {
        return availableLanguages;
    }

    public static void setCurrentLanguage(String key){
        for (String lang : availableLanguages.keySet()) {
            if (lang.equalsIgnoreCase(key)) {
                currentLanguage = lang;
                return;
            }
        }

        currentLanguage = DEFAULT_LANGUAGE;
    }

    public static String translate(String translationKey){
        if (currentLanguage == null){
            setCurrentLanguage(DEFAULT_LANGUAGE);
        }

        String translation;
        if ((translation = availableLanguages.get(currentLanguage).getTranslations().get(translationKey)) == null){
            translation = availableLanguages.get(DEFAULT_LANGUAGE).getTranslations().get(translationKey);
        }
        return translation;
    }
}
