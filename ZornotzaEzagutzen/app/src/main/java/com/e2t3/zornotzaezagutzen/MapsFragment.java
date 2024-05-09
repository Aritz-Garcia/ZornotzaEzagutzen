package com.e2t3.zornotzaezagutzen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.e2t3.zornotzaezagutzen.appdatabase.AppDatabase;
import com.e2t3.zornotzaezagutzen.data.entities.Gune;
import com.e2t3.zornotzaezagutzen.shared.Utils;
import com.e2t3.zornotzaezagutzen.shared.Values;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

// Mapa fragmentua kudeatzeko klasea
public class MapsFragment extends Fragment {


    private GoogleMap mMap;
    private static final int PERMISO_LOCALIZACION = 0;
    private double clienteLatitude, clienteLongitude;
    private static FusedLocationProviderClient locationClient;
    private LocationManager locationManager;
    private AppDatabase appDatabase;
    List<Gune> guneak;
    private static SharedPreferences spZornotza;
    private static int zenbatu = 0;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            spZornotza = getActivity().getSharedPreferences("zornotza", Context.MODE_PRIVATE);
            mMap = googleMap;
            setGuneak();
            LatLng firstCameraPosition = new LatLng(43.22130658089291, -2.7340722441030167);

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setBuildingsEnabled(true);
            mMap.setMinZoomPreference(16);
            mMap.getUiSettings().setCompassEnabled(false);
            mMap.getUiSettings().setTiltGesturesEnabled(false);
            mMap.getUiSettings().setMapToolbarEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

            // Gune baten gainean klikatzerakoan exekutatzen da.
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @SuppressLint("MissingPermission")
                @Override
                public boolean onMarkerClick(Marker marker) {

                    locationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener(getActivity(), location -> {
                        if (location != null) {
                            clienteLatitude = location.getLatitude();
                            clienteLongitude = location.getLongitude();
                            double markerLat = marker.getPosition().latitude;
                            double markerLng = marker.getPosition().longitude;
                            if (Utils.distance(clienteLatitude, markerLat, clienteLongitude, markerLng, 0, 0) < 50) {
                                Gune gunea = bilatuguneaLokalizazioz(marker.getPosition());
                                if (gunea != null) {
                                    // GUne barruan badago exekutatzen da
                                    Intent intent = new Intent(MainActivity.progressBarGuneak.getContext(), GuneActivity.class);
                                    intent.putExtra("gunea", gunea);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            } else {
                                // show snippet
                                marker.showInfoWindow();
                            }
                        }
                    });
                    return true;
                }
            });

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);

            }
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(firstCameraPosition)      // Sets the center of the map to Mountain View
                    .zoom(18)
                    .bearing(0)
                    .tilt(35)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            // Mapan klik egitean
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onMapClick(LatLng latLng) {
                    Location newLocation = new Location(LocationManager.GPS_PROVIDER);
                    newLocation.setLatitude(latLng.latitude);
                    newLocation.setLongitude(latLng.longitude);

                    newLocation.setAccuracy(3.0f);
                    newLocation.setTime(System.currentTimeMillis());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        newLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                    }
                    if (locationClient != null) locationClient.setMockLocation(newLocation);
                }
            });
        }
    };

    // Guneak mapan erakusten duen metodoa
    private void setUpMarkers() {
        boolean bat = false;
        for (Gune gune : guneak) {
            LatLng latLng = new LatLng(gune.latitude, gune.longitude);
            // mapako puntua
            MarkerOptions marker = new MarkerOptions().position(latLng)
                                                        .title(gune.zenbakia + ". Gunea")
                                                        .snippet(gune.izena);

            // borobila
            mMap.addCircle(new CircleOptions()
                    .center(new LatLng(gune.latitude, gune.longitude))
                    .radius(30)
                    .fillColor(Values.fillColor)
                    .strokeColor(Values.strokeColor)
                    .strokeWidth(5));

            String guneaGutxiago = "gune" + (gune.zenbakia - 1);
            String gunea = "gune" + gune.zenbakia;
            String lehenengoa = "gune1";
            boolean guneEginda = spZornotza.getBoolean(gunea, false);
            boolean aurrekoaEgunda = spZornotza.getBoolean(guneaGutxiago, false);
            boolean lehenengoGunea = spZornotza.getBoolean(lehenengoa, false);
            if (guneEginda) {
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            } else if (aurrekoaEgunda && !bat) {
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                bat = true;
            } else if (!lehenengoGunea && gune.zenbakia == 1) {
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                bat = true;
            }
            mMap.addMarker(marker);
        }
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

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            localizacionAceptada();
        }
        initializeLocationListener();
    }

    // Erabiltzailearen kokapena nahi den modura erabiltzea ahalbidetzen duen metodoa
    @SuppressLint("MissingPermission")
    public static void initializeDeveloperMode() {
        locationClient = LocationServices.getFusedLocationProviderClient(MainActivity.progressBarGuneak.getContext());
        locationClient.setMockMode(true);
    }

    // Erabiltzailearen kokapena begiratzen duen metodoa
    @SuppressLint("MissingPermission")
    private void initializeLocationListener() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    clienteLatitude = location.getLatitude();
                    clienteLongitude = location.getLongitude();
                    if (mMap != null) {
                        mMap.clear();
                        setUpMarkers();
                    }
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        locationClient = LocationServices.getFusedLocationProviderClient(MainActivity.progressBarGuneak.getContext());
    }

    // Kokapen eskaera onartuta badagoen konprobatzen duen metodoa, bestela eskera egingo du.
    private void localizacionAceptada() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            denaOndo();
        } else {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISO_LOCALIZACION);
        }
    }

    // Baimenak emanda badauden konprobatzen duen metodoa
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISO_LOCALIZACION) {
            if (grantResults.length != 1 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Joan ezarpenetara eta aktibatu eskuz", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISO_LOCALIZACION);
        } else {
            denaOndo();
        }

        if (zenbatu != 0) {
            guneBerria();
        }
        zenbatu++;
    }

    // Baimenak emanda badaude exekutatzen den metodoa
    private void denaOndo() {
        Toast.makeText(getActivity(), "Bahimenak onartuta", Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "Dena ondo", Toast.LENGTH_SHORT).show();
    }

    // Guneak kargatzen dituen metodoa
    private void setGuneak() {
        this.guneak = new ArrayList<>();

        MainActivity mainActivity = (MainActivity) getActivity();
        appDatabase = mainActivity.appDatabase;
        List<Gune> lguneak = appDatabase.guneDao().getAll();
        guneak = lguneak;

        setUpMarkers();
    }

    // Gunearen kokapena bilatzen duen metodoa
    private Gune bilatuguneaLokalizazioz(LatLng latLng) {
        for (Gune gune : guneak) {
            if (gune.latitude == latLng.latitude && gune.longitude == latLng.longitude) {
                return gune;
            }
        }
        return null;
    }

    // Agurra azaldu behar baden ala ez konprobatzen duen metodoa
    private void guneBerria() {
        boolean gune1 = spZornotza.getBoolean("gune1", false);
        boolean gune2 = spZornotza.getBoolean("gune2", false);
        boolean gune3 = spZornotza.getBoolean("gune3", false);
        boolean gune4 = spZornotza.getBoolean("gune4", false);
        boolean gune5 = spZornotza.getBoolean("gune5", false);
        boolean gune6 = spZornotza.getBoolean("gune6", false);
        boolean gune7 = spZornotza.getBoolean("gune7", false);
        boolean gune8 = spZornotza.getBoolean("gune8", false);
        boolean gune9 = spZornotza.getBoolean("gune9", false);

        if (gune1 && gune2 && gune3 && gune4 && gune5 && gune6 && gune7 && gune8 && !gune9) {
            Gune gunea9 = new Gune("Agurra", 9, -1, -1, 0);
            Intent intent = new Intent(getActivity(), GuneActivity.class);
            intent.putExtra("gunea", gunea9);
            startActivity(intent);
        }
    }

}