package example.com.homework07_group12;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements TodoFragement.ToDoInterface,DoneFragment.DoneInterface,DoingFragment.DoingInterface,ITask {

    private ArrayList<Task> taskList;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskList=new ArrayList<>();


        database = FirebaseDatabase.getInstance();

        TabLayout tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager= (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter=new FixedPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }


    @Override
    public void setRecyclerView(RecyclerView recyclerView) {

    }

    @Override
    public ArrayList getList() {

        DatabaseReference fetchTasks=database.getReference("Tasks");
        fetchTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Task task=new Task();
                    task.id=snapshot.child("id").getValue().toString();
                    task.name=snapshot.child("name").getValue().toString();
                    task.date=snapshot.child("date").getValue().toString();
                    task.status=snapshot.child("status").getValue().toString();
                    taskList.add(task);

                    //setListView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return taskList;
    }

    @Override
    public void CreateTask(String taskName) {

        String id=UUID.randomUUID().toString();
        String path= "Tasks/todo/" + id;
        DatabaseReference addTask= database.getReference(path);
        Task task=new Task();
        task.id=id;
        task.name=taskName;
        task.status="todo";
        Date date = Calendar.getInstance().getTime();
        task.date=date.toString().trim();
        addTask.setValue(task);
        //fetchListView();


    }



    @Override
    public void updateToDoingStatus(final Task task) {
        String path= "Tasks/todo/" + task.id;
        DatabaseReference addTask= database.getReference(path);
        addTask.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DatabaseReference updateTasks=database.getReference("Tasks/doing/"+task.id);
               // Map<String,Object> updateObj=new HashMap<>();
               // updateObj.put("status","doing");
                updateTasks.setValue(task);
            }
        });

    }

    @Override
    public void updateToDoneStatus(final Task task) {

        String path= "Tasks/doing/" + task.id;
        DatabaseReference addTask= database.getReference(path);
        addTask.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DatabaseReference updateTasks=database.getReference("Tasks/done/"+task.id);
                // Map<String,Object> updateObj=new HashMap<>();
                // updateObj.put("status","doing");
                updateTasks.setValue(task);
            }
        });
    }

    @Override
    public void deleteTask(final Task task) {

        AlertDialog.Builder builder=new AlertDialog.Builder(this)
                .setTitle("Delete the task?")
                .setMessage("Do you really want to delete the task ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String path= "Tasks/done/" + task.id;
                        DatabaseReference addTask= database.getReference(path);
                        addTask.removeValue();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        final AlertDialog alert=builder.create();
        alert.show();
        Button btnYes=alert.getButton(DialogInterface.BUTTON_POSITIVE);
        btnYes.setBackgroundColor(Color.GREEN);
        btnYes.setTextColor(Color.WHITE);
        Button btnNo=alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        btnNo.setBackgroundColor(Color.RED);
        btnNo.setTextColor(Color.WHITE);


    }
}
