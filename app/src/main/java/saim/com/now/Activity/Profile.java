package saim.com.now.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import saim.com.now.R;
import saim.com.now.Utilities.SharedPrefDatabase;

public class Profile extends AppCompatActivity {

    public static Toolbar toolbar;

    CircleImageView imgProfile;
    ImageView imgProCall, imgProChangeImage, imgProChangePass, imgProChangeEmail;
    TextView txtProName, txtProEmail, txtProMobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

        imgProfile = (CircleImageView) findViewById(R.id.imgProfile);

        imgProCall = (ImageView) findViewById(R.id.imgProCall);
        imgProChangeImage = (ImageView) findViewById(R.id.imgProChangeImage);
        imgProChangePass = (ImageView) findViewById(R.id.imgProChangePass);
        imgProChangeEmail = (ImageView) findViewById(R.id.imgProChangeEmail);

        txtProName = (TextView) findViewById(R.id.txtProName);
        txtProEmail = (TextView) findViewById(R.id.txtProEmail);
        txtProMobile = (TextView) findViewById(R.id.txtProMobile);

        PopulateView();
    }

    public void PopulateView(){
        YoYo.with(Techniques.ZoomIn).duration(1000).playOn(imgProfile);
        YoYo.with(Techniques.ZoomIn).duration(1000).playOn(imgProCall);
        YoYo.with(Techniques.ZoomIn).duration(1000).playOn(imgProChangeImage);
        YoYo.with(Techniques.ZoomIn).duration(1000).playOn(imgProChangePass);
        YoYo.with(Techniques.ZoomIn).duration(1000).playOn(imgProChangeEmail);
        Picasso.with(getApplicationContext())
                .load(new SharedPrefDatabase(getApplicationContext()).RetriveUserImage())
                .placeholder(R.drawable.ic_placeholder_icon)
                .error(R.drawable.ic_placeholder_icon)
                .into(imgProfile);
        txtProName.setText(new SharedPrefDatabase(getApplicationContext()).RetriveUserName());
        txtProEmail.setText(new SharedPrefDatabase(getApplicationContext()).RetriveUserEmail());
        txtProMobile.setText(new SharedPrefDatabase(getApplicationContext()).RetriveUserMobile());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
