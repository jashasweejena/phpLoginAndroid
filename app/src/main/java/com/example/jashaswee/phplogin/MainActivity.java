package com.example.jashaswee.phplogin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editEmail, editPassword, editName;
    Button signIn, register;

    int i = 0;

    JSONParser jsonParser;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editName = findViewById(R.id.editName);

        signIn = findViewById(R.id.btnSignIn);
        register = findViewById(R.id.btnRegister);

        jsonParser = new JSONParser();

        url = "http://192.168.59.100/androidlogin/index.php";

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new tryLogin().execute(editName.getText().toString(), editPassword.getText().toString(), "");
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (i == 0) {

                    i = 1;
                    register.setText("CREATE ACCOUNT");
                    editEmail.setVisibility(View.VISIBLE);
                    signIn.setVisibility(View.GONE);

                } else {

                    register.setText("REGISTER");
                    editEmail.setVisibility(View.GONE);
                    signIn.setVisibility(View.VISIBLE);

                    new tryLogin().execute(editName.getText().toString(), editPassword.getText().toString(), editEmail.getText().toString());

                    i = 0;

                }
            }
        });

    }

    class tryLogin extends AsyncTask<String, String, JSONObject> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected JSONObject doInBackground(String... strings) {
            String email = strings[2];
            String password = strings[1];
            String name = strings[0];

            JSONObject json = null;

            try {

                ArrayList params = new ArrayList();
                params.add(new BasicNameValuePair("username", name));
                params.add(new BasicNameValuePair("password", password));
                if (email.length() > 0) {
                    params.add(new BasicNameValuePair("email", email));
                }

                json = jsonParser.makeHttpRequest(url, "POST", params);



            } catch (JSONException e) {
                e.printStackTrace();
            }

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            try {

                if (result != null) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            catch(Exception e) {
                e.printStackTrace();
            }

        }
    }
}
