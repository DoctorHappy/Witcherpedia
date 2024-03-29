package com.example.drhappy.witcherpedia;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	private FragmentManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(view -> {
			//Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
                /*EditText editText = (EditText) findViewById(R.id.editText);
                  String message = editText.getText().toString();
                  intent.putExtra(EXTRA_MESSAGE, message);*/
			//startActivity(intent);

			ListFragment listFragment = ListFragment.newInstance("Factions");

			FragmentTransaction ft = manager.beginTransaction();
			ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

			ft.replace(R.id.frame_container, listFragment, "ListFragment");
			ft.commit();

			//Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
			//        .setAction("Action", null).show();
		});

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		manager = getSupportFragmentManager();

		if (savedInstanceState == null) {
			FragmentTransaction ft = manager.beginTransaction();
			//ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
			System.out.println("null on create");
			ft.replace(R.id.frame_container, HomeFragment.newInstance());
			ft.commit();
		}

		System.out.println("On create: " + ((Witcherpedia) getApplicationContext()).getCurrent_view());
        /*
        String current_view = ((Witcherpedia)getApplicationContext()).getCurrent_view();

        if (current_view == null) {
            current_view = "Witcherpedia";
        } else {
            if (current_view.contains("Description")) {
                int current_layout = -1;
                if (current_view.contains("Unit")) {
                    //current_layout = R.id.content_unit_description;
                } else if (current_view.contains("Territory")) {
                    //current_layout = R.id.content_territory_description;
                }

                //setVisibleLayout(R.id.content_list, current_layout);
                //setDescription(current_view, desc_selected);
            } else if (current_view.equals("Overview")) {
                //setVisibleLayout(R.id.content_list, R.id.content_overview);
                //setOverview();
            } else {
                //setVisibleLayout(R.id.content_unit_description, R.id.content_list);
                //setAdapter(current_view);
            }

            setTitle(current_view);
        }*/
	}

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        System.out.println("On save instance state: " + ((Witcherpedia)getApplicationContext()).getCurrent_view());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        System.out.println("On restore instance state: " + ((Witcherpedia)getApplicationContext()).getCurrent_view());
    }*/

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			//super.onBackPressed();

			String current_view = ((Witcherpedia) getApplicationContext()).getCurrent_view();

			if (current_view.equals("Witcherpedia")) {
				drawer.openDrawer(GravityCompat.START);

			} else if (current_view.equals("Factions") || current_view.equals("Bestiary File")) {
				//setAdapter("Witcherpedia");
				FragmentTransaction ft = manager.beginTransaction();
				ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

				ft.replace(R.id.frame_container, HomeFragment.newInstance());
				ft.commit();


				NavigationView navigationView = findViewById(R.id.nav_view);
				navigationView.getMenu().findItem(R.id.factions).setChecked(false);
				navigationView.getMenu().findItem(R.id.monsters).setChecked(false);

			} else if (current_view.equals("Contents")) {
				//setAdapter("Factions");
				ListFragment listFragment = (ListFragment) manager.findFragmentByTag("ListFragment");
				if (listFragment != null && listFragment.isVisible()) {
					listFragment.setListAdapter("Factions");

					CustomArrayAdapter adapter = (CustomArrayAdapter) listFragment.getListView().getAdapter();
					listFragment.getListView().setSelection(adapter.labels.indexOf(((Witcherpedia) getApplicationContext()).getFaction_selected()) - 2);
				}

				((Witcherpedia) getApplicationContext()).setFaction_selected("");

			} else if (current_view.equals("Units") || current_view.equals("Heroes") || current_view.equals("Territories") || current_view.equals("Overview")) {
				if (current_view.equals("Overview")) {
					ListFragment listFragment = ListFragment.newInstance("Contents");

					FragmentTransaction ft = manager.beginTransaction();
					ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

					ft.replace(R.id.frame_container, listFragment, "ListFragment");
					ft.commit();
				} else {
					ListFragment listFragment = (ListFragment) manager.findFragmentByTag("ListFragment");
					if (listFragment != null && listFragment.isVisible()) {
						listFragment.setListAdapter("Contents");
					}
				}

			} else if (current_view.equals("Beasts") || current_view.equals("Cursed") || current_view.equals("Draconids") || current_view.equals("Elementae & Constructs") || current_view.equals("Hybrids")
					|| current_view.equals("Insectoids") || current_view.equals("Necrophages") || current_view.equals("Ogroids") || current_view.equals("Relicts") || current_view.equals("Specters") || current_view.equals("Vampires")) {
				ListFragment listFragment = (ListFragment) manager.findFragmentByTag("ListFragment");
				if (listFragment != null && listFragment.isVisible()) {
					listFragment.setListAdapter("Bestiary File");
				}
			}
            /*else if (current_view.contains("Description")) {
                ListFragment listFragment = null;
                if (current_view.contains("Unit")) {
                    //setVisibleLayout(R.id.content_unit_description, R.id.content_list);
                    listFragment = ListFragment.newInstance("Units");
                } else if (current_view.contains("Territory")) {
                    //setVisibleLayout(R.id.content_territory_description, R.id.content_list);
                    listFragment = ListFragment.newInstance("Territories");
                }

                FragmentTransaction ft = manager.beginTransaction();
                ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

                ft.replace(R.id.frame_container, listFragment, "ListFragment");
                ft.commit();

                CustomArrayAdapter adapter = ((CustomArrayAdapter)listFragment.getListView().getAdapter());
                listFragment.getListView().setSelection(adapter.labels.indexOf(((Witcherpedia)getApplicationContext()).getDesc_selected()) - 2);
            }*/

			//setTitle(((Witcherpedia)getApplicationContext()).getCurrent_view());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		// Handle navigation view item clicks here.
		item.setChecked(true);
		int id = item.getItemId();

		switch (id) {
			case R.id.factions:
				ListFragment flistFragment = ListFragment.newInstance("Factions");

				FragmentTransaction fft = manager.beginTransaction();
				fft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

				fft.replace(R.id.frame_container, flistFragment, "ListFragment");
				fft.commit();

				break;
			case R.id.monsters:
				ListFragment blistFragment = ListFragment.newInstance("Bestiary File");

				FragmentTransaction bft = manager.beginTransaction();
				bft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

				bft.replace(R.id.frame_container, blistFragment, "ListFragment");
				bft.commit();

				break;
			case R.id.nav_gallery:

				break;
			case R.id.nav_manage:

				break;
			case R.id.nav_share:

				break;
			case R.id.nav_send:

				break;
		}

		//setTitle(((Witcherpedia)getApplicationContext()).getCurrent_view());

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);

		return true;
	}

}
