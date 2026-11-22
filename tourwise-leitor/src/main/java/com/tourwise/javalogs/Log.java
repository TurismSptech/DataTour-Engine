
package com.tourwise.javalogs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.tourwise.database.InsertData;

public class Log {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    
    public static void log(String log, String cor) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateBanco = LocalDateTime.now();  
        String dateString = dateBanco.format(formatter);  
        
        //insere no banco
        InsertData insert = new InsertData();
        insert.insertJavaLog(log, dateBanco);

        //define a cor 
        String c = switch (cor == null ? "" : cor.toLowerCase()) {
            case "red" -> ANSI_RED;
            case "green" -> ANSI_GREEN;
            case "yellow" -> ANSI_YELLOW;
            default -> ANSI_BLUE;
        };        

        //imprime no console
        System.out.println(dateString + c + " - [INFO]" +
                "[SYSTEM]" + ANSI_RESET +
                " - " + log + "\n");
    }
}
