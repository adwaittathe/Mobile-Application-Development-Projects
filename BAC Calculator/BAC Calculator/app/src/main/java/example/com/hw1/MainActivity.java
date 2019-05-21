package example.com.hw1;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private double weight;
    private String gender;
    double drinksize = 0;
    double r = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        final SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        final ProgressBar progBarAlcohol = (ProgressBar) findViewById(R.id.progressBar);
        final Button btnSave = (Button) findViewById(R.id.btnSave);
        final Button btnReset = (Button) findViewById(R.id.btnReset);
        final Button btnAdddrink = (Button) findViewById(R.id.btnAddDrink);
        final TextView txtProgress = (TextView) findViewById(R.id.txtAlcPercentage);
        final Switch sw = (Switch) findViewById(R.id.switchGender);
        final EditText editWeight = (EditText) findViewById(R.id.editWeight);
        final TextView txtBACpercent = (TextView) findViewById(R.id.txtBAClevelpercentage);
        final TextView txtAlcoholStaus = (TextView) findViewById(R.id.txtStatusWarning);
        final RadioGroup rgDrinkGroup = (RadioGroup) findViewById(R.id.radioGroupDrink);
        final RadioButton rg_one_oz = (RadioButton) findViewById(R.id.rb_One_oz);
        final RadioButton rg_five_oz = (RadioButton) findViewById(R.id.rb_five_oz);
        final RadioButton rg_twelve_oz = (RadioButton) findViewById(R.id.rb_twelve_oz);

        txtAlcoholStaus.setVisibility(View.INVISIBLE);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setEnabled(true);
                btnAdddrink.setEnabled(true);
                editWeight.getText().clear();
                progBarAlcohol.setProgress(0);
                sb.setProgress(5);
                txtBACpercent.setText("0.00");
                txtAlcoholStaus.setVisibility(View.INVISIBLE);
                rg_one_oz.setChecked(true);
                rg_one_oz.setEnabled(true);
                rg_five_oz.setEnabled(true);
                rg_twelve_oz.setEnabled(true);
                sb.setEnabled(true);
                sw.setEnabled(true);
                editWeight.setEnabled(true);
                weight = 0;
                drinksize = 0;
                r = 0;
                sw.setChecked(true);
            }
        });

        sb.setProgress(5);
        sb.setMax(25);
        progBarAlcohol.setProgress(0);
        txtProgress.setText(String.valueOf(sb.getProgress() + "%"));

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progress = ((int) Math.round(progress / 5)) * 5;
                seekBar.setProgress(progress);
                txtProgress.setText(String.valueOf(progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        btnAdddrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (weight <= 0) {
                    editWeight.setError("Please save weight and gender");
                } else {
                    txtAlcoholStaus.setVisibility(View.VISIBLE);

                    rgDrinkGroup.getCheckedRadioButtonId();
                    if (rgDrinkGroup.getCheckedRadioButtonId() == R.id.rb_One_oz) {
                        drinksize = 1;
                    } else if (rgDrinkGroup.getCheckedRadioButtonId() == R.id.rb_five_oz) {
                        drinksize = 5;
                    } else if (rgDrinkGroup.getCheckedRadioButtonId() == R.id.rb_twelve_oz) {
                        drinksize = 12;
                    } else {
                        drinksize = 66;
                    }

                    double alcpercentage = Double.parseDouble(String.valueOf(sb.getProgress()));
                    double BAC;
                    double BAC_per;
                    double A = drinksize * alcpercentage;
                    BAC = (((A * 6.24) / (weight * r)));
                    Log.d("bac", BAC + "");
                    BAC_per = BAC / 100;
                    double prevAlcPercentage = Double.parseDouble(txtBACpercent.getText().toString().trim());
                    double finalBAC = BAC_per + prevAlcPercentage;
                    int prog = (int) ((finalBAC * 100) / 0.25);
                    progBarAlcohol.setProgress(prog);
                    BAC_display(finalBAC);
                }
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if (editWeight.getText().toString().trim().length() <= 0) {
                                               editWeight.setError("Enter the weight in lb");
                                           } else {
                                               String temp = txtBACpercent.getText().toString().trim();
                                               if (!temp.equals("0.00")) {
                                                   Double currentBAC = Double.parseDouble(txtBACpercent.getText().toString().trim());
                                                   Double currentA = (currentBAC * weight * r) / 6.24;
                                                   Double new_r;
                                                   Double new_weight = Double.parseDouble(editWeight.getText().toString());
                                                   if (sw.isChecked()) {
                                                       new_r = 0.55;
                                                   } else {
                                                       new_r = 0.68;
                                                   }
                                                   Double new_BAC = (((currentA * 6.24) / (new_weight * new_r)));
                                                   Double new_BAC_per = new_BAC / 100;
                                                   int prog = (int) ((new_BAC * 100) / 0.25);
                                                   progBarAlcohol.setProgress(prog);
                                                   BAC_display(new_BAC);
                                               }
                                               weight = Double.parseDouble(editWeight.getText().toString());
                                               if (sw.isChecked()) {
                                                   r = 0.55;
                                               } else {
                                                   r = 0.68;
                                               }
                                           }
                                       }
                                   }
        );
    }

    public void BAC_display(Double BAC_value) {
        final SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        final ProgressBar progBarAlcohol = (ProgressBar) findViewById(R.id.progressBar);
        final Button btnSave = (Button) findViewById(R.id.btnSave);
        final Button btnReset = (Button) findViewById(R.id.btnReset);
        final Button btnAdddrink = (Button) findViewById(R.id.btnAddDrink);
        final TextView txtProgress = (TextView) findViewById(R.id.txtAlcPercentage);
        final Switch sw = (Switch) findViewById(R.id.switchGender);
        final EditText editWeight = (EditText) findViewById(R.id.editWeight);
        final TextView txtBACpercent = (TextView) findViewById(R.id.txtBAClevelpercentage);
        final TextView txtAlcoholStaus = (TextView) findViewById(R.id.txtStatusWarning);
        final RadioGroup rgDrinkGroup = (RadioGroup) findViewById(R.id.radioGroupDrink);
        final RadioButton rg_one_oz = (RadioButton) findViewById(R.id.rb_One_oz);
        final RadioButton rg_five_oz = (RadioButton) findViewById(R.id.rb_five_oz);
        final RadioButton rg_twelve_oz = (RadioButton) findViewById(R.id.rb_twelve_oz);

        if (BAC_value <= 0.08) {
            txtAlcoholStaus.setText("You're Safe");
            txtAlcoholStaus.setBackgroundColor(Color.GREEN);
            txtAlcoholStaus.setTextColor(Color.WHITE);
        } else if (0.08 > BAC_value | BAC_value < 0.20) {
            txtAlcoholStaus.setText("Be careful...");
            txtAlcoholStaus.setBackgroundColor(Color.parseColor("#FFA500"));
            txtAlcoholStaus.setTextColor(Color.WHITE);
        } else {
            txtAlcoholStaus.setText("Over the limit");
            txtAlcoholStaus.setBackgroundColor(Color.RED);
            txtAlcoholStaus.setTextColor(Color.WHITE);
        }
        if (BAC_value >= ((double) 0.2500)) {
            btnSave.setEnabled(false);
            btnAdddrink.setEnabled(false);
            rg_one_oz.setEnabled(false);
            rg_five_oz.setEnabled(false);
            rg_twelve_oz.setEnabled(false);
            sb.setEnabled(false);
            sw.setEnabled(false);
            editWeight.setEnabled(false);


            Toast.makeText(MainActivity.this, "No more drinks for you", Toast.LENGTH_LONG).show();
        }
        txtBACpercent.setText(String.format("%.4f", BAC_value));
    }
}

