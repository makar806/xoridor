/*
 * Created on 30-dec-2005.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.i18n;

public abstract class I18nAdapter<T> implements I18nListener {
    public I18nAdapter(T t, String key) {
        this.t = t;
        this.key = key;
    }
    
    public void signalI18nChange(I18nManager i18n) {
        signalI18nChange(t, i18n.getValue(key));
    }

    public abstract void signalI18nChange(T t, String value);
    
    protected String getKey() {
        return key;
    }
    
    protected T getT() {
        return t;
    }

    private T t;
    private String key;
}