package saim.com.now;

import android.animation.Animator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Login extends AppCompatActivity {

    LinearLayout layoutLogin, layoutRegister;
    TextView txtLoginAgain, txtRegisterAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {

        layoutLogin = (LinearLayout) findViewById(R.id.layoutLogin);
        layoutRegister = (LinearLayout) findViewById(R.id.layoutRegister);

        txtLoginAgain = (TextView) findViewById(R.id.txtLoginAgain);
        txtRegisterAgain = (TextView) findViewById(R.id.txtRegisterAgain);

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
    }
}
