package de.tautenhahn.easydata;

import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Whatever input we want to understand has first to be split into handy chunks, every recognized element into
 * one separate token. Rest of the content may be in any parts but these should not be too big. <br>
 * Why do I not use a stream? Object streams are bad at iterating things, they tend to create new Spliterator
 * objects at each non-trivial manipulation. An old-fashioned Iterator can be passed between consumers much
 * more elegantly.
 *
 * @author TT
 */
public class Tokenizer implements Iterator<Token>
{

  private final Scanner data;

  private final String delimiter;

  private Matcher pending;

  private int row;

  private final Pattern pattern;

  /**
   * Creates instance.
   *
   * @param data must use an delimiter matching a fixed line ending ("\n" or "\r\n")so we can re-insert it.
   */
  Tokenizer(Scanner data, char opening, char closing)
  {
    this.data = data;
    delimiter = data.delimiter().toString();
    if (!RegexHelper.isLineBreak(delimiter))
    {
      throw new IllegalArgumentException("Scanners delimiter must be a specified line break.");
    }
    String open = RegexHelper.mask(opening);
    String close = RegexHelper.mask(closing);
    pattern = Pattern.compile("([^" + open + "]+)|" + //
                              "(" + open + "[^" + open + close + "]+" + close + ")|" + //
                              open, Pattern.DOTALL);
    pending = getMatcher();
  }

  @Override
  public boolean hasNext()
  {
    return pending != null;
  }

  @Override
  public Token next()
  {
    Token result = new Token(pending.group(0), row, pending.start());
    if (!pending.find())
    {
      pending = getMatcher();
    }
    return result;
  }

  private Matcher getMatcher()
  {
    if (data.hasNext())
    {
      Matcher result = pattern.matcher(data.next() + delimiter);
      row++;
      result.find(); // this special pattern will always match
      return result;
    }
    return null;
  }
}
