package pre.pkh.mobile_programming_5_gps;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView logView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main", "onCreate");

        logView = (TextView) findViewById(R.id.log);
        logView.setText("GPS 가 잡혀야 좌표가 구해짐");

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // GPS 프로바이더 사용가능여부
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 위성의 gps 이용해서 위치정보를 얻을 수 있는 기능
        // 네트워크 프로바이더 사용가능여부, 위성이 아니라 네트워크같은 와이파이
 //       boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
// gps는 터널 x 자동차 위치에 속도에 따라 추측하는 것임.
        Log.d("Main", "isGPSEnabled=" + isGPSEnabled);
//        Log.d("Main", "isNetworkEnabled=" + isNetworkEnabled);

        LocationListener locationListener = new LocationListener() { // 이동할 때 마다 위치 바뀌는것.
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();

                logView.setText("latitude: " + lat + ", longitude: " + lng);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                logView.setText("onStatusChanged");
            }

            public void onProviderEnabled(String provider) {
                logView.setText("onProviderEnabled");
            }

            public void onProviderDisabled(String provider) {
                logView.setText("onProviderDisabled");
            }
        };

        // Register the listener with the Location Manager to receive location updates

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        // 처음 0 은 몇밀리 세컨드 있다가 호출해라. 지금 나와 0거리에 있는 경우
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        // 수동으로 위치 구하기
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            double lng = lastKnownLocation.getLatitude();
            double lat = lastKnownLocation.getLatitude();
            Log.d("Main", "longtitude=" + lng + ", latitude=" + lat);
            logView.setText("longtitude=" + lng + ", latitude=" + lat);
        }
    }
}
