package example.com.inclass07_group12;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class ProfileFragment extends Fragment {

    private IProfileFragment mListener;
    int avatar_id=0;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        getActivity().setTitle("My Profile");
        avatar_id=mListener.getImageId();
        setAvatar(avatar_id);
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ImageView imgSelectAvatar= (ImageView) getView().findViewById(R.id.img_avatar_select);
        Button btnSave=(Button)getView().findViewById(R.id.btn_save);
        final EditText textFirstName=(EditText)getView().findViewById(R.id.edit_firstName);
        final EditText textLastName=(EditText)getView().findViewById(R.id.edit_last_name);
        final EditText textStudentid=(EditText)getView().findViewById(R.id.edit_student_id);
        final RadioGroup rg=(RadioGroup)getView().findViewById(R.id.rg_Department);
        final RadioButton rb_CS=(RadioButton)getView().findViewById(R.id.rb_CS);
        final RadioButton rb_BIO=(RadioButton)getView().findViewById(R.id.rb_BIO);
        final RadioButton rb_Other=(RadioButton)getView().findViewById(R.id.rb_Other);
        final RadioButton rb_SIS=(RadioButton)getView().findViewById(R.id.rb_SIS);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(avatar_id==0)
                {
                    Toast.makeText(getActivity(), "Please select avataer", Toast.LENGTH_SHORT).show();
                }
                else if(textFirstName.getText().toString().length()<=0) {
                    Toast.makeText(getActivity(),"Please enter First Name",Toast.LENGTH_LONG).show();
                }
                else if(textLastName.getText().toString().length()<=0){
                    Toast.makeText(getActivity(),"Please enter Last Name",Toast.LENGTH_LONG).show();

                }
                else if(textStudentid.getText().toString().length()<=0){
                    Toast.makeText(getActivity(),"Please enter StudentId",Toast.LENGTH_LONG).show();

                }
                else if(rb_BIO.isChecked()==false && rb_CS.isChecked()==false && rb_SIS.isChecked()==false && rb_Other.isChecked()==false)
                {
                    //rb_BIO.isChecked()==false | rb_CS.isChecked()==false | rb_SIS.isChecked()==false | rb_Other.isChecked()==false
                    Toast.makeText(getActivity(),"Please select department",Toast.LENGTH_LONG).show();
                }
                else {
                    final RadioButton rbDept = (RadioButton) getView().findViewById(rg.getCheckedRadioButtonId());
                    final User user = new User(textFirstName.getText().toString(), textLastName.getText().toString(), textStudentid.getText().toString(), rbDept.getText().toString(), avatar_id);
                    mListener.GoToDisplayActivity(user);
                }
            }
        });
        imgSelectAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.GoToSelectAvatar();
            }
        });


        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("My Profile");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_profile, container, false);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IProfileFragment) {
            mListener = (IProfileFragment) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface IProfileFragment {
        // TODO: Update argument type and name
        public void GoToSelectAvatar();
        public int getImageId();
        public void GoToDisplayActivity(User user);

    }


    public  void setAvatar(int i)
    {
        int img_value=i;
        ImageView img_avatar= (ImageView) getActivity().findViewById(R.id.img_avatar_select);
        TextView txtAvatar=(TextView)getView().findViewById(R.id.txtAvatar);

        if (img_value == 1) {
            img_avatar.setImageResource(R.drawable.avatarf1);
            txtAvatar.setVisibility(View.INVISIBLE);

        }
        else if(img_value==2){
            img_avatar.setImageResource(R.drawable.avatar_f_2);
            txtAvatar.setVisibility(View.INVISIBLE);
        }
        else if(img_value==3){
            img_avatar.setImageResource(R.drawable.avatar_f_3);
            txtAvatar.setVisibility(View.INVISIBLE);
        }
        else if(img_value==4){
            img_avatar.setImageResource(R.drawable.avatar_m_1);
            txtAvatar.setVisibility(View.INVISIBLE);
        }
        else if(img_value==5){
            img_avatar.setImageResource(R.drawable.avatar_m_2);
            txtAvatar.setVisibility(View.INVISIBLE);
        }
        else if(img_value==6){
            img_avatar.setImageResource(R.drawable.avatar_m_3);
            txtAvatar.setVisibility(View.INVISIBLE);
        }

    }
}
