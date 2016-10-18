package org.parabot.api.translations;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.parabot.api.Configuration;
import org.parabot.api.io.Directories;
import org.parabot.api.io.WebUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
    }


    public static void getTranslations() {
        try {
            JSONObject object = (JSONObject) WebUtil.getJsonParser().parse(WebUtil.getContents(Configuration.LIST_TRANSLATIONS));
            JSONObject languages = (JSONObject) object.get("languages");
            for (Object key : languages.keySet()) {
                String keyStr = (String) key;
                String keyValue = languages.get(keyStr).toString();
                downloadJson(keyStr);
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
        String full = "";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(TranslationHelper.class.getResourceAsStream("/storage/languages/strings_" + key + ".json")))) {
            String line;
            while ((line = br.readLine()) != null) {
                full += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject object = (JSONObject) WebUtil.getJsonParser().parse(full);
            for (Object s : object.keySet()) {
                hashMap.put((String) s, (String) object.get(s));
            }
        } catch (ParseException e) {
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
