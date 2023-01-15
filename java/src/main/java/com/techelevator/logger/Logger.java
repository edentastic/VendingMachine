package com.techelevator.logger;

import java.io.*;

public class Logger implements Closeable {
    private File logFile;
    private PrintWriter writer;

    public Logger(String pathName) {
        this.logFile = new File(pathName);
        if(this.logFile.exists()) {
            try {
                this.writer = new PrintWriter(new FileWriter(this.logFile, true));
            } catch (IOException e) {
                System.out.println("file not found");
                ;
            }
        }else{
            try{
                this.writer = new PrintWriter(this.logFile);
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
            }
        }
    }

    public void write(String message){
        this.writer.println(message);
        this.writer.flush();
    }



    @Override
    public void close() throws IOException {
        this.writer.close();
    }
}
