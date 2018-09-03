package com.developer.iamsafi.realm_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.iamsafi.realm_android.Models.Users;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    EditText et_user;
    EditText et_pass;
    Button btn_login;
    Button btn_show;
    Button btn_update;
    Button btn_register;
    Button btn_delete;
    TextView tv_records;
    private String pass;
    private String user;
    private Realm realm;
    //TODO: Autoincrement problem is not solved
    static int id = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_user = findViewById(R.id.editText_user);
        et_pass = findViewById(R.id.editText_pass);
        btn_login = findViewById(R.id.button_login);
        btn_register = findViewById(R.id.button_register);
        btn_show = findViewById(R.id.button_show);
        btn_update = findViewById(R.id.button_update);
        btn_delete = findViewById(R.id.button_delete);
        tv_records = findViewById(R.id.text_records);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm.init(MainActivity.this);
                realm = Realm.getDefaultInstance();
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Users> users = realm.where(Users.class).equalTo("name", "Safi").findAll();
                        if(users!=null){
                        boolean ans=users.deleteAllFromRealm();
                        Log.i("SAFI","THe value is "+ans);
                    }
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {

                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {

                    }
                });
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm.init(MainActivity.this);
                realm = Realm.getDefaultInstance();
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Users users = realm.where(Users.class).equalTo("id", 44).findFirst();
                        if (users != null) {
                            users.setPassword("9998");
                            realm.copyToRealmOrUpdate(users);
                        } else {
                            //Toast.makeText(MainActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                            Log.i("SAFI", "No Record Found");
                        }

                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.i("SAFI", "OnSuccess");
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        error.printStackTrace();
                        Log.i("SAFI", "error" + error.toString());
                    }
                });
                realm.close();
            }
        });
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm.init(MainActivity.this);
                realm = Realm.getDefaultInstance();
                RealmResults<Users> realmResults = realm.where(Users.class).findAll();
                tv_records.setText("Records: \n");
                for (Users u : realmResults) {
                    tv_records.append("ID:" + u.getId() + "\tUser Name : " + u.getName().toString() + "\tPassword : " + u.getPassword().toString() + "\n");
                }
                realm.close();
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pass = et_pass.getText().toString();
                user = et_user.getText().toString();
                Realm.init(MainActivity.this);
                realm = Realm.getDefaultInstance();
                RealmResults<Users> realmResults = realm.where(Users.class).equalTo("name", user).and().equalTo("Password", pass).findAll();
                if (realmResults.size() > 0) {
                    Toast.makeText(MainActivity.this, "User login", Toast.LENGTH_LONG).show();
                    Log.i("SAFI", "User is successfully inserted");
                } else {
                    Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_LONG).show();
                    Log.i("SAFI", "User not found");
                }
                realm.close();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pass = et_pass.getText().toString();
                user = et_user.getText().toString();

                Realm.init(MainActivity.this);
                realm = Realm.getDefaultInstance();
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Users u = new Users();
                        u.setName(user);
                        u.setPassword(pass);
                        Number id = (realm.where(Users.class).max("id"));
                        u.setId(id.intValue() + 1);
                        realm.copyToRealm(u);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.i("SAFI", "Successfully Inserted");
                        id++;
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        error.printStackTrace();
                        Log.i("SAFI", "Failed to insert because" + error.toString());
                    }
                });
                realm.close();
            }
        });
    }
}
