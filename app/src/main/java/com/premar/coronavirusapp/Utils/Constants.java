package com.premar.coronavirusapp.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Constants {
    public Constants() {
    }

    public static final String BASE_URL = "https://corona.lmao.ninja/";
    public static final String UGANDA = "uganda";
    public static final String NIGERIA = "nigeria";

    public static final String UPDATED = "updated";

    public static String formatNumber(long number){
        NumberFormat formatter = new DecimalFormat("#,###");
        double num = (double) number;
        return formatter.format(num);
    }
}
