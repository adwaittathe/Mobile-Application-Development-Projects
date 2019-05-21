package example.com.hw5_group12;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter( Context context, int resource,  List<News> objects) {
        super(context, resource, objects);

    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news= getItem(position);
        ViewHolder viewHolder;

        if(convertView==null)
        {
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.news_layout,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.image=(ImageView)convertView.findViewById(R.id.img_news);
            viewHolder.news_title=(TextView) convertView.findViewById(R.id.txt_news_title);
            viewHolder.news_author=(TextView) convertView.findViewById(R.id.txt_news_author);
            viewHolder.news_date=(TextView) convertView.findViewById(R.id.txt_news_date);
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.news_title.setText(String.valueOf(news.title).trim());

        viewHolder.news_author.setText(String.valueOf(news.author).trim());

        viewHolder.news_date.setText(String.valueOf(news.publishedAt.split("T")[0]).trim());
        if(!news.urlToImage.trim().equals("")) {
            Picasso.get().load(news.urlToImage.trim()).into(viewHolder.image);
        }

        return convertView;
    }

    private static class ViewHolder{
        ImageView image;
        TextView news_title;
        TextView news_author;
        TextView news_date;
    }




}
