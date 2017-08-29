package br.org.maanaim.checkpray.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.SparseLongArray;
import android.view.View;
import android.widget.ProgressBar;

import br.org.maanaim.checkpray.R;
import br.org.maanaim.checkpray.SharedPreference.MySaveSharedPreference;
import br.org.maanaim.checkpray.bean.Compromisso;
import br.org.maanaim.checkpray.fragments.SplashCheckPrayFragment;
import br.org.maanaim.checkpray.interfaces.Splash;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashCheckPray extends AppCompatActivity implements Splash {

    SplashCheckPrayFragment mFragment;

    int progressStatus = 0;

    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFragment = new SplashCheckPrayFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_splash, mFragment, "SPLASH")
                .commit();

    }


    @Override
    public void redirect() {
        final long[] id = {0};

        new Thread(new Runnable() {
            @Override
            public void run() {
                id[0] = MySaveSharedPreference.getUserId(SplashCheckPray.this);

                while(progressStatus < 200){
                    // Update the progress status
                    progressStatus +=1;

                    // Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(30);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mFragment.mProgressBar.setProgress(progressStatus);
                            // Show the progress on TextView
                        }
                    });
                }

                if (id[0] != 0) {
                    Intent i = new Intent(SplashCheckPray.this, CompromissosActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(SplashCheckPray.this, LoginActivity.class);
                    startActivity(i);
                }
                // Fecha esta activity
                finish();
            }
        }).start(); // Start the operation

    }


}
