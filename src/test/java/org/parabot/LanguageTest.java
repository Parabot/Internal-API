package org.parabot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parabot.api.io.Directories;
import org.parabot.api.translations.TranslationHelper;
import org.parabot.api.translations.TranslationLanguage;

/**
 * @author JKetelaar
 */
public class LanguageTest {

    @Before
    public void setUp(){
        Directories.validate();
    }

    @Test
    public void testAmount() {
        Assert.assertTrue(TranslationHelper.getAvailableLanguages().size() > 0);
    }

    @Test
    public void testAfterCacheClearing() {
        Assert.assertTrue(TranslationHelper.getAvailableLanguages().size() > 0);
        Directories.clearCache();
        Assert.assertEquals(TranslationHelper.translate("APPLET_FETCHED"), "Applet fetched.");
    }

    @Test
    public void testOtherLanguage() {
        for (TranslationLanguage language : TranslationHelper.getAvailableLanguages().values()) {
            if (!language.getKey().equalsIgnoreCase(TranslationHelper.DEFAULT_LANGUAGE)) {
                TranslationHelper.setCurrentLanguage(language.getKey());
            }
        }
        String translation = TranslationHelper.translate("DOWNLOAD_UPDATE_PARABOT_AT");
        Assert.assertTrue(translation != null && !translation.equals("Please download the newest version of Parabot at "));
    }
}
