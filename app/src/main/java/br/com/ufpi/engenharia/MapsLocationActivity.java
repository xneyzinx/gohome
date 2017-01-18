package br.com.ufpi.engenharia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.ufpi.engenharia.controle.ControleImovel;

/*
* Método para mostrar apenas 1 imóvel selecionado no mapa
*
* */

public class MapsLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ControleImovel controleImovel = new ControleImovel(MapsLocationActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /*
    * Método para mostrar o imóvel no mapa
    * @param googleMap
    * */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double dLatitude;
        double dLongitude;
        String nomeI;

        Intent iR = this.getIntent();
        dLatitude = iR.getDoubleExtra("doubleLatitude", 0.0);
        dLongitude = iR.getDoubleExtra("doubleLongitude", 0.0);
        nomeI = iR.getStringExtra(ListaImoveisActivity.nomeImovel);

        // Add a marker in Sydney and move the camera
        LatLng latlongImovel = new LatLng(dLatitude, dLongitude);

        mMap.addMarker(new MarkerOptions().title(nomeI).snippet("").position(latlongImovel));
//        mMap.moveCamera(CameraUpdateFactory.zoomTo(4.0f));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlongImovel, 17.0f));
    }
}
