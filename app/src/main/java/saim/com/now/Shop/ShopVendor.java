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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import saim.com.now.Adapter.AdapterServiceShopList;
import saim.com.now.Adapter.AdapterServiceVendorList;
import saim.com.now.Model.ModelServiceList;
import saim.com.now.Model.ModelShopMenu;
import saim.com.now.Model.ModelShopVendor;
import saim.com.now.R;
import saim.com.now.Utilities.ApiURL;
import saim.com.now.Utilities.MySingleton;
import saim.com.now.Utilities.SharedPrefDatabase;

public class ShopVendor extends AppCompatActivity {

    public static Toolbar toolbar;

    ArrayList<ModelShopVendor> arrayListServiceVendorList = new ArrayList<>();
    RecyclerView recyclerViewServiceVendorList;
    RecyclerView.LayoutManager layoutManagerServiceVendorList;
    RecyclerView.Adapter serviceVendorListAdapter;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_vendor);

        init();
    }


    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Vendor List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBar);

        recyclerViewServiceVendorList = (RecyclerView) findViewById(R.id.recyclerViewServiceVendorList);
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerViewServiceVendorList.setLayoutManager(manager);
        recyclerViewServiceVendorList.setHasFixedSize(true);

        ServiceVendorList();
    }


    public void ServiceVendorList(){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.Vendor_List,
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
                                    String service_shop_vendor_id = jsonObjectServiceList.getString("service_shop_vendor_id");
                                    String service_shop_vendor_name = jsonObjectServiceList.getString("service_shop_vendor_name");
                                    String service_shop_vendor_icon = jsonObjectServiceList.getString("service_shop_vendor_icon");
                                    String service_shop_vendor_location = jsonObjectServiceList.getString("service_shop_vendor_location");
                                    String service_shop_vendor_mobile = jsonObjectServiceList.getString("service_shop_vendor_mobile");

                                    ModelShopVendor modelShopVendor = new ModelShopVendor(id, service_shop_vendor_id, service_shop_vendor_name, service_shop_vendor_icon, service_shop_vendor_location, service_shop_vendor_mobile);
                                    arrayListServiceVendorList.add(modelShopVendor);
                                }

                                serviceVendorListAdapter = new AdapterServiceVendorList(arrayListServiceVendorList);
                                recyclerViewServiceVendorList.setAdapter(serviceVendorListAdapter);

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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                Log.d("SAIM LOCATION", new SharedPrefDatabase(getApplicationContext()).RetriveUserLocation());
                params.put("service_shop_vendor_location", new SharedPrefDatabase(getApplicationContext()).RetriveUserLocation());

                return params;
            }
        };;
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
