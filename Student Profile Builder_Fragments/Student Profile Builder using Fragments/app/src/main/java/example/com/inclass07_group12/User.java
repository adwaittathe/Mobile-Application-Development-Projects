package example.com.inclass07_group12;

import java.io.Serializable;

public class User implements Serializable {
    String firstName;
    String lastName;
    String studentId;
    String Department;
    int ImageId;

    public User(String firstName, String lastName, String studentId, String department, int ImageId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        Department = department;
        this.ImageId=ImageId;
    }

}
