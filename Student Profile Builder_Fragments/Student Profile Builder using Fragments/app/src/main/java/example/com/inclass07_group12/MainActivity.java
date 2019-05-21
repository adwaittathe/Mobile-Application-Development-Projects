package example.com.inclass07_group12;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ProfileFragment.IProfileFragment,SelectAvatarFragment.ISelectAvatar,DisplayFragment.IDisplayFragment {

    private int avatar_id;
    private User current_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MainActivity.this.setTitle("My Profile");
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.linear_container,new ProfileFragment(),"profile")
                .commit();



    }


    @Override
    public void GoToSelectAvatar() {


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linear_container,new SelectAvatarFragment(),"select")
                .addToBackStack(null)
                .commit();
        //MainActivity.this.setTitle("Select Avatar");
    }

    @Override
    public int getImageId() {

        return avatar_id;
    }

    @Override
    public void GoToDisplayActivity(User user) {

        current_user=user;

        DisplayFragment df = new DisplayFragment();
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        df.setArguments(args);

        //MainActivity.this.setTitle("Display My Profile");
        getSupportFragmentManager().beginTransaction().replace(R.id.linear_container, df,"display")
                .addToBackStack(null)
                .commit();



    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0)
        {
            getSupportFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }



    @Override
    public void chooseavatar(int i) {

        avatar_id=i;
        getSupportFragmentManager().popBackStack();
        /*
        ProfileFragment pf = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("profile");
        Bundle args = new Bundle();
        args.putInt("avatarId", i);
        pf.setArguments(args);

//Inflate the fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.linear_container, pf)
                .addToBackStack(null)
                .commit();
                */
    }

    @Override
    public User getUserdetails() {
        return current_user;
    }

    @Override
    public void EditClick() {

        getSupportFragmentManager().popBackStack();
    }
}
