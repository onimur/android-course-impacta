package com.onimus.courseimpacta.lab07.app.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.onimus.courseimpacta.R;
import com.onimus.courseimpacta.lab01.app.controller.MainUtilitiesActivity;
import com.onimus.courseimpacta.lab07.design.pattern.LocationAdapter;

import java.util.List;

public class GPSActivity extends MainUtilitiesActivity {

    private TextView latitudeView;
    private TextView longitudeView;
    private TextView enderecoView;

    private ImageButton btn_googlemaps;

    private LocationManager lm;
    private LocationListener ll;

    private String provedor;

    private void localizar(Location l) {
        if (l != null) {
            final double latitude = l.getLatitude();
            final double longitude = l.getLongitude();
            Resources r = getResources();

            latitudeView.setText(String.format("%s %s", r.getString(R.string.lab07_tv_latitude), latitude));
            longitudeView.setText(String.format("%s %s", r.getString(R.string.lab07_tv_longitude), longitude));

            btn_googlemaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String latitudeS = String.valueOf(latitude);
                    String longitudeS = String.valueOf(longitude);
                    Uri gmmIntentUri = Uri.parse("geo:"+latitudeS+","+longitudeS);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    } else {
                        Toast.makeText(getBaseContext(), getString(R.string.lab07_installgoogle), Toast.LENGTH_SHORT).show();
                    }


                }
            });



            try {
                List<Address> enderecos = new Geocoder(this).getFromLocation(latitude, longitude, 1);
                if (enderecos.isEmpty()) {
                    enderecoView.setText(R.string.lab07_tv_semEndereco);
                } else {
                    Address endereco = enderecos.get(0);
                    enderecoView.setText(String.format("%s, %s, %s, %s, %s", endereco.getThoroughfare(),
                            endereco.getSubThoroughfare(),
                            endereco.getSubLocality(),
                            endereco.getSubAdminArea(),
                            endereco.getCountryName()));
                }
            } catch (Exception cause) {
                Log.e("GPS", "Problemas", cause);
            }
        } else {
            latitudeView.setText(R.string.lab07_tv_semProvedor);
            longitudeView.setText(null);
            enderecoView.setText(null);
        }
    }

    private class LocationAction extends LocationAdapter {

        @Override
        public void onLocationChanged(Location location) {
            super.onLocationChanged(location);

            localizar(location);
        }
    }

    @SuppressLint("MissingPermission")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab07_gps);
        latitudeView =  findViewById(R.id.lab07_latitude);
        longitudeView =  findViewById(R.id.lab07_longitude);
        enderecoView =  findViewById(R.id.lab07_tv_endereco);
        btn_googlemaps = findViewById(R.id.lab07_btn_googlemaps);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        ll = new LocationAction();
        provedor = lm.getBestProvider(new Criteria(), false);
        if (provedor != null) {
            localizar(lm.getLastKnownLocation(provedor));
        }


    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();

        lm.requestLocationUpdates(provedor, 5000, 0, ll);
    }

    protected void onPause() {
        super.onPause();

        lm.removeUpdates(ll);
    }

}
