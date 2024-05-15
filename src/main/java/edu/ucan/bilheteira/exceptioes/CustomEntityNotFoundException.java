package edu.ucan.bilheteira.exceptioes;


public class CustomEntityNotFoundException extends RuntimeException{
    public CustomEntityNotFoundException(String message){
        super(message);
    }
}
