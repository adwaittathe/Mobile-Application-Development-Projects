package example.com.inclass03_group12;

import android.widget.ImageView;

import java.io.Serializable;

public class User implements Serializable {
    public int ImageId;
    public String FirstName;
    public String lastName;
    public int StudentId;
    public String Department;

    @Override
    public String toString() {
        return "User{" +
                "IamgeId=" + ImageId +
                ", FirstName='" + FirstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", StudentId=" + StudentId +
                ", Department='" + Department + '\'' +
                '}';
    }

    public User(int iamgeId, String firstName, String lastName, int studentId, String department) {
        ImageId = iamgeId;
        FirstName = firstName;
        this.lastName = lastName;
        StudentId = studentId;
        Department = department;
    }
}
