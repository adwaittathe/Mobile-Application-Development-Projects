package example.com.finalexam_expense;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddExpense extends AppCompatActivity {

    Button btnAdd;
    Button btnCancel;
    EditText exp;
    EditText val;

    FirebaseDatabase database;
    DatabaseReference mroot;
    Float rem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        btnAdd=(Button)findViewById(R.id.btnAddExpense_act);
        btnCancel=(Button)findViewById(R.id.btnCancelAct);
        this.setTitle("Add Expense");

        exp=(EditText) findViewById(R.id.act_expense);
        val=(EditText) findViewById(R.id.act_value);

        database= FirebaseDatabase.getInstance();
        mroot = database.getReference();

        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
            rem=(Float)getIntent().getExtras().getFloat("ex");
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(exp.getText().toString().length()==0)
                {
                    Toast.makeText(AddExpense.this, "Please enter Expense Name", Toast.LENGTH_SHORT).show();
                }
                else if(val.getText().toString().length()==0)
                {
                    Toast.makeText(AddExpense.this, "Please enter Expense Value", Toast.LENGTH_SHORT).show();
                }
                else if( Float.parseFloat(val.getText().toString())>rem)

                {
                    AlertDialog.Builder build= new AlertDialog.Builder(AddExpense.this);
                    build.setTitle("Budget Exceeded !!!")
                            .setMessage("Oops! Check your budget please!")
                     .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                     });



                    final AlertDialog alert= build.create();
                    alert.show();


                }

                else{
                    String id = UUID.randomUUID().toString();
                    Expense expense = new Expense();
                    expense.id = id;
                    expense.name = exp.getText().toString();
                    expense.value = val.getText().toString();
                    mroot.child("Expense/" + id).setValue(expense);
                    finish();
                }
            }
        });


    }
}
