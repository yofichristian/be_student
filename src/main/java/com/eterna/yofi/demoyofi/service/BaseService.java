package com.eterna.yofi.demoyofi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class BaseService {

    public static final Pattern TIME_PATTERN = Pattern.compile("\\d+:\\d+:\\d+");
    public static final Pattern ALPHABET_PATTERN = Pattern.compile(".*[a-zA-Z]+.*");
    public static final Pattern NUMERIC_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static final ObjectMapper objectMapper = new ObjectMapper()
//            .enable(SerializationFeature.INDENT_OUTPUT)
//            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //    public static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
//    public static final SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy");
    public static final SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMdd_HHmmss");
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    public static String toJson(Object obj) {
        try {
            if (obj instanceof String)
                return (String) obj;
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return "";
        }
    }

    public static String printDate(Date dt) {
        if (dt == null) return sdf1.format(new Date());
        return sdf1.format(dt);
    }

    public static String successRate(double val) {
        return String.format("%2.2f%%", val);
    }

    public static String getNow() {
        return sdf1.format(new Date());
    }

    public static String expiredDate(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

//    public static String hex(byte[] bytes) {
//        char[] result = Hex.encodeHex(bytes);
//        return new String(result);
//    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static UUID getGuidFromByteArray(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        return new UUID(bb.getLong(), bb.getLong());
    }

    public static <T> T duplicate(Object source, Class<T> targetType) {
        if (source == null)
            return null;
        try {
            String json = objectMapper.writeValueAsString(source);
            T target = objectMapper.readValue(json, targetType);
            return target;
        } catch (Exception e) {
            return null;
        }
    }

    public static HashMap<String, Object> jsonToHashMap(final String json) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            map = objectMapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (Exception e) {
            return map;
        }
        return map;
    }

    public static boolean isLastDate() {
        return isLastDate(LocalDateTime.now());
    }

    public static boolean isLastDate(LocalDateTime theDate) {
        int thisMonth = theDate.getMonth().getValue();
        int nextMonth = theDate.plusDays(1).getMonth().getValue();
        return thisMonth != nextMonth;
    }

    public static double calculatePercentage(double obtained, double total) {
        if (total == 0) return 0;
        return obtained * 100 / total;
    }

    private static boolean isAlfabet(String s) {
        return ALPHABET_PATTERN.matcher(s).find();
    }

    public static boolean isNumeric(String str) {
        return NUMERIC_PATTERN.matcher(str).matches();
    }

//    public static LocalDate strToLocalDate(String tgl) {
//        LocalDateTime ldt = strToLocalDateTime(tgl);
//        if (ldt == null) return null;
//        return ldt.toLocalDate();
//    }

    public static LocalDateTime strToLocalDateTime(String tgl) {

        if (StringUtils.isEmpty(tgl)) return null;

        tgl = tgl.replace("T", " ").replace("Z", "");

        String time = getTimeString(tgl);
        String times[] = time.split(":");
        LocalTime localTime = LocalTime.of(Integer.parseInt(times[0]), Integer.parseInt(times[1]), Integer.parseInt(times[2]));

        String item[] = tgl.split("[\\-\\\\//\\s]+");

        if (item.length == 1) return null;

//        String pattern = "dd MMM yyyy HH:mm:ss";
//        String data = "";

        String yy = item[2];
        String dd = item[0];
        if ((yy.length() > 2) && isNumeric(yy) && dd.length() < 4 && isNumeric(dd)) {
            if (isAlfabet(item[1])) {
//                pattern = "dd MMM yyyy HH:mm:ss";
//                data = dd + " " + item[1] + " " + yy + " " + time;
                return LocalDateTime.of(
                        LocalDate.of(Integer.parseInt(yy), monthNameToNumber(item[1]), Integer.parseInt(dd)),
                        localTime
                );
            } else {
//                pattern = "dd-MM-yyyy HH:mm:ss";
//                data = dd + "-" + item[1] + "-" + yy + " " + time;
                return LocalDateTime.of(
                        LocalDate.of(Integer.parseInt(yy), Integer.parseInt(item[1]), Integer.parseInt(dd)),
                        localTime
                );
            }
//            try {
//                return strToLocalDateTime(data, pattern);
//            } catch (Exception e) {
////                System.err.print("Error parse datetime : " + e.getMessage());
//                log.error("Error parse datetime : " + e.getMessage(), e);
//            }
//            return null;
        }

        yy = item[0];
        dd = item[2];
        if (isAlfabet(item[1])) {
//            pattern = "dd MMM yyyy HH:mm:ss";
//            data = dd + " " + item[1] + " " + yy + " " + time;
            try {
//                return strToLocalDateTime(data, pattern);
                return LocalDateTime.of(
                        LocalDate.of(Integer.parseInt(yy), monthNameToNumber(item[1]), Integer.parseInt(dd)),
                        localTime
                );
            } catch (Exception e) {
//                System.err.print("Error parse datetime : " + e.getMessage());
                log.error("Error parse datetime : " + e.getMessage(), e);
            }
            return null;
        }

        try {
//            pattern = "dd-MM-yyyy HH:mm:ss";
//            data = dd + "-" + item[1] + "-" + yy + " " + time;
//            return strToLocalDateTime(data, pattern);
            return LocalDateTime.of(
                    LocalDate.of(Integer.parseInt(yy), Integer.parseInt(item[1]), Integer.parseInt(dd)),
                    localTime
            );
        } catch (Exception e) {
            System.err.print("Error parse datetime : " + e.getMessage());
        }
        return null;

    }

    public static LocalDate strToLocalDate(String tgl) {

        if (StringUtils.isEmpty(tgl)) return null;

        tgl = tgl.replace("T", " ").replace("Z", "");

        String item[] = tgl.split("[\\-\\\\//\\s]+");

        if (item.length == 1) return null;

        String yy = item[2];
        String dd = item[0];
        if ((yy.length() > 2) && isNumeric(yy) && dd.length() < 4 && isNumeric(dd)) {
            if (isAlfabet(item[1])) {
//                pattern = "dd MMM yyyy HH:mm:ss";
                return LocalDate.of(Integer.parseInt(yy), monthNameToNumber(item[1]), Integer.parseInt(dd));
            } else {
//                pattern = "dd-MM-yyyy HH:mm:ss";
                return LocalDate.of(Integer.parseInt(yy), Integer.parseInt(item[1]), Integer.parseInt(dd));
            }
        }

        yy = item[0];
        dd = item[2];
        if (isAlfabet(item[1])) {
//            pattern = "dd MMM yyyy HH:mm:ss";
//            data = dd + " " + item[1] + " " + yy + " " + time;
            try {
//                return strToLocalDateTime(data, pattern);
                return LocalDate.of(Integer.parseInt(yy), monthNameToNumber(item[1]), Integer.parseInt(dd));
            } catch (Exception e) {
//                System.err.print("Error parse datetime : " + e.getMessage());
                log.error("Error parse datetime : " + e.getMessage(), e);
            }
            return null;
        }

        try {
//            pattern = "dd-MM-yyyy HH:mm:ss";
//            data = dd + "-" + item[1] + "-" + yy + " " + time;
//            return strToLocalDateTime(data, pattern);
            return LocalDate.of(Integer.parseInt(yy), Integer.parseInt(item[1]), Integer.parseInt(dd));
        } catch (Exception e) {
            System.err.print("Error parse datetime : " + e.getMessage());
        }
        return null;

    }

    public static LocalDate strToLocalDateWithException(String tgl)  {

        if (StringUtils.isEmpty(tgl)) return null;

        tgl = tgl.replace("T", " ").replace("Z", "");

        String item[] = tgl.split("[\\-\\\\//\\s]+");

        if (item.length == 1) return null;

        String yy = item[2];
        String dd = item[0];
        if ((yy.length() > 2) && isNumeric(yy) && dd.length() < 4 && isNumeric(dd)) {
            if (isAlfabet(item[1])) {
//                pattern = "dd MMM yyyy HH:mm:ss";
                return LocalDate.of(Integer.parseInt(yy), monthNameToNumber(item[1]), Integer.parseInt(dd));
            } else {
//                pattern = "dd-MM-yyyy HH:mm:ss";
                return LocalDate.of(Integer.parseInt(yy), Integer.parseInt(item[1]), Integer.parseInt(dd));
            }
        }

        yy = item[0];
        dd = item[2];
        if (isAlfabet(item[1])) {
//            pattern = "dd MMM yyyy HH:mm:ss";
//            data = dd + " " + item[1] + " " + yy + " " + time;
            try {
//                return strToLocalDateTime(data, pattern);
                return LocalDate.of(Integer.parseInt(yy), monthNameToNumber(item[1]), Integer.parseInt(dd));
            } catch (Exception e) {
//                System.err.print("Error parse datetime : " + e.getMessage());
                log.error("Error parse datetime : " + e.getMessage(), e);
                throw e;
            }
        }

        try {
//            pattern = "dd-MM-yyyy HH:mm:ss";
//            data = dd + "-" + item[1] + "-" + yy + " " + time;
//            return strToLocalDateTime(data, pattern);
            return LocalDate.of(Integer.parseInt(yy), Integer.parseInt(item[1]), Integer.parseInt(dd));
        } catch (Exception e) {
            System.err.print("Error parse datetime : " + e.getMessage());
            throw e;
        }

    }

    public static LocalDateTime strToLocalDateTime(String tgl, String pattern) throws Exception {
        DateTimeFormatter f = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(tgl.toUpperCase(), f);
    }

    public static int monthNameToNumber(String shortName) {
        String s = shortName.substring(0, 1).toUpperCase() + shortName.substring(1).toLowerCase();
        return DateTimeFormatter.ofPattern("MMM")
                .withLocale(Locale.ENGLISH)
                .parse(s)
                .get(ChronoField.MONTH_OF_YEAR);
    }

    public static String getTimeString(String date) {

        if (!TIME_PATTERN.matcher(date).find())
            return "00:00:00";
        try {
            String[] item = date.split(":");
            if (item.length == 1) return "00:00:00";

            //get hour
            String item2[] = item[0].split("[\\s]+");
            String hh = item2[item2.length - 1];

            if (item.length == 2) return hh + ":" + item[1] + ":00";
            if (item.length == 3) return hh + ":" + item[1] + ":" + item[2].substring(0, 2);

            return hh + ":00:00";
        } catch (Exception e) {
            return "00:00:00";
        }
    }

    public static Date toDate(LocalDateTime date) {
        return toDate(date, ZoneId.systemDefault());
    }

    public static Date toDate(LocalDateTime date, ZoneId zoneId) {
        if (isNull(date, zoneId)) return null;
        return Date.from(date.atZone(zoneId).toInstant());
    }

    public static boolean isNull(Object... obj) {
        for (Object o : obj) {
            if (Objects.isNull(o)) return true;
        }
        return false;
    }

    public static int parseInt(String s) {
        if (StringUtils.isEmpty(s)) return 0;
        if (!isNumeric(s)) return 0;
        return Math.round(Float.parseFloat(s));
    }

    public static double parseDouble(String s) {
        if (StringUtils.isEmpty(s)) return 0;
        if (!isNumeric(s)) return 0;
        return Double.parseDouble(s);
    }

    public static float parseFloat(String s) {
        if (StringUtils.isEmpty(s)) return 0;
        if (!isNumeric(s)) return 0;
        try {
            return Float.parseFloat(s);
        } catch (Exception e) {
            System.err.println("Error parse float: " + e.getMessage());
            return 0f;
        }
    }

    public static void createDirectories(String foldername) {
        try {
            Path path = Paths.get(foldername);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (Exception e) {
            System.err.println("Error create directory: " + e.getMessage());
        }
    }

    public static byte[] readFileResource(String resourcePath) {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream(resourcePath);
            return is.readAllBytes();
        } catch (Exception e) {
//            log.error("Error get resource file {} {}", resourcePath, e.getMessage());
            System.err.println("Error get resource file " +  resourcePath + ", " + e.getMessage());
            return null;
        }
    }

    public static String printExcelString(String val) {
        if (val.length()>32700) {
            return val.substring(0,32700);
        }
        return val;
    }

    public static void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            System.err.println("Main thread was interrupted during sleep.");
        }
    }

    public static Object nullZero(Object val) {
//        try {
//            if (obj==null) return 0;
//        } catch (Exception e) {
//            //
//        }
//        return obj;
        return nullZero(val, 0);
    }

    public static Object nullZero(Object val, Object defaultVal) {
        try {
            if (val==null) {
                if (defaultVal==null) return 0;
                return defaultVal;
            }
        } catch (Exception e) {
            //
        }
        return val;
    }

}
