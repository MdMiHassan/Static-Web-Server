package server.Service;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import server.http.HTTPService;

public class ServiceExecutor {
    private BlockingQueue<Service> serviceQueue;
    private LinkedList<Thread> worker;

    public ServiceExecutor(int capacity) {
        serviceQueue = new LinkedBlockingQueue<Service>();
        worker = new LinkedList<>();
        for (int i = 0; i < capacity; i++) {
            Thread w = new Thread(() -> {
                while (true) {
                    try {
                        serviceQueue.take().serve();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            w.start();
            worker.add(w);
        }
    }

    public void add(HTTPService httpService) {
        try {
            serviceQueue.put(httpService);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        Thread w;
        while ((w = worker.poll()) != null) {
            w.interrupt();
        }
    }
}
