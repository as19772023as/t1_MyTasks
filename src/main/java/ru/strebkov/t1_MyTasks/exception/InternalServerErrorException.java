package ru.strebkov.t1_MyTasks.exception;

public class InternalServerErrorException extends  RuntimeException {
    public InternalServerErrorException(String msg) {
        super(msg);
    }
}
