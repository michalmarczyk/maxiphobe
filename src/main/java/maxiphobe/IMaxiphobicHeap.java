package maxiphobe;

import clojure.lang.*;
import java.util.*;


public interface IMaxiphobicHeap extends IPersistentStack, ISeq {

    Comparator comparator();

    boolean isEmpty();
}
