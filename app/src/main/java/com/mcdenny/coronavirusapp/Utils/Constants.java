package com.mcdenny.coronavirusapp.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Constants {
    public Constants() {
    }

    public static final String BASE_URL = "https://corona.lmao.ninja/";
    public static final String NIGERIAN_STATES_URL = "http://locationsng-api.herokuapp.com/api/v1/";
    public static final String UGANDA = "uganda";
    public static final String NIGERIA = "nigeria";

    public static final String UPDATED = "updated";

    public static final int HOSPITAL_RESULT_OK = 201;

    public static String formatNumber(long number){
        NumberFormat formatter = new DecimalFormat("#,###");
        double num = (double) number;
        return formatter.format(num);
    }
}
