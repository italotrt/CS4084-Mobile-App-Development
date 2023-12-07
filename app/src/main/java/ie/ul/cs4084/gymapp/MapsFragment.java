package ie.ul.cs4084.gymapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import androidx.fragment.app.Fragment;

import android.Manifest;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MapsFragment extends Fragment {

    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        // Checks for location permission. If allowed, calls "enableMyLocation()".
        // If not allowed, asks for permission again.
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mMap = googleMap;
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }

    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    // If location permissions are granted, displays location on map, moves camera to it and calls "getNearbyGyms()".
    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        mMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), location -> {
                    if (location != null) {
                        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f));
                    }
                });

        getNearbyGyms();
    }

    // If location permissions are not granted, asks for them again.
    // If successful, calls "enableMyLocation()", else displays a message to user.
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), result -> {
                if (result) {
                    enableMyLocation();
                } else {
                    Toast.makeText(requireContext(),
                            "Please enable Location permission.",
                            Toast.LENGTH_LONG).show();
                }
            });

    // Once map is created, this method is immediately called to locate and create markers for Gyms in a 5km radius.
    private void getNearbyGyms() {
        Log.d("getNearbyGyms", "getNearbyGyms called ");

        String placeType = "gym";

        int radius = 5000; // 5000 meters or 5 kilometers

        // Requests the last known location. If successful, builds a Places API request URL and then uses the Volley networking library to make a request.
        // If a response is received, calls the "parseNearbyGymsResponse()" method to parse it.
        getLastKnownLocationAsync(new LocationCallback() {
            @Override
            public void onLocationReceived(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // Builds the Places API request URL
                    String placesApiUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                            "location=" + latitude + "," + longitude +
                            "&radius=" + radius +
                            "&type=" + placeType +
                            "&key=" + getString(R.string.google_maps_key); // Replace with your API key

                    RequestQueue queue = Volley.newRequestQueue(requireContext());

                StringRequest stringRequest = new StringRequest(Request.Method.GET, placesApiUrl,
                            response -> {
                                // Parse the response JSON and add markers to the map
                                Log.d("Volley Request", "Request responded");
                                parseNearbyGymsResponse(response);
                                },
                                // Error with response result.
                            error -> Toast.makeText(requireContext(),
                                    "Places request can't be resolved.",
                                    Toast.LENGTH_SHORT).show());

                    queue.add(stringRequest);

                }
            @Override
            public void onLocationFailure(Exception e) {
                e.printStackTrace();
            }

        });

    }

    // Takes a response from a Places API request and parses it for relevant information.
    private void parseNearbyGymsResponse(String response) {
        Log.d("parseNearbyGymsResponse", "Response parsing");
        try {
            JSONObject jsonResponse = new JSONObject(response);

            // Check if response contains results
            if (jsonResponse.has("results")) {
                JSONArray resultsArray = jsonResponse.getJSONArray("results");

                // Iterate through the results
                // For each place: extract the relevant details and then create a Marker for them on the map
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject placeObject = resultsArray.getJSONObject(i);

                    // Extract information about the place
                    String placeName = placeObject.getString("name");
                    JSONObject geometryObject = placeObject.getJSONObject("geometry");
                    JSONObject locationObject = geometryObject.getJSONObject("location");
                    double placeLatitude = locationObject.getDouble("lat");
                    double placeLongitude = locationObject.getDouble("lng");

                    String key = "Opening hours unavailable.";

                    if (placeObject.has("opening_hours")) {
                        JSONObject openingHoursObject = placeObject.getJSONObject("opening_hours");

                        // Checking available keys for debugging purposes.
                        Iterator<String> keysIterator = openingHoursObject.keys();

                        while (keysIterator.hasNext()) {
                            String key2 = keysIterator.next();
                            Log.d("Key check", "Key: " + key2 + ", Value: " + openingHoursObject.get(key2));
                        }

                        if (openingHoursObject.has("weekday_text")) {
                            StringBuilder openingHours = new StringBuilder();
                            JSONArray weekdayTextArray = openingHoursObject.getJSONArray("weekday_text");
                            for (int j = 0; j < weekdayTextArray.length(); j++) {
                                openingHours.append(weekdayTextArray.getString(j)).append("\n");
                            }
                            key = openingHours.toString().trim();
                        } else if (openingHoursObject.has("open_now")) {
                            key = openingHoursObject.getBoolean("open_now") ? "Place is open." : "Place is closed.";
                        }

                    }
                    // Add a marker for each Gym on the map
                    addMarker(placeName, placeLatitude, placeLongitude, key);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Takes extracted details from "parseNearbyGymsResponse()" and creates a marker using them.
    private void addMarker(String placeName, double latitude, double longitude, String openingHours) {
        Log.d("addMarker", "Marker added");
        if (mMap != null) {
            LatLng placeLatLng = new LatLng(latitude, longitude);
            String markerTitle = placeName + " | " + openingHours;
            mMap.addMarker(new MarkerOptions().position(placeLatLng).title(markerTitle));
        }
    }

    // Callback interface for "getLastKnownLocationAsync()" so that it can be handled asynchronously
    public interface LocationCallback {
        void onLocationReceived(Location location);
        void onLocationFailure(Exception e);
    }

    // Gets the last known location of the user.
    private void getLastKnownLocationAsync(final LocationCallback callback) {
        Log.d("getLastKnownLocation", "getLastKnownLocation called");
        // Checking location permissions
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Requesting location
            fusedLocationClient.getLastLocation()
                    // If successful, checks if location is valid. If yes, uses the "onLocationReceived()" method obtained through the callback parameter.
                    // If not successful, or if successful but provided a null input, calls the "onLocationFailure()" method.
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            callback.onLocationReceived(location);
                        } else {
                            callback.onLocationFailure(new RuntimeException("Location is null"));
                        }
                    })
                    .addOnFailureListener(requireActivity(), callback::onLocationFailure);
        }
    }



}
