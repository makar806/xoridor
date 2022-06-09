/*
 * Created on 30-dec-2005.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.i18n;

import java.util.List;

public interface I18nCatalogProvider {
   /** Returns an iterator over the language names.
     */
   public List<String> getLanguages ();
   
   /** Returns the resource for a given language.
     */
   public String getResource(String language);

   /** Returns a decent language suitable as default language.
     */
   public String getDefaultLanguage();
}
