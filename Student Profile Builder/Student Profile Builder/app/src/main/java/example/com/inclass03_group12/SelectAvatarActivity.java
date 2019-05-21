package example.com.inclass03_group12;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SelectAvatarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);

        final ImageView img_F1=(ImageView)findViewById(R.id.img_avatar_F1);
        final ImageView img_F2=(ImageView)findViewById(R.id.img_avatar_F2);
        final ImageView img_F3=(ImageView)findViewById(R.id.img_avatar_F3);
        final ImageView img_M1=(ImageView)findViewById(R.id.img_avatar_M1);
        final ImageView img_M2=(ImageView)findViewById(R.id.img_avatar_M2);
        final ImageView img_M3=(ImageView)findViewById(R.id.img_avatar_M3);

        img_F1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent int_avtatr= new Intent();
                int_avtatr.putExtra(MainActivity.AVATAR_KEY,1);
                setResult(RESULT_OK,int_avtatr);
                finish();

            }
        });

        img_F2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent int_avtatr= new Intent();
                int_avtatr.putExtra(MainActivity.AVATAR_KEY,2);
                setResult(RESULT_OK,int_avtatr);
                finish();
            }
        });

        img_F3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent int_avtatr= new Intent();
                int_avtatr.putExtra(MainActivity.AVATAR_KEY,3);
                setResult(RESULT_OK,int_avtatr);
                finish();
            }
        });

        img_M1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent int_avtatr= new Intent();
                int_avtatr.putExtra(MainActivity.AVATAR_KEY,4);
                setResult(RESULT_OK,int_avtatr);
                finish();
            }
        });

        img_M2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent int_avtatr= new Intent();
                int_avtatr.putExtra(MainActivity.AVATAR_KEY,5);
                setResult(RESULT_OK,int_avtatr);
                finish();
            }
        });

        img_M3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent int_avtatr= new Intent();
                int_avtatr.putExtra(MainActivity.AVATAR_KEY,6);
                setResult(RESULT_OK,int_avtatr);
                finish();
            }
        });



    }
}
