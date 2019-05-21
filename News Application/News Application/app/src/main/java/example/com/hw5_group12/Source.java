package example.com.hw5_group12;

import java.io.Serializable;

public class Source implements Serializable {
    String id;
    String name;

    public Source() {
    }

    @Override
    public String toString() {
        return "Source{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
