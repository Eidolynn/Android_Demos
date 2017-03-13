package com.ihack.android.geofencingexample;

/*This example shows you how to set up Geofencing capabilities to your app.
* Geofencing will be done by utilizing the LocationServices API from GoogleApiClient.
* First, add all the necessary dependencies in order to get the GoogleMapFragement up and running. See GoogleMapObjectTest.
* Once that's done, we will begin setting up the necessary dependencies for GooglePlayServices.
* We will be setting up a manually managed connection with GooglePlayServices.
*
* Add the necessary intent service as a child to the application section in the AndroidManifest.
* Implement GoogleApiClient.ConnectionCallbacks and GoogleApiClient.OnConnectionFailedListener
*  (The necessary imports should be automatically added depending on your ambiguous settings..check out the imports sections)
*
*  Set up a manually managed connection with GPlayServices (see ManualGPlayConnection Sections)
*
*  Initialize a private GoogleApiClient object.
*  Add these methods; onConnected, onConnectionSuspended, and onConnectionFailed
*  Create a GoogleAPI LocationServices instance.
* */

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    //Global Declarations~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~GLOBAL
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation; //
    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;
    // Bool to be displayed if the user recreates the activity and starts another connection attempt
    private static final String STATE_RESOLVING_ERROR = "resolving_error";
    //Const int for permissions request codes
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0001;
    //End Global Declarations~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~GLOBAL

    //To keep track of the mRseolvingError boolean across activity restarts(such as when the users rotates the screen)
    // .putBoolean is used to saveBoolean in the activity's saved instance data using onSaveInstanceState
    //The saved state is later recovered in the onCreate method
    //ManualGPlayConnection
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_RESOLVING_ERROR, mResolvingError);
    }//end onSaveInstanceState

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create GoogleAPI's LocationServices Instance
        if(mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }//fi end creat GoogleAPI's LocationServices Instance

        //recover the previously saved state of mResolvingError
        mResolvingError = savedInstanceState != null && savedInstanceState.getBoolean(STATE_RESOLVING_ERROR, false);

    }//end onCreate method

    //called when the client is ready
    @Override
    public void onConnected(Bundle bundle) {

        //Check if the app has permission to obtain the user's FINE LOCATION
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            //Did the user Deny? Should we show an explanation of why we need this access/permission
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)){
                //TODO: Show explanantion to user *asynchronously*
            }//end If show explanation
            else{

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                // MY_PERMISSIONS_REQUEST_ACCESS... is an
                // app-defined int constant. The callback method gets the
                // result of the request.

            }//end no explanation needed

        }//end permission check for FINE     LOCATION

        //getLastLocation returns a Location object that contains coordinates of geographical. Return may be null --location is not avail.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation != null){
            //Set the text of the couple of textviews in act._main.xml to display the coordinates.
            TextView mLatitudeText = (TextView) findViewById(R.id.lat_text);
            TextView mLongitudeText = (TextView) findViewById(R.id.long_text);
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }//fi

    }//end onConnected

    @Override
    public void onConnectionSuspended(int i) {

    }
    //to connect, call connect() from activities OnStart method
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    //to disconnect, call disconnect() from activities OnStart method
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }//end onStop

    // When an unresolvable error has occurs and a connection to Google APIs
    // can not be established. onConnectionFailed is called. Error Dialog is presented.
    //ManualGPlayConnection
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if(mResolvingError) {
            //already trying to resolve error
            return;
        }//fi

        else if (connectionResult.hasResolution()) {
            try{
                mResolvingError = true;
                connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch(IntentSender.SendIntentException e){
                //error with the resolution intent...try again
                mGoogleApiClient.connect();
            }//catch
        }//end else if 1
        else{
            //show dialog with GoogleApiAvailability.getErrorDialog()
            showErrorDialog(connectionResult.getErrorCode());
            mResolvingError = true;
        }//end else if 2

    }//end onConnectionFailed

    //ManualGPlayConnection
    private void showErrorDialog(int errorCode){
        //Creates Fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        //Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "Wait-- Something is not right here");
    }//end showErrorDialog

    //ManualGPlayConnection
    public void onDialogDismissed(){
        mResolvingError = false;
    }//end onDialogDismissed

    //Fragment that displays an error dialog
    //ManualGPlayConnection
    public static class ErrorDialogFragment extends DialogFragment{
        public ErrorDialogFragment(){}

        public Dialog onCreateDialog(Bundle savedInstanceState){
            //get error cod and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);

        }//end onCreateDialog

        @Override
        public void onDismiss(DialogInterface dialog) {
            ((MainActivity) getActivity()).onDialogDismissed();
        }//end onDismiss
    }//end ErrorDialogFragment that displays an error dialog

    //After users completes dialog provided by startResolutionForResult() or dismissed the message
    //provided by GoogleApiAvailability.getErrorDialog(), MainActivity receives the onActivityResult callback
    //wth the RESULT_OK result code. The app can now call connect again...
    //ManualGPlayConnection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            mResolvingError = false;
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mGoogleApiClient.isConnecting() &&
                        !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }//fi GoogleApiClient is neither trying to connect nor  connected already
            }//fi
        }
    }//end onActivityResult

    //Handle the permission request response...
    //When the app requests permissions in onConnected by calling requestPermissions(),
    //the onRequestPermissionsResult() method is invoked.
    //Must Override it here to find out the results of the request.
    //This callback is passed the same request code that was passed the requestPermissions()
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){

        switch(requestCode){
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                //If request is cancelled, the result arrays are empty
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    //Permission was granted! Lets penetrate this asshole.


                }//end if
                else{
                    //Man fuck that user. Permission Denied.
                    Toast.makeText(this, "Fine location denied by user", Toast.LENGTH_LONG).show();
                }//end else
                return;
            }//end case

            // other case lines here to check for other permissions
            //When permissions is granted to a permission then when you request another permission in the same group the system
                //automatically grants them.
                //The user only needs to grant permission once for each permission group,
            // The app should still explicitly request for each permission even if the user has granted one in its same group
            //The grouping of permissions may change in future android releases.

        }//end switch

    }//end onRequestPermissionResults

}//end MainActivity
