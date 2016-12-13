package structures;

import javafx.scene.shape.Circle;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Created by chris on 2016-12-10.
 */
public class SortedCircleList {
    private LinkedList<Circle> delegate;

    public SortedCircleList() {
        this.delegate = new LinkedList<>();
    }

    public Circle getFirst() {
        return delegate.getFirst();
    }

    public Circle getLast() {
        return delegate.getLast();
    }

    public Circle removeFirst() {
        return delegate.removeFirst();
    }

    public Circle removeLast() {
        return delegate.removeLast();
    }


    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    public int size() {
        return delegate.size();
    }

    public SortedCircleList add(Circle circle) {
        this.delegate.add(circle);
        this.delegate.sort((o1, o2) -> (o1.getCenterX() <= o2.getCenterX()) ? -1 : 2);
        return this;
    }

    public SortedCircleList addY(Circle circle) {
        this.delegate.add(circle);
        this.delegate.sort((o1, o2) -> (o1.getCenterY() <= o2.getCenterY()) ? -1 : 2);
        return this;
    }

    public boolean remove(Object o) {
        return delegate.remove(o);
    }

    public boolean addAll(Collection<? extends Circle> c) {
        return delegate.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends Circle> c) {
        return delegate.addAll(index, c);
    }

    public void clear() {
        delegate.clear();
    }

    public Circle get(int index) {
        return delegate.get(index);
    }

    public Circle remove(int index) {
        return delegate.remove(index);
    }

    public int indexOf(Object o) {
        return delegate.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return delegate.lastIndexOf(o);
    }

    public Circle peek() {
        return delegate.peek();
    }

    public Circle element() {
        return delegate.element();
    }

    public Circle poll() {
        return delegate.poll();
    }

    public Circle remove() {
        return delegate.remove();
    }

    public boolean offer(Circle circle) {
        return delegate.offer(circle);
    }

    public boolean offerFirst(Circle circle) {
        return delegate.offerFirst(circle);
    }

    public boolean offerLast(Circle circle) {
        return delegate.offerLast(circle);
    }

    public Circle peekFirst() {
        return delegate.peekFirst();
    }

    public Circle peekLast() {
        return delegate.peekLast();
    }

    public Circle pollFirst() {
        return delegate.pollFirst();
    }

    public Circle pollLast() {
        return delegate.pollLast();
    }

    public void push(Circle circle) {
        add(circle);
    }

    public Circle pop() {
        return delegate.pop();
    }

    public boolean removeFirstOccurrence(Object o) {
        return delegate.removeFirstOccurrence(o);
    }

    public boolean removeLastOccurrence(Object o) {
        return delegate.removeLastOccurrence(o);
    }

    public ListIterator<Circle> listIterator(int index) {
        return delegate.listIterator(index);
    }

    public Iterator<Circle> descendingIterator() {
        return delegate.descendingIterator();
    }

    public Object[] toArray() {
        return delegate.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return delegate.toArray(a);
    }

    public Spliterator<Circle> spliterator() {
        return delegate.spliterator();
    }

    public Iterator<Circle> iterator() {
        return delegate.iterator();
    }

    public ListIterator<Circle> listIterator() {
        return delegate.listIterator();
    }

    public List<Circle> subList(int fromIndex, int toIndex) {
        return delegate.subList(fromIndex, toIndex);
    }

    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    public boolean containsAll(Collection<?> c) {
        return delegate.containsAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return delegate.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return delegate.retainAll(c);
    }

    public boolean removeIf(Predicate<? super Circle> filter) {
        return delegate.removeIf(filter);
    }

    public Stream<Circle> stream() {
        return delegate.stream();
    }

    public Stream<Circle> parallelStream() {
        return delegate.parallelStream();
    }

    public void forEach(Consumer<? super Circle> action) {
        delegate.forEach(action);
    }

    public void replaceAll(UnaryOperator<Circle> operator) {
        delegate.replaceAll(operator);
    }

    public void sort(Comparator<? super Circle> c) {
        delegate.sort(c);
    }

    public double diameterShouldbe(int i) {
        double counter = 0.0;
        for (int j = 0; j < i + 1; j++) {
            counter += this.delegate.get(j).getRadius();
        }
        return counter;
    }

    public LinkedList<Circle> getDelegate() {
        return delegate;
    }

    public LinkedList<Double> getPositions() {
        LinkedList<Double> list = new LinkedList<>();
        for (int i = 0; i < delegate.size(); i++) {
            list.addLast(delegate.get(i).getCenterX());
        }
        return list;
    }
}
