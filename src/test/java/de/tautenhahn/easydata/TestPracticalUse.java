package de.tautenhahn.easydata;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;


/**
 * These are not tests for the classes but for the usability of the concept.<br>
 * TODOS:
 * <ul>
 * <li>add a filtering for supporting newlines in different target formats</li>
 * <li>add a sort function to the FOR-tag</li>
 * <li>add a main class</li>
 * <li>decide whether to support creating artificial collections as "skills.values.category".
 * </ul>
 *
 * @author TT
 */
public class TestPracticalUse
{

  /**
   * Prove that the tool is suitable for creating a LaTeX document. Test data is all fake stuff, obviously.
   *
   * @throws IOException
   */
  @Test
  public void cvAsLatex() throws IOException
  {
    Map<String, Object> cv = null;
    try (InputStream json = TestPracticalUse.class.getResourceAsStream("/cv.json");
      Reader reader = new InputStreamReader(json, StandardCharsets.UTF_8))
    {
      cv = new Gson().fromJson(reader, Map.class);
    }
    try (InputStream ti = TestPracticalUse.class.getResourceAsStream("/cv_template.tex");
      Reader template = new InputStreamReader(ti, StandardCharsets.UTF_8);
      OutputStream out = new FileOutputStream("cv.tex");
      Writer document = new OutputStreamWriter(out, StandardCharsets.UTF_8))
    {
      DataIntoTemplate systemUnderTest = new DataIntoTemplate(cv, '<', '@', '>');
      systemUnderTest.fillData(template, document);
    }

  }
}
