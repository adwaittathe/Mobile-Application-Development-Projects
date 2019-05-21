package example.com.finalexam_expense;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DoTask {

    EditText editBudget;
    Button btnSet;
    Button btnAdd;
    ArrayList<Expense> expenseList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rec_adapter;
    private RecyclerView.LayoutManager rec_layout;
    float spent;
    Button btnEdit;
    TextView budgetText;
    TextView spendtDetails;
    FirebaseDatabase database;
    DatabaseReference mroot;
    ProgressBar pb;
    float remaining;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Expense Manager");

        database= FirebaseDatabase.getInstance();
        mroot = database.getReference();

        pb=(ProgressBar)findViewById(R.id.progressBar);

        btnAdd=(Button)findViewById(R.id.btnAdd) ;
        spendtDetails=(TextView)findViewById(R.id.txtSpentDetails);


        budgetText=(TextView)findViewById(R.id.txtBudget);
        budgetText.setVisibility(View.INVISIBLE);
        editBudget=(EditText)findViewById(R.id.editBudget);
        btnSet=(Button)findViewById(R.id.btnSet) ;
        btnEdit=(Button)findViewById(R.id.btnEdit) ;



        expenseList=new ArrayList<>();

        editBudget.setVisibility(View.VISIBLE);
        btnSet.setVisibility(View.VISIBLE);
        btnEdit.setVisibility(View.INVISIBLE);



        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                budgetText.setVisibility(View.INVISIBLE);
                editBudget.setVisibility(View.VISIBLE);
                String expense=editBudget.getText().toString();
                DatabaseReference r=mroot.child("Budget");
                r.setValue(expense);
                //editBudget.setVisibility(View.INVISIBLE);
                btnSet.setVisibility(View.VISIBLE);
                //budgetText.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.INVISIBLE);


            }
        });
        setView();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intexp=new Intent(MainActivity.this, AddExpense.class);
                intexp.putExtra("ex",remaining);
                startActivity(intexp);

            }
        });
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String expense=editBudget.getText().toString();
                if(expense.length()==0)
                {
                    Toast.makeText(MainActivity.this, "Please Enter Budget", Toast.LENGTH_SHORT).show();
                }
                else if(spent > Float.parseFloat(expense))
                {
                    Toast.makeText(MainActivity.this, "Spent amout is already gretaer than budget", Toast.LENGTH_SHORT).show();
                }
                else {
                    DatabaseReference r = mroot.child("Budget");
                    r.setValue(expense);

                    btnSet.setVisibility(View.INVISIBLE);
                    btnEdit.setVisibility(View.VISIBLE);
                    budgetText.setVisibility(View.VISIBLE);
                    editBudget.setVisibility(View.INVISIBLE);
                    budgetText.setText("Monthly Budget : " + expense);
                }



            }
        });


    }
    public void fetchBudget()
    {

        mroot.child("Budget").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot!=null) {
                    String bud = (String) dataSnapshot.getValue();
                    if (bud != null) {
                        pb.setMax((int) Float.parseFloat(bud));
                        pb.setProgress((int) spent);
                        remaining = Float.parseFloat(bud)-spent;
                        spendtDetails.setText(spent + "/" + bud);
                    }
                    else
                    {

                        spendtDetails.setText("0/0");
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public  void setView()
    {
        mroot.child("Expense").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                spent=0;
                expenseList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    if(dataSnapshot1!=null)
                    {
                        Expense expense=dataSnapshot1.getValue(Expense.class);

                        float exp=Float.parseFloat(expense.value.toString());
                        spent=spent+exp;

                        expenseList.add(expense);

                    }
                }
                fetchBudget();
                setRecy();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setRecy()
    {

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);

        recyclerView.setHasFixedSize(true);
        rec_layout=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rec_layout);

        rec_adapter=new MyAdapter(expenseList,this);
        recyclerView.setAdapter(rec_adapter);

    }


    @Override
    public void Delete(final Expense expense1) {


        AlertDialog.Builder build= new AlertDialog.Builder(this);
        build.setTitle("Delete")
                .setMessage("Are you sure want to delete the expense ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mroot.child("Expense/"+expense1.id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                setRecy();
                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("diag","Clicked NO");
                    }
                });

        final AlertDialog alert= build.create();
        alert.show();





    }
}
