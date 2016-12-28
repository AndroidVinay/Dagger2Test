package vinay.com.dagger2test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainActivityFragment
		.MainActivityFragmentHost, LocationListener {

	public static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
	public static int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 2;

	@Inject
	LocationManager locationManager;
	@Inject
	List<Location> allLocations;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getAppComponent().inject(this);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

	}

	@Override
	protected void onResume() {
		super.onResume();

		List<String> providers = locationManager.getAllProviders();
		boolean atLeastOneProviderEnabled = false;
		for (String provider : providers) {
			if (locationManager.isProviderEnabled(provider)) {

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					if (ActivityCompat.checkSelfPermission(this, Manifest.permission
							.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
							ActivityCompat.checkSelfPermission(this, Manifest.permission
									.ACCESS_COARSE_LOCATION) != PackageManager
									.PERMISSION_GRANTED) {

						requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
								MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
						return;
					}
					if (ActivityCompat.checkSelfPermission(this, Manifest.permission
							.ACCESS_COARSE_LOCATION) != PackageManager
							.PERMISSION_GRANTED) {
						requestPermissions(new String[]{Manifest.permission
										.ACCESS_COARSE_LOCATION},
								MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
						return;
					}

				}
				locationManager.requestLocationUpdates(provider, 0, 0, this);
				atLeastOneProviderEnabled = true;
			}
		}

		if (!atLeastOneProviderEnabled) {
			findMainFragment().showMessage("No location providers enabled.");
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
				PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
					MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		locationManager.removeUpdates(this);

	}

	@Override
	public void inject(MainActivityFragment fragment) {
		getAppComponent().inject(fragment);
	}

	private ApplicationComponent getAppComponent() {
		return ((MainApplication) getApplication()).getComponent();
	}

	private void locationUpdated(Location location) {
		allLocations.add(location);

		String message = String.format(
				"Total location updates: %d.  You are now at: %.2f, %.2f",
				allLocations.size(),
				location.getLatitude(),
				location.getLongitude());
		findMainFragment().showMessage(message);
	}

	public void onLocationChanged(Location location) {
		locationUpdated(location);
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// Don't care
	}

	public void onProviderEnabled(String provider) {
		// Don't care
	}

	public void onProviderDisabled(String provider) {
		// Don't care
	}

	private MainActivityFragment findMainFragment() {
		return (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
