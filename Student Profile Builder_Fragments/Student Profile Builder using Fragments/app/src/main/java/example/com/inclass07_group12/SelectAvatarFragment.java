package example.com.inclass07_group12;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class SelectAvatarFragment extends Fragment {

    private  ISelectAvatar mListener;

    public SelectAvatarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_avatar, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("Select Avatar");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Select Avatar");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        final ImageView img_F1=(ImageView)getView().findViewById(R.id.img_avatar_F1);
        final ImageView img_F2=(ImageView)getView().findViewById(R.id.img_avatar_F2);
        final ImageView img_F3=(ImageView)getView().findViewById(R.id.img_avatar_F3);
        final ImageView img_M1=(ImageView)getView().findViewById(R.id.img_avatar_M1);
        final ImageView img_M2=(ImageView)getView().findViewById(R.id.img_avatar_M2);
        final ImageView img_M3=(ImageView)getView().findViewById(R.id.img_avatar_M3);

        img_F1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                mListener.chooseavatar(1);



            }
        });

        img_F2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.chooseavatar(2);
            }
        });

        img_F3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.chooseavatar(3);
            }
        });

        img_M1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.chooseavatar(4);

            }
        });

        img_M2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.chooseavatar(5);

            }
        });

        img_M3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.chooseavatar(6);
            }
        });

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ISelectAvatar) {
            mListener = (ISelectAvatar) context;
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
    public interface ISelectAvatar {

        public void chooseavatar(int i);


    }
}
