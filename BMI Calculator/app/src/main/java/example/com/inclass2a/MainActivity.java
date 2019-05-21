package example.com.inclass2a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView txtResult=(TextView)findViewById(R.id.txt_bmi_result);
        txtResult.setVisibility(View.INVISIBLE);

        final TextView txtObese=(TextView)findViewById(R.id.txtOverview);
       txtObese.setVisibility(View.INVISIBLE);





        Button btnBMI=(Button)findViewById(R.id.btnBMI);
        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TextView txtResult=(TextView)findViewById(R.id.txt_bmi_result);
                TextView Weight=(TextView)findViewById(R.id.editWeight);
                TextView heightFeet=(TextView)findViewById(R.id.editHeight1);
                TextView heightInches=(TextView)findViewById(R.id.editHeight2);

                if(Weight.getText().toString().trim().length()<=0 ){
                    Toast.makeText(MainActivity.this,"Please enter Weight",Toast.LENGTH_LONG).show();

                }
                else if(heightFeet.getText().toString().trim().length()<=0){
                    Toast.makeText(MainActivity.this,"Please enter Height in Feets",Toast.LENGTH_LONG).show();
                }
                else if(heightInches.getText().toString().trim().length()<=0){
                    Toast.makeText(MainActivity.this,"Please enter Height in Inches",Toast.LENGTH_LONG).show();
                }
                else if(Integer.parseInt(heightInches.getText().toString().trim())>=12  ){
                    Toast.makeText(MainActivity.this,"Please enter Height less than 12 inches ",Toast.LENGTH_LONG).show();
                }

                else {
                    double height_FeetToInch = Double.parseDouble(heightFeet.getText().toString()) * 12.0;

                    double total_height = height_FeetToInch + Double.parseDouble(heightInches.getText().toString());
                    Log.d("str", total_height + "");

                    double weight_double = Double.parseDouble(Weight.getText().toString());
                    double bmi_result = (weight_double / (total_height * total_height)) * 703.0;

                    String bmi_val = "";
                    if (bmi_result <= 18.5) {
                        bmi_val = "You are underweight";
                    } else if (18.5 < bmi_result && bmi_result < 24.9) {
                        bmi_val = "You are normalweight";
                    } else if (25 < bmi_result && bmi_result < 29.9) {

                        bmi_val = "You are overweight";
                    } else if (bmi_result >= 30) {
                        bmi_val = "You are obese";
                    }
                    Toast.makeText(MainActivity.this, "BMI Calculated", Toast.LENGTH_LONG).show();
                    txtResult.setVisibility(View.VISIBLE);
                    txtObese.setVisibility(View.VISIBLE);

                    txtResult.setText("Your BMI: " + bmi_result);
                    txtObese.setText(bmi_val);

                }
            }
        });
    }
}
