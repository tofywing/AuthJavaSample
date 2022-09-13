package org.example;

import org.example.callback.InputCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InputService extends Thread{
    private static final InputService INSTANCE = new InputService();

    private final Scanner mScanner;

    //key: callback target, value: callback instance
    private final Map<String, InputCallback> mCallbackMap;

    public String awaitInput(String target) {
        String input = mScanner.nextLine();
        if (mCallbackMap.containsKey(target)) {
            mCallbackMap.get(target).input(input);
        }
        return input;
    }

    public static InputService get() {
        return INSTANCE;
    }

    private InputService() {
        mCallbackMap = new HashMap<>();
        mScanner = new Scanner(System.in);
        this.start();
    }

    public void bind(String target, InputCallback inputCallback) {
        mCallbackMap.put(target, inputCallback);
    }
}
