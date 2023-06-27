package org.example;

import java.io.File;

public interface FileAdapter {

    File file = null;
    public static void connect(String fileName){
        File file = new File(fileName);
    }

}
