package gridengine.provider.utils;

public final class NumberParseUtils {

    final static String NUMERIC_STRING_REGEX = "\\d+";
    final static String SUB_GIGO = "G";
    final static String SUB_MEGA = "M";
    final static String SUB_KILO = "K";
    final static String EMPTY_VALUE = "-";

    private NumberParseUtils() {
    }

    public static Integer stringToInt(final String value) {
        if (value.trim().equals(EMPTY_VALUE)) {
            return null;
        }
        if (value.matches(NUMERIC_STRING_REGEX)) {
            return Integer.parseInt(value);
        }
        throw new IllegalArgumentException(String.format("Can`t be converted to Integer: %s", value));
    }

    public static Double stringToDouble(final String value) {
        if (value.trim().equals(EMPTY_VALUE)) {
            return null;
        }
        if (value.matches(NUMERIC_STRING_REGEX) || value.contains(".")) {
            return Double.parseDouble(value);
        }
        throw new IllegalArgumentException(String.format("Can`t be converted to Double: %s", value));
    }

    public static Long stringToLong(final String value) {
        if (value.trim().equals(EMPTY_VALUE)) {
            return null;
        }
        if (value.contains(SUB_GIGO)) {
            return (long) (Double.parseDouble(value.replace(SUB_GIGO, "")) * 1000000000);
        }
        if (value.contains(SUB_MEGA)) {
            return (long) (Double.parseDouble(value.replace(SUB_MEGA, "")) * 1000000);
        }
        if (value.contains(SUB_KILO)) {
            return (long) (Double.parseDouble(value.replace(SUB_KILO, "")) * 1000);
        }
        if (value.matches(NUMERIC_STRING_REGEX)) {
            return Long.parseLong(value);
        }
        throw new IllegalArgumentException(String.format("Can`t be converted to Long: %s", value));
    }

    public static String checkString(final String value) {
        if (value.trim().equals(EMPTY_VALUE)) {
            return null;
        }
        return value;
    }

}
