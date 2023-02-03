package util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
public class SynchronizedQueue<T> implements Queue<T> {

    private Queue<T> queue;

    public SynchronizedQueue() {
        this.queue = new LinkedList<T>();
    }

    @Override
    public synchronized int size() {
        return queue.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public synchronized boolean contains(Object o) {
        return queue.contains(o);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return queue.iterator();
    }

    @Override
    public synchronized Object[] toArray() {
        return queue.toArray();
    }

    @Override
    public synchronized <T> T[] toArray(T[] a) {
        return queue.toArray(a);
    }

    @Override
    public synchronized boolean remove(Object o) {

        return queue.remove(o);
    }

    @Override
    public synchronized boolean containsAll(Collection<?> c) {

        return queue.containsAll(c);
    }

    @Override
    public synchronized boolean addAll(Collection<? extends T> c) {
        return queue.addAll(c);
    }

    @Override
    public synchronized boolean removeAll(Collection<?> c) {
        return queue.removeAll(c);
    }

    @Override
    public synchronized boolean retainAll(Collection<?> c) {
        return queue.retainAll(c);
    }

    @Override
    public synchronized void clear() {
        queue.clear();

    }

    @Override
    public synchronized boolean add(T e) {
        return add(e);
    }

    @Override
    public synchronized boolean offer(T e) {
        return queue.offer(e);
    }

    @Override
    public synchronized T remove() {
        return queue.remove();
    }

    @Override
    public synchronized T poll() {
        return queue.poll();
    }

    @Override
    public synchronized T element() {
        return queue.element();
    }

    @Override
    public synchronized T peek() {
        return queue.peek();
    }
}
