package com.example.elle.assignment_4;

import android.content.res.TypedArray;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        LocationListener
{
    private static final String TAG = "Test" ;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
    public static ArrayList<Question> mQuestionsList = new ArrayList<>();
    public int mIndex = 0; //Int index för att ha koll på vilken plats bland frågorna vi är på
    public int mIndexM = 0; //Int index för att ha koll på vilken marker vi är på
    ArrayList<Marker> mPositions = new ArrayList<>() ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        TypedArray questions = getResources().obtainTypedArray(R.array.questions);

        for (int i = 0; i < questions.length(); i++ ){
            TypedArray questionArray = getResources().obtainTypedArray(questions.getResourceId(i, 0));
            Question question = new Question(questionArray.getString(0), questionArray.getString(1), questionArray.getString(2), questionArray.getString(3), questionArray.getInt(4, 0));
            mQuestionsList.add(question);
            questionArray.recycle();
        }
        questions.recycle();


        setUpMapIfNeeded();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
        mGoogleApiClient.connect();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        WelcomeDialog mWelcome = new WelcomeDialog();
        mWelcome.show(ft, "welcome");

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

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

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        UiSettings mUIsettings = mMap.getUiSettings();
        mUIsettings.setZoomControlsEnabled(true);
        mUIsettings.setMyLocationButtonEnabled(true);

        LatLng start = new LatLng(55.545685, 13.106897);
        LatLng challenge_one = new LatLng(55.545201, 13.107554);
        LatLng challenge_two = new LatLng(55.545375, 13.106475);
        LatLng challenge_three = new LatLng(55.545096, 13.107355);
        LatLng challenge_four = new LatLng(55.545096, 13.108342);

        mPositions.add(mMap.addMarker(new MarkerOptions()
                .position(start)
                .title("Start")
                .snippet("This is where the treasurehunt starts")
                .visible(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_home))));

        mPositions.add(mMap.addMarker(new MarkerOptions()
                .position(challenge_one)
                .title("Challenge")
                .snippet("This is the first challenge")
                .visible(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_challenge))));

        mPositions.add(mMap.addMarker(new MarkerOptions()
                .position(challenge_two)
                .title("Challenge")
                .snippet("This is the second challenge")
                .visible(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_challenge))));

        mPositions.add(mMap.addMarker(new MarkerOptions()
                .position(challenge_three)
                .title("Challenge")
                .snippet("This is the third challenge")
                .visible(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_challenge))));

        mPositions.add(mMap.addMarker(new MarkerOptions()
                .position(challenge_four)
                .title("Challenge")
                .snippet("This is the fourth challenge")
                .visible(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_challenge))));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(start));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));

        onUserSelectValue(false);


    }

    public void onUserSelectValue(boolean answer){
        if (answer == true) {
            switch (mIndex) {
                case 1:
                    mPositions.get(mIndex+1).setVisible(true);
                    mPositions.get(mIndex).setVisible(false);

                    break;
                case 2:
                    mPositions.get(mIndex+1).setVisible(true);
                    mPositions.get(mIndex).setVisible(false);

                    break;
                case 3:
                    mPositions.get(mIndex+1).setVisible(true);
                    mPositions.get(mIndex).setVisible(false);

                    break;
                case 4:
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    FinishDialog mDialog = new FinishDialog();
                    mDialog.show(ft, "Finish Dialog");
                    setUpMap();
                default:
                    mIndex = 0;
            }

            }
        else {
            mIndex = 0;
            mIndexM = 0;
            mPositions.get(mIndex).setVisible(true);
            mPositions.get(mIndex+1).setVisible(false);
            mPositions.get(mIndex+2).setVisible(false);
            mPositions.get(mIndex+3).setVisible(false);
            mPositions.get(mIndex+4).setVisible(false);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Connected");
        //Get updated every time a movement is done - when somebody moves around. A location request
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        Vibrator mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        MediaPlayer mMediaPlayer = (MediaPlayer.create(this, R.raw.dest));

        Location mLocation = new Location("mLocation");
        Marker curMarker = mPositions.get(mIndexM);
        mLocation.setLongitude(curMarker.getPosition().longitude);
        mLocation.setLatitude(curMarker.getPosition().latitude);

        float distance = 0;
        distance = location.distanceTo(mLocation);

        if (distance < 10){
            mVibrator.vibrate(100); //Om avstånd är mindre än 10 meter, vibrerar telefonen
            mMediaPlayer.start(); //Om avstånd är mindre än 10 meter, spelas en trudelutt.
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (curMarker.getTitle().equals("Start")){
                StartClickDialog mDialog = new StartClickDialog();
                mDialog.show(ft, "Start Dialog");
                curMarker.setVisible(false);
                mPositions.get(mIndexM+1).setVisible(true);

            }else {
                ChallengeClickDialog mDialog = new ChallengeClickDialog();
                mDialog.show(ft, "Challenge Dialog");

            }
        }

    }
}
