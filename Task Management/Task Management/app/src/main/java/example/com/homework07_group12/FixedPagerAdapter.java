package example.com.homework07_group12;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FixedPagerAdapter extends FragmentStatePagerAdapter {

    public FixedPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                 return new TodoFragement();
            case 1:
                 return new DoingFragment();
            case 2:
                 return new DoneFragment();
            default:
                return null;
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "To do";
            case 1:
                return "Doing";
            case 2:
                return "Done";
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
