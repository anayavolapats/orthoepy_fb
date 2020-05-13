package com.example.orthoepy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "AddToDatabase";
    private TextInputLayout e_mail;
    private TextInputLayout name;
    private TextInputLayout password;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef =mFirebaseDatabase.getReference();
        e_mail = findViewById(R.id.emailTextView);
        name = findViewById(R.id.nameTextView);
        password = findViewById(R.id.passwordTextView);
        Button sign_in = findViewById(R.id.registerButton);
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((check_email() == true) && (check_name() == true) && (check_password() == true)) {
                    Intent i = new Intent(SignInActivity.this, HomeActivity.class);
                    //i.putExtra("name", name.getText().toString()); //это чтобы передать инфу в другую активити
                    //i.putExtra("mail", e_mail.getText().toString());
                    startActivity(i);
                    //User man = new User(e_mail.getText().toString(), name.getText().toString(), password.getText().toString());
                    myRef.child("avocado").setValue("eater 3");
                }
            }
        });
    }

    public boolean check_name(){
        boolean name_result = true;
        if(name.getEditText().getText().toString().isEmpty()){
            name.setError("Здесь ничего нет");
            name_result = false;
        }
        if(!name.getEditText().getText().toString().isEmpty()){
            name.setError(null);
        }
        return name_result;
    }

    public boolean check_email() {
        boolean e_mail_result = true;
        if (e_mail.getEditText().getText().toString().isEmpty()) {
            e_mail.setError("Здесь ничего нет");
            e_mail_result = false;
        } else {
            if (e_mail.getEditText().getText().toString().contains("@") && e_mail.getEditText().getText().toString().contains(".")) {
            }else{
                e_mail.setError("Это не e-mail");
                e_mail_result = false;
            }
        } if(!e_mail.getEditText().getText().toString().isEmpty()){
            e_mail.setError(null);
        }
        return e_mail_result;
    }

    public boolean check_password(){
        boolean password_result = true;
        if(password.getEditText().getText().toString().isEmpty()){
            password.setError("Здесь ничего нет");
            password_result = false;
        }
        if(password.getEditText().getText().toString().length() < 6){
            password.setError("Пароль должен быть длиннее 6 символов");
            password_result = false;
        }
        if(!password.getEditText().getText().toString().isEmpty()){
            password.setError(null);
        }
        return password_result;
    }
}
