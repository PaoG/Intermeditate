package com.example.pao.favouritepoint;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.pao.favouritepoint.model.Category;
import com.example.pao.favouritepoint.model.Item;


import java.io.IOException;
import java.util.List;



public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void onSave(View view)
    {
        EditText location_tf=(EditText)findViewById(R.id.TFAddress);
        String location=location_tf.getText().toString();
        List<Address> addressList=null;
        if(location!=null || !location.equals(""))        {

            Geocoder geocoder= new Geocoder(this);
            try {
                addressList= geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address=addressList.get(0);
            LatLng latLng=new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(location));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            insertItem("Clothes",location, address.getLatitude(), address.getLongitude());


        }
    }


    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

       // mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setMyLocationEnabled(true);
       List<Item> items= getAll();
        //access via new for-loop
        for(Item item : items) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(item.latitud, item.latitud)).title(item.name));
        }

    }

    public void saveCurrentPosition(View view) {

        EditText location_tf=(EditText)findViewById(R.id.txtPlaceToSave);
        LatLng latLng=new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
        insertItem("Clothes",location_tf.getText().toString(), latLng.latitude, latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title(location_tf.getText().toString()));

    }

    public void insertItem(String categoryName, String placeName, double lat, double log ) {
        Category category = new Category(categoryName);
        category.save();

        Item item1 = new Item(placeName, category,lat, log);
        item1.save();

    }

    public static List<Item> getAll() {
        return new Select()
                .from(Item.class)
                .orderBy("Name ASC")
                .execute();
    }
}
