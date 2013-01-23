
package de.wwservices.util.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URI;

/**
 * Util Klasse für den Umgang mit Dateien und Streams.
 * 
 * @author joergw
 * 
 */
public class IOHelper
{
  private static final String DEFAULT_ENCODING = "UTF-8";
  

  // --------------------------------------------------------------------------
  
  /**
   * Liest eine Datei als String mit dem Default Encoding des IOHelpers ein.
   * 
   * @param file die einzulesende Datei.
   * @return
   * @throws IOException
   */
  public static String readFile(File file) throws IOException
  {
    return readFile(file.toURI(), DEFAULT_ENCODING);
  }
  
  /**
   * Liest eine Datei von einer URI ein und verwendet daf�r UTF-8 encoding.
   * 
   * @param fileName die URI der Datei.
   * @return
   * @throws IOException
   */
  public static String readFile(URI fileName, String encoding) throws IOException
  {
    Reader inputReader = null;
    try
    {
      FileInputStream in = new FileInputStream(new File(fileName));
      inputReader = new InputStreamReader(in, encoding);

      StringWriter stringWriter = new StringWriter();
      char buffer[] = new char[1024];
      int read = inputReader.read(buffer);
      while (read != -1)
      {
        stringWriter.write(buffer, 0, read);
        read = inputReader.read(buffer);
      }
      return stringWriter.toString();
    }
    finally
    {
      close(inputReader);
    }
  }

  /**
   * Schließt einen Stream oder File oder andere Objekte die {@link Closeable}
   * implementieren.
   * 
   * @param closeable das zu schlie�ende Objekt
   */
  public static final void close(Closeable closeable)
  {
    if (closeable != null)
    {
      try
      {
        closeable.close();
      }
      catch (IOException exception)
      {
        // :TODO Logging
      }
    }
  }
}
