package org.parabot.api.translations;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.parabot.api.Configuration;
import org.parabot.api.io.Directories;
import org.parabot.api.io.WebUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
        getTranslations();
    }


    public static void getTranslations() {
        try {
            JSONObject object = (JSONObject) WebUtil.getJsonParser().parse(WebUtil.getContents(Configuration.LIST_TRANSLATIONS));
            JSONObject languages = (JSONObject) object.get("languages");
            for (Object key : languages.keySet()) {
                String languageKey = (String) key;
                String languageName = languages.get(languageKey).toString();

                downloadJson(languageKey);

                availableLanguages.put(languageKey, new TranslationLanguage(languageKey, languageName));
            }
        } catch (MalformedURLException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void downloadJson(String key) {
        File keyFile = new File(Directories.getLanguagesPath() + "/" + key + ".json");
        try {
            if (!keyFile.exists()) {
                WebUtil.downloadFile(new URL(String.format(Configuration.GET_TRANSLATION, key)), keyFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> fileToHashmap(HashMap<String, String> hashMap, String key) {
        File languageFile = new File(Directories.getLanguagesPath() + "/" + key + ".json");
        if (!languageFile.exists()) {
            getTranslations();
        }
        try {
            JSONObject object = (JSONObject) WebUtil.getJsonParser().parse(new FileReader(languageFile));
            for (Object s : object.keySet()) {
                hashMap.put((String) s, (String) object.get(s));
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    public static TranslationLanguage getLanguage(String key) {
        for (String lang : availableLanguages.keySet()) {
            if (lang.equalsIgnoreCase(key)) {
                return availableLanguages.get(lang);
            }
        }
        return null;
    }

    public static HashMap<String, TranslationLanguage> getAvailableLanguages() {
        return availableLanguages;
    }

    public static void setCurrentLanguage(String key) {
        for (String lang : availableLanguages.keySet()) {
            if (lang.equalsIgnoreCase(key)) {
                currentLanguage = lang;
                return;
            }
        }

        currentLanguage = DEFAULT_LANGUAGE;
    }

    public static String translate(String translationKey) {
        if (currentLanguage == null) {
            setCurrentLanguage(DEFAULT_LANGUAGE);
        }

        String translation;
        if ((translation = availableLanguages.get(currentLanguage).getTranslations().get(translationKey)) == null) {
            translation = availableLanguages.get(DEFAULT_LANGUAGE).getTranslations().get(translationKey);
        }
        return translation;
    }
}
