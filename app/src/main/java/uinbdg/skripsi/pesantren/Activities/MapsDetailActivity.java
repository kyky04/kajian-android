package uinbdg.skripsi.pesantren.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import uinbdg.skripsi.pesantren.Model.DataItemPesantren;
import uinbdg.skripsi.pesantren.R;

public class MapsDetailActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {
    private static final String TAG = "MapsDetailActivity";
    private GoogleMap mMap;
    private Marker myMarker;

    String myLat, myLong;

    String nama, jarak;

    private List<DataItemPesantren> pesantrenList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (getIntent().hasExtra("pesantren")) {
            pesantrenList = (List<DataItemPesantren>) getIntent().getSerializableExtra("pesantren");
        } else {
            myLong = getIntent().getStringExtra("long");
            myLat = getIntent().getStringExtra("lat");
            nama = getIntent().getStringExtra("nama");
            jarak = getIntent().getStringExtra("jarak");
            Log.d(TAG, "onCreate: " + myLong + " -- " + jarak);
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setOnMarkerClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        if (pesantrenList.size() > 0) {
            for (int i = 0; i < pesantrenList.size(); i++) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(pesantrenList.get(i).getLatitude()), Double.valueOf(pesantrenList.get(i).getLongitude()))).snippet(new DecimalFormat("##.##").format(pesantrenList.get(i).getJarak()) + " KM").title(pesantrenList.get(i).getNama()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(pesantrenList.get(i).getLatitude()), Double.valueOf(pesantrenList.get(i).getLongitude())), 8));
                mMap.setOnInfoWindowClickListener(this);
            }
        } else {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.valueOf(myLat), Double.valueOf(myLong)))
//                    .snippet(new DecimalFormat("##.##").format(jarak))
                    .title(nama).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(myLat), Double.valueOf(myLong)), 16f));
            mMap.setOnInfoWindowClickListener(this);
        }


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(myMarker)) {
            marker.showInfoWindow();
            Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.equals(myMarker)) {

        }
    }


}
