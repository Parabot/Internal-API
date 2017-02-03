package org.parabot.api;

/**
 * @author JKetelaar
 */
public class Configuration {

    public static final String BOT_TITLE = "Parabot";
    public static final String BOT_SLOGAN = "The best RuneScape private server bot";

    public static final String V3_API_ENDPOINT = "http://v3.bdn.parabot.org/api/";

    public static final String COMPARE_VERSION_URL = V3_API_ENDPOINT + "bot/compare/%s/%s";
    public static final String COMPARE_CHECKSUM_URL = V3_API_ENDPOINT + "bot/checksum/%s/%s";

    public static final String DOWNLOAD_BOT = "http://bdn.parabot.org/versions/";

    public static final String GET_TRANSLATION = V3_API_ENDPOINT + "bot/translations/get/%s";
    public static final String LIST_TRANSLATIONS = V3_API_ENDPOINT + "bot/translations/list";

    public static final String NIGHTLY_APPEND = "?nightly=true";

    public static final String LIBRARIES_DOWNLOAD = V3_API_ENDPOINT + "bot/libraries/download";
}
