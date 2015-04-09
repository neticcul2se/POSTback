package pae.com.wa.postback;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {


    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // txtResult
        final TextView result = (TextView)findViewById(R.id.txtResult);
        // btnGetData
        final Button getData = (Button) findViewById(R.id.btnGetData);
        final EditText txtA = (EditText)findViewById(R.id.txtA);
        final EditText txtB = (EditText)findViewById(R.id.txtB);
        // Perform action on click
        getData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // String url = "http://httpbin.org/post";
                //String resultServer  = getHttpGet(url);
                //result.setText(resultServer);

                String url = "http://httpbin.org/post";

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("strA", txtA.getText().toString()));
                params.add(new BasicNameValuePair("strB", txtB.getText().toString()));

                String resultServer  = getHttpGet(url, params);
                result.setText(resultServer);
            }
        });

    }


    public String getHttpGet(String url, List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httPost = new HttpPost(url);



        try {
            httPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }


}