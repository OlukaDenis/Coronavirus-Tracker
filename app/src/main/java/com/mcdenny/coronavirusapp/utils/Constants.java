package com.mcdenny.coronavirusapp.utils;

import com.google.android.gms.maps.model.LatLng;

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
    public static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);

    public static final LatLng MELBOURNE = new LatLng(-37.81319, 144.96298);

    public static final LatLng DARWIN = new LatLng(-12.4634, 130.8456);

    public static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);

    public static final LatLng ADELAIDE = new LatLng(-34.92873, 138.59995);

    public static final LatLng PERTH = new LatLng(-31.952854, 115.857342);

    public static final LatLng ALICE_SPRINGS = new LatLng(-24.6980, 133.8807);


    //Util methods

    public static String formatNumber(long number){
        NumberFormat formatter = new DecimalFormat("#,###");
        double num = (double) number;
        return formatter.format(num);
    }
}
