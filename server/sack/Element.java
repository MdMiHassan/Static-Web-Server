package server.sack;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import server.conf.DefaultConfig;

public class Element {
    private byte[] data;
    private int size;
   

    private Element(File file) throws IOException {
        wrap(file);
    }

    private void wrap(File file) throws IOException {
        if (file.length() <= DefaultConfig.MAX_CACHE_MEMORY) {
            data = new byte[(int) file.length()];
            InputStream fStream = new FileInputStream(file);
            while (fStream.read(data) != data.length) {
                fStream = new FileInputStream(file);
            }
            size=(int)file.length();
            fStream.close();
        } else {
            throw new UnsupportedOperationException();
        }
    }
    public int getSize() {
        return size;
    }
    public static Element of(File file) {
        try {
            return new Element(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] getByteArray() {
        return data;
    }
}