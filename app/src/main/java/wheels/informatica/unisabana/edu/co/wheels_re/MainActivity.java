package wheels.informatica.unisabana.edu.co.wheels_re;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private EditText correo;
    private EditText contrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login = findViewById(R.id.loginButton);
        login.setOnClickListener(new buttonClick());


    }

    public void login(View v) {
        correo = findViewById(R.id.mail);
        contrasena = findViewById(R.id.password);
        if (correo.getText().toString().trim().equals("prueba")) {
            if (contrasena.getText().toString().trim().equals("1234")) {
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                finish();
            } else {
                Snackbar.make(v, "Usuario o contraseña incorrecta", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } else {
            Snackbar.make(v, "Usuario o contraseña incorrecta", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    class buttonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            login(v);
        }
    }
}
