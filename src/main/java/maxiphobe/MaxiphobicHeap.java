package maxiphobe;

import clojure.lang.*;
import java.util.*;


public class MaxiphobicHeap
        extends ASeq
        implements IMaxiphobicHeap, Counted, IPersistentStack {

    public static final EmptyMaxiphobicHeap EMPTY =
            new EmptyMaxiphobicHeap(PersistentArrayMap.EMPTY);

    final Comparator comp;
    final Heap heap;

    public MaxiphobicHeap(Heap heap, IPersistentMap meta) {
        super(meta);
        this.comp = RT.DEFAULT_COMPARATOR;
        this.heap = heap;
    }

    public MaxiphobicHeap(Comparator comp, Heap heap, IPersistentMap meta) {
        super(meta);
        this.comp = comp;
        this.heap = heap;
    }

    public static class EmptyMaxiphobicHeap
            extends Obj
            implements IMaxiphobicHeap, IPersistentList, List, ISeq, Counted, IHashEq {

        final Comparator comp;

        EmptyMaxiphobicHeap(IPersistentMap meta) {
            super(meta);
            this.comp = RT.DEFAULT_COMPARATOR;
        }

        EmptyMaxiphobicHeap(Comparator comp, IPersistentMap meta) {
            super(meta);
            this.comp = comp;
        }

        public EmptyMaxiphobicHeap(Comparator comp) {
            this.comp = comp;
        }

        static final int _hasheq = Util.hasheq(PersistentList.EMPTY);

        public Comparator comparator() {
            return comp;
        }

        public int count() {
            return 0;
        }

        public int hashCode() {
            return 1;
        }

        public int hasheq() {
            return _hasheq;
        }

        public boolean equals(Object o) {
            return (o instanceof Sequential || o instanceof List)
                    && RT.seq(o) == null;
        }

        public boolean equiv(Object o){
            return equals(o);
        }

        public MaxiphobicHeap cons(Object x) {
            return new MaxiphobicHeap(comp, new Heap(x), meta());
        }

        public Object first() {
            return null;
        }

        public ISeq more() { return this; }

        public ISeq next() {
            return null;
        }

        public ISeq seq() {
            return null;
        }

        public IPersistentCollection empty() {
            return this;
        }

        public Object peek() {
            return null;
        }

        public EmptyMaxiphobicHeap pop() {
            throw new IllegalStateException("Can't pop empty priority queue");
        }

        public EmptyMaxiphobicHeap withMeta(IPersistentMap meta) {
            return new EmptyMaxiphobicHeap(comp, meta);
        }

        public Iterator iterator() {
            return new Iterator() {

                public boolean hasNext() {
                    return false;
                }

                public Object next() {
                    throw new NoSuchElementException();
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        public Object get(int n) {
            return RT.nth(this, n);
        }

        public boolean isEmpty() {
            return true;
        }

        public int size() {
            return 0;
        }

        public boolean contains(Object x) {
            return false;
        }

        public boolean containsAll(Collection collection) {
            return collection.isEmpty();
        }

        public int indexOf(Object x) {
            return -1;
        }

        public int lastIndexOf(Object x) {
            return -1;
        }

        public Object set(int n, Object x) {
            throw new UnsupportedOperationException();
        }

        public boolean add(Object x) {
            throw new UnsupportedOperationException();
        }

        public void add(int n, Object x) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection collection) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(int index, Collection collection) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object x) {
            throw new UnsupportedOperationException();
        }

        public Object remove(int n) {
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(Collection collection) {
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(Collection collection) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }

        List reify() {
            return Collections.unmodifiableList(new ArrayList(this));
        }

        public ListIterator listIterator() {
            return reify().listIterator();
        }

        public ListIterator listIterator(int n) {
            return reify().listIterator(n);
        }

        public Object[] toArray() {
            return reify().toArray();
        }

        public Object[] toArray(Object[] array) {
            return reify().toArray(array);
        }

        public List subList(int from, int to) {
            return reify().subList(from, to);
        }
    }

    public static class Heap {

        final Object min;
        final Heap left;
        final Heap right;
        final int size;

        public Heap(Object min) {
            this.min = min;
            this.left = null;
            this.right = null;
            this.size = 1;
        }

        public Heap(Object min, Heap left, Heap right) {
            this.min = min;
            this.left = left;
            this.right = right;
            this.size = 1 + size(left) + size(right);
        }
    }

    public Comparator comparator() {
        return comp;
    }

    static public IMaxiphobicHeap meld(IMaxiphobicHeap left, IMaxiphobicHeap right) {
        Comparator comp = left.comparator();

        if (comp != right.comparator())
            throw new IllegalStateException(
                    "Cannot meld priority queues that use different comparators"
            );

        if (left.isEmpty())
            return right;
        if (right.isEmpty())
            return left;

        Heap lheap = ((MaxiphobicHeap) left).heap;
        Heap rheap = ((MaxiphobicHeap) right).heap;

        return new MaxiphobicHeap(comp, meld(comp, lheap, rheap), null);
    }

    static Heap meld(Comparator comp, Heap left, Heap right) {
        if (left == null)
            return right;
        if (right == null)
            return left;

        if (comp.compare(left.min, right.min) <= 0)
            return meld3(comp, left.min, left.left, left.right, right);
        return meld3(comp, right.min, left, right.left, right.right);
    }

    static Heap meld3(Comparator comp, Object min, Heap a, Heap b, Heap c) {
        int sa = size(a);
        int sb = size(b);
        int sc = size(c);

        if (sa >= sb && sa >= sc)
            return new Heap(min, a, meld(comp, b, c));
        if (sb >= sa && sb >= sc)
            return new Heap(min, b, meld(comp, a, c));
        return new Heap(min, c, meld(comp, a, b));
    }

    Heap meld(Heap left, Heap right) {
        if (left == null)
            return right;
        if (right == null)
            return left;

        if (comp.compare(left.min, right.min) <= 0)
            return meld3(left.min, left.left, left.right, right);
        return meld3(right.min, left, right.left, right.right);
    }

    Heap meld3(Object min, Heap a, Heap b, Heap c) {
        int sa = size(a);
        int sb = size(b);
        int sc = size(c);

        if (sa >= sb && sa >= sc)
            return new Heap(min, a, meld(b, c));
        if (sb >= sa && sb >= sc)
            return new Heap(min, b, meld(a, c));
        return new Heap(min, c, meld(a, b));
    }

    static int size(Heap heap){
        if (heap == null)
            return 0;
        return heap.size;
    }

    public MaxiphobicHeap cons(Object x) {
        return new MaxiphobicHeap(comp, meld(heap, new Heap(x)), meta());
    }

    public Object first() {
        return peek();
    }

    public IMaxiphobicHeap next() {
        if (size(heap) <= 1)
            return null;
        return pop();
    }

    public Object peek() {
        return heap.min;
    }

    public IMaxiphobicHeap pop() {
        if (heap.size == 1)
            return new EmptyMaxiphobicHeap(comp, meta());
        return new MaxiphobicHeap(comp, meld(heap.left, heap.right), meta());
    }

    public MaxiphobicHeap withMeta(IPersistentMap meta) {
        return new MaxiphobicHeap(heap, meta);
    }

    public IPersistentCollection empty() {
        return new EmptyMaxiphobicHeap(comp, meta());
    }

    public int count() {
        return heap.size;
    }
}
