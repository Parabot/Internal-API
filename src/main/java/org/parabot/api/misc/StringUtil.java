package org.parabot.api.misc;

/**
 * @author mkyong, JKetelaar
 */
public class StringUtil {

    private static java.util.Random random = new java.util.Random();
    private static char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();

    public static String implode(String separator, String... data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length - 1; i++) {
            //data.length - 1 => to not add separator at the end
            if (!data[i].matches(" *")) {//empty string are ""; " "; "  "; and so on
                sb.append(data[i]);
                sb.append(separator);
            }
        }
        sb.append(data[data.length - 1].trim());
        return sb.toString();
    }

    public static String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < hex.length() - 1; i += 2) {

            // grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            // convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            // convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }

        return sb.toString();
    }

    public static String randomString(final int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static long longForName(String name) {
        long l = 0L;
        for (int i = 0; i < name.length() && i < 12; i++) {
            char c = name.charAt(i);
            l *= 37L;
            if (c >= 'A' && c <= 'Z') {
                l += (1 + c) - 65;
            } else if (c >= 'a' && c <= 'z') {
                l += (1 + c) - 97;
            } else if (c >= '0' && c <= '9') {
                l += (27 + c) - 48;
            }
        }

        while (l % 37L == 0L && l != 0L) {
            l /= 37L;
        }

        return l;
    }

    public static String nameForLong(long name) {
        try {
            if (name <= 0L || name >= 0x5b5b57f8a98a5dd1L) {
                return "invalid_name";
            }
            if (name % 37L == 0L) {
                return "invalid_name";
            }

            int i = 0;
            char ac[] = new char[12];
            while (name != 0L) {
                long l1 = name;
                name /= 37L;
                ac[11 - i++] = chars[(int) (l1 - name * 37L)];
            }
            return new String(ac, 12 - i, i);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String fixName(String name) {
        if (name.length() > 0) {
            char ac[] = name.toCharArray();
            for (int j = 0; j < ac.length; j++)
                if (ac[j] == '_') {
                    ac[j] = ' ';
                    if (j + 1 < ac.length && ac[j + 1] >= 'a' && ac[j + 1] <= 'z') {
                        ac[j + 1] = (char) ((ac[j + 1] + 65) - 97);
                    }
                }

            if (ac[0] >= 'a' && ac[0] <= 'z') {
                ac[0] = (char) ((ac[0] + 65) - 97);
            }
            return new String(ac);
        } else {
            return name;
        }
    }
}