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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DoneFragment extends Fragment {

    private DoneInterface mListener;
    private FirebaseDatabase database;
    private ArrayList<Task> task_list;
    RecyclerView recyclerView;
    public DoneFragment() {
        // Required empty public constructor
    }

    public static DoneFragment newInstance(String param1, String param2) {
        DoneFragment fragment = new DoneFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerviewDone);
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
        setListView();
    }

    public void setListView() {
        database = FirebaseDatabase.getInstance();
        task_list=new ArrayList<>();
        DatabaseReference fetchTasks = database.getReference("Tasks/done/");
        fetchTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                task_list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Task task = new Task();

                    task.id = snapshot.child("id").getValue().toString();
                    task.name = snapshot.child("name").getValue().toString();
                    task.date = snapshot.child("date").getValue().toString();
                    task.status = snapshot.child("status").getValue().toString();
                    task_list.add(task);


                    //setListView();
                }

                ITask interfacea = (ITask) getContext();
                TaskAdapter adapter = new TaskAdapter(task_list, interfacea);
                RecyclerView.LayoutManager rec_layout = new LinearLayoutManager(getContext());
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
    public void onResume() {
        setListView();
        super.onResume();
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
        return inflater.inflate(R.layout.fragment_done, container, false);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DoneInterface) {
            mListener = (DoneInterface) context;
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

    public interface DoneInterface {


    }
}
