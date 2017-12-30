package co.dust.smspasuruan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by irsal on 6/9/17.
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {

//    private final FragmentManager mFragmentManager;
//    private final Map<Integer, String> mTags = new HashMap<>();

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();


    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
//        mFragmentManager = fm;
    }

    private static String makeTagName(int position) {
        return MainFragmentAdapter.class.getName() + ":" + position;
    }

    @Override
    public Fragment getItem(int position) {
//        String tagName = mSavedInstanceState.getString(makeTagName(position));
//        if (!TextUtils.isEmpty(tagName)) {
//            Fragment fragment = mFragmentManager.findFragmentByTag(tagName);
//            return fragment != null ? fragment : mFragmentList.get(position);
//        }
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }


    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

}
