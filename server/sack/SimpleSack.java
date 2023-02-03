package server.sack;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import server.conf.DefaultConfig;

public class SimpleSack implements Sack {
    private Map<String, Element> memory;
    Queue<String> lru;
    private long size;

    public SimpleSack() {
        this.memory = new HashMap<>();
        this.size = 0;
        this.lru = new LinkedList<>();
    }

    @Override
    public synchronized ByteArrayInputStream get(String uri) {
        return new ByteArrayInputStream(memory.get(uri).getByteArray());
    }

    @Override
    public synchronized boolean add(String uri) {
        File file = new File(uri);
        if (file.length() + size <= DefaultConfig.MAX_CACHE_MEMORY) {
            return save(file);
        } else if (file.length() <= DefaultConfig.MAX_CACHE_ELEMENT_SIZE) {
            String top;
            while (file.length() + size > DefaultConfig.MAX_CACHE_MEMORY && (top = lru.poll()) != null) {
                size -= memory.remove(top).getSize();
            }
            return save(file);
        }
        return false;
    }

    private boolean save(File file) {
        Element element = Element.of(file);
        if ((element = Element.of(file)) != null) {
            try {
                if (memory.put(file.getCanonicalPath(), element) == null) {
                    size += file.length();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean contains(String uri) {
        return memory.containsKey(uri);
    }
}
