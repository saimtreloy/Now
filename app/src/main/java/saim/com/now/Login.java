package saim.com.now;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import saim.com.now.Utilities.ApiURL;
import saim.com.now.Utilities.MySingleton;

public class Login extends AppCompatActivity {

    ProgressDialog progressDialog;

    LinearLayout layoutLogin, layoutRegister;
    TextView txtLoginAgain, txtRegisterAgain;

    TextInputEditText inputEmailLogin, inputPasswordLogin;
    Button btnLogin;

    TextInputEditText inputName, inputEmail, inputMobile, inputPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        layoutLogin = (LinearLayout) findViewById(R.id.layoutLogin);
        layoutRegister = (LinearLayout) findViewById(R.id.layoutRegister);

        txtLoginAgain = (TextView) findViewById(R.id.txtLoginAgain);
        txtRegisterAgain = (TextView) findViewById(R.id.txtRegisterAgain);

        inputEmailLogin = (TextInputEditText) findViewById(R.id.inputEmailLogin);
        inputPasswordLogin = (TextInputEditText) findViewById(R.id.inputPasswordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        inputName = (TextInputEditText) findViewById(R.id.inputName);
        inputEmail = (TextInputEditText) findViewById(R.id.inputEmail);
        inputMobile = (TextInputEditText) findViewById(R.id.inputMobile);
        inputPassword = (TextInputEditText) findViewById(R.id.inputPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        ButtonClicked();
    }

    private void ButtonClicked() {

        txtRegisterAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.SlideOutLeft).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutRegister.setVisibility(View.VISIBLE);
                        layoutLogin.setVisibility(View.GONE);
                        YoYo.with(Techniques.SlideInRight).duration(250).playOn(layoutRegister);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(layoutLogin);
            }
        });


        txtLoginAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.SlideOutRight).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutRegister.setVisibility(View.GONE);
                        layoutLogin.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.SlideInLeft).duration(250).playOn(layoutLogin);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(layoutRegister);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputEmailLogin.getText().toString().isEmpty() && !inputPasswordLogin.getText().toString().isEmpty()) {
                    UserLogin(inputEmailLogin.getText().toString(), inputPasswordLogin.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Input field can not be emplty!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputName.getText().toString().isEmpty() && !inputEmail.getText().toString().isEmpty() && !inputMobile.getText().toString().isEmpty() && !inputPassword.getText().toString().isEmpty()) {
                    UserRegistration(inputName.getText().toString(), inputEmail.getText().toString(), inputMobile.getText().toString(), inputPassword.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Input field can not be emplty!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }




    public void UserRegistration(final String user_name, final String user_email, final String user_mobile, final String user_pass){
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.Register,
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
                                inputName.setText("");
                                inputEmail.setText("");
                                inputMobile.setText("");
                                inputPassword.setText("");
                                txtLoginAgain.performClick();
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
                params.put("user_name", user_name);
                params.put("user_email", user_email);
                params.put("user_mobile", user_mobile);
                params.put("user_pass", user_pass);

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    public void UserLogin(final String user_email, final String user_pass){
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.Login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
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
                params.put("user_email", user_email);
                params.put("user_pass", user_pass);

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
