package com.mcdenny.coronavirusapp.view.ui.maps;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.SeekBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mcdenny.coronavirusapp.R;

import static com.mcdenny.coronavirusapp.utils.Constants.ADELAIDE;
import static com.mcdenny.coronavirusapp.utils.Constants.ALICE_SPRINGS;
import static com.mcdenny.coronavirusapp.utils.Constants.BRISBANE;
import static com.mcdenny.coronavirusapp.utils.Constants.DARWIN;
import static com.mcdenny.coronavirusapp.utils.Constants.MELBOURNE;
import static com.mcdenny.coronavirusapp.utils.Constants.PERTH;
import static com.mcdenny.coronavirusapp.utils.Constants.SYDNEY;

public class MapsActivity extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerDragListener,
        SeekBar.OnSeekBarChangeListener,
        OnMapReadyCallback {

    private GoogleMap mMap;

    private Marker mPerth;

    private Marker mSydney;

    private Marker mBrisbane;

    private Marker mAdelaide;

    private Marker mMelbourne;

    private Marker mDarwin1;
    private Marker mDarwin2;
    private Marker mDarwin3;
    private Marker mDarwin4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Hide the zoom controls as the button panel will cover it.
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Add lots of markers to the map.
        addMarkersToMap();

        // Set listeners for marker events.  See the bottom of this class for their behavior.
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerDragListener(this);

        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localised.
        mMap.setContentDescription("Map with lots of markers.");

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(PERTH)
                .include(SYDNEY)
                .include(ADELAIDE)
                .include(BRISBANE)
                .include(MELBOURNE)
                .include(DARWIN)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
    }

    private void addMarkersToMap() {
        // Uses a colored icon.
        mBrisbane = mMap.addMarker(new MarkerOptions()
                .position(BRISBANE)
                .title("Brisbane")
                .snippet("Population: 2,074,200")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        // Uses a custom icon with the info window popping out of the center of the icon.
        mSydney = mMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("Sydney")
                .snippet("Population: 4,627,300")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location))
                .infoWindowAnchor(0.5f, 0.5f));

        // Creates a draggable marker. Long press to drag.
        mMelbourne = mMap.addMarker(new MarkerOptions()
                .position(MELBOURNE)
                .title("Melbourne")
                .snippet("Population: 4,137,400")
                .draggable(true));

        // Place four markers on top of each other with differing z-indexes.
        mDarwin1 = mMap.addMarker(new MarkerOptions()
                .position(DARWIN)
                .title("Darwin Marker 1")
                .snippet("z-index 1")
                .zIndex(1));
        mDarwin2 = mMap.addMarker(new MarkerOptions()
                .position(DARWIN)
                .title("Darwin Marker 2")
                .snippet("z-index 2")
                .zIndex(2));
        mDarwin3 = mMap.addMarker(new MarkerOptions()
                .position(DARWIN)
                .title("Darwin Marker 3")
                .snippet("z-index 3")
                .zIndex(3));
        mDarwin4 = mMap.addMarker(new MarkerOptions()
                .position(DARWIN)
                .title("Darwin Marker 4")
                .snippet("z-index 4")
                .zIndex(4));


        // A few more markers for good measure.
        mPerth = mMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .title("Perth")
                .snippet("Population: 1,738,800"));
        mAdelaide = mMap.addMarker(new MarkerOptions()
                .position(ADELAIDE)
                .title("Adelaide")
                .snippet("Population: 1,213,000"));

        // Vector drawable resource as a marker icon.
        mMap.addMarker(new MarkerOptions()
                .position(ALICE_SPRINGS)
                .icon(vectorToBitmap(R.drawable.ic_location, Color.parseColor("#008040")))
                .title("Alice Springs"));
    }

    /**
     * Demonstrates converting a {@link Drawable} to a {@link BitmapDescriptor},
     * for use as a marker icon.
     */
    private BitmapDescriptor vectorToBitmap(@DrawableRes int id, @ColorInt int color) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        DrawableCompat.setTint(vectorDrawable, color);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
}
