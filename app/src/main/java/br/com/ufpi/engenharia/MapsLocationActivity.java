package br.com.ufpi.engenharia;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.ufpi.engenharia.R;

public class MapsLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Shot the map for the current location
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng depcomp = new LatLng(-5.056294, -42.789921);
        mMap.addMarker(new MarkerOptions().title("Departamento de Computação").snippet("Prédio do Departamento de Computação").position(depcomp));
//        mMap.moveCamera(CameraUpdateFactory.zoomTo(4.0f));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(depcomp, 17.0f));
    }
}
