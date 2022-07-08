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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.crumbtrail.ComposeActivity;
import com.example.crumbtrail.R;
import com.example.crumbtrail.adapters.CustomWindowAdapter;
import com.example.crumbtrail.data.model.Food;
import com.example.crumbtrail.data.model.MapMarker;
import com.example.crumbtrail.data.model.Review;
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
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    public static final String TAG = "HomeFragment";
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private LocationRequest mLocationRequest;
    Location mCurrentLocation;
    private long UPDATE_INTERVAL = 60000;  /* 60 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */
    private FusedLocationProviderClient fusedLocationClient;
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        mapFragment = (SupportMapFragment) getChildFragmentManager()
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
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(UnitedStates, 5);
            googleMap.animateCamera(cameraUpdate);
            Toast.makeText(getActivity(), "Map Fragment was loaded properly!", Toast.LENGTH_SHORT).show();
            map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    showAlertDialogForPoint(latLng);
                }
            });
            map.setInfoWindowAdapter(new CustomWindowAdapter(getLayoutInflater()));
        } else {
            Toast.makeText(getActivity(), "Error - Map was null!!", Toast.LENGTH_SHORT).show();
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
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                    }
                });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
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
                Toast.makeText(getActivity(), "Error while saving!", Toast.LENGTH_SHORT).show();
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

    void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);

        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(requireContext());
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }
    public void onLocationChanged(Location location) {
        // GPS may be turned off
        if (location == null) {
            return;
        }

        mCurrentLocation = location;
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
        displayLocation();
    }

    private LatLng displayLocation() {
        if (mCurrentLocation != null) {
            Toast.makeText(requireActivity(), "GPS location was found!", Toast.LENGTH_SHORT).show();
            LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            map.animateCamera(cameraUpdate);
            return latLng;
        } else {
            Toast.makeText(requireActivity(), "Current location was null, enable GPS on emulator!", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(requireContext());
        settingsClient.checkLocationSettings(locationSettingsRequest);
        //noinspection MissingPermission
        getFusedLocationProviderClient(requireContext()).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
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
            addMarkersFromBackend();
            removeOldMarkersFromBackend();
        });
    }
}
