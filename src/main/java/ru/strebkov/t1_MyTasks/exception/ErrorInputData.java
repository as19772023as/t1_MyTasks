package ru.strebkov.t1_MyTasks.exception;

public class ErrorInputData extends  RuntimeException {
    public ErrorInputData(String msg) {
        super(msg);
    }
}
