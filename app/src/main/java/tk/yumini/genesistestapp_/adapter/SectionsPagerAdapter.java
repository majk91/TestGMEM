package tk.yumini.genesistestapp_.adapter;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import tk.yumini.genesistestapp_.R;
import tk.yumini.genesistestapp_.view.fragments.FavoriresFragment;
import tk.yumini.genesistestapp_.view.fragments.PlaceholderFragment;
import tk.yumini.genesistestapp_.view.fragments.RepositoryListFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return  RepositoryListFragment.newInstance();
            }
            case 1:{
                return FavoriresFragment.newInstance();
            }
            default:{
                Toast.makeText(mContext, "not exist Fragment", Toast.LENGTH_SHORT).show();
                return PlaceholderFragment.newInstance(position + 1);
            }
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}