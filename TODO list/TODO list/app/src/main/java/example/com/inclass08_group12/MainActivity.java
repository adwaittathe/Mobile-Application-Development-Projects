package example.com.inclass08_group12;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements ITask {

    ArrayList<Task> taskList;
    ArrayList<Task> PendingList;

    ArrayList<Task> CompletedList;
    DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference();
    TaskAdapter task_adapter;
    int menu_option=0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menu_showAll:
                menu_option=0;
                //menushowall_list();
                //task_adapter.notifyDataSetChanged();

                break;


            case R.id.menu_showCompleted:
                menu_option=1;
                //menushowcompleted_list();
                //task_adapter.notifyDataSetChanged();
                break;

            case R.id.menu_showPending:
                menu_option=2;
               // menushowpending_list();
                //task_adapter.notifyDataSetChanged();
                break;

            default:
                break;


        }
        return super.onOptionsItemSelected(item);
    }



    public void getData()
    {
        ValueEventListener vl=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                for(DataSnapshot snap: dataSnapshot.getChildren())
                {
                    Task task= snap.getValue(Task.class);
                    taskList.add(task);
                    if(task.status.toString().equals("unchecked"))
                    {
                        CompletedList.add(task);
                    }
                    else
                    {
                        PendingList.add(task);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

    }
    //FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //mDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = mDatabase.getReference();
        final Spinner spinner=(Spinner)findViewById(R.id.spinner);
        String[] priority = new String[]{
                "Priority",
                "High",
                "Medium",
                "Low",

        };

        final List<String> priorityList = new ArrayList<>(Arrays.asList(priority));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,priorityList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);



        /*
        final ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.spinner_items,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        */


        final ListView listview=(ListView)findViewById(R.id.listview);

        final EditText editTodo=(EditText)findViewById(R.id.editToDO);
        Button btnAdd=(Button)findViewById(R.id.btnAdd);

        DatabaseReference to_do_ref=mDatabase.child("Todo");


        to_do_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                taskList=new ArrayList<>();
                switch (menu_option) {
                    case 0:
                        menushowall(dataSnapshot);
                        break;
                    case 1:
                       menushow_Completed(dataSnapshot);
                        break;
                    case 2:
                       menushow_Pending(dataSnapshot);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final Task task=taskList.get(position);
                final int  delete_pos=position;
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirm Delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabase.child("Todo").child(task.id).removeValue();
                                taskList.remove(delete_pos);
                               
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });
                final AlertDialog alertDialog=builder.create();
                alertDialog.show();


                return false;
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference to_do_ref=mDatabase.child("Todo");

                //to_do_ref.setValue(editTodo.getText().)
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

                Date date = Calendar.getInstance().getTime();
                String mydate = dateFormat.format(date);
                //String taskId = mDatabase.push().getKey();
               // DatabaseReference each_task=to_do_ref.child(taskId);
                Task task=new Task();
                //task.id=taskId;
                task.note=editTodo.getText().toString();
                task.priority=spinner.getSelectedItem().toString();
                task.status="unchecked";
                task.time=mydate.toString();
                taskList.add(task);
                to_do_ref.setValue(taskList);

            }
        });
    }


    public void menushowall(DataSnapshot snapshot)
    {


        ArrayList<Task> value= new ArrayList<Task>();
        for(DataSnapshot child:snapshot.getChildren())
        {
            //String a=child.getKey().toString();
            if(child!=null) {

                Task task =  child.getValue(Task.class);
                task.id=child.getKey();
                value.add(task);
            }
        }
        if(taskList!=null)
        {
            taskList  = value;
           // MainList=taskList;
        }
        setchanges();
    }
    public void menushowall_list()
    {

       // getData();
        setchanges();

    }
    public void menushowpending_list()
    {
       // getData();
        //taskList=PendingList;
       // setchanges();

        ArrayList<Task> value= new ArrayList<Task>();
        for (Task t:taskList)
        {
            if(t.status.toString().equals("unchecked"))
            {

                value.add(t);
            }
        }
        taskList=value;
        setchanges();

    }
    public void menushowcompleted_list()
    {
        //getData();
        //taskList=CompletedList;
       // setchanges();

       // keyList=new ArrayList<>();
        ArrayList<Task> value= new ArrayList<Task>();
        for (Task t:taskList)
        {
            if(t.status.toString().equals("checked"))
            {
                //keyList.add(t.id);
                value.add(t);
            }
        }
        taskList=value;
        setchanges();

    }

    public void menushow_Completed(DataSnapshot snapshot)
    {

       // keyList=new ArrayList<>();
        ArrayList<Task> value= new ArrayList<Task>();
        for(DataSnapshot snap:snapshot.getChildren())
        {

            if(snap!=null) {
                String status=snap.child("status").getValue().toString();
                if(status.equals("checked")) {
                   // keyList.add(snap.getKey());
                    Task task = snap.getValue(Task.class);
                    task.id = snap.getKey();
                    value.add(task);
                }
            }
        }
        if(taskList!=null)
        {
            taskList  = value;
        }
        setchanges();



    }


    public void menushow_Pending(DataSnapshot snapshot)
    {

       // keyList=new ArrayList<>();
        ArrayList<Task> value= new ArrayList<Task>();
        for(DataSnapshot snap:snapshot.getChildren())
        {

            if(snap!=null) {
                String status=snap.child("status").getValue().toString();
                if(status.equals("unchecked")) {
                  //  keyList.add(snap.getKey());
                    Task task = snap.getValue(Task.class);
                    task.id = snap.getKey();
                    value.add(task);
                }
            }
        }
        if(taskList!=null)
        {
            taskList  = value;
        }
        setchanges();
    }
    public void setchanges()
    {

        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                String p1=o1.status.toString();
                String p2=o2.status.toString();

                if (p1.equals("unchecked") && (p2.equals("checked")))
                    return -1;
                else if(p1.equals("checked") && p2.equals("unchecked"))
                    return 1;
                else if(p1.equals(p2))
                {
                    String m1=o1.priority.toString();
                    String m2=o2.priority.toString();
                    if(m1.equals(m2)) return 0;
                    if(m1.equals("Low") && (m2.equals("Medium") || m2.equals("High")))
                        return 1;
                    if(m1.equals("Medium") && m2.equals("High"))
                        return 1;
                    return -1;


                }
                return 0;
            };
        });

        final ListView listview=(ListView)findViewById(R.id.listview);
        task_adapter=new TaskAdapter(MainActivity.this,R.layout.listview_display,taskList,this);
        listview.setAdapter(task_adapter);

    }
    @Override
    public void checkbox_true(int id,Task task) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date date = Calendar.getInstance().getTime();
        String mydate = dateFormat.format(date);

        DatabaseReference r = mDatabase.child("Todo").child(task.id);
        Map<String,Object> updateObject = new HashMap<>();
        updateObject.put("status","checked");
        updateObject.put("time",mydate);
        r.updateChildren(updateObject);

    }


    @Override
    public void checkbox_false(int position,Task task) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date date = Calendar.getInstance().getTime();
        String mydate = dateFormat.format(date);
        Toast.makeText(MainActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
        DatabaseReference r = mDatabase.child("Todo").child(task.id);
        Map<String,Object> updateObject = new HashMap<>();
        updateObject.put("status","unchecked");
        updateObject.put("time",mydate);
        r.updateChildren(updateObject);

    }
}
