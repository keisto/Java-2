package xyz.y_not.keiser2_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListItemsActivity extends Activity {
    private static final String SAVE_U_ARRAY = "SAVE_U_ARRAY";
    private static final String SAVE_P_ARRAY = "SAVE_P_ARRAY";
    private static final String SAVE_E_ARRAY = "SAVE_E_ARRAY";
    public static final int REQUEST_CODE = 100;
    ListView          userList;
    ArrayList<String> users     = new ArrayList<String>();
    ArrayList<String> passwords = new ArrayList<String>();
    ArrayList<String> emails    = new ArrayList<String>();
    ArrayAdapter      adapter;

    private String userName;
    private String password;
    private String email;
    private String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_items);

        userList  = (ListView) findViewById(R.id.userList);

        // PROBLEM : Instance Not Saving!

        if(savedInstanceState != null) {
            users     = savedInstanceState.getStringArrayList(SAVE_U_ARRAY);
            passwords = savedInstanceState.getStringArrayList(SAVE_P_ARRAY);
            emails    = savedInstanceState.getStringArrayList(SAVE_E_ARRAY);
        }

        // PROBLEM : No Instance == No replace/updating List
        
//        Intent callingIntent = getIntent();
//        if(callingIntent.getStringExtra("myKey") != null)  {
//            userName = callingIntent.getStringExtra("eName");
//            password = callingIntent.getStringExtra("ePass");
//            email    = callingIntent.getStringExtra("eMail");
//            key      = callingIntent.getStringExtra("myKey");
//            int i = users.indexOf(key);
//            users.remove(i);
//            passwords.remove(i);
//            emails.remove(i);
//
//            users.add(userName);
//            passwords.add(password);
//            emails.add(email);
//            reload(userName, password, email);
//            System.out.println("Interger i == " + i );
//        }
    }

    public void newUser(View v){
        Intent intent = new Intent(ListItemsActivity.this, AddItemActivity.class);
            intent.putExtra("userName", userName);
            intent.putExtra("password", password);
            intent.putExtra("email"   , email);
            intent.putExtra("key"     , key);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            userName = data.getStringExtra("userName");
            password = data.getStringExtra("password");
            email = data.getStringExtra("email");
            key  = data.getStringExtra("key");
            reload(userName, password, email);
        }

    }
    public void reload(String _name, String _pass, String _mail){
        users.add(_name);
        passwords.add(_pass);
        emails.add(_mail);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                users);
        userList.setAdapter(adapter);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                {
                    Intent intent = new Intent(ListItemsActivity.this, ViewItemActivity.class);
                    intent.putExtra("userName", users.get(position));
                    intent.putExtra("password", passwords.get(position));
                    intent.putExtra("email", emails.get(position));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle _state) {
        super.onSaveInstanceState(_state);
        _state.putStringArrayList(SAVE_U_ARRAY, users);
        _state.putStringArrayList(SAVE_P_ARRAY, passwords);
        _state.putStringArrayList(SAVE_E_ARRAY, emails);
    }
}
