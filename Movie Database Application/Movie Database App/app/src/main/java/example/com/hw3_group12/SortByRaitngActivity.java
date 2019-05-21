package example.com.hw3_group12;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ListIterator;

public class SortByRaitngActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_by_raitng);
        final ImageView img_prev = (ImageView) findViewById(R.id.imgPrevious2);
        final ImageView img_first = (ImageView) findViewById(R.id.imgFirst2);
        final ImageView img_next = (ImageView) findViewById(R.id.imgNext2);
        final ImageView img_last = (ImageView) findViewById(R.id.imgLast2);
        final Button btnFinish = (Button) findViewById(R.id.btnFinish2);
        final TextView Movie_title = (TextView) findViewById(R.id.main_movie_title2);
        final TextView Movie_Desc = (TextView) findViewById(R.id.main_MovieDescription2);
        final TextView Movie_Genre = (TextView) findViewById(R.id.main_genre2);
        final TextView Movie_Rating = (TextView) findViewById(R.id.main_rating2);
        final TextView Movie_Year = (TextView) findViewById(R.id.main_Year2);
        final TextView Movie_IMDB = (TextView) findViewById(R.id.main_IMDB2);

        if (getIntent() != null && getIntent().getExtras() != null) {
            final ArrayList<Movie> arrayMov = getIntent().getParcelableArrayListExtra(MainActivity.ARRAYLIST_MOVIE);
            final ListIterator<Movie> list = arrayMov.listIterator();
            final Movie first = list.next();
            Movie_title.setText(String.valueOf(first.Name));
            Movie_Desc.setText(String.valueOf(first.Movie_Description));
            Movie_Desc.setMovementMethod(new ScrollingMovementMethod());
            Movie_Genre.setText(String.valueOf(first.Genre));
            Movie_Rating.setText(String.valueOf(first.Rating + "/5"));
            Movie_Year.setText(String.valueOf(first.Year));
            Movie_IMDB.setText(String.valueOf(first.IMDB));
            img_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.hasNext()) {
                        //list.next();
                        Movie new_Movie = list.next();
                        if (new_Movie.Name.equals(Movie_title.getText().toString().trim())) {
                            if (list.hasNext()) {
                                Movie next = list.next();
                                Movie_title.setText(String.valueOf(next.Name));
                                Movie_Desc.setText(String.valueOf(next.Movie_Description));
                                Movie_Genre.setText(String.valueOf(next.Genre));
                                Movie_Rating.setText(String.valueOf(next.Rating + "/5"));
                                Movie_Year.setText(String.valueOf(next.Year));
                                Movie_IMDB.setText(String.valueOf(next.IMDB));
                            }
                        } else {
                            Movie_title.setText(String.valueOf(new_Movie.Name));
                            Movie_Desc.setText(String.valueOf(new_Movie.Movie_Description));
                            Movie_Genre.setText(String.valueOf(new_Movie.Genre));
                            Movie_Rating.setText(String.valueOf(new_Movie.Rating + "/5"));
                            Movie_Year.setText(String.valueOf(new_Movie.Year));
                            Movie_IMDB.setText(String.valueOf(new_Movie.IMDB));
                        }
                    }
                }
            });
            img_prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.hasPrevious()) {
                        Movie new_Movie = list.previous();
                        if (new_Movie.Name.equals(Movie_title.getText().toString().trim())) {
                            if (list.hasPrevious()) {
                                Movie prev = list.previous();
                                Movie_title.setText(String.valueOf(prev.Name));
                                Movie_Desc.setText(String.valueOf(prev.Movie_Description));
                                Movie_Genre.setText(String.valueOf(prev.Genre));
                                Movie_Rating.setText(String.valueOf(prev.Rating + "/5"));
                                Movie_Year.setText(String.valueOf(prev.Year));
                                Movie_IMDB.setText(String.valueOf(prev.IMDB));
                            }
                        } else {
                            Movie_title.setText(String.valueOf(new_Movie.Name));
                            Movie_Desc.setText(String.valueOf(new_Movie.Movie_Description));
                            Movie_Genre.setText(String.valueOf(new_Movie.Genre));
                            Movie_Rating.setText(String.valueOf(new_Movie.Rating + "/5"));
                            Movie_Year.setText(String.valueOf(new_Movie.Year));
                            Movie_IMDB.setText(String.valueOf(new_Movie.IMDB));
                        }


                    }
                }
            });

            img_first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    while (list.hasPrevious()) {
                        Movie first = list.previous();
                        Movie_title.setText(String.valueOf(first.Name));
                        Movie_Desc.setText(String.valueOf(first.Movie_Description));
                        Movie_Genre.setText(String.valueOf(first.Genre));
                        Movie_Rating.setText(String.valueOf(first.Rating + "/5"));
                        Movie_Year.setText(String.valueOf(first.Year));
                        Movie_IMDB.setText(String.valueOf(first.IMDB));
                    }

                }
            });

            img_last.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    while (list.hasNext()) {
                        Movie first = list.next();
                        Movie_title.setText(String.valueOf(first.Name));
                        Movie_Desc.setText(String.valueOf(first.Movie_Description));
                        Movie_Genre.setText(String.valueOf(first.Genre));
                        Movie_Rating.setText(String.valueOf(first.Rating + "/5"));
                        Movie_Year.setText(String.valueOf(first.Year));
                        Movie_IMDB.setText(String.valueOf(first.IMDB));
                    }
                }
            });

            btnFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }
    }
}
