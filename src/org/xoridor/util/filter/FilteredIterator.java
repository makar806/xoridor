/*
 * Created on 2-mrt-2005.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.filter;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FilteredIterator<T> implements Iterator<T>, Iterable<T> {
    public FilteredIterator (Iterable<T> iterable, Filter<T> filter) {
        this(iterable.iterator(), filter);
    }
    
    public FilteredIterator (Iterator<T> iterator, Filter<T> filter) {
        this.filter = filter;
        this.iterator = iterator;
    }
    
    public boolean hasNext() {
        if (next == null)
            try {
                setNext();
            }
            catch (NoSuchElementException e) {
                return false;
            }
        return true;
    }
    
    private void setNext() throws NoSuchElementException {
        do {
            next = iterator.next();
        }
        while (!(filter.ok(next)));
    }

    public T next() {
        if (next == null)
            try {
                setNext();
            }
            catch (NoSuchElementException e) {
                throw new NoSuchElementException();
            }
        T result = next;
        next = null;
        return result;
    }

    public void remove() {
        throw new UnsupportedOperationException();        
    }

    public Iterator<T> iterator() {
        return this;
    }
    
    private final Iterator<T> iterator;
    private final Filter<T> filter;
    private T next;
}