package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.helloworld.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NumberViewModel numberModel;
    private ListViewModel listModel;
    private ArrayAdapter<String> arrayAdapter;
    private static final int REQ_CODE = 123;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        numberModel = new ViewModelProvider(this).get(NumberViewModel.class);
        listModel = new ViewModelProvider(this).get(ListViewModel.class);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listModel.getNumbers().getValue());

        binding.listView.setAdapter(arrayAdapter);

        numberModel.getNumber().observe(this, number -> {
            binding.text.setText("" + number);
        });

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberModel.increase();
                listModel.addNumber("" + numberModel.getNumber().getValue());
                arrayAdapter.notifyDataSetChanged();
            }
        });

        binding.subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberModel.decrease();
                listModel.addNumber("" + numberModel.getNumber().getValue());
                arrayAdapter.notifyDataSetChanged();
            }
        });

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("number", listModel.getNumbers().getValue().get(i));
                pos = i;
                startActivityForResult(intent, REQ_CODE);
            }
        });
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQ_CODE) {
            String data = intent.getStringExtra("number");
            listModel.updateNumber(pos, data);
        }
    }
}