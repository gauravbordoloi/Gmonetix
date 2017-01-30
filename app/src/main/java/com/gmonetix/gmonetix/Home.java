package com.gmonetix.gmonetix;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.gmonetix.gmonetix.constant.Const;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static String PAGE_TITLE = "page_title";
    private static String PAGE_URL = "page_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Recent(),getResources().getString(R.string.recent_posts));
        adapter.addFragment(new Categories(),getResources().getString(R.string.categories));
        adapter.addFragment(new AskYourProblemActivity(),getResources().getString(R.string.queries));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_settings:
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                break;
            case R.id.nav_contact_us:
                startActivity(new Intent(Home.this,ContactUs.class));
                break;
            case R.id.nav_about_us:
                Intent intent = new Intent(Home.this,SitePageLoader.class);
                intent.putExtra(PAGE_TITLE,"About Us");
                intent.putExtra(PAGE_URL, Const.pages.replace("PAGE_ID","53"));
                startActivity(intent);
                break;
            case R.id.nav_facebook:
                startActivity(openFacebook(getApplicationContext()));
                break;
            case R.id.nav_youtube:
                startActivity(openYoutube(getApplicationContext()));
                break;
            case R.id.nav_website:
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.gmonetix.com")));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Intent openFacebook(Context context){
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana",0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/122591891284060"));
        } catch (Exception e){
            return new Intent(Intent.ACTION_VIEW,Uri.parse("https://facebook.com/gmonetix"));
        }
    }

    public static Intent openYoutube(Context context){
        try {
            context.getPackageManager().getPackageInfo("com.google.android.youtube",0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCpfLFzQqRl01sXP7dxIQ_rg"));
        } catch (Exception e){
            return new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/channel/UCpfLFzQqRl01sXP7dxIQ_rg"));
        }
    }
}
