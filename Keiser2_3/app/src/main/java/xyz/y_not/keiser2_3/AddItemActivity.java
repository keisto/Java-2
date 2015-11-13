package xyz.y_not.keiser2_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddItemActivity extends Activity {
    private static final String SAVE_USER = "AddItemActivity.SAVE_USER";
    private static final String SAVE_PASS = "AddItemActivity.SAVE_PASS";
    private static final String SAVE_MAIL = "AddItemActivity.SAVE_MAIL";

    private String userName;
    private String password;
    private String email;
    private String key = "";

    EditText user;
    EditText pass;
    EditText mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        user = (EditText) findViewById(R.id.userName);
        pass = (EditText) findViewById(R.id.password);
        mail = (EditText) findViewById(R.id.email);
        if(savedInstanceState != null) {
            userName = savedInstanceState.getString(SAVE_USER, "");
            password = savedInstanceState.getString(SAVE_PASS, "");
            email    = savedInstanceState.getString(SAVE_MAIL, "");

        } else {
            // Do your regular setup.
        }
        Intent callingIntent = getIntent();
        if(callingIntent.getStringExtra("eName") != null) {
            userName = callingIntent.getStringExtra("eName");
            password = callingIntent.getStringExtra("ePass");
            email    = callingIntent.getStringExtra("eMail");
            key      = callingIntent.getStringExtra("myKey");
            user.setText(userName);
            pass.setText(password);
            mail.setText(email);;
        }
    }

    public void onAdd(View v){
        if (key.equals("")){
            userName = user.getText().toString();
            password = pass.getText().toString();
            email    = mail.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("userName", userName);
            intent.putExtra("password", password);
            intent.putExtra("email"   , email);
            intent.putExtra("key"     , key);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Intent intent = new Intent(AddItemActivity.this, ListItemsActivity.class);
            intent.putExtra("eName", userName);
            intent.putExtra("ePass", password);
            intent.putExtra("eMail", email);
            intent.putExtra("myKey", key);
            startActivity(intent);
            finish();
        }
        
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(SAVE_USER, userName);
        savedInstanceState.putString(SAVE_PASS, password);
        savedInstanceState.putString(SAVE_MAIL, email);
        super.onSaveInstanceState(savedInstanceState);
    }

}
