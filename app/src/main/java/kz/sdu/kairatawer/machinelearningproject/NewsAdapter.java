package kz.sdu.kairatawer.machinelearningproject;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by асус on 18.12.2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private ArrayList<News> newsList;
    private Context context;

    public NewsAdapter(Context c, ArrayList<News> newsList) {
        this.context = c;
        this.newsList = newsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.date.setText(news.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailedNewsActivity.class);
                String[] newsInfo = new String[6];
                newsInfo[0] = news.getDate();
                newsInfo[1] = news.getTitle();
                newsInfo[2] = news.getShort_info();
                newsInfo[3] = news.getDescription();
                newsInfo[4] = news.getLink();
                newsInfo[5] = news.getLabel();
                intent.putExtra("News",newsInfo);
                context.startActivity(intent);
            }
        });
        if(!news.getLink().equals("")) {
            Glide.with(context).load(news.getLink()).centerCrop().into(holder.image);
        } else {
            Glide.with(context).load(R.drawable.bnews).centerCrop().into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }
}
