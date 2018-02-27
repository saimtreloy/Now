package saim.com.now;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import saim.com.now.Activity.Profile;
import saim.com.now.Adapter.AdapterServiceList;
import saim.com.now.Database.DatabaseHandler;
import saim.com.now.Model.ModelLocation;
import saim.com.now.Model.ModelServiceList;
import saim.com.now.Utilities.ApiURL;
import saim.com.now.Utilities.MySingleton;
import saim.com.now.Utilities.SharedPrefDatabase;

public class MainActivity extends AppCompatActivity {

    public static Toolbar toolbar;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle;
    ProgressBar progressBar;

    ArrayList<ModelServiceList> arrayListServiceList = new ArrayList<>();
    ArrayList<String> arrayListLocationList = new ArrayList<>();
    RecyclerView recyclerViewServiceList;
    RecyclerView.LayoutManager layoutManagerServiceList;
    RecyclerView.Adapter serviceListAdapter;

    CircleImageView imgNavProfile;

    //Location
    LinearLayout layoutLocation;
    Spinner spinnerLocation;
    Button btnLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                Log.d("SAIM DRAWER", "Nav drawer opend");
                super.onDrawerOpened(drawerView);
            }
        };

        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationItemClicked();


        recyclerViewServiceList = (RecyclerView) findViewById(R.id.recyclerViewServiceList);
        //layoutManagerServiceList = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        //recyclerViewServiceList.setLayoutManager(layoutManagerServiceList);

        //AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 333);
        //recyclerViewServiceList.setLayoutManager(layoutManager);

        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerViewServiceList.setLayoutManager(manager);
        recyclerViewServiceList.setHasFixedSize(true);

        layoutLocation = (LinearLayout) findViewById(R.id.layoutLocation);
        spinnerLocation = (Spinner) findViewById(R.id.spinnerLocation);
        btnLocation = (Button) findViewById(R.id.btnLocation);

        LocationList();
        //ServiceList();
    }

    private void NavigationItemClicked() {
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        View headerView = navigationView.getHeaderView(0);

        imgNavProfile = (CircleImageView) headerView.findViewById(R.id.imgNavProfile);
        ImageView imgLogoutHeader = (ImageView) headerView.findViewById(R.id.imgLogoutHeader);

        TextView txtNavName = (TextView) headerView.findViewById(R.id.txtNavName);
        TextView txtNavEmail = (TextView) headerView.findViewById(R.id.txtNavEmail);
        TextView txtNavRate = (TextView) headerView.findViewById(R.id.txtNavRate);

        Picasso.with(getApplicationContext())
                .load(new SharedPrefDatabase(getApplicationContext()).RetriveUserImage())
                .placeholder(R.drawable.ic_placeholder_icon)
                .error(R.drawable.ic_placeholder_icon)
                .into(imgNavProfile);
        txtNavName.setText(new SharedPrefDatabase(getApplicationContext()).RetriveUserName());
        txtNavEmail.setText(new SharedPrefDatabase(getApplicationContext()).RetriveUserEmail());

        imgNavProfile.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                drawerLayout.closeDrawers();
            }
        });
        imgLogoutHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                AlertLogout();
            }
        });

        Menu nav_menu = navigationView.getMenu();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.btnMenuProfile) {
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnOptionTC:
                return true;
            case R.id.btnOptionPP:
                return true;
            case R.id.btnOptionExit:
                AlertClose();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        AlertClose();
    }

    public void AlertClose() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void AlertLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Login.modelMainUsersArrayList.clear();
                        ClearUserData("", "", "", "", "", "");
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void ServiceList(){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.Service_List,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                JSONArray jsonArrayServiceList = jsonObject.getJSONArray("service_list");
                                for (int i=0; i<jsonArrayServiceList.length(); i++){
                                    JSONObject jsonObjectServiceList = jsonArrayServiceList.getJSONObject(i);

                                    String id = jsonObjectServiceList.getString("id");
                                    String service_id = jsonObjectServiceList.getString("service_id");
                                    String service_name = jsonObjectServiceList.getString("service_name");
                                    String service_icon = jsonObjectServiceList.getString("service_icon");
                                    String service_color = jsonObjectServiceList.getString("service_color");
                                    String service_status = jsonObjectServiceList.getString("service_status");

                                    ModelServiceList modelServiceList = new ModelServiceList(id, service_id, service_name, service_icon, service_color,service_status);
                                    arrayListServiceList.add(modelServiceList);
                                }

                                serviceListAdapter = new AdapterServiceList(arrayListServiceList);
                                recyclerViewServiceList.setAdapter(serviceListAdapter);

                            }else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.d("HDHD 1", e.toString() + "\n" + response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void LocationList(){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.Location,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                JSONArray jsonArrayServiceList = jsonObject.getJSONArray("service_list");
                                for (int i=0; i<jsonArrayServiceList.length(); i++){
                                    JSONObject jsonObjectServiceList = jsonArrayServiceList.getJSONObject(i);

                                    String id = jsonObjectServiceList.getString("id");
                                    String location_name = jsonObjectServiceList.getString("location_name");
                                    String location_lat = jsonObjectServiceList.getString("location_lat");
                                    String location_lon = jsonObjectServiceList.getString("location_lon");

                                    ModelLocation modelLocation = new ModelLocation(id, location_name, location_lat, location_lat);
                                    arrayListLocationList.add(location_name);
                                }

                                //showLocationList("Select your location", arrayListLocationList);
                                populateSpinnerLocation(arrayListLocationList);
                            }else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.d("HDHD 1", e.toString() + "\n" + response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    public void ClearUserData(String id, String name, String email, String mobile, String pass, String image){

        new SharedPrefDatabase(getApplicationContext()).StoreUserID(id);
        new SharedPrefDatabase(getApplicationContext()).StoreUserName(name);
        new SharedPrefDatabase(getApplicationContext()).StoreUserEmail(email);
        new SharedPrefDatabase(getApplicationContext()).StoreUserMobile(mobile);
        new SharedPrefDatabase(getApplicationContext()).StoreUserPass(pass);
        new SharedPrefDatabase(getApplicationContext()).StoreUserImage(image);

    }

    public void populateSpinnerLocation(ArrayList<String> locationList) {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, locationList);
        spinnerLocation.setAdapter(arrayAdapter);

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = spinnerLocation.getSelectedItem().toString();
                new SharedPrefDatabase(getApplicationContext()).StoreUserLocation(s);
                layoutLocation.setVisibility(View.GONE);
                recyclerViewServiceList.setVisibility(View.VISIBLE);
                ServiceList();
            }
        });
    }

    public void showLocationList(String title, ArrayList<String> locationList) {

        LayoutInflater factory = LayoutInflater.from(this);
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(this).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, locationList);
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new SharedPrefDatabase(getApplicationContext()).StoreUserLocation(arrayListLocationList.get(position));
                ServiceList();
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }

}
