package pt.makerstudio.outlet;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static String TAG = MainActivity.class.getName();
    public static String DOMAIN = "https://connected-outlet.glitch.me";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void turnOutletOn(View view) {
        Log.d(TAG, "On");
        new RequestTask().execute("/on");
    }

    public void turnOutletOff(View view) {
        Log.d(TAG, "Off");
        new RequestTask().execute("/off");
    }

    class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            try {
                URL url = new URL(DOMAIN + uri[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "Request accepted.");
                } else {
                    Log.w(TAG, "Request denied by server.");
                }
                urlConnection.disconnect();
            } catch (MalformedURLException e) {
                Log.e(TAG, "URL is not valid.", e);
            } catch(IOException e) {
                Log.e(TAG, "Could not open socket.", e);
            }

            return "Done";
        }
    }
}
