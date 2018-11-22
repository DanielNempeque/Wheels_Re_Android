package unisabana.edu.co.wheels_sabana;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private EditText correo;
    private EditText contrasena;
    private float sesion;
    private View x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.loginButton);
        login.setOnClickListener(new buttonClick());


    }

    public void login(View v) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    class buttonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            x = v;
            InvocarServicioIniciarSesion is =  new InvocarServicioIniciarSesion();
            is.execute();
        }
    }
    private class InvocarServicioIniciarSesion extends AsyncTask<Void,Integer,Void>{
        private int progreso;
        @Override
        protected void onPreExecute() {
            progreso = 0;
        }

        @Override
        protected void onPostExecute(Void result) {
            System.out.println("Session:  "+sesion);
            if(sesion != 0){
                login(x);
            }else {
                Snackbar.make(x, "No se ha encontrado el usuario", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            iniciarSesion();
            return null;
        }
    }
    private void iniciarSesion() {

        correo = findViewById(R.id.mail);
        contrasena = findViewById(R.id.password);
        String cor = correo.getText().toString().trim();
        String pass = contrasena.getText().toString().trim();
        String response = "";
        try {

            URL url =  new URL("https://wheels-software.appspot.com/_ah/api/proxy/v1/ingresarAplicacion/"+cor+"/"+pass);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(5000);
            con.setConnectTimeout(5000);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("user", cor);
            postDataParams.put("password", pass);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(postDataParams.toString());
            writer.close();

            int responseCode = con.getResponseCode();
            System.out.println("Codigo respuesta " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK)

            {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
                JSONObject respu = new JSONObject(response);
                sesion = Float.parseFloat(respu.getString("sesion"));

            } else{
                response = "";
            }
            System.out.println(response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
