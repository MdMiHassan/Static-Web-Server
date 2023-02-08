package server;

import java.io.File;
import java.io.IOException;

import server.conf.DefaultConfig;
import server.sack.SackEngine;
import util.Logger;

public class Context {

     private String jreDir;
     private String userDir;
     private String publicDir;
     private String osArchitecture;
     private String osName;
     private String osVersion;
     private SackEngine sackEngine;
     private String pathSeparator;
     private int port;

     public Context(String[] args) {
          jreDir = System.getProperty("java.home");
          userDir = System.getProperty("user.dir");
          osArchitecture = System.getProperty("os.arch");
          osName = System.getProperty("os.name");
          osVersion = System.getProperty("os.version");
          pathSeparator=System.getProperty("file.separator");
          port=DefaultConfig.SERVER_PORT;
          sackEngine = new SackEngine();
          sackEngine.start();
          parseConfig(args);

     }

     private void parseConfig(String[] args) {
          if (args.length > 0) {
               parse(args, 0);
               return;
          }
          publicDir = userDir.substring(0, userDir.lastIndexOf('\\'));
          Logger.show("Public directory not defined!! server will serve file from: public dir= " + publicDir);
     }

     private void parse(String[] args, int i) {
          if (i < args.length) {
               String token = args[i];
               switch (token) {
                    case "--pdir":
                         if (parsePdir(args, i + 1)) {
                              parse(args, i + 2);
                         }
                         break;
                    case "--port":
                         if (parsePort(args, i + 1)) {
                              parse(args, i + 2);
                         }
                         break;
                    default:
                         StringBuilder wrongCmd = new StringBuilder();
                         for (; i < args.length; i++) {
                              wrongCmd.append(" " + args[i]);
                         }
                         Logger.show("Invalid token \"" + token + "\" " + wrongCmd.toString());
                         break;
               }
          }

     }

     private boolean parsePort(String[] args, int i) {
          if (i < args.length) {
               port = Integer.parseUnsignedInt(args[i]);
               if (port >= 0 && port < 65535) {
                    Logger.show("Server will started at port: " + port);
                    return true;
               } else {
                    Logger.show("Invalid port number!");
               }
          } else {
               Logger.show("Port number not defined");
          }
          Logger.show("Server will started at port: " + port);
          return false;
     }

     private boolean parsePdir(String[] args, int i) {
          if (i < args.length) {
               String path = args[i];
               if (isValidFilePath(path)) {
                    Logger.show("server will serve file from: public dir= " + path);
                    return true;
               } else {
                    Logger.show("Invalid path!");
               }
          } else {
               Logger.show("Path not defined");
          }
          Logger.show("server will serve file from: public dir= " + publicDir);
          return false;
     }

     public boolean isValidFilePath(String path) {
          File f = new File(path);
          try {
               f.getCanonicalPath();
               return true;
          } catch (IOException e) {
               return false;
          }
     }

     public String getJreDir() {
          return jreDir;
     }

     public String getUserDir() {
          return userDir;
     }

     public String getPublicDir() {
          return publicDir;
     }

     public String getOsArchitecture() {
          return osArchitecture;
     }

     public String getOsName() {
          return osName;
     }

     public String getOsVersion() {
          return osVersion;
     }

     public SackEngine getSackEngine() {
          return sackEngine;
     }

     public int getPort() {
          return port;
     }

    public String getPathSeparator() {
        return pathSeparator;
    }

}
