package com.example.crumbtrail.fragments;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crumbtrail.R;
import com.example.crumbtrail.adapters.CustomWindowAdapter;
import com.example.crumbtrail.data.model.MapMarker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    public static final String TAG = "HomeFragment";
    private GoogleMap map;
    Location mCurrentLocation;
    private final static String KEY_LOCATION = "location";
    private List<MapMarker> allMarkers;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allMarkers = new ArrayList<>();

        if (savedInstanceState != null && savedInstanceState.keySet().contains(KEY_LOCATION)) {
            // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
            // is not null.
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }

        queryMapMarkers();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {
            // Map is ready
            // Add a new latitude in the midwest and move to it
            LatLng UnitedStates = new LatLng(38.20888835170237 , -101.71670772135258);
            map.moveCamera(CameraUpdateFactory.newLatLng(UnitedStates));
            //zoom to position with level 5
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(UnitedStates, 3);
            googleMap.animateCamera(cameraUpdate);
            Log.i(TAG, "Map Fragment was loaded properly!");
            map.setOnMapLongClickListener(latLng -> showAlertDialogForPoint(latLng));
            map.setInfoWindowAdapter(new CustomWindowAdapter(getLayoutInflater()));
        } else {
            Toasty.error(requireActivity(), "Error - Map was null!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addMarkersFromBackend() {
        Log.i(TAG, String.valueOf(allMarkers.size()));
        for (MapMarker mapMarker : allMarkers) {
            BitmapDescriptor defaultMarker = BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            String title = mapMarker.getName();
            String food = mapMarker.getFood();
            String discount = mapMarker.getDiscount();
            // Creates and adds marker to the map
            LatLng latLng = new LatLng(mapMarker.getLocation().getLatitude(), mapMarker.getLocation().getLongitude());
            Marker marker = map.addMarker(new MarkerOptions().position(latLng)
                    .title(title).snippet(food).snippet(discount).icon(defaultMarker));

            marker.setDraggable(false);

            // Animate marker using drop effect
            // --> Call the dropPinEffect method here
            dropPinEffect(marker);
        }
    }

    private void removeOldMarkersFromBackend() {
        Date today = new Date();
        int hours = 24;
        int time = (hours * 3600 * 1000);
        Date expirationDate = new Date(today.getTime() - (time));

        ParseQuery<MapMarker> query = ParseQuery.getQuery(MapMarker.class);
        query.whereLessThan("createdAt", expirationDate);

        query.findInBackground((mapMarkers, e) -> {
            if (e != null) {
                Log.e(TAG, "Issue with getting Old MapMarkers", e);
                return;
            }

            try {
                ParseObject.deleteAll(mapMarkers);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            addMarkersFromBackend();
        });
    }

    private void showAlertDialogForPoint(final LatLng latLng) {
        View messageView = LayoutInflater.from(getContext()).inflate(R.layout.message_item, null);
        // Create alert dialog builder
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
// set message_item.xml to AlertDialog builder
        alertDialogBuilder.setView(messageView);
// Create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                (dialog, which) -> {
                    // Define color of marker icon
                    // Define custom marker
                    BitmapDescriptor defaultMarker = BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                    // Extract content from alert dialog
                    String title = ((EditText) alertDialog.findViewById(R.id.etTitle))
                            .getText().toString();
                    String food = ((EditText) alertDialog.findViewById(R.id.etFood))
                            .getText().toString();
                    String discount = ((EditText) alertDialog.findViewById(R.id.etDiscount))
                            .getText().toString();
                    // Creates and adds marker to the map
                    Marker marker = map.addMarker(new MarkerOptions().position(latLng)
                            .title(title).snippet(food).snippet(discount).icon(defaultMarker));

                    createMarker(title, food, discount, latLng);

                    marker.setDraggable(true);

                    // Animate marker using drop effect
                    // --> Call the dropPinEffect method here
                    dropPinEffect(marker);
                });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL",
                (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }

    private void createMarker(String title, String food, String discount, LatLng latLng) {
        MapMarker mapMarker = new MapMarker();
        mapMarker.setDiscount(discount);
        mapMarker.setName(title);
        mapMarker.setFood(food);
        ParseGeoPoint parseGeoPoint = new ParseGeoPoint(latLng.latitude, latLng.longitude);
        mapMarker.setLocation(parseGeoPoint);
        mapMarker.saveInBackground(e -> {
            if (e != null) {
                Log.e(TAG, "Error while saving new map marker!", e);
                Toasty.error(requireActivity(), "Error while saving!", Toast.LENGTH_SHORT).show();
            }
            Log.i(TAG, "MapMarker save was successful!");
        });
    }

    private void dropPinEffect(final Marker marker) {
        // Handler allows us to repeat a code block after a specified delay
        final android.os.Handler handler = new android.os.Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        // Use the bounce interpolator
        final android.view.animation.Interpolator interpolator =
                new BounceInterpolator();

        // Animate marker with a bounce updating its position every 15ms
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                // Calculate t for bounce based on elapsed time
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed
                                / duration), 0);
                // Set the anchor
                marker.setAnchor(0.5f, 1.0f + 14 * t);

                if (t > 0.0) {
                    // Post this event again 15ms from now.
                    handler.postDelayed(this, 15);
                } else { // done elapsing, show window
                    marker.showInfoWindow();
                }
            }
        });
    }

    private void queryMapMarkers() {
        ParseQuery<MapMarker> query = ParseQuery.getQuery(MapMarker.class);
        query.include(MapMarker.KEY_LOCATION);
        // order MapMarkers by creation date (newest first)
        query.addDescendingOrder("createdAt");
        query.findInBackground((mapMarkers, e) -> {
            if (e != null) {
                Log.e(TAG, "Issue with getting MapMarkers", e);
                return;
            }

            // for debugging purposes let's print every Review description to logcat
            for (MapMarker mapMarker : mapMarkers) {
                Log.i(TAG, "MapMaker: " + mapMarker.getLocation());
            }

            // save received MapMarkers
            allMarkers.addAll(mapMarkers);
            removeOldMarkersFromBackend();
        });
    }
}
