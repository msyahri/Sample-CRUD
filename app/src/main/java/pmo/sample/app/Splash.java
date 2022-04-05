package pmo.sample.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread(){
            public void run (){
                try{
                    sleep(3000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    //startActivity(new Intent(Splash.this, Login_Utama.class));
                    startActivity(new Intent(Splash.this, main_menu.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}