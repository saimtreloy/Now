package saim.com.now.Shop;

import android.content.Intent;
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

import saim.com.now.Adapter.AdapterServiceShopCatList;
import saim.com.now.Adapter.AdapterServiceShopList;
import saim.com.now.Model.ModelShopCatMenu;
import saim.com.now.Model.ModelShopMenu;
import saim.com.now.R;
import saim.com.now.Utilities.ApiURL;
import saim.com.now.Utilities.MySingleton;

public class ShopCategory extends AppCompatActivity {

    public static Toolbar toolbar;

    ArrayList<ModelShopCatMenu> arrayListServiceShopCatList = new ArrayList<>();
    RecyclerView recyclerViewServiceShopCatList;
    RecyclerView.LayoutManager layoutManagerServiceShopCatList;
    RecyclerView.Adapter serviceShopCatListAdapter;

    ProgressBar progressBar;

    public String SERVICE_SHOP_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_category);

        this.SERVICE_SHOP_ID = getIntent().getExtras().getString("SERVICE_SHOP_ID");

        toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("SERVICE_SHOP_NAME"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBar);

        recyclerViewServiceShopCatList = (RecyclerView) findViewById(R.id.recyclerViewServiceShopCatList);
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerViewServiceShopCatList.setLayoutManager(manager);
        recyclerViewServiceShopCatList.setHasFixedSize(true);

        ServiceShopList(SERVICE_SHOP_ID);
    }


    public void ServiceShopList(final String service_shop_id){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.Service_Shop_Cat_List,
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
                                    String service_shop_ic_id = jsonObjectServiceList.getString("service_shop_ic_id");
                                    String service_shop_ic_name = jsonObjectServiceList.getString("service_shop_ic_name");
                                    String service_shop_ic_icon = jsonObjectServiceList.getString("service_shop_ic_icon");
                                    String service_shop_ic_color = jsonObjectServiceList.getString("service_shop_ic_color");
                                    String service_shop_id = jsonObjectServiceList.getString("service_shop_id");

                                    ModelShopCatMenu modelShopCatMenu = new ModelShopCatMenu(id, service_shop_ic_id, service_shop_ic_name, service_shop_ic_icon, service_shop_ic_color, service_shop_id);
                                    arrayListServiceShopCatList.add(modelShopCatMenu);
                                }

                                serviceShopCatListAdapter = new AdapterServiceShopCatList(arrayListServiceShopCatList);
                                recyclerViewServiceShopCatList.setAdapter(serviceShopCatListAdapter);

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
                params.put("service_shop_id", service_shop_id);

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_shop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if(id == R.id.btnOptionShopCart) {
            startActivity(new Intent(getApplicationContext(), ShopCartList.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
