package kz.sdu.kairatawer.machinelearningproject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.baoyz.widget.PullRefreshLayout;

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

public class NegativeFragment extends Fragment {

    ArrayList<News> newsList = new ArrayList<>();
    RecyclerView mRecyclerNews;
    ProgressBar mProgressBar;
    PullRefreshLayout mLayout;

    public NegativeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_negative, container, false);

        mLayout = view.findViewById(R.id.pull_refresh_layout);

        mRecyclerNews = (RecyclerView) view.findViewById(R.id.rv_news);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_news);

        new NewsLoader().execute("https://intense-beach-16748.herokuapp.com/fetchnegativenews");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerNews.setLayoutManager(layoutManager);

        mLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new NewsLoader().execute("https://intense-beach-16748.herokuapp.com/fetchnegativenews");
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private class NewsLoader extends AsyncTask<String, String, String> {

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
                    buffer.append(line + "\n");
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
                newsList.clear();
                JSONArray allNews = new JSONArray(result);
                for (int i = 0; i < allNews.length(); i++) {
                    JSONObject object = allNews.getJSONObject(i);
                    Log.e("Iterator working",object.getString("title"));
                    newsList.add(new News(
                            object.getString("date"),
                            object.getString("title"),
                            object.getString("short_info"),
                            object.getString("description"),
                            object.getString("link"),
                            "Negative"
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mProgressBar.setVisibility(View.GONE);
            mRecyclerNews.setVisibility(View.VISIBLE);
            Log.e("Recycler Inflated",String.valueOf(newsList.size()));
            NewsAdapter mAdapter = new NewsAdapter(getActivity(), newsList);
            mRecyclerNews.setAdapter(mAdapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerNews.setLayoutManager(layoutManager);
            mRecyclerNews.setItemAnimator(new DefaultItemAnimator());
            mLayout.setRefreshing(false);

        }
    }

}
