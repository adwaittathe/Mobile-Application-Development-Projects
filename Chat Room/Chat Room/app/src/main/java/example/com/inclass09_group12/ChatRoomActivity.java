package example.com.inclass09_group12;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class ChatRoomActivity extends AppCompatActivity implements IMessageTask{

    DatabaseReference mroot= FirebaseDatabase.getInstance().getReference();
    FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mauth;
    Uri filePath;
    String UserName;
    String LastName;
    String UserId;
    String imagePath;
    private ArrayList<Message> message_list;

    final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        ChatRoomActivity.this.setTitle("Chat Room");
        message_list=new ArrayList<>();
        final TextView txtName=(TextView)findViewById(R.id.txtThreadName);
        //DatabaseReference myRef = database.getReference("")
        mauth=FirebaseAuth.getInstance();
        mauth.getCurrentUser();
        FirebaseUser firebaseUser=mauth.getCurrentUser();
        UserId=firebaseUser.getUid();
        String name=firebaseUser.getDisplayName();
        String Email=firebaseUser.getEmail();
        DatabaseReference myRef = database.getReference("Users/"+UserId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    UserName = dataSnapshot.child("FirstName").getValue().toString();
                    LastName = dataSnapshot.child("LastName").getValue().toString();
                    String Fullname = UserName + " " +LastName;

                    txtName.setText(Fullname);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference msgRef = database.getReference("Messages");
        msgRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                message_list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    Message msg=new Message();
                    String image_check=snapshot.child("ImageURL").getValue().toString();
                    if(image_check.equals("No Image"))
                    {
                        msg.ImageURL=null;
                    }
                    else
                    {
                        msg.ImageURL=image_check;
                    }
                    //msg.ImageURL=snapshot.child("ImageURL").getValue().toString();
                    msg.Time=snapshot.child("Time").getValue().toString();
                    msg.UserName=snapshot.child("UserName").getValue().toString();
                    msg.UserLastName=snapshot.child("UserLastName").getValue().toString();
                    msg.msg_Text=snapshot.child("msg_Text").getValue().toString();
                    msg.UserId=snapshot.child("UserId").getValue().toString();
                    msg.msgId=snapshot.child("msgId").getValue().toString();
                    message_list.add(msg);
                    setListView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TextView txtUser=(TextView)findViewById(R.id.txtThreadName);
        ImageView homeButton=(ImageView) findViewById(R.id.homeButton);
        final EditText edinewMSg=(EditText) findViewById(R.id.editNewMessage);
        ImageView imgAddImage=(ImageView) findViewById(R.id.imgAddImage);
        ImageView imgSendMsg=(ImageView) findViewById(R.id.imgSendMsg);

        imgAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chooseImage();
            }
        });
        imgSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message=edinewMSg.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                Date date = Calendar.getInstance().getTime();
                final String mydate = dateFormat.format(date);
                DatabaseReference messages=mroot.child("Messages");
                final String msgId=UUID.randomUUID().toString();
                final DatabaseReference current_msg=messages.child(msgId);


                imagePath="MessageImages/"+UUID.randomUUID()+".jpg";
                StorageReference storageReference=firebaseStorage.getReference();
                final StorageReference messageReference=storageReference.child(imagePath);

                UploadTask uploadTask=null;
                if(filePath!=null) {
                     uploadTask= messageReference.putFile(filePath);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                            messageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Log.d("url" ,"Download URL : " + uri.toString());
                                }
                            });

                            Message msg=new Message();
                            msg.msgId=msgId;
                            msg.msg_Text=message;
                            msg.Time=mydate;
                            msg.ImageURL=imagePath;
                            msg.UserName=UserName;
                            msg.UserLastName=LastName;
                            msg.UserId=UserId;
                            current_msg.setValue(msg);

                            ImageView imgAddImage=(ImageView) findViewById(R.id.imgAddImage);
                            imgAddImage.setImageResource(R.drawable.addimage);
                            setListView();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
                else
                {
                    Message msg=new Message();
                    msg.msgId=msgId;
                    msg.msg_Text=message;
                    msg.Time=mydate;
                    msg.ImageURL="No Image";
                    msg.UserName=UserName;
                    msg.UserLastName=LastName;
                    msg.UserId=UserId;
                    current_msg.setValue(msg);

                }



            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mauth.signOut();
                startActivity(new Intent(ChatRoomActivity.this,MainActivity.class));
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("messages");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Log.d("str",bitmap.toString());

                ImageView imgAddImage=(ImageView) findViewById(R.id.imgAddImage);
                imgAddImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setMessageImage(final ImageView img, String path) {
        StorageReference storageReference=firebaseStorage.getReference();
        StorageReference setMsgRef=storageReference.child(path);

        Glide.with(this).load(storageReference).into(img);


        final long ONE_MEGABYTE = (1024 * 1024)*5;
        setMsgRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Log.d("dasa",bytes.toString());

                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


                img.setImageBitmap(Bitmap.createScaledBitmap(bmp, img.getWidth(),
                        img.getHeight(), false));
                // Data for "images/island.jpg" is returns, use this as needed
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


    }

    @Override
    public void deleteMsg(final Message message) {

        StorageReference storageReference=firebaseStorage.getReference();
        final DatabaseReference deleteRef=mroot.child("Messages/"+message.msgId);
        //StorageReference setMsgRef=storageReference.child(path);
        if(message.ImageURL!=null)
        {

            StorageReference setMsgRef=storageReference.child(message.ImageURL);
            setMsgRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {


                    deleteRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        }
        else
        {
            deleteRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            });
        }

    }

    public void setListView()
    {
        ListView listView=(ListView)findViewById(R.id.listMessages);
        MessageAdapter adapter=new MessageAdapter(ChatRoomActivity.this,R.layout.listview_layout,message_list,this,UserId);
        listView.setAdapter(adapter);

    }
}
