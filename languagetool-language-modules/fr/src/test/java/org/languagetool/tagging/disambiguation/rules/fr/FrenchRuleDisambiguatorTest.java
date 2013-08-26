/* LanguageTool, a natural language style checker 
 * Copyright (C) 2005 Daniel Naber (http://www.danielnaber.de)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */

package org.languagetool.tagging.disambiguation.rules.fr;

import junit.framework.TestCase;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.French;
import org.languagetool.tagging.disambiguation.rules.XmlRuleDisambiguator;
import org.languagetool.tagging.disambiguation.xx.DemoDisambiguator;
import org.languagetool.tagging.fr.FrenchTagger;
import org.languagetool.tokenizers.SentenceTokenizer;
import org.languagetool.tokenizers.WordTokenizer;

import java.io.IOException;

public class FrenchRuleDisambiguatorTest extends TestCase {
  private FrenchTagger tagger;
  private WordTokenizer tokenizer;
  private SentenceTokenizer sentenceTokenizer;
  private XmlRuleDisambiguator disambiguator;
  private DemoDisambiguator disamb2;
  private JLanguageTool lt; 
  
  @Override
  public void setUp() {
    tagger = new FrenchTagger();
    tokenizer = new WordTokenizer();
    sentenceTokenizer = new SentenceTokenizer();
    disambiguator = new XmlRuleDisambiguator(new French());
    disamb2 = new DemoDisambiguator();    
    try {
      lt = new JLanguageTool(new French());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  public void testChunker() throws IOException {
    TestTools.myAssert("Il a enfin publié son livre.",
        "/[null]SENT_START Il/[il]R pers suj 3 m s  /[null]null a/[avoir]V avoir ind pres 3 s  /[null]null enfin/[enfin]A  /[null]null publié/[publier]V ppa m s  /[null]null son/[son]D e s  /[null]null livre/[livre]N e s ./[null]null", 
        tokenizer, sentenceTokenizer, tagger, disambiguator);
    TestTools.myAssert("Il a enfin publié son livre.",
        "/[null]SENT_START Il/[il]R pers suj 3 m s  /[null]null a/[a]N m sp|a/[avoir]V avoir ind pres 3 s  /[null]null enfin/[enfin]A  /[null]null publié/[publier]V ppa m s|publié/[publié]J m s  /[null]null son/[son]D m s|son/[son]N m s  /[null]null livre/[livre]N e s|livre/[livrer]V imp pres 2 s|livre/[livrer]V ind pres 1 s|livre/[livrer]V ind pres 3 s|livre/[livrer]V sub pres 1 s|livre/[livrer]V sub pres 3 s ./[null]null",
        tokenizer, sentenceTokenizer, tagger, disamb2);
    TestTools.myAssert("Je danse toutes les semaines au club.",
        "/[null]SENT_START Je/[je]R pers suj 1 s  /[null]null danse/[danser]V ind pres 1 s|danse/[danser]V sub pres 1 s  /[null]null toutes/[tous]R f p|toutes/[tout]D f p  /[null]null les/[le]D e p  /[null]null semaines/[semaine]N f p  /[null]null au/[au]D m s  /[null]null club/[club]N m s ./[null]null",
        tokenizer, sentenceTokenizer, tagger, disambiguator);
    TestTools.myAssert("Je danse toutes les semaines au club.",
        "/[null]SENT_START Je/[je]R pers suj 1 s  /[null]null danse/[danse]N f s|danse/[danser]V imp pres 2 s|danse/[danser]V ind pres 1 s|danse/[danser]V ind pres 3 s|danse/[danser]V sub pres 1 s|danse/[danser]V sub pres 3 s  /[null]null toutes/[tous]R f p|toutes/[tout]D f p  /[null]null les/[le]D e p|les/[les]R pers obj 3 p  /[null]null semaines/[semaine]N f p  /[null]null au/[au]D m s  /[null]null club/[club]N m s ./[null]null", 
        tokenizer, sentenceTokenizer, tagger, disamb2);
    TestTools.myAssert("Quand j'étais petit, je jouais au football.",
        "/[null]SENT_START Quand/[quand]C sub  /[null]null j/[je]R pers suj 1 s '/[null]null étais/[être]V etre ind impa 1 s  /[null]null petit/[petit]J m s ,/[null]null  /[null]null je/[je]R pers suj 1 s  /[null]null jouais/[jouer]V ind impa 1 s  /[null]null au/[au]D m s  /[null]null football/[football]N m s ./[null]null", 
        tokenizer, sentenceTokenizer, tagger, disambiguator);
    TestTools.myAssert("Quand j'étais petit, je jouais au football.",
        "/[null]SENT_START Quand/[quand]C sub  /[null]null j/[j]N m sp|j/[je]R pers suj 1 s '/[null]null étais/[étai]N m p|étais/[être]V etre ind impa 1 s|étais/[être]V etre ind impa 2 s  /[null]null petit/[petit]J m s|petit/[petit]N m s ,/[null]null  /[null]null je/[je]R pers suj 1 s  /[null]null jouais/[jouer]V ind impa 1 s|jouais/[jouer]V ind impa 2 s  /[null]null au/[au]D m s  /[null]null football/[football]N m s ./[null]null", 
        tokenizer, sentenceTokenizer, tagger, disamb2);
    TestTools.myAssert("Je suis petite.",
        "/[null]SENT_START Je/[je]R pers suj 1 s  /[null]null suis/[être]V etre ind pres 1 s  /[null]null petite/[petit]J f s ./[null]null", 
        tokenizer, sentenceTokenizer, tagger, disambiguator);
    TestTools.myAssert("Je suis petite.",
        "/[null]SENT_START Je/[je]R pers suj 1 s  /[null]null suis/[suivre]V imp pres 2 s|suis/[suivre]V ind pres 1 s|suis/[suivre]V ind pres 2 s|suis/[être]V etre ind pres 1 s  /[null]null petite/[petit]J f s|petite/[petit]N f s ./[null]null", 
        tokenizer, sentenceTokenizer, tagger, disamb2);
  }

  public void testAnnotations() throws IOException {
     final AnalyzedSentence sent = lt.getAnalyzedSentence("Les avions");
     assertEquals(sent.getAnnotations(), "Disambiguator log: \n\n" +
        "RP-D_N_AMBIG:1 Les[le/D e p*,les/R pers obj 3 p*] -> Les[le/D e p*]"+
             "\nRB-LE_LA_LES:1 Les[le/D e p*] -> Les[le/D e p*]" +
        "\n\nRP-D_N_AMBIG:1 avions[avoir/V avoir ind impa 1 p,avion/N m p,avoir/SENT_END] -> avions[avion/N m p,avoir/SENT_END]\n");
  }
  
}


