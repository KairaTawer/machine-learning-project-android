package kz.sdu.kairatawer.machinelearningproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alertdialogpro.AlertDialogPro;
import com.bumptech.glide.Glide;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class DetailedNewsActivity extends AppCompatActivity {

    News n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_news);

        String[] news = getIntent().getStringArrayExtra("News");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        n = new News(news[0],news[1],news[2],news[3],news[4],news[5]);

        ImageView mImage = (ImageView) findViewById(R.id.image);
        TextView mDate = (TextView) findViewById(R.id.tv_date);
        TextView mTitle = (TextView) findViewById(R.id.tv_title);
        TextView mShort = (TextView) findViewById(R.id.tv_short_info);
        TextView mDescription = (TextView) findViewById(R.id.tv_description);
        Button mReport = (Button) findViewById(R.id.btn_report);

        if(!n.getLink().equals("")) Glide.with(this).load(n.getLink()).centerCrop().into(mImage);
        mDate.setText(n.getDate() + "  |");
        mTitle.setText(n.getTitle());
        mShort.setText(n.getShort_info());
        mDescription.setText(n.getDescription());

        mReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialogPro.Builder builder = new AlertDialogPro.Builder(DetailedNewsActivity.this);
                builder.
                        setTitle("Report an error").
                        setMessage("Are you sure that post is misclassified?").
                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                postData();
                                Snackbar.make(v,"Your report sent", BaseTransientBottomBar.LENGTH_LONG).show();
                            }
                        }).
                        setNegativeButton("No", null).
                        show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(DetailedNewsActivity.this, NewsActivity.class));
                return(true);
        }

        return(super.onOptionsItemSelected(item));
    }

    public void postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://intense-beach-16748.herokuapp.com/postjson");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("title", n.getTitle()));
            nameValuePairs.add(new BasicNameValuePair("short_info", n.getShort_info()));
            nameValuePairs.add(new BasicNameValuePair("label", n.getLabel()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        } catch (IOException e) {

        }
    }

}
