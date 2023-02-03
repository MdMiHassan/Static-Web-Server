package server.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import util.Utility;

//Request-Line = Method SP Request-URI SP HTTP-Version CRLF
public class HTTPRequestLine {
    private short requestMethod;
    private short requestProtocol;
    private Map<String, String> uriParameters;
    

    private String resourcePath;
    private String anchor;

    public HTTPRequestLine() {
        this.uriParameters = new HashMap<>();
    }

    public void setRequestMethod(short requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setRequestProtocol(short requestProtocol) {
        this.requestProtocol = requestProtocol;
    }

    public void setUriParameter(String key,String value) {
        this.uriParameters.put(key, value);
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public short getRequestMethod() {
        return requestMethod;
    }

    public short getRequestProtocol() {
        return requestProtocol;
    }
    public Map<String, String> getUriParameters() {
        return uriParameters;
    }
    public Set<String> getUriParametersKeySet() {
        return uriParameters.keySet();
    }
    public String getUriParametersKeySet(String key) {
        return uriParameters.get(key);
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public String getAnchor() {
        return anchor;
    }
    public void parseResource(String s){
        int markIndex=s.indexOf('?');
        if(markIndex<0){
            setResourcePath(Utility.getNativePath(s));
        }else{
            setResourcePath(Utility.getNativePath(s.substring(0, markIndex)));
            int htIndex=s.indexOf('#');
            StringTokenizer st;
            if(htIndex<0){
                st=new StringTokenizer(s.substring(markIndex+1,s.length()));
            }else{
                st=new StringTokenizer(s.substring(markIndex+1,htIndex));
            }
            while(st.hasMoreTokens()){
                String kv=st.nextToken();
                int i=kv.indexOf('=');
                if(i>0){
                    setUriParameter(kv.substring(0, i), kv.substring(i+1,kv.length()));
                }
            }
        }
    }
}
