package com.example.todolist.common.utils;

public class BaseUtils {

    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setCurrentAccount(String account) {threadLocal.set(account);}

    public static String getCurrentAccount() {
        return threadLocal.get();
    }

    public static void removeCurrentAccount() {
        threadLocal.remove();
    }

}
