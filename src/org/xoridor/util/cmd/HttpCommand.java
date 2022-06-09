/*
 * Created on 12-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.cmd;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xoridor.util.xml.ValueNotFoundException;
import org.xoridor.util.xml.XmlProperties;

public abstract class HttpCommand<E extends Exception> implements Command<E> {
    public void execute() throws E {
        try {
            xp = new XmlProperties(getHttpResult(getUrl(), getParameters().toString()));
            if (!xp.getName().equals("ok"))
                throw createNokException(xp.getValue("id"), xp.getValue("message"));
        }
        catch (ConnectException exc) {
            throw createException(exc);
        }
        catch (UnknownHostException exc) {
            throw createException(exc);
        }
        catch (MalformedURLException exc) {
            throw createException(exc);
        }
        catch (IOException exc) {
            throw createException(exc);
        }
        catch (SAXException exc) {
            throw createException(exc);
        }
        catch (ParserConfigurationException exc) {
            throw createException(exc);
        }
        catch (ValueNotFoundException exc) {
            throw createException(exc);
        }
    }
    
    protected abstract E createException(Exception exc);
    
    protected abstract E createNokException(String id, String message);

    protected abstract String getParameters();

    protected abstract String getUrl();

    protected InputStream getHttpResult(String url, String parameters) throws MalformedURLException, IOException {
        URLConnection con = new URL(url).openConnection();
        con.setDoInput(true);
        if (parameters.length() > 0) {
            con.setDoOutput(true);
            DataOutputStream dos = new DataOutputStream(con.getOutputStream());
            dos.writeBytes(parameters.toString());
            dos.close();
        }
        InputStream is = con.getInputStream();
        return is;
    }

    public void addParameter(StringBuffer parameters, String key, String value) {
        try {
            if (parameters.length()>0)
                parameters.append("&");
            parameters.append(URLEncoder.encode(key,"UTF-8"));
            parameters.append("=");
            parameters.append(URLEncoder.encode(value,"UTF-8"));
        } 
        catch (UnsupportedEncodingException ue) {
        }
    }

    protected XmlProperties getXmlProperties() {
        return xp;
    }

    private XmlProperties xp;
}
