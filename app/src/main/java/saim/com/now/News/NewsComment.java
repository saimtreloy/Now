package saim.com.now.News;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import saim.com.now.AdapterNews.AdapterNewsComments;
import saim.com.now.ModelNews.ModelNewsComment;
import saim.com.now.ModelNews.ModelRecentPost;
import saim.com.now.ModelNews.ModelTicker;
import saim.com.now.R;
import saim.com.now.Utilities.ApiURL;
import saim.com.now.Utilities.MySingleton;
import saim.com.now.Utilities.SharedPrefDatabase;

public class NewsComment extends AppCompatActivity {

    public static Toolbar toolbar;
    ProgressDialog progressDialog;

    public String news_name, news_id;

    static ArrayList<ModelNewsComment> commentArrayList = new ArrayList<>();
    RecyclerView recyclerViewNewsComments;
    RecyclerView.LayoutManager layoutManagerComments;
    RecyclerView.Adapter commentsAdapter;

    EditText inputComments;
    ImageView imgSendComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeBody);
        setContentView(R.layout.activity_news_comment);

        news_name = getIntent().getExtras().getString("NEWS_NAME");
        news_id = getIntent().getExtras().getString("NEWS_ID");

        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(news_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");
        progressDialog.setCanceledOnTouchOutside(false);

        recyclerViewNewsComments = (RecyclerView) findViewById(R.id.recyclerViewNewsComments);
        layoutManagerComments = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewNewsComments.setLayoutManager(layoutManagerComments);
        recyclerViewNewsComments.setHasFixedSize(true);

        inputComments = (EditText) findViewById(R.id.inputComments);
        imgSendComment = (ImageView) findViewById(R.id.imgSendComment);

        RetriveComment(news_id);


        imgSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(inputComments.getText().toString())) {
                    PostComment(news_id, new SharedPrefDatabase(getApplicationContext()).RetriveUserID(), inputComments.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Input field can not be empty!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void RetriveComment(final String post_id){
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.NEWS_COMMENTS_RETRIVE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                JSONArray jsonArrayResult = jsonObject.getJSONArray("result");
                                for (int i =0; i<jsonArrayResult.length(); i++) {
                                    JSONObject jsonObjectResult = jsonArrayResult.getJSONObject(i);
                                    String user_name = jsonObjectResult.getString("user_name");
                                    String user_image = jsonObjectResult.getString("user_image");
                                    String comment = jsonObjectResult.getString("comment");
                                    String date = jsonObjectResult.getString("date");

                                    ModelNewsComment modelNewsComment = new ModelNewsComment(user_name, user_image, comment, date);
                                    commentArrayList.add(modelNewsComment);
                                }

                                commentsAdapter = new AdapterNewsComments(commentArrayList);
                                recyclerViewNewsComments.setAdapter(commentsAdapter);

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
                params.put("post_id", post_id);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    public void PostComment(final String post_id, final String user_id, final String comment ){
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.NEWS_COMMENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                Calendar cal = Calendar.getInstance();
                                String date = dateFormat.format(cal);

                                ModelNewsComment modelNewsComment = new ModelNewsComment(new SharedPrefDatabase(getApplicationContext()).RetriveUserName(),
                                        new SharedPrefDatabase(getApplicationContext()).RetriveUserImage(),
                                        comment,
                                        date);
                                commentArrayList.add(modelNewsComment);
                                commentsAdapter.notifyDataSetChanged();
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
                params.put("post_id", post_id);
                params.put("user_id", user_id);
                params.put("comment", comment);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
