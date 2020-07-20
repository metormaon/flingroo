package il.ac.technion.cs.fling.internal.grammar.sententials;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * An unmodifiable finite sequence. Supports stack notations.
 *
 * @author Ori Roth
 */
public class Word<T> implements List<T> {
  private final List<T> inner;

  public Word() {
    inner = Collections.emptyList();
    verify();
  }
  public Word(final T t) {
    inner = Collections.singletonList(t);
    verify();
  }
  @SafeVarargs public Word(final T... origin) {
    inner = Arrays.asList(origin);
    verify();
  }
  public Word(final Collection<T> origin) {
    inner = new ArrayList<>(origin);
    verify();
  }
  public static <T> Word<T> empty() {
    return new Word<>();
  }
  @SuppressWarnings("unused") @Override public boolean add(final T t) {
    throw new UnsupportedOperationException();
  }
  @SuppressWarnings("unused") @Override public void add(final int index, final T element) {
    throw new UnsupportedOperationException();
  }
  @SuppressWarnings("unused") @Override public boolean addAll(final Collection<? extends T> c) {
    throw new UnsupportedOperationException();
  }
  @SuppressWarnings("unused") @Override public boolean addAll(final int index, final Collection<? extends T> c) {
    throw new UnsupportedOperationException();
  }
  @Override public void clear() {
    throw new UnsupportedOperationException();
  }
  @Override public boolean contains(final Object o) {
    return inner.contains(o);
  }
  @Override public boolean containsAll(final Collection<?> c) {
    return inner.containsAll(c);
  }
  @Override public T get(final int index) {
    return inner.get(index);
  }
  @Override public int indexOf(final Object o) {
    return inner.indexOf(o);
  }
  @Override public boolean isEmpty() {
    return inner.isEmpty();
  }
  @Override public Iterator<T> iterator() {
    return inner.iterator();
  }
  @Override public int lastIndexOf(final Object o) {
    return inner.lastIndexOf(o);
  }
  @Override public ListIterator<T> listIterator() {
    return inner.listIterator();
  }
  @Override public ListIterator<T> listIterator(final int index) {
    return inner.listIterator(index);
  }
  @SuppressWarnings("unused") @Override public boolean remove(final Object o) {
    throw new UnsupportedOperationException();
  }
  @SuppressWarnings("unused") @Override public T remove(final int index) {
    throw new UnsupportedOperationException();
  }
  @SuppressWarnings("unused") @Override public boolean removeAll(final Collection<?> c) {
    throw new UnsupportedOperationException();
  }
  @SuppressWarnings("unused") @Override public boolean retainAll(final Collection<?> c) {
    throw new UnsupportedOperationException();
  }
  @SuppressWarnings("unused") @Override public T set(final int index, final T element) {
    throw new UnsupportedOperationException();
  }
  @Override public int size() {
    return inner.size();
  }
  @Override public List<T> subList(final int fromIndex, final int toIndex) {
    return inner.subList(fromIndex, toIndex);
  }
  @Override public Object[] toArray() {
    return inner.toArray();
  }
  @Override public <U> U[] toArray(final U[] a) {
    return inner.toArray(a);
  }
  public T top() {
    assert !inner.isEmpty();
    return inner.get(inner.size() - 1);
  }
  public Word<T> push(final T t) {
    final List<T> $ = new ArrayList<>(inner.size() + 1);
    $.add(t);
    return new Word<>($);
  }
  public Word<T> push(final List<T> list) {
    final List<T> $ = new ArrayList<>(inner.size() + list.size());
    $.addAll(inner);
    $.addAll(list);
    return new Word<>($);
  }
  public Word<T> pop() {
    assert !inner.isEmpty();
    return new Word<>(inner.subList(0, inner.size() - 1));
  }
  @Override public int hashCode() {
    return inner.hashCode();
  }
  @Override public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Word))
      return false;
    return inner.equals(((Word<?>) o).inner);
  }
  @Override public String toString() {
    return inner.stream().map(Object::toString).collect(Collectors.joining(""));
  }
  private void verify() {
    inner.stream().forEach(Objects::requireNonNull);
  }
  public static <T> Word<T> of(Stream<T> s) {
    return new Word<>(s.collect(toList()));
  }
}
