package com.lugakorrav.gogame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
    }

    public void onClick(View view) {
        Intent intent = new Intent(NewGameActivity.this, GameActivity.class);

        EditText editText = (EditText) findViewById(R.id.editText_field_size);
        String str = editText.getText().toString();
        int count = 9;
        if (!str.isEmpty()) {
            count = (int)Float.parseFloat(editText.getText().toString());
        }
        if (count > 19) {
            Toast.makeText(getApplicationContext(),
                    R.string.toast_too_big_field_size, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            intent.putExtra("count", count);
            startActivity(intent);
            finish();
        }
    }
}
