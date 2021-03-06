package de.tautenhahn.easydata;

import java.io.IOException;
import java.io.Writer;


/**
 * Just the ability to resolve some part of the template using the given data.
 * 
 * @author TT
 */
public interface Resolver
{

  /**
   * Writes result of transformation into given Writer.
   * 
   * @param startTag just passed in to allow singleton instances when there is not much to do.
   * @param data all data available within the current context, namely original data plus some attributes
   *          defined in surrounding tags.
   * @param output
   * @throws IOException
   */
  void resolve(Token start, Object data, Writer output) throws IOException;

}
