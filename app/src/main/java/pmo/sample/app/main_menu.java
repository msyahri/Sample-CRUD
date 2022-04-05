package pmo.sample.app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class main_menu extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_about)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //Instance Firebase Auth
        auth = FirebaseAuth.getInstance();


        //Menambahkan Listener untuk mengecek apakah user telah logout / keluar
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //Jika iya atau null, maka akan berpindah pada halaman login
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null ){
                    //Toast.makeText(main_menu.this, "Logout Success", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(main_menu.this , Login_Utama.class);
                    //startActivity(intent);
                    //finish();
                }
            }
        };


    }
    //menampilkan menu bar option atas
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout:

                AlertDialog.Builder alertt = new AlertDialog.Builder(this);
                alertt.setTitle("Anda yakin ingin keluar?");
                alertt.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(1);
                    }
                })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });

                alertt.create();
                alertt.show();

               // Toast.makeText(this, "Log Out Telah Dilakukan", Toast.LENGTH_SHORT).show();
               // auth.signOut();
                break;
        }

            return true;
    }
    @Override
    protected void onStart(){
        super.onStart();
        auth.addAuthStateListener(authListener);
    }
    @Override
    protected void onStop(){
        super.onStop();
        if (authListener != null){
            auth.removeAuthStateListener(authListener);
        }

    }



}