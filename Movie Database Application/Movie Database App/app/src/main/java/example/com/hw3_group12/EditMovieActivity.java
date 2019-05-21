package example.com.hw3_group12;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        final Button btnSave = (Button) findViewById(R.id.btnSavechanges);
        final EditText editMovieName = (EditText) findViewById(R.id.editMovieName2);
        final EditText editMoieDescription = (EditText) findViewById(R.id.editMultiline_description2);
        final Spinner spinner_genre = (Spinner) findViewById(R.id.spinnerGenre2);
        final SeekBar seek_rating = (SeekBar) findViewById(R.id.seekBarRating2);
        final EditText editYear = (EditText) findViewById(R.id.editYear2);
        final EditText editIMDB = (EditText) findViewById(R.id.editIMDB2);
        final TextView rating_show = (TextView) findViewById(R.id.rating_show2);


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
        if (getIntent().getExtras() != null && getIntent() != null) {

            Bundle bundle = getIntent().getExtras();
            Movie movie = (Movie) bundle.getParcelable(MainActivity.edit_selectedMovie_int);
            editMovieName.setText(String.valueOf(movie.Name));
            editMoieDescription.setText(String.valueOf(movie.Movie_Description));

            seek_rating.setProgress(movie.Rating);
            rating_show.setText(String.valueOf(movie.Rating));
            editYear.setText(String.valueOf(movie.Year));
            editIMDB.setText(String.valueOf(movie.IMDB));
            for (int i = 0; i < spinner_genre.getCount(); i++) {
                if (spinner_genre.getItemAtPosition(i).toString().equalsIgnoreCase(movie.Genre)) {
                    spinner_genre.setSelection(i);
                }
            }
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String input=editIMDB.getText().toString().toLowerCase();

                    if (!input.contains("www.imdb.com") |spinner_genre.getSelectedItem().toString().equals("Select") | editMovieName.getText().toString().length() <= 0 || !Patterns.WEB_URL.matcher(editIMDB.getText().toString()).matches() | editMoieDescription.getText().toString().length() <= 0 | editYear.getText().toString().length() <= 0 | editIMDB.getText().toString().length() <= 0) {
                        if (editMovieName.getText().toString().length() <= 0) {
                            editMovieName.requestFocus();
                            editMovieName.setError("Please enter movie name");
                        }
                        if (spinner_genre.getSelectedItem().toString().equals("Select")) {
                            Toast.makeText(EditMovieActivity.this, "Please select genre", Toast.LENGTH_LONG).show();
                        }

                        if (editMoieDescription.getText().toString().length() <= 0) {
                            editMovieName.requestFocus();
                            editMoieDescription.setError("Please enter movie description ");
                        }

                        if (editYear.getText().toString().length() <= 0) {
                            editMovieName.requestFocus();
                            editYear.setError("Please enter movie year");
                        }
                        if (editIMDB.getText().toString().length() <= 0) {
                            editMovieName.requestFocus();
                            editIMDB.setError("Please enter IMDB url");
                        }
                        if(editIMDB.getText().toString().length() > 0 && !input.contains("www.imdb.com"))
                        {
                            Toast.makeText(EditMovieActivity.this, "Please enter valid IMDB URL", Toast.LENGTH_LONG).show();
                        }
                        if (!Patterns.WEB_URL.matcher(editIMDB.getText().toString()).matches() && editIMDB.getText().toString().length() > 0) {
                            Toast.makeText(EditMovieActivity.this, "Please enter valid IMDB URL", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Movie movie = new Movie();
                        movie.Name = editMovieName.getText().toString().trim();
                        movie.Movie_Description = editMoieDescription.getText().toString().trim();
                        movie.Genre = spinner_genre.getSelectedItem().toString();
                        movie.Rating = seek_rating.getProgress();
                        movie.Year = Integer.parseInt(editYear.getText().toString().trim());
                        movie.IMDB = editIMDB.getText().toString().trim();

                        Intent intent = new Intent();
                        intent.putExtra(MainActivity.VALUEKEY_MOVIE, movie);
                        setResult(RESULT_OK, intent);
                        Toast.makeText(EditMovieActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }

    }
}
