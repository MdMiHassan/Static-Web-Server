package server.sack;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import util.SynchronizedQueue;

public class SackEngine implements Runnable{
    private Thread thread;
    private BlockingQueue<String> queue;
    private Sack sack;
    public SackEngine(){
        queue=new LinkedBlockingQueue<>();
        sack=new SimpleSack();
        thread=new Thread(this);
    }
    @Override
    public void run() {
        while(true){
            try {
                sack.add(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }  
        }
    }
    public void save(String uri){
        queue.add(uri);
    }
    public byte[] get(String uri){
        return sack.get(uri);
    }
    public boolean contains(String uri){
        return sack.contains(uri);
    }
    public void start(){
        thread.start();
    }
}
