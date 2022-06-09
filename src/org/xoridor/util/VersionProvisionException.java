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

public class VersionProvisionException extends Exception {
    public VersionProvisionException(IOException source) {
        super(source);
    }
}
