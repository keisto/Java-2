package xyz.y_not.keiser2_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewItemActivity extends Activity {

    private String userName;
    private String password;
    private String email;
    TextView user;
    TextView pass;
    TextView mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_item);
        user = (TextView) findViewById(R.id.userName);
        pass = (TextView) findViewById(R.id.password);
        mail = (TextView) findViewById(R.id.email);

        Intent callingIntent = getIntent();

        userName = callingIntent.getStringExtra("userName");
        password = callingIntent.getStringExtra("password");
        email    = callingIntent.getStringExtra("email");

        user.setText("Username: " + userName);
        pass.setText("Password: " + password);
        mail.setText("Email: "    + email);
    }
    public void onEdit(View v){
        Intent intent = new Intent(ViewItemActivity.this, AddItemActivity.class);
            intent.putExtra("eName", userName);
            intent.putExtra("ePass", password);
            intent.putExtra("eMail", email);
            intent.putExtra("myKey", userName);
        startActivity(intent);
        finish();
    }
    public void onShare(View v){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, user + ", " + pass + ", " + mail);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Share with..."));
    }
}
