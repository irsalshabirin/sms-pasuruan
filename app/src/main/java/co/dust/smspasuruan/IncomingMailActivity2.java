package co.dust.smspasuruan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.dust.smspasuruan.adapter.MainFragmentAdapter;
import co.dust.smspasuruan.fragment.AllMailFragment;
import co.dust.smspasuruan.fragment.DispositionFragment;
import co.dust.smspasuruan.fragment.ReadMailFragment;
import co.dust.smspasuruan.fragment.UnreadMailFragment;
import co.dust.smspasuruan.tools.APIService;
import co.dust.smspasuruan.tools.CredentialStorage;
import co.dust.smspasuruan.tools.ServiceFactory;
import co.dust.smspasuruan.tools.StaticHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomingMailActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = IncomingMailActivity2.class.getSimpleName();

    @BindView(R.id.smart_tab_layout)
    SmartTabLayout smartTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private CredentialStorage credentialStorage;

    private MainFragmentAdapter mainFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_mail_2);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        credentialStorage = new CredentialStorage(this);

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "satkerId : " + credentialStorage.getSatkerId());
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initTabLayout();
        prepareMenu();
    }

    private void initTabLayout() {
        mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());

        mainFragmentAdapter.addFragment(AllMailFragment.newInstance(), "Semua Surat");
        mainFragmentAdapter.addFragment(UnreadMailFragment.newInstance(), "Belum Dibaca");
        mainFragmentAdapter.addFragment(ReadMailFragment.newInstance(), "Sudah Dibaca");
        mainFragmentAdapter.addFragment(DispositionFragment.newInstance(), "Ter Disposisi");

        mViewPager.setAdapter(mainFragmentAdapter);
        mViewPager.setOffscreenPageLimit(mViewPager.getAdapter().getCount());

        smartTabLayout.setViewPager(mViewPager);

        smartTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                try {
                    navigationView.getMenu().getItem(position).setChecked(true);
                } catch (Exception ignored) {

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
//        return true;
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_search:
                Intent intent = new Intent(this, SearchMailActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_all_mail:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.nav_unread_mail:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.nav_read_mail:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.nav_disposition:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.nav_logout:
                showDialogLogOut();
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareMenu() {

//        if (headerView != null) {
//            navigationView.removeHeaderView(headerView);
//        }

        View view = navigationView.getHeaderView(0);

        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        tvName.setText(credentialStorage.getName());
        TextView tvSatker = (TextView) view.findViewById(R.id.tv_satker);
        tvSatker.setText(credentialStorage.getSatkerName());

        // setting header when login or not
//        if (!sessionManager.isLoggedIn()) {
//
//            headerView = navigationView.inflateHeaderView(R.layout.nav_header_login);
//            setFBCallback();
//
//        } else {

//            if (BuildConfig.DEBUG) {
//                Log.e(TAG, "img path : " + sessionManager.getImgPath());
//            }

//            headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
//            TextView txtWelcome = (TextView) headerView.findViewById(R.id.txtWelcome);
//            ImageView imageView = (ImageView) headerView.findViewById(R.id.imageView);

//            txtWelcome.setText(
//                    String.format(getString(R.string.welcome), sessionManager.getFullName())
//            );

//            try {
//                Glide.clear(imageView);
//            } catch (Exception ignored) {
//
//            }

//            String imgPath = sessionManager.getImgPath();
//            if (!imgPath.startsWith("http")) {
//                imgPath = AppConfig.url() + imgPath;
//                sessionManager.setImagePath(imgPath);
//            }
//
//            Glide.with(imageView.getContext())
//                    .load(imgPath)
//                    .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imageView);
//
//
//        }

        Menu menu = navigationView.getMenu();

        // all mail
        menu.findItem(R.id.nav_all_mail).setIcon(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_mail)
                .colorRes(android.R.color.white)
                .actionBarSize()
        ).setVisible(true);

        // unread mail
        menu.findItem(R.id.nav_unread_mail).setIcon(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_markunread_mailbox)
                .colorRes(android.R.color.white)
                .actionBarSize()
        ).setVisible(true);

        // read mail
        menu.findItem(R.id.nav_read_mail).setIcon(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_mail_outline)
                .colorRes(android.R.color.white)
                .actionBarSize()
        ).setVisible(true);

        // disposition
        menu.findItem(R.id.nav_disposition).setIcon(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_next_week)
                .colorRes(android.R.color.white)
                .actionBarSize()
        ).setVisible(true);

        // log out
        menu.findItem(R.id.nav_logout).setIcon(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_exit_to_app)
                .colorRes(android.R.color.white)
                .actionBarSize()
        ).setVisible(true);


    }

    private void showDialogLogOut() {
        new MaterialDialog.Builder(this)
                .title(R.string.logout)
                .content(R.string.are_you_sure)
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        doLogOut();

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void doLogOut() {
        StaticHelper.showProgressDialog(this, null, getString(R.string.loading), false);
        APIService apiService = ServiceFactory.createRetrofitService(APIService.class, true);
        apiService.updateFCMToken(credentialStorage.getUserId(), "").enqueue(new Callback<APIService.Message>() {
            @Override
            public void onResponse(Call<APIService.Message> call, Response<APIService.Message> response) {
                StaticHelper.hideProgressDialog();
                if (response.isSuccessful()) {
                    if (!response.body().error) {
                        credentialStorage.logout();

                        Intent intent = new Intent(IncomingMailActivity2.this, LoginActivity.class);
                        // set the new task and clear flags
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        // remove token if user log out
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        }

                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(IncomingMailActivity2.this, response.body().message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<APIService.Message> call, Throwable t) {
                StaticHelper.hideProgressDialog();
            }
        });
    }
}
