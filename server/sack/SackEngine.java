package server.sack;

import java.io.ByteArrayInputStream;

import util.SynchronizedQueue;

public class SackEngine implements Runnable{
    private Thread thread;
    private SynchronizedQueue<String> queue;
    private Sack sack;
    public SackEngine(){
        queue=new SynchronizedQueue<>();
        sack=new SimpleSack();
        thread=new Thread(this);
    }
    @Override
    public void run() {
        while(true){
            if(queue.size()>0){
                sack.add(queue.poll());
            }else{
                try {
                    Thread.sleep(300000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void save(String uri){
        queue.add(uri);
    }
    public ByteArrayInputStream get(String uri){
        return sack.get(uri);
    }
    public boolean contains(String uri){
        return sack.contains(uri);
    }
    public void start(){
        thread.start();
    }
}
