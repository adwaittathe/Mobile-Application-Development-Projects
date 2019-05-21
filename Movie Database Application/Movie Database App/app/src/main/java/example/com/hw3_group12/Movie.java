package example.com.hw3_group12;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{
    String Name;
    String Movie_Description;
    String Genre;
    int Rating;
    int Year;
    String IMDB;

    public  Movie()
    {

    }
    public Movie(String name, String movie_Description, String genre, int rating, int year, String IMDB) {
        Name = name;
        Movie_Description = movie_Description;
        Genre = genre;
        Rating = rating;
        Year = year;
        this.IMDB = IMDB;
    }

    protected Movie(Parcel in) {
        Name = in.readString();
        Movie_Description = in.readString();
        Genre = in.readString();
        Rating = in.readInt();
        Year = in.readInt();
        IMDB = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public String toString() {
        return "Movie{" +
                "Name='" + Name + '\'' +
                ", Movie_Description='" + Movie_Description + '\'' +
                ", Genre='" + Genre + '\'' +
                ", Rating=" + Rating +
                ", Year=" + Year +
                ", IMDB='" + IMDB + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Movie_Description);
        dest.writeString(Genre);
        dest.writeInt(Rating);
        dest.writeInt(Year);
        dest.writeString(IMDB);
    }
}
