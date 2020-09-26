package tk.yumini.genesistestapp_.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import tk.yumini.genesistestapp_.R;
import tk.yumini.genesistestapp_.adapter.SectionsPagerAdapter;
import tk.yumini.genesistestapp_.model.room.FavRepo;
import tk.yumini.genesistestapp_.view.fragments.FavoriresFragment;

public class MainActivity extends AppCompatActivity implements FavoriresFragment.onSomeEventListener {

    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        mPager = findViewById(R.id.view_pager);
        mPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(mPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPager.setCurrentItem(0);
                mAboutDataListener.onDataReceived(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public void someEvent(FavRepo model) {
        mAboutDataListener.onupdateList(model);
    }

    private OnAboutDataReceivedListener mAboutDataListener;


    public interface OnAboutDataReceivedListener {
        void onDataReceived(String model);
        void onupdateList(FavRepo model);
    }

    public void setAboutDataListener(OnAboutDataReceivedListener listener) {
        this.mAboutDataListener = listener;
    }

}