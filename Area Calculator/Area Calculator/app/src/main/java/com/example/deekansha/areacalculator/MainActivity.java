package com.example.deekansha.areacalculator;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private double area = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView txt_shape = (TextView) findViewById(R.id.txt_shape);
        final EditText length1 = (EditText) findViewById(R.id.lenght1);
        final EditText length2 = (EditText) findViewById(R.id.length2);
        final ImageView img_triangle = (ImageView) findViewById(R.id.img_bt_triangle);
        final ImageView img_square = (ImageView) findViewById(R.id.img_btn_square);
        final ImageView img_circle = (ImageView) findViewById(R.id.img_btn_circle);
        final Button btn_calculate = (Button) findViewById(R.id.bt_calculate);
        final TextView txt_value = (TextView) findViewById(R.id.txt_value);
        final Button btn_clear = (Button) findViewById(R.id.bt_clear);
        final TextView txtLength1 = (TextView) findViewById(R.id.txtLenght1);
        final TextView txtLength2 = (TextView) findViewById(R.id.txtLenght2);

        img_triangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_shape.setText("Triangle");
                length2.setVisibility(View.VISIBLE);
                txtLength2.setVisibility(View.VISIBLE);

            }
        });


        img_square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_shape.setText("Square");
                length2.setVisibility(View.INVISIBLE);
                txtLength2.setVisibility(View.INVISIBLE);

            }
        });


        img_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_shape.setText("Circle");
                length2.setVisibility(View.INVISIBLE);
                txtLength2.setVisibility(View.INVISIBLE);

            }
        });


        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((txt_shape.getText().toString().trim().equals("Triangle")) && (length1.getText().length()<=0 | length2.getText().length()<=0))
                {
                    Toast.makeText(MainActivity.this, "Please enter the lengths", Toast.LENGTH_LONG).show();

                }
                else if ((txt_shape.getText().toString().trim().equals("Circle") | txt_shape.getText().toString().trim().equals("Square")) && length1.getText().length()<=0) {

                    Toast.makeText(MainActivity.this, "Please enter the length", Toast.LENGTH_LONG).show();

                }
                else if (txt_shape.getText().toString().trim().equals("Select a shape")) {

                    Toast.makeText(MainActivity.this, "Please select a shape", Toast.LENGTH_LONG).show();

                }else  {

                    if (txt_shape.getText().toString().trim().equals("Triangle"))
                    {
                        area = 0.5 * Double.parseDouble(length1.getText().toString()) * Double.parseDouble(length2.getText().toString());
                    }
                    else if (txt_shape.getText().toString().trim().equals("Square"))
                    {
                        area = Double.parseDouble(length1.getText().toString()) * Double.parseDouble(length1.getText().toString());
                    }
                    else if (txt_shape.getText().toString().trim().equals("Circle"))
                    {
                        area = 3.1416 * Double.parseDouble(length1.getText().toString()) * Double.parseDouble(length1.getText().toString());
                    }

                    txt_value.setText(String.valueOf(String.format("%.2f", area)));
                }


            }

        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                length1.setText("");
                length2.setText("");
                txt_value.setText("");
                txt_shape.setTextColor(Color.BLACK);
                txt_shape.setText("Select a shape");
                txt_shape.setTextColor(Color.BLACK);
                length2.setVisibility(View.VISIBLE);
                txtLength2.setVisibility(View.VISIBLE);
                area = 0;
            }
        });

    }
}
