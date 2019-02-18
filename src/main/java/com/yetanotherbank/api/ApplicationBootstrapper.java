package com.yetanotherbank.api;


public class ApplicationBootstrapper {
    public static void main(String[] args) {
        DaggerWebApplication
                .create()
                .server()
                .start();
    }
}
