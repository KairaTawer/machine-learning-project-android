package kz.sdu.kairatawer.machinelearningproject;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class MainActivity extends AppCompatActivity {

    int positiveCount,negativeCount = 0;
    boolean isPositive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button mBtnPositive = (Button) findViewById(R.id.btn_positive);
        final Button mBtnNegative = (Button) findViewById(R.id.btn_negative);
        final Button mBtnResults = (Button) findViewById(R.id.btn_results);
        mBtnResults.setEnabled(false);

        new PositiveCountLoader().execute("https://intense-beach-16748.herokuapp.com/fetchpositivenews");
        new NegativeCountLoader().execute("https://intense-beach-16748.herokuapp.com/fetchnegativenews");

        mBtnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnPositive.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.background_green));
                mBtnNegative.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.background_gray));
                if (!mBtnResults.isEnabled()) {
                    int colorFrom = Color.parseColor("#BDBDBD");
                    int colorTo = Color.parseColor("#4A90E2");
                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                    colorAnimation.setDuration(250); // milliseconds
                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            mBtnResults.setBackgroundColor((int) animator.getAnimatedValue());
                        }

                    });
                    colorAnimation.start();
                    mBtnResults.setEnabled(true);
                }
                isPositive = true;
                mBtnResults.setText(String.format("Show result (%s)",positiveCount));
            }
        });

        mBtnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnNegative.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.background_red));
                mBtnPositive.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.background_gray));
                if (!mBtnResults.isEnabled()) {
                    int colorFrom = Color.parseColor("#BDBDBD");
                    int colorTo = Color.parseColor("#4A90E2");
                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                    colorAnimation.setDuration(250); // milliseconds
                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            mBtnResults.setBackgroundColor((int) animator.getAnimatedValue());
                        }

                    });
                    colorAnimation.start();
                    mBtnResults.setEnabled(true);
                }
                isPositive = false;
                mBtnResults.setText(String.format("Show result (%s)",negativeCount));
            }
        });

        mBtnResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewsActivity.class);
                intent.putExtra("isPositive",isPositive);
                startActivity(intent);
            }
        });
    }

    private class PositiveCountLoader extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONArray allNews = new JSONArray(result);
                positiveCount = allNews.length();
                Log.e("TAG_PositiveCount",String.valueOf(positiveCount));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class NegativeCountLoader extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONArray allNews = new JSONArray(result);
                negativeCount = allNews.length();
                Log.e("TAG_NegativeCount",String.valueOf(negativeCount));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
