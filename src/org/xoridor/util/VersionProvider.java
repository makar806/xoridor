/*
 * Created on 4-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

public class VersionProvider {
    private static final String VERSION_PROPERTY = "version";
    private static final String VERSION_FILE = "build.version";

    public String getVersion() throws VersionProvisionException {
        Properties properties = new Properties();
        try {
            properties.load(new URLProvider().getURL(VERSION_FILE).openStream());
            return properties.getProperty(VERSION_PROPERTY);
        }
        catch (MalformedURLException exc) {
            throw new VersionProvisionException(exc);
        }
        catch (IOException exc) {
            throw new VersionProvisionException(exc);
        }
    }
}
