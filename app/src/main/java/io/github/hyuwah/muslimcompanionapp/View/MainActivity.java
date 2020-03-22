package io.github.hyuwah.muslimcompanionapp.View;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.hyuwah.muslimcompanionapp.R;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.drawer_layout)
  DrawerLayout drawer;
  @BindView(R.id.nav_view)
  NavigationView navigationView;
  @BindView(R.id.view_fragment)
  FrameLayout viewFragment;

  FragmentManager fragmentManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    // Setup Toolbar & Drawer Layout
    setSupportActionBar(toolbar);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();
    navigationView.setNavigationItemSelectedListener(this);
    navigationView.setCheckedItem(R.id.nav_ayat_fetcher);

    fragmentManager = getSupportFragmentManager();

    // Set default screen
    fragmentManager.beginTransaction().replace(R.id.view_fragment, new AyahFetcherView()).commit();

  }


  @Override
  public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }


  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.

    Fragment fragment = null;
    Class fragmentClass= null;

    switch (item.getItemId()) {
      case R.id.nav_ayat_fetcher:
        fragmentClass = AyahFetcherView.class;
        break;

      case R.id.nav_share:

        String message = "Download Muslim Companion Apps di [insert link google play]!\nBe a better muslim.";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        Intent chooser = Intent.createChooser(share,"Share this apps");

        Log.i(getLocalClassName(), "onNavigationItemSelected: "+"Touchdown!");

        if(share.resolveActivity(getPackageManager()) != null){
          startActivity(chooser);
        }

        break;

      default:
        fragmentClass = AyahFetcherView.class;
    }

    if(fragmentClass != null){
      try {
        fragment = (Fragment) fragmentClass.newInstance();
      } catch (Exception e) {
        e.printStackTrace();
      }

      fragmentManager.beginTransaction().replace(R.id.view_fragment, fragment).commit();

      item.setChecked(true);
    }

    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

}
