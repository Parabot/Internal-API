package org.parabot;

import org.junit.Assert;
import org.junit.Test;
import org.parabot.api.translations.TranslationHelper;
import org.parabot.api.translations.TranslationLanguage;

import java.util.HashMap;

/**
 * @author JKetelaar
 */
public class LanguageTest {
    @Test
    public void test() {
        HashMap<String, TranslationLanguage> s = TranslationHelper.getAvailableLanguages();
        Assert.assertTrue(s.size() >= 0);

        Assert.assertEquals(TranslationHelper.translate("DEBUG_MODE"), "Debug mode: ");

        TranslationHelper.setCurrentLanguage("nl");
        Assert.assertEquals(TranslationHelper.translate("DEBUG_MODE"), "Foutopsporing modus: ");
    }
}
