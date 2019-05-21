package example.com.inclass03_group12;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static int SELECT_AVATAR = 100;
    public static String AVATAR_KEY = "avatar";
    public static String USER_KEY;
    private int img_value=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView img_avatar = (ImageView) findViewById(R.id.img_avatar_select);
        final Button btnSave=(Button) findViewById(R.id.btn_save);
        final EditText edit_FirstName=(EditText)findViewById(R.id.edit_firstName);
        final EditText edit_LastName=(EditText)findViewById(R.id.edit_last_name);
        final EditText edit_StudentId=(EditText)findViewById(R.id.edit_student_id);
        final RadioGroup rg_depart=(RadioGroup)findViewById(R.id.rg_Department);
        final RadioButton rb_CS=(RadioButton)findViewById(R.id.rb_CS);
        final RadioButton rb_BIO=(RadioButton)findViewById(R.id.rb_BIO);
        final RadioButton rb_SIS=(RadioButton)findViewById(R.id.rb_SIS);
        final RadioButton rb_Other=(RadioButton)findViewById(R.id.rb_Other);
        final TextView selectAvatar=(TextView)findViewById(R.id.txtAvatar);
        selectAvatar.setVisibility(View.VISIBLE);
        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
            User usr= (User)getIntent().getExtras().getSerializable(DisplayResultActivity.EDITUSER_KEY);
            edit_FirstName.setText(String.valueOf(usr.FirstName));
            edit_LastName.setText(String.valueOf(usr.lastName));
            edit_StudentId.setText(String.valueOf(usr.StudentId));
            img_value= usr.ImageId;

            String dep= String.valueOf(usr.Department);
            if(dep.equals("CS"))
            {
                rb_CS.setChecked(true);
            }
            else if(dep.equals("SIS"))
            {
                rb_SIS.setChecked(true);
            }
            else if(dep.equals("BIO"))
            {
                rb_BIO.setChecked(true);
            }
            else if(dep.equals("Other"))
            {
               rb_Other.setChecked(true);
            }


            if (img_value == 1) {
                img_avatar.setImageResource(R.drawable.avatarf1);
            }
            else if(img_value==2){
                img_avatar.setImageResource(R.drawable.avatar_f_2);
            }
            else if(img_value==3){
                img_avatar.setImageResource(R.drawable.avatar_f_3);
            }
            else if(img_value==4){
                img_avatar.setImageResource(R.drawable.avatar_m_1);
            }
            else if(img_value==5){
                img_avatar.setImageResource(R.drawable.avatar_m_2);
            }
            else if(img_value==6){
                img_avatar.setImageResource(R.drawable.avatar_m_3);
            }
            selectAvatar.setVisibility(View.INVISIBLE);

        }



        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_avatar_select = new Intent(MainActivity.this, SelectAvatarActivity.class);
                startActivityForResult(int_avatar_select, MainActivity.SELECT_AVATAR);


            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

 //               Log.d("img",""+img_avatar.getId());
   //             Log.d("img","sele"+R.drawable.selectimage);
                if(img_value==0)
                {
                    Toast.makeText(MainActivity.this,"Please select avatar",Toast.LENGTH_LONG).show();
                }
                else if(edit_FirstName.getText().toString().length()<=0) {
                    Toast.makeText(MainActivity.this,"Please enter First Name",Toast.LENGTH_LONG).show();
                }
                else if(edit_LastName.getText().toString().length()<=0){
                    Toast.makeText(MainActivity.this,"Please enter Last Name",Toast.LENGTH_LONG).show();

                }
                else if(edit_StudentId.getText().toString().length()<=0){
                    Toast.makeText(MainActivity.this,"Please enter StudentId",Toast.LENGTH_LONG).show();

                }
                else if(rb_BIO.isChecked()==false && rb_CS.isChecked()==false && rb_SIS.isChecked()==false && rb_Other.isChecked()==false)
                {
                    //rb_BIO.isChecked()==false | rb_CS.isChecked()==false | rb_SIS.isChecked()==false | rb_Other.isChecked()==false
                    Toast.makeText(MainActivity.this,"Please select department",Toast.LENGTH_LONG).show();
                }


               /* if(img_value==0| edit_FirstName.getText().toString().length()<=0 | edit_LastName.getText().toString().length()<=0 | edit_StudentId.getText().toString().length()<=0)
                {
                    Toast.makeText(MainActivity.this,"Please enter inputs",Toast.LENGTH_LONG).show();
                }
                */
                else {

                    RadioButton rb_dept_checked = (RadioButton) findViewById(rg_depart.getCheckedRadioButtonId());
                    User usr = new User(img_value, edit_FirstName.getText().toString().trim(), edit_LastName.getText().toString().trim(), Integer.parseInt(edit_StudentId.getText().toString().trim()), rb_dept_checked.getText().toString().trim());
                    Log.d("user", usr.toString() + "");
                    Intent int_serializable = new Intent(MainActivity.this, DisplayResultActivity.class);
                    int_serializable.putExtra(USER_KEY, usr);
                    startActivity(int_serializable);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SELECT_AVATAR) {
            if (resultCode == RESULT_OK) {
                img_value = data.getExtras().getInt(AVATAR_KEY);
                ImageView img_avatar = (ImageView) findViewById(R.id.img_avatar_select);
                TextView selectAvatar=(TextView)findViewById(R.id.txtAvatar);
                if (img_value == 1) {
                    img_avatar.setImageResource(R.drawable.avatarf1);

                }
                else if(img_value==2){
                    img_avatar.setImageResource(R.drawable.avatar_f_2);
                }
                else if(img_value==3){
                    img_avatar.setImageResource(R.drawable.avatar_f_3);
                }
                else if(img_value==4){
                    img_avatar.setImageResource(R.drawable.avatar_m_1);
                }
                else if(img_value==5){
                    img_avatar.setImageResource(R.drawable.avatar_m_2);
                }
                else if(img_value==6){
                    img_avatar.setImageResource(R.drawable.avatar_m_3);
                }
                selectAvatar.setVisibility(View.INVISIBLE);



            }
        }
    }
}
