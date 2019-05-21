package example.com.homework07_group12;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TodoFragement extends Fragment  {

    private FirebaseDatabase database;
    TaskAdapter adapter;
    RecyclerView recyclerView;

    private ArrayList<Task> task_list;

    private ToDoInterface mListener;

    public TodoFragement() {
        // Required empty public constructor
    }

    public static TodoFragement newInstance(String param1, String param2) {
        TodoFragement fragment = new TodoFragement();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        setListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        setListView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Button btnCreate=(Button) getView().findViewById(R.id.btnCreate);

       // task_list=mListener.getList();

        recyclerView=(RecyclerView)getView().findViewById(R.id.recyclerViewTodo);
        final EditText addnewTask=(EditText)getView().findViewById(R.id.addNewTask);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String taskName= addnewTask.getText().toString().trim();
                mListener.CreateTask(taskName);
            }
        });



        //mListener.setRecyclerView(recyclerView);


        super.onActivityCreated(savedInstanceState);
    }


    public void setListView()
    {
        database = FirebaseDatabase.getInstance();
        task_list=new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        task_list=new ArrayList<>();
        DatabaseReference fetchTasks=database.getReference("Tasks/todo/");
        fetchTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                task_list.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Task task=new Task();

                    if(snapshot.child("status").getValue().toString().equals("todo"))
                    {
                        task.id = snapshot.child("id").getValue().toString();
                        task.name = snapshot.child("name").getValue().toString();
                        task.date = snapshot.child("date").getValue().toString();
                        task.status = snapshot.child("status").getValue().toString();
                        task_list.add(task);

                    }
                    //setListView();
                }

                ITask interfacea = (ITask)getContext();
                adapter=new TaskAdapter(task_list,interfacea);
                RecyclerView.LayoutManager rec_layout=new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(rec_layout);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_fragement, container, false);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToDoInterface) {
            mListener = (ToDoInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
/*
    @Override
    public void updateToDoneStatus(Task task) {



    }
*/
    public interface ToDoInterface {
        void setRecyclerView(RecyclerView recyclerView);
        ArrayList getList();
        void CreateTask(String taskName);

    }
}
