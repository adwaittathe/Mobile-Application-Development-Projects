package example.com.hw3_group12;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    public static String ARRAYLIST_MOVIE;
    public static String ARRAYLIST_MOVIE_EDIT;
    public static String edit_selectedMovie;
    public static String edit_selectedMovie_int;
    public static int REQ_CODE=100;
    public static String VALUEKEY_MOVIE="movie";
    public static int REQ_CODE_EDITMOVIE=200;
    public static int REQ_CODE_SortYEAR=300;
    public int Id=0;
    public ArrayList<Movie> array_movies= new ArrayList<Movie>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        Movie mFirst =new Movie("Dark Knight","Awesome Movie","Action",5,2015,"www.joker.com");
        Movie mSecond =new Movie("Dark Knight Rises","Awesome Movie BEN","Action",1,2019,"www.Risesjoker.com");
        Movie mThird =new Movie("RIO","Awesome Movie RIO","Action",2,2011,"www.rio.com");
        Movie mFourth =new Movie("DDLJ","SRK loves KAJAL,,,,,,BLAH BALH---- SIMARAn.... bauji mujhe janedo pls jauji.... jaa simran jaa...............................................wapas aa simarn ","Action",2,2020,"www.ddlj-fkjfsdkjfdskssdkjsdksdfjkksfdfds.com");
        Movie mFifth =new Movie("3Idiots","randhoddas shamaldas chanchadgiofgdfdflk blah blah fskdljdslkfslkdfslkfdskljfdlkfsdlkfsdlkjfsdklfdsjklfsdlkfdlskjfkdjffsdlkjfdslkfdjslksdfjflsdkjfdslkjfdslkfdsjlkfdsjlkfsdjfsdlkjsfdlkfsdjlfsdkjflskdsfd","Action",3,1994,"www.3idiots.com");
        Movie mSixth =new Movie("Tere Naam","SALMAN WIN ","Action",3,2300,"www.salman.com");
        array_movies.add(mFirst);
        array_movies.add(mSecond);
        array_movies.add(mThird);
        array_movies.add(mFourth);
        array_movies.add(mFifth);
        array_movies.add(mSixth);

*/
        final Button btnAdd= (Button)findViewById(R.id.btnAddMovie);
        final Button btnEdit= (Button)findViewById(R.id.btnEdit);
        final Button btnDelete=(Button)findViewById(R.id.btnDeleteMovie);
        final Button btnSort_year=(Button)findViewById(R.id.btnListByYear);
        final Button btnSort_rating=(Button)findViewById(R.id.btnShowListbyRating);
        for (Movie m :array_movies ) {
            Log.d("mov","Main activity after creating"+m.toString());
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_addMovie= new Intent(MainActivity.this, AddMovieActivity.class);
                int_addMovie.putParcelableArrayListExtra(MainActivity.ARRAYLIST_MOVIE,array_movies);
                startActivityForResult(int_addMovie,MainActivity.REQ_CODE);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(array_movies.size()>0) {
                    final String[] movieName = new String[array_movies.size()];
                    for (int i = 0; i < movieName.length; i++) {

                        movieName[i] = array_movies.get(i).Name;
                    }
                    AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
                    build.setTitle("Pick a movie")
                            .setItems(movieName, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("mov", "item clicked" + movieName[which]);
                                    String pickedMovie = movieName[which];
                                    Intent int_edit = new Intent(MainActivity.this, EditMovieActivity.class);
                                    Log.d("str", "edit_test " + array_movies.get(which));
                                    Movie mov = new Movie();
                                    Id = which;
                                    mov.Name = array_movies.get(which).Name;
                                    mov.Genre = array_movies.get(which).Genre;
                                    mov.Movie_Description = array_movies.get(which).Movie_Description;
                                    mov.IMDB = array_movies.get(which).IMDB;
                                    mov.Year = array_movies.get(which).Year;
                                    mov.Rating = array_movies.get(which).Rating;
                                    int_edit.putExtra(MainActivity.edit_selectedMovie_int, mov);
                                    startActivityForResult(int_edit, MainActivity.REQ_CODE_EDITMOVIE);
                                }
                            });
                    final AlertDialog alert = build.create();
                    alert.show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"There are no movies added, Please add a movie",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(array_movies.size()>0) {
                    final String[] movieName = new String[array_movies.size()];
                    for (int i = 0; i < movieName.length; i++) {

                        movieName[i] = array_movies.get(i).Name;
                    }
                    AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
                    build.setTitle("Pick a movie")
                            .setItems(movieName, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this, "Movie " + array_movies.get(which).Name + " is deleted", Toast.LENGTH_LONG).show();
                                    array_movies.remove(which);
                                }
                            });
                    final AlertDialog alert = build.create();
                    alert.show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"There are no movies added, Please add a movie",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSort_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(array_movies.size()>0) {
                    Collections.sort(array_movies, new Comparator<Movie>() {
                        @Override
                        public int compare(Movie o1, Movie o2) {
                            if (o1.Year > o2.Year) {
                                return 1;
                            } else if (o1.Year < o2.Year) {
                                return -1;
                            }
                            return 0;
                        }
                    });
                    Intent int_soryByYear = new Intent("example.com.hw3_group12.intent.action.SortYear");
                    int_soryByYear.putParcelableArrayListExtra(MainActivity.ARRAYLIST_MOVIE, array_movies);
                    startActivity(int_soryByYear);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"There are no movies added, Please add a movie",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSort_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(array_movies.size()>0) {
                    Collections.sort(array_movies, new Comparator<Movie>() {
                        @Override
                        public int compare(Movie o1, Movie o2) {
                            if (o1.Rating < o2.Rating) {
                                return 1;

                            } else if (o1.Rating > o2.Rating) {
                                return -1;
                            }
                            return 0;
                        }
                    });
                    Intent int_soryByRating = new Intent("example.com.hw3_group12.intent.action.SortRating");
                    int_soryByRating.putParcelableArrayListExtra(MainActivity.ARRAYLIST_MOVIE, array_movies);
                    startActivity(int_soryByRating);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"There are no movies added, Please add a movie",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQ_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                ArrayList<Movie> mv=data.getParcelableArrayListExtra(VALUEKEY_MOVIE);
                array_movies=mv;
            }
        }
        if(requestCode==REQ_CODE_EDITMOVIE)
        {
            if(resultCode==RESULT_OK) {
                Movie mov = data.getExtras().getParcelable(MainActivity.VALUEKEY_MOVIE);
                array_movies.set(Id, mov);
            }
        }
    }
}
