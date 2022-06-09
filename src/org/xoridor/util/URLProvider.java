/*
 * Created on 5-feb-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util;

import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

public class URLProvider {
    public URL getURL(String file) throws MalformedURLException {
        if (ClassLoader.getSystemResource(file) != null)
            return ClassLoader.getSystemResource(file);
        return new File(file).toURL();
    }

    public Image getIcon(String file) throws MalformedURLException {
        return new ImageIcon(getURL(file)).getImage();
    }
}