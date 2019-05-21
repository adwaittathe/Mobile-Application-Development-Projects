package example.com.homework07_group12;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {


    ArrayList<Task> tasks_list;
    ITask task_interface;

    public TaskAdapter(ArrayList<Task> tasks_list, ITask task_interface)
    {

        this.tasks_list=tasks_list;
        this.task_interface=task_interface;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_items,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final Task task=tasks_list.get(i);
        SimpleDateFormat formatter4=new SimpleDateFormat("MMM dd yyyy");
        try {
            Date date=new SimpleDateFormat("MMM dd yyyy").parse(task.date.toString().trim());
            viewHolder.taskDate.setText(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        final String status= task.status.toLowerCase().trim();
        switch (status)
        {
            case "todo":
                viewHolder.taskImage.setImageResource(R.drawable.arrow);
                viewHolder.taskName.setText(task.name.toString().trim());

                break;
            case "doing":
                viewHolder.taskImage.setImageResource(R.drawable.tick);
                viewHolder.taskName.setText(task.name.toString().trim());
                viewHolder.taskDate.setText(task.date.toString().trim());
                break;
            case "done":
                viewHolder.taskImage.setImageResource(R.drawable.delete);
                viewHolder.taskName.setText(task.name.toString().trim());
                viewHolder.taskDate.setText(task.date.toString().trim());
                break;
            default:
                break;

        }
        viewHolder.taskImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status)
                {
                    case "todo":
                        task.status="doing";
                        task_interface.updateToDoingStatus(task);
                        break;
                    case "doing":
                        task.status="done";
                        task_interface.updateToDoneStatus(task);
                        break;
                    case "done":
                        task_interface.deleteTask(task);
                        break;
                    default:
                        break;

                }

            }
        });
        //viewHolder.taskImage.setImageResource();
    }

    @Override
    public int getItemCount() {
        return tasks_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        TextView taskDate;
        ImageView taskImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName=(TextView)itemView.findViewById(R.id.taskName);
            taskDate=(TextView)itemView.findViewById(R.id.taskDate);
            taskImage=(ImageView) itemView.findViewById(R.id.taskImage);

        }
    }
}
