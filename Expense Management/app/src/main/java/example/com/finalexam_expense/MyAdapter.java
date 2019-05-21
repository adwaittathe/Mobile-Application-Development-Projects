package example.com.finalexam_expense;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {



    DoTask itask;
    ArrayList<Expense> expense_list;

    public MyAdapter (ArrayList<Expense> email_list, DoTask task) {
        this.expense_list = email_list;
        this.itask=task;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclelayout,viewGroup,false);

        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Expense expense=expense_list.get(i);
        viewHolder.txtName.setText(expense.name);
        viewHolder.txtValue.setText(String.valueOf(expense.value));
        viewHolder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               itask.Delete(expense);
            }
        });


    }

    @Override
    public int getItemCount() {
        return expense_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName, txtValue;
        ImageView imgDel;

        public ViewHolder(View itemView){
            super(itemView);


            txtName=(TextView)itemView.findViewById(R.id.recExpense);
            txtValue=(TextView)itemView.findViewById(R.id.recValue);
            imgDel=(ImageView) itemView.findViewById(R.id.imgDel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                }
            });

        }



    }

}
