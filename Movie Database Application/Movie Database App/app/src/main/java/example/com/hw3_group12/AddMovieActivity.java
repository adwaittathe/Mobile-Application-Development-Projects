package example.com.hw3_group12;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddMovieActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        if (getIntent() != null && getIntent().getExtras() != null) {
            final ArrayList<Movie> array_Movie = getIntent().getParcelableArrayListExtra(MainActivity.ARRAYLIST_MOVIE);
            final Button btnAdd = (Button) findViewById(R.id.btnAddMovie_activity);
            final EditText editMovieName = (EditText) findViewById(R.id.editMovieName);
            final EditText editMoieDescription = (EditText) findViewById(R.id.editMultiline_description);
            final Spinner spinner_genre = (Spinner) findViewById(R.id.spinnerGenre);
            final SeekBar seek_rating = (SeekBar) findViewById(R.id.seekBarRating);
            final EditText editYear = (EditText) findViewById(R.id.editYear);
            final EditText editIMDB = (EditText) findViewById(R.id.editIMDB);
            final TextView rating_show = (TextView) findViewById(R.id.mainRatingShow);
            seek_rating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    rating_show.setText(String.valueOf(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genre_name, R.layout.support_simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner_genre.setAdapter(adapter);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String input=editIMDB.getText().toString().toLowerCase();
                    if (!input.contains("www.imdb.com") | spinner_genre.getSelectedItem().toString().equals("Select") | editMovieName.getText().toString().length() <= 0 | editMoieDescription.getText().toString().length() <= 0 | editYear.getText().toString().length() <= 0 | editIMDB.getText().toString().length() <= 0 | !Patterns.WEB_URL.matcher(editIMDB.getText().toString()).matches()) {
                        if (editMovieName.getText().toString().length() <= 0) {
                            editMovieName.requestFocus();
                            editMovieName.setError("Please enter movie name");
                        }

                        if (spinner_genre.getSelectedItem().toString().equals("Select")) {
                            Toast.makeText(AddMovieActivity.this, "Please select genre", Toast.LENGTH_LONG).show();
                        }

                        if (editMoieDescription.getText().toString().length() <= 0) {
                            editMovieName.requestFocus();
                            editMoieDescription.setError("Please enter movie description ");
                        }

                        if (editYear.getText().toString().length() <= 0) {
                            editMovieName.requestFocus();
                            editYear.setError("Please enter movie year");
                        }

                        if (!Patterns.WEB_URL.matcher(editIMDB.getText().toString()).matches() && editIMDB.getText().toString().length() > 0 ) {

                            Toast.makeText(AddMovieActivity.this, "Please enter valid IMDB URL", Toast.LENGTH_LONG).show();

                        }



                        if(editIMDB.getText().toString().length() > 0 && !input.contains("www.imdb.com"))
                        {
                            Toast.makeText(AddMovieActivity.this, "Please enter valid IMDB URL", Toast.LENGTH_LONG).show();
                        }

                        if (editIMDB.getText().toString().length() <= 0) {
                            editMovieName.requestFocus();
                            editIMDB.setError("Please enter IMDB url");
                        }
                    } else {
                        Movie newMovie = new Movie();
                        newMovie.Name = editMovieName.getText().toString().trim();
                        newMovie.Movie_Description = editMoieDescription.getText().toString().trim();
                        newMovie.Genre = spinner_genre.getSelectedItem().toString();
                        newMovie.Rating = seek_rating.getProgress();
                        newMovie.Year = Integer.parseInt(editYear.getText().toString().trim());
                        newMovie.IMDB = editIMDB.getText().toString().trim();
                        array_Movie.add(newMovie);
                        Toast.makeText(AddMovieActivity.this, "Movie " + newMovie.Name + " added successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putParcelableArrayListExtra(MainActivity.VALUEKEY_MOVIE, array_Movie);
                        setResult(RESULT_OK, intent);

                        finish();
                    }
                }
            });
        }
    }
}
