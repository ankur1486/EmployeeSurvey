package src.com.employeesurvey.util;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class LocationUtils {
	// Milliseconds per second
	private static final int MILLISECONDS_PER_SECOND = 1000;

	// The update interval
	private static final int UPDATE_INTERVAL_IN_SECONDS = 100;

	// A fast interval ceiling
	private static final int FAST_CEILING_IN_SECONDS = 1;

	// Update interval in milliseconds
	private static final long UPDATE_INTERVAL_IN_MILLISECONDS = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;

	// A fast ceiling of update intervals, used when the app is visible
	private static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = MILLISECONDS_PER_SECOND * FAST_CEILING_IN_SECONDS;

	private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 900;

	private static final String NO_ADDRESS = "No Address found";

	private LocationClient mLocationClient;
	private Context mContext;
	private String TAG = LocationUtils.class.getName();
	private LocationRequest mLocationRequest;
	private LocationListener mLocationListener;

	public LocationUtils(Context context, LocationListener locationListener) {
		this.mContext = context;
		this.mLocationListener = locationListener;
		mLocationClient = new LocationClient(context, mConnectionCallbacks, mConnectionFailedListener);
		createLocationRequest();

	}

	/**
	 * call this method on onStart() in activity
	 */
	public void connectLocationService() {
		mLocationClient.connect();
	}

	/**
	 * call this method on onStop() in activity
	 */
	public void disconnectLocationService() {
		if (mLocationClient.isConnected()) {
			stopLocationUpdates();
		}
		mLocationClient.disconnect();
	}

	/**
	 * create a location request
	 */
	private void createLocationRequest() {
		mLocationRequest = LocationRequest.create();
		// Set the update interval
		mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
		// Use high accuracy
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		// Set the interval Fastest interval
		mLocationRequest.setFastestInterval(FAST_INTERVAL_CEILING_IN_MILLISECONDS);
	}

	/**
	 * start periodic location updates
	 */
	private void startLocationUpdates() {
		mLocationClient.requestLocationUpdates(mLocationRequest, mLocationListener);
	}

	/**
	 * stop periodic location updates
	 */
	private void stopLocationUpdates() {
		mLocationClient.removeLocationUpdates(mLocationListener);
	}

	/**
	 * Location service calls when location is connected or disconnected
	 */
	private ConnectionCallbacks mConnectionCallbacks = new ConnectionCallbacks() {

		@Override
		public void onDisconnected() {
		}

		@Override
		public void onConnected(Bundle bundle) {
			if (servicesConnected()) {
				startLocationUpdates();
			}
		}
	};

	/**
	 * Location service calls if an error occurs while connecting to location client
	 */
	private OnConnectionFailedListener mConnectionFailedListener = new OnConnectionFailedListener() {

		@Override
		public void onConnectionFailed(ConnectionResult connectionResult) {

			if (connectionResult.hasResolution()) {
				try { // Start an Activity that tries to resolve the error
					connectionResult.startResolutionForResult((Activity) mContext,
							CONNECTION_FAILURE_RESOLUTION_REQUEST);
				} catch (IntentSender.SendIntentException e) { // Log the error
					e.printStackTrace();
				}
			} else {
				connectLocationService();
			}

		}
	};

	/**
	 * Verify that Google Play services is available before making a request.
	 * 
	 * @return true if Google Play services is available, otherwise false
	 */
	private boolean servicesConnected() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);
		if (ConnectionResult.SUCCESS == resultCode) {
			return true;
		} else {
			GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) mContext, 0).show();
			return false;
		}

	}

	/**
	 * @return Location object for lastLocation
	 */
	public Location getLocation() {
		Location location = mLocationClient.getLastLocation();
		return (location != null ? location : null);
	}

	/**
	 * getAddress from location
	 */
	public void getAddressFromLocation(/* AddressListener mAddressListener, */
	/* boolean isShowProgressDialog */) {
		// if(mLocationClient.isConnected()) {
		// Location location = mLocationClient.getLastLocation();
		// if (location != null) {
		// if (Geocoder.isPresent()) {
		// GetAddressTask addressTask = new GetAddressTask(
		// /* mAddressListener, isShowProgressDialog */);
		// addressTask.execute(location);
		// } else {
		// GetAddressFromServerTask addressTask = new GetAddressFromServerTask(
		// /* mAddressListener, isShowProgressDialog */);
		// addressTask.execute(location);
		// }
		// } else {
		// System.out.println("Service not available");
		// // mAddressListener.errorMessage("Service not available");
		// }
		// }
	}

	private class GetAddressTask extends AsyncTask<Location, Void, String> {

		// private AddressListener addressListener;
		// private ProgressDialog mProgressDialog;
		// private boolean isShowProgressDialog;
		private double latitude;
		private double longitude;

		public GetAddressTask(/* AddressListener addressListener, */
		/* boolean isShowProgressDialog */) {
			// this.addressListener = addressListener;
			// this.isShowProgressDialog = isShowProgressDialog;
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(Location... params) {
			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
			int maxResults = 1;

			Location location = params[0];
			latitude = location.getLatitude();
			longitude = location.getLongitude();

			List<Address> addresses = null;
			try {
				addresses = geocoder.getFromLocation(latitude, longitude, maxResults);
			} catch (IOException e) {
				//e.printStackTrace();
				return fetchCityNameUsingGoogleMap(String.valueOf(latitude), String.valueOf(longitude));
			}

			int addressesSize = addresses.size();// 0;
			if (addressesSize > 0) {
				// Get the first address
				Address address = addresses.get(0);
				String addressText = String.format("%s, %s, %s",
				// If there's a street address, add it
						address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
						// Locality is usually a city
						address.getLocality(),
						// The country of the address
						address.getCountryName());
				if (!TextUtils.isEmpty(address.getCountryCode())) {
					// Return the text
					// return addressText +address.getCountryCode();
					return address.getCountryCode();
				} else {// i.e., Geocoder failed

					return fetchCityNameUsingGoogleMap(String.valueOf(latitude), String.valueOf(longitude));
				}
			} else {// i.e., Geocoder failed

				return fetchCityNameUsingGoogleMap(String.valueOf(latitude), String.valueOf(longitude));
			}
		}

		// search for locality and sublocality

		@Override
		protected void onPostExecute(String result) {

			Log.i(TAG, "result :" + result);
		}

	}

	private class GetAddressFromServerTask extends AsyncTask<Location, Void, String> {

		// private AddressListener addressListener;
		// private ProgressDialog mProgressDialog;
		private boolean isShowProgressDialog;

		public GetAddressFromServerTask(/*
										 * AddressListener addressListener, boolean isShowProgressDialog
										 */) {
			// this.addressListener = addressListener;
			// this.isShowProgressDialog = isShowProgressDialog;
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(Location... params) {
			Location location = params[0];
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();

			// return fetchCityNameUsingGoogleMap(String.valueOf(latitude),
			// String.valueOf(longitude));

			return "latitude :" + latitude + "longitude :" + longitude;
		}

		// search for locality and sublocality

		@Override
		protected void onPostExecute(String result) {
			Log.i(TAG, "latitude ***** longitude :" + result);
		}

	}

	private String cityName = null;

	private String fetchCityNameUsingGoogleMap(String latitude, String longitude) {

		String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude
				+ "&sensor=false&language=" + Locale.getDefault();

		Log.i(TAG, "Geolocation URl :" + googleMapUrl);
		AndroidHttpClient ANDROID_HTTP_CLIENT = AndroidHttpClient.newInstance(LocationUtils.class.getName());

		try {
			JSONObject googleMapResponse = new JSONObject(ANDROID_HTTP_CLIENT.execute(new HttpGet(googleMapUrl),
					new BasicResponseHandler()));

			// many nested loops.. not great -> use expression instead
			// loop among all results
			JSONArray results = (JSONArray) googleMapResponse.get("results");
			for (int i = 0; i < results.length(); i++) {
				// loop among all addresses within this result
				JSONObject result = results.getJSONObject(i);
				if (result.has("address_components")) {
					JSONArray addressComponents = result.getJSONArray("address_components");
					// loop among all address component to find a 'locality'
					// or 'sublocality'
					for (int j = 0; j < addressComponents.length(); j++) {
						JSONObject addressComponent = addressComponents.getJSONObject(j);
						if (result.has("types")) {
							JSONArray types = addressComponent.getJSONArray("types");

							for (int k = 0; k < types.length(); k++) {
								if ("country".equals(types.getString(k))) {
									if (addressComponent.has("short_name")) {
										cityName = addressComponent.getString("short_name");
										break;
									}
								}
								if ("political".equals(types.getString(k))) {
									if (addressComponent.has("short_name")) {
										cityName = addressComponent.getString("short_name");
										break;
									}
								}
							}

						}
					}
					if (cityName != null) {
						// Log.i(TAG, "city name returned :" + cityName);
						return cityName;
					}
				}
			}
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}finally{
			ANDROID_HTTP_CLIENT.close();
		}
		return NO_ADDRESS;
	}

}
