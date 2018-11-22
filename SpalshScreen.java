package unisabana.edu.co.wheels_sabana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SpalshScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
