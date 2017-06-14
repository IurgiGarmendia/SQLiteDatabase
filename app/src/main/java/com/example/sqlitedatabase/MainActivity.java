package com.example.sqlitedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText mEditTextWord;
    EditText mEditTextDefinition;
    DictionaryDatabase mDB;
    ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB= new DictionaryDatabase(this);
        mEditTextWord= (EditText) findViewById(R.id.et_word);
        mEditTextDefinition= (EditText) findViewById(R.id.et_definition);
        mListView= (ListView) findViewById(R.id.listView);

        Button buttonUpdate = (Button) findViewById(R.id.button);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecord();
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, mDB.getDefinition(id), Toast.LENGTH_SHORT).show();


            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Record delted= "+mDB.deleteRecor(id), Toast.LENGTH_SHORT).show();
                updateWorlList();
                return true;
            }
        });


        updateWorlList();
    }

    private void saveRecord(){
        String word=mEditTextWord.getText().toString();
        String definition=mEditTextDefinition.getText().toString();
        mDB.saveRecord(word,definition);
        mEditTextWord.setText("");
        mEditTextDefinition.setText("");

        updateWorlList();
    }

    private void updateWorlList() {
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                mDB.getWordList(),new String[]{"word"}, new int[]{android.R.id.text1}, 0);
        mListView.setAdapter(simpleCursorAdapter);
    }


}
