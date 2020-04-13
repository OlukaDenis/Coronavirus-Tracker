package com.mcdenny.coronavirusapp.utils;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Config {
    public Config() {
    }

    public static final String BASE_URL = "https://corona.lmao.ninja/";
    public static final String NIGERIAN_STATES_URL = "http://locationsng-api.herokuapp.com/api/v1/";
    public static final String PAYPAL_CLIENT_ID = "ASdds-5cjAlci1b_Z0CIDCGExbPTFwYLDQ1X5riR_rCG0TuH-oMoRAJ59gPqM5h02ziEFfeMUlM5yM0G";
    public static final int PAYPAL_REQUEST_CODE = 7777;

    public static final String UGANDA = "uganda";
    public static final String NIGERIA = "nigeria";

    public static final String UPDATED = "updated";

    public static final int HOSPITAL_RESULT_OK = 201;

    //Covid 19 probability
    public static final int COUGH = 90;
    public static final int COLD = 60;
    public static final int DIARRHEA = 60;
    public static final int SORE_THROAT = 60;
    public static final int BODY_ACHES = 50;
    public static final int HEADACHE = 60;
    public static final int FEVER = 98;
    public static final int BREATHING_DIFFICULTY = 99;
    public static final int FATIGUE = 50;
    public static final int TRAVEL = 80;
    public static final int TRAVEL_HISTORY = 98;
    public static final int DIRECT_CONTACT = 99;

    //Default markers
    public static final LatLng JOS_HOSPITAL = new LatLng(9.906647, 8.954732);

    public static final LatLng PLATEAU_SPECIALIST = new LatLng(9.896261, 8.884068);


    //Util methods

    public static String formatNumber(long number){
        NumberFormat formatter = new DecimalFormat("#,###");
        double num = (double) number;
        return formatter.format(num);
    }
}
