/* Generated By:JavaCC: Do not edit this line. PortParser.java */
/*
 * Copyright (c) 2006 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Michael Golubev (Borland) - initial API and implementation
 */
package org.eclipse.uml2.diagram.common.parser.port;

import java.io.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.diagram.parser.*;
import org.eclipse.uml2.diagram.parser.lookup.LookupResolver;
import org.eclipse.uml2.diagram.parser.lookup.LookupSuite;
import org.eclipse.uml2.uml.*;

public class PortParser extends ExternalParserBase implements PortParserConstants {
        private Port mySubject;

    private static class TypeLookupCallback implements LookupResolver.Callback {
        private final Port myPort;

                public TypeLookupCallback(Port property){
                        myPort = property;
        }

        public void lookupResolved(NamedElement resolution) {
                if (resolution instanceof Type){
                        myPort.setType((Type)resolution);
                }
        }
    }

    public PortParser(){
        this(new StringReader("")); //$NON-NLS-1$
    }

    public PortParser(LookupSuite lookup){
        this();
        setLookupSuite(lookup);
    }

        public EClass getSubjectClass(){
                return UMLPackage.eINSTANCE.getPort();
        }

        public void parse(EObject target, String text) throws ExternalParserException {
                checkContext();
                ReInit(new StringReader(text));
                mySubject = (Port)target;
                Declaration();
                mySubject = null;
        }

        protected static int parseInt(Token t) throws ParseException {
                if (t.kind != PortParserConstants.INTEGER_LITERAL){
                        throw new IllegalStateException("Token: " + t + ", image: " + t.image); //$NON-NLS-1$ //$NON-NLS-2$
                }
                try {
                        return Integer.parseInt(t.image); //XXX: "0005", "99999999999999999999999"
                } catch (NumberFormatException e){
                        throw new ParseException("Not supported integer value:" + t.image); //$NON-NLS-1$
                }
        }

  final public void Declaration() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS:
    case MINUS:
    case NUMBER_SIGN:
    case TILDE:
      Visibility();
      break;
    default:
      jj_la1[0] = jj_gen;
      ;
    }
    PortName();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case COLON:
      PortType();
      break;
    default:
      jj_la1[1] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LBRACKET:
      Multiplicity();
      break;
    default:
      jj_la1[2] = jj_gen;
      ;
    }
    jj_consume_token(0);
  }

  final public void PortName() throws ParseException {
        String name;
    name = NameWithSpaces();
                mySubject.setName(name);
  }

  final public void Visibility() throws ParseException {
        VisibilityKind kind;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS:
      jj_consume_token(PLUS);
                         kind = VisibilityKind.PUBLIC_LITERAL;
      break;
    case MINUS:
      jj_consume_token(MINUS);
                          kind = VisibilityKind.PRIVATE_LITERAL;
      break;
    case NUMBER_SIGN:
      jj_consume_token(NUMBER_SIGN);
                                kind = VisibilityKind.PROTECTED_LITERAL;
      break;
    case TILDE:
      jj_consume_token(TILDE);
                          kind = VisibilityKind.PACKAGE_LITERAL;
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                mySubject.setVisibility(kind);
  }

  final public void Multiplicity() throws ParseException {
    MultiplicityRange();
  }

/* XXX: Parse conflict in case of empty default value
void MultiplicityDesignator() :
{ }
{
	<LCURLY> 
	(
		( MultiplicityUnique() [ MultiplicityOrdered() ] ) 
		|
		( MultiplicityOrdered() [ MultiplicityUnique() ] ) 
	) 
	<RCURLY>
}

void MultiplicityUnique() :
{}
{
		<UNIQUE> { mySubject.setIsUnique(true); }
	|
		<NON_UNIQUE> { mySubject.setIsUnique(false); }	
}

void MultiplicityOrdered() :
{}
{
		<ORDERED> { mySubject.setIsOrdered(true); }
	|
		<UNORDERED> { mySubject.setIsOrdered(false); }
}

*/

/* XXX: ValueSpecification -- how to parse */
  final public void MultiplicityRange() throws ParseException {
        Token tLower = null;
        Token tUpper;
    jj_consume_token(LBRACKET);
    if (jj_2_1(2)) {
      tLower = jj_consume_token(INTEGER_LITERAL);
      jj_consume_token(DOT);
      jj_consume_token(DOT);
                                                                        mySubject.setLower(parseInt(tLower));
    } else {
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case STAR:
      tUpper = jj_consume_token(STAR);
                                if (tLower == null){
                                        mySubject.setLower(0);
                                }
                                mySubject.setUpper(LiteralUnlimitedNatural.UNLIMITED);
      break;
    case INTEGER_LITERAL:
      tUpper = jj_consume_token(INTEGER_LITERAL);
                                if (tLower == null){
                                        mySubject.setLower(parseInt(tUpper));
                                }
                                mySubject.setUpper(parseInt(tUpper));
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(RBRACKET);
  }

  final public void PortType() throws ParseException {
        String type;
    jj_consume_token(COLON);
    type = NameWithSpaces();
                                          applyLookup(Type.class, type, new TypeLookupCallback(mySubject));
  }

  final public String NameWithSpaces() throws ParseException {
        StringBuffer result = new StringBuffer();
        Token t;
    t = jj_consume_token(IDENTIFIER);
                                   result.append(t.image);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENTIFIER:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_1;
      }
      t = jj_consume_token(IDENTIFIER);
                                     result.append(' '); result.append(t.image);
    }
                {if (true) return result.toString();}
    throw new Error("Missing return statement in function"); //$NON-NLS-1$
  }

  final private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  final private boolean jj_3_1() {
    if (jj_scan_token(INTEGER_LITERAL)) return true;
    if (jj_scan_token(DOT)) return true;
    return false;
  }

  public PortParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  public Token token, jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  public boolean lookingAhead = false;
  private boolean jj_semLA;
  private int jj_gen;
  final private int[] jj_la1 = new int[6];
  static private int[] jj_la1_0;
  static {
      jj_la1_0();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x7800,0x10,0x40,0x7800,0x2010000,0x4000000,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[1];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  public PortParser(java.io.InputStream stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new PortParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(java.io.InputStream stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public PortParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new PortParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public PortParser(PortParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(PortParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  final private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }

  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  final public Token getToken(int index) {
    Token t = lookingAhead ? jj_scanpos : token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.Vector jj_expentries = new java.util.Vector();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      boolean exists = false;
      for (java.util.Enumeration e = jj_expentries.elements(); e.hasMoreElements();) {
        int[] oldentry = (int[])(e.nextElement());
        if (oldentry.length == jj_expentry.length) {
          exists = true;
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              exists = false;
              break;
            }
          }
          if (exists) break;
        }
      }
      if (!exists) jj_expentries.addElement(jj_expentry);
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[29];
    for (int i = 0; i < 29; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 6; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 29; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  final public void enable_tracing() {
  }

  final public void disable_tracing() {
  }

  final private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
    }
    jj_rescan = false;
  }

  final private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}