package example.com.gmaps;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Restaurents implements Serializable{

    public String UserId;
    public double latitude;
    public double longitude;
    public String name;
    public  String id;
    public  String placeId;


    public Restaurents()
    {
    }

    @Override
    public String toString() {
        return "Restaurents{" +
                "UserId='" + UserId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", placeId='" + placeId + '\'' +
                '}';
    }
}
