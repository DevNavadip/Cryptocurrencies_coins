package demo.app.cryptocurrencies.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Constants {

    public static final String BASE_URL = "https://api.coinranking.com/v2/";
    public static final String IMAGE_URL = "";

    //token
    public static String AuthorisedToken = "";

    //api endpoints
    public static final String API_URL_COINS_LIST = "coins";

    //    public static final String INPUT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String INPUT_FORMAT = "MM-dd-yyyy HH:mm:ss";
    public static final String OUTPUT_FORMAT = "dd MMM yyyy";
    public static final String DATE_FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_MMM_dd_yyyy = "MMM dd, yyyy";
    public static final String DATE_FORMAT_dd_MM_yyyy = "dd/MM/yyyy";

    //bundle
    public static final String BUNDLE_SERVICE = "BUNDLE_SERVICE";

    //types
    public static final String COINS_ORDER_BY_MARKETCAP = "marketCap";
    public static final String COINS_ORDER_BY_PRICE = "price";
    public static final String COINS_ORDER_BY_24H_VOLUME = "24hVolume";


    public static String changeDateFormat(String time) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(INPUT_FORMAT, Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(OUTPUT_FORMAT, Locale.getDefault());
        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String changeDateFormat(String time, String INPUT_FORMAT, String OUTPUT_FORMAT) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(INPUT_FORMAT, Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(OUTPUT_FORMAT, Locale.getDefault());
        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
