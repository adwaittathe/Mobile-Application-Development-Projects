package example.com.inclass03_group12;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class DisplayResultActivity extends AppCompatActivity {

    public static String EDITUSER_KEY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);
        final TextView txtFirst_name= (TextView) findViewById(R.id.txt_firstname_result);


        final ImageView img_avatar=(ImageView)findViewById(R.id.resultImg_displayAvatar);
        final TextView txtDepartment= (TextView) findViewById(R.id.txtDept_result_display);
        final TextView txtStdID=(TextView)findViewById(R.id.txtStudentID_result);

        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
            User usr= (User)getIntent().getExtras().getSerializable(MainActivity.USER_KEY);
            Log.d("userRes",usr.toString()+"");
            txtFirst_name.setText(String.valueOf(usr.FirstName) + " " + String.valueOf(usr.lastName));
            txtDepartment.setText(String.valueOf(usr.Department));
            txtStdID.setText(String.valueOf(usr.StudentId));
            int img_val= usr.ImageId;
            if (img_val == 1) {
                img_avatar.setImageResource(R.drawable.avatarf1);
            }
            else if(img_val==2){
                img_avatar.setImageResource(R.drawable.avatar_f_2);
            }
            else if(img_val==3){
                img_avatar.setImageResource(R.drawable.avatar_f_3);
            }
            else if(img_val==4){
                img_avatar.setImageResource(R.drawable.avatar_m_1);
            }
            else if(img_val==5){
                img_avatar.setImageResource(R.drawable.avatar_m_2);
            }
            else if(img_val==6){
                img_avatar.setImageResource(R.drawable.avatar_m_3);
            }




        }

        final Button btnEdit = (Button) findViewById(R.id.btnedit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getIntent()!=null && getIntent().getExtras()!=null)
                {
                    User usr = (User) getIntent().getExtras().getSerializable(MainActivity.USER_KEY);
                    Intent inten_edit= new Intent(DisplayResultActivity.this,MainActivity.class);
                    inten_edit.putExtra(EDITUSER_KEY,usr);
                    startActivity(inten_edit);
                }

            }
        });

    }
}
