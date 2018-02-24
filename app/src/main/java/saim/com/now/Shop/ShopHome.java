package saim.com.now.Shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import saim.com.now.Adapter.AdapterServiceList;
import saim.com.now.Adapter.AdapterServiceShopList;
import saim.com.now.Model.ModelServiceList;
import saim.com.now.Model.ModelShopMenu;
import saim.com.now.R;
import saim.com.now.Utilities.ApiURL;
import saim.com.now.Utilities.MySingleton;

public class ShopHome extends AppCompatActivity {

    public static Toolbar toolbar;
    public static String VENDOR_ID;

    ArrayList<ModelShopMenu> arrayListServiceShopList = new ArrayList<>();
    RecyclerView recyclerViewServiceShopList;
    RecyclerView.LayoutManager layoutManagerServiceShopList;
    RecyclerView.Adapter serviceShopListAdapter;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_home);

        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shop Now");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBar);

        recyclerViewServiceShopList = (RecyclerView) findViewById(R.id.recyclerViewServiceShopList);
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerViewServiceShopList.setLayoutManager(manager);
        recyclerViewServiceShopList.setHasFixedSize(true);

        VENDOR_ID = getIntent().getExtras().getString("VENDOR_ID");

        ServiceShopList();
    }


    public void ServiceShopList(){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.Service_Shop_List,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("SAIM RESPONSE", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                JSONArray jsonArrayServiceList = jsonObject.getJSONArray("service_list");
                                for (int i=0; i<jsonArrayServiceList.length(); i++){
                                    JSONObject jsonObjectServiceList = jsonArrayServiceList.getJSONObject(i);

                                    String id = jsonObjectServiceList.getString("id");
                                    String service_shop_id = jsonObjectServiceList.getString("service_shop_id");
                                    String service_shop_name = jsonObjectServiceList.getString("service_shop_name");
                                    String service_shop_icon = jsonObjectServiceList.getString("service_shop_icon");
                                    String service_shop_color = jsonObjectServiceList.getString("service_shop_color");
                                    String service_shop_type = jsonObjectServiceList.getString("service_shop_type");

                                    ModelShopMenu modelShopMenu = new ModelShopMenu(id, service_shop_id, service_shop_name, service_shop_icon, service_shop_color, service_shop_type);
                                    arrayListServiceShopList.add(modelShopMenu);
                                }

                                serviceShopListAdapter = new AdapterServiceShopList(arrayListServiceShopList);
                                recyclerViewServiceShopList.setAdapter(serviceShopListAdapter);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu_shop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
