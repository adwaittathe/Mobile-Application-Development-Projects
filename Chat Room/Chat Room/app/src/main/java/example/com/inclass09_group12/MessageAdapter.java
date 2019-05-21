package example.com.inclass09_group12;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.ocpsoft.prettytime.PrettyTime;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MessageAdapter extends ArrayAdapter<Message> {


    FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    IMessageTask message_interface;
    String userId;
    public MessageAdapter(Context context, int resource,  List<Message> objects, IMessageTask interface_msg, String UserId) {
        super(context, resource, objects);
        this.message_interface=interface_msg;
        this.userId=UserId;
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        final Message message=getItem(position);
        final ViewHolder viewHolder;
        if(convertView==null)
        {
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.listview_layout,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.msg_text=(TextView) convertView.findViewById(R.id.msg_text);
            viewHolder.msg_user=(TextView) convertView.findViewById(R.id.msg_firstName);
            viewHolder.msg_time=(TextView) convertView.findViewById(R.id.msg_time);
            viewHolder.msg_delete=(ImageView) convertView.findViewById(R.id.msg_delete);
            viewHolder.msg_image=(ImageView) convertView.findViewById(R.id.msg_image);
            //viewHolder.rl=(RelativeLayout)convertView.findViewById(R.id.relativeLayout);
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.msg_text.setText(message.msg_Text);
        viewHolder.msg_user.setText(message.UserName );
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date date=dateFormat.parse(message.Time.toString());
            PrettyTime pt=new PrettyTime();
            //Log.d("msgtime","prettytime final____"+pt.format(date));

            viewHolder.msg_time.setText(pt.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        };

        if(message.UserId.equals(this.userId))
        {
            viewHolder.msg_delete.setVisibility(View.VISIBLE);
        }
        else
        {
            viewHolder.msg_delete.setVisibility(View.INVISIBLE);
        }
        viewHolder.msg_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                message_interface.deleteMsg(message);
            }
        });
        if(message.ImageURL!=null)
        {
            StorageReference storageReference=firebaseStorage.getReference();
            StorageReference setMsgRef=storageReference.child(message.ImageURL);

           // Glide.with(getContext()).load(storageReference).into(viewHolder.msg_image);

            final long ONE_MEGABYTE = (1024 * 1024)*5;
            setMsgRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    viewHolder.msg_image.setVisibility(View.VISIBLE);
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    viewHolder.msg_image.setImageBitmap(bmp);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
           // message_interface.setMessageImage(viewHolder.msg_image, message.ImageURL );
        }
        else
        {
            //RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)viewHolder.msg_user.getLayoutParams();
          //  param.addRule(RelativeLayout.ALIGN_TOP);
           // viewHolder.msg_user.setLayoutParams(param);




          //  ViewGroup.MarginLayoutParams params=(ViewGroup.MarginLayoutParams)viewHolder.msg_text.getLayoutParams();
           // params.leftMargin=80;
//here 100 means 100px,not 80% of the width of the parent view
//you may need a calculation to convert the percentage to pixels
      //      params.topMargin=50;
         //   viewHolder.msg_text.setLayoutParams(params);
            //viewHolder.msg_image.setImageResource(0);
           // viewHolder.msg_image.setImageDrawable(null);
          //  viewHolder.msg_image.setImageResource(0);
           // viewHolder.msg_image.setVisibility(View.INVISIBLE);
            viewHolder.msg_image.setVisibility(View.GONE);
            //viewHolder.msg_image.setLayoutParams(new LinearLayout.LayoutParams(0,0));

        }




        return convertView;
    }

    private static class ViewHolder{
        TextView msg_text;
        ImageView msg_image;
        TextView msg_user;
        TextView msg_time;
        ImageView msg_delete;
        //RelativeLayout rl;

    }

}
