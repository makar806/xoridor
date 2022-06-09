/*
 * Created on 2-mrt-2005.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.filter;

public interface Filter<T> {
    public boolean ok(T t);
}
