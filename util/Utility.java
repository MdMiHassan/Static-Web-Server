package util;

import java.io.File;
import java.nio.file.FileSystems;

public class Utility {
    public static String generateETagValue(File file){
        return String.valueOf(file.lastModified());
    }
    public static String getFileExtension(String fileName){
        int n=fileName.length();
        char[] ext=new char[n];
        for(int i=n-1;i>=0;i--){
          char c=fileName.charAt(i);
          if(c=='.'){
            return new String(ext,i+1,n-i-1);
          }
          ext[i]=c;
        }
        return null;
    }
    public static String getNativePath(String path){
      if(path.length()>0){
        return path.replace("/",FileSystems.getDefault().getSeparator());              
      }
      return path;
    }
}
