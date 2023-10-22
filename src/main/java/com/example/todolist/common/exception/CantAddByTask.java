package com.example.todolist.common.exception;

public class CantAddByTask extends BaseException{
    public CantAddByTask() {
    }

    public CantAddByTask(String msg) {
        super(msg);
    }
}
