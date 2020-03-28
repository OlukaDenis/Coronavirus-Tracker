package com.premar.coronavirusapp.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.premar.coronavirusapp.model.CoronaCountry;
import com.premar.coronavirusapp.model.DataConverter;

@Database(entities = {CoronaCountry.class}, version = 2, exportSchema = false )
@TypeConverters(DataConverter.class)

public abstract class CovidDatabase extends RoomDatabase {
    private static CovidDatabase INSTANCE;
    public abstract CoronaCountryDao countryDao();

    public static CovidDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CovidDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static CovidDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context,
                CovidDatabase.class, "Covid.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

    }
}