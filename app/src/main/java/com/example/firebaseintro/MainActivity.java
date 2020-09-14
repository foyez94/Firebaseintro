package com.example.firebaseintro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.xml.sax.Attributes;

import java.net.HttpCookie;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MainActivity";

    private EditText email;
    private  EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.emailID);
        password= (EditText) findViewById(R.id.passwdID) ;
        login=(Button) findViewById(R.id.buttonID);

        mAuth = FirebaseAuth.getInstance();

        database =FirebaseDatabase.getInstance();
        databaseReference =database.getReference("message");

        databaseReference.setValue("foyez");


        databaseReference.addValueEventListener(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String value =dataSnapshot.getValue(String.class);
                Toast.makeText(MainActivity.this, value,Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //user in signed in
                    Log.d(TAG, "user sign-in");
                }else {
                    //user is signed out
                    Log.d(TAG, "user sign-out");
                }
            }
        };

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}