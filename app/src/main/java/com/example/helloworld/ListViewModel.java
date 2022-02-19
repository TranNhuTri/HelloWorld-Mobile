package com.example.helloworld;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> numbers;
    public LiveData<ArrayList<String>> getNumbers() {
        if (numbers == null) {
            numbers = new MutableLiveData<ArrayList<String>>();
            numbers.setValue(new ArrayList<>());
        }
        return numbers;
    }

    public void addNumber(String number) {
        ArrayList<String> arr = numbers.getValue();
        arr.add(number);
        numbers.setValue(arr);
    }

    public void updateNumber(int index, String data) {
        ArrayList<String> arr = numbers.getValue();
        arr.set(index, data);
        numbers.setValue(arr);
    }
}
