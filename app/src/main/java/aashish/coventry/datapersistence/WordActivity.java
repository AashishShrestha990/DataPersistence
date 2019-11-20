package aashish.coventry.datapersistence;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordActivity extends AppCompatActivity {
    // TextView tvMeaning;
    private ListView dictionary;
    private Map<String, String> Ddictionary;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        dictionary = findViewById(R.id.dictionary);
        btnAdd = findViewById(R.id.btnAdd);
        Ddictionary = new HashMap<>();
        readFromFile();

        ArrayAdapter adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,
                new ArrayList<String>(Ddictionary.keySet())
        );

        dictionary.setAdapter(adapter);

        dictionary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String word = parent.getItemAtPosition(position).toString();
                String meaning = Ddictionary.get(word);
                Intent intent = new Intent(WordActivity.this, MeaningActivity.class);
                intent.putExtra("meaning", meaning);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordActivity.this, AddWordActivity.class);
                startActivity(intent);
            }
        });

    }

    private void readFromFile() {
        try {
            FileInputStream fos = openFileInput("word.txt");
            InputStreamReader isr = new InputStreamReader(fos);
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("->");
                Ddictionary.put(parts[0],parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
