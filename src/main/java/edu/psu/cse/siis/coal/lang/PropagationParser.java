/* Generated By:JavaCC: Do not edit this line. PropagationParser.java */
package edu.psu.cse.siis.coal.lang;

import edu.psu.cse.siis.coal.ExtendedSignature;
import edu.psu.cse.siis.coal.Model;
import edu.psu.cse.siis.coal.arguments.Argument;
import edu.psu.cse.siis.coal.arguments.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropagationParser implements edu.psu.cse.siis.coal.lang.PropagationParserConstants {

  private static Logger logger = LoggerFactory.getLogger(PropagationParser.class);

  private Model model;
  private Map<String, Argument> namedArguments;
  private Map<String, String> namedSuperclasses;
  private Map<String, Field> fields;

  private void addModeledType(String type) {
    model.addModeledType(type);
  }

  private void addModifier(ExtendedSignature extendedSignature, Argument[] arguments,
      String modifierModifier) {
    model.addModifier(extendedSignature, arguments, modifierModifier);
  }

  private void addCopyConstructor(String signature, Argument[] arguments) {
    model.addCopyConstructor(signature, arguments);
  }

  private void addQuery(ExtendedSignature extendedSignature, Argument[] arguments) {
    model.addQuery(extendedSignature, arguments);
  }

  private void addNamedSuperclass(String name, String superclass) {
    namedSuperclasses.put(name, superclass);
  }

  private void addExcludedClass(String name) {
    model.addExcludedClass(name);
  }

  private void addSource(ExtendedSignature extendedSignature, Argument[] arguments) {
    model.addSource(extendedSignature, arguments);
  }

  private void addConstant(String signature, Argument[] arguments) {
    model.addConstant(signature, arguments);
  }

  private void addField(String name, String type, int count, int line) throws ParseException {
    if (fields.containsKey(name)) {
      throw new ParseException("Previously declared field " + name + " at line "
          + line);
    }
    fields.put(name, new Field(name, type));
  }

  private Field getField(String fieldName, int line) throws ParseException {
    Field field = fields.get(fieldName);
    if (field == null) {
      throw new ParseException("Unknown field " + fieldName + " at line "
          + line);
    }
    return field;
  }

  private String getNamedSuperclass(String name) throws ParseException {
    String superclass = namedSuperclasses.get(name);
    if (superclass == null) {
      throw new ParseException("Undeclared superclass: " + name);
    }
    return superclass;
  }

  public static void parseModelFromDirectory(Model model, String dir) throws FileNotFoundException,
            ParseException {
    logger.info("Parsing model from directory " + dir);

    List<File> files = new ArrayList<File>();
    try {
      DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir), "*.model");
      for (Path file : stream) {
        files.add(file.toFile());
      }
      stream.close();
    } catch (IOException e) {
      throw new RuntimeException("Could not list model files in directory " + dir);
    }

    for (File file : files) {
      parseModelFromFile(model, file);
    }
  }

  public static void parseModelFromFile(Model model, File file)
      throws FileNotFoundException, ParseException {
    logger.info("Parsing model from file " + file);

    PropagationParser parser = new PropagationParser(model, new FileInputStream(file));
    parser.start();
  }

  private PropagationParser(Model model, java.io.InputStream inputStream) {
    this(inputStream);
    this.model = model;
  }

  public static void main(String[] args) throws FileNotFoundException, ParseException {
    for (String filePath : args) {
      Model.loadModelFromFile(filePath);
    }
    Model.v().endInitialization();
    Model.v().dump();
  }

  final public void start() throws ParseException {
  namedArguments = new HashMap<String, Argument>();
  namedSuperclasses = new HashMap<String, String>();
  fields = new HashMap<String, Field>();
    label_1:
    while (true) {
      object();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CLASS:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
    }
  }

  final public void object() throws ParseException {
    jj_consume_token(CLASS);
    classIdentifier();
    jj_consume_token(36);
    classDeclaration();
    jj_consume_token(37);
  }

  final public void classIdentifier() throws ParseException {
  String type;
    type = typeDescriptor();
    addModeledType(type);
  }

  final public void classDeclaration() throws ParseException {
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MODIFIER:
      case ARGUMENT:
      case COPY:
      case QUERY:
      case SUPER:
      case EXCLUDE:
      case SOURCE:
      case STATICFIELD:
      case IDENTIFIER:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENTIFIER:
        fieldDeclaration();
        break;
      case ARGUMENT:
        argument();
        break;
      case MODIFIER:
        modifier();
        break;
      case COPY:
        copy();
        break;
      case SUPER:
        superclassDeclaration();
        break;
      case QUERY:
        query();
        break;
      case EXCLUDE:
        exclude();
        break;
      case SOURCE:
        source();
        break;
      case STATICFIELD:
        staticField();
        break;
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  final public void fieldDeclaration() throws ParseException {
  Token t;
  String name;
  String type;
  String optional;
  int count = -1;
    t = jj_consume_token(IDENTIFIER);
    type = t.image;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 38:
      jj_consume_token(38);
      t = jj_consume_token(IDENTIFIER);
      jj_consume_token(39);
      optional = t.image;
      type = type + "<" + optional + ">";
      break;
    default:
      jj_la1[3] = jj_gen;
      ;
    }
    t = jj_consume_token(IDENTIFIER);
    name = t.image;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 42:
      count = count();
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
    jj_consume_token(40);
    addField(name, type, count, t.beginLine);
  }

  final public String typeDescriptor() throws ParseException {
  Token t;
    if (jj_2_1(2147483647)) {
      t = jj_consume_token(TYPE_DESCRIPTOR);
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENTIFIER:
        t = jj_consume_token(IDENTIFIER);
        break;
      default:
        jj_la1[5] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  final public void argument() throws ParseException {
  Token t;
  String argumentName;
  Argument argument;
    jj_consume_token(ARGUMENT);
    t = jj_consume_token(IDENTIFIER);
    argumentName = t.image;
    jj_consume_token(EQUALS);
    argument = argumentDeclaration();
    namedArguments.put(argumentName, argument);
  }

  final public Argument argumentDeclaration() throws ParseException {
  Argument argument = new Argument();
    if (jj_2_2(2)) {
      argumentWithNumbers(argument);
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENTIFIER:
      case 42:
        argumentWithoutNumbers(argument);
        break;
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    {if (true) return argument;}
    throw new Error("Missing return statement in function");
  }

  final public void argumentWithNumbers(Argument argument) throws ParseException {
    numbers(argument);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 40:
      jj_consume_token(40);
      break;
    default:
      jj_la1[9] = jj_gen;
      if (jj_2_3(2)) {
        jj_consume_token(COLON);
        actionAndField(argument);
        label_3:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case 41:
            ;
            break;
          default:
            jj_la1[7] = jj_gen;
            break label_3;
          }
          jj_consume_token(41);
          argumentDeclarationElement(argument);
        }
        jj_consume_token(40);
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COLON:
          jj_consume_token(COLON);
          argumentDeclarationElement(argument);
          label_4:
          while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 41:
              ;
              break;
            default:
              jj_la1[8] = jj_gen;
              break label_4;
            }
            jj_consume_token(41);
            argumentDeclarationElement(argument);
          }
          jj_consume_token(40);
          break;
        default:
          jj_la1[10] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
  }

  final public void argumentWithoutNumbers(Argument argument) throws ParseException {
    if (jj_2_4(2)) {
      field(argument);
      jj_consume_token(40);
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENTIFIER:
      case 42:
        actionAndField(argument);
        label_5:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case 41:
            ;
            break;
          default:
            jj_la1[11] = jj_gen;
            break label_5;
          }
          jj_consume_token(41);
          argumentDeclarationElement(argument);
        }
        jj_consume_token(40);
        break;
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  final public void actionAndField(Argument argument) throws ParseException {
    actions(argument);
    field(argument);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER:
    case STRING_LITERAL:
    case 42:
      inlineValues(argument);
      break;
    default:
      jj_la1[13] = jj_gen;
      ;
    }
  }

  final public void argumentDeclarationElement(Argument argument) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TYPE:
      type(argument);
      break;
    case PROPERTIES:
      properties(argument);
      break;
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void numbers(Argument argument) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER:
      singleNumber(argument);
      break;
    case 42:
      multipleNumbers(argument);
      break;
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void singleNumber(Argument argument) throws ParseException {
  Token t;
    t = jj_consume_token(INTEGER);
    try {
      argument.setArgnum(new int[] { Integer.parseInt(t.image) });
    } catch (NumberFormatException e) {
      {if (true) throw new ParseException("Badly formatted integer: " + t.image);}
    }
  }

  final public void multipleNumbers(Argument argument) throws ParseException {
  int number;
  List<Integer> numberList = new ArrayList<Integer>(2);
    jj_consume_token(42);
    number = multipleNumbersElement();
    numberList.add(number);
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 41:
        ;
        break;
      default:
        jj_la1[16] = jj_gen;
        break label_6;
      }
      jj_consume_token(41);
      number = multipleNumbersElement();
    numberList.add(number);
    }
    jj_consume_token(43);
    int[] numbers = new int[numberList.size()];
    for (int i = 0; i < numberList.size(); ++i) {
      numbers[i] = numberList.get(i);
    }
    argument.setArgnum(numbers);
  }

  final public int multipleNumbersElement() throws ParseException {
  Token t;
    t = jj_consume_token(INTEGER);
    try {
      {if (true) return Integer.parseInt(t.image);}
    } catch(NumberFormatException e) {
      {if (true) throw new ParseException("Badly formatted integer: " + t.image);}
    }
    throw new Error("Missing return statement in function");
  }

  final public void field(Argument argument) throws ParseException {
  Token t;
    t = jj_consume_token(IDENTIFIER);
    argument.setField(getField(t.image, t.beginLine));
  }

  final public void type(Argument argument) throws ParseException {
  String type;
  Token t;
    jj_consume_token(TYPE);
    type = typeDescriptor();
    argument.setType(type);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case COLON:
      jj_consume_token(COLON);
      t = jj_consume_token(IDENTIFIER);
    argument.setReferencedFieldName(t.image);
      break;
    default:
      jj_la1[17] = jj_gen;
      ;
    }
  }

  final public void inlineValues(Argument argument) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER:
    case STRING_LITERAL:
      singleInlineValue(argument);
      break;
    case 42:
      multipleInlineValues(argument);
      break;
    default:
      jj_la1[18] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void singleInlineValue(Argument argument) throws ParseException {
  String value;
    value = inlineValue();
    argument.setInlineValues(new String[] { value });
  }

  final public void multipleInlineValues(Argument argument) throws ParseException {
  List<String> valueList = new ArrayList<String>();
  String value;
    jj_consume_token(42);
    value = inlineValue();
    valueList.add(value);
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 41:
        ;
        break;
      default:
        jj_la1[19] = jj_gen;
        break label_7;
      }
      jj_consume_token(41);
      value = inlineValue();
    valueList.add(value);
    }
    jj_consume_token(43);
    argument.setInlineValues(valueList.toArray(new String[valueList.size()]));
  }

  final public String inlineValue() throws ParseException {
  Token t;
  String inlineValue;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case STRING_LITERAL:
      t = jj_consume_token(STRING_LITERAL);
    inlineValue = t.image;
    inlineValue = inlineValue.substring(1, inlineValue.length() - 1);
      break;
    case INTEGER:
      t = jj_consume_token(INTEGER);
                  inlineValue = t.image;
      break;
    default:
      jj_la1[20] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return inlineValue;}
    throw new Error("Missing return statement in function");
  }

  final public void actions(Argument argument) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENTIFIER:
      singleAction(argument);
      break;
    case 42:
      multipleActions(argument);
      break;
    default:
      jj_la1[21] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void singleAction(Argument argument) throws ParseException {
  Token t;
    t = jj_consume_token(IDENTIFIER);
    argument.setActions(new String[] { t.image });
  }

  final public void multipleActions(Argument argument) throws ParseException {
  Token t;
  List<String> actionList = new ArrayList<String>(2);
    jj_consume_token(42);
    t = jj_consume_token(IDENTIFIER);
    actionList.add(t.image);
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 41:
        ;
        break;
      default:
        jj_la1[22] = jj_gen;
        break label_8;
      }
      jj_consume_token(41);
      t = jj_consume_token(IDENTIFIER);
    actionList.add(t.image);
    }
    jj_consume_token(43);
    argument.setActions(actionList.toArray(new String[actionList.size()]));
  }

  final public void properties(Argument argument) throws ParseException {
    jj_consume_token(PROPERTIES);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER:
    case STRING_LITERAL:
      singleProperty(argument);
      break;
    case 42:
      multipleProperties(argument);
      break;
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void singleProperty(Argument argument) throws ParseException {
  String key, value;
    key = inlineValue();
    jj_consume_token(COLON);
    value = inlineValue();
    argument.addProperty(key, value);
  }

  final public void multipleProperties(Argument argument) throws ParseException {
  String key, value;
    jj_consume_token(42);
    key = inlineValue();
    jj_consume_token(COLON);
    value = inlineValue();
    argument.addProperty(key, value);
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 41:
        ;
        break;
      default:
        jj_la1[24] = jj_gen;
        break label_9;
      }
      jj_consume_token(41);
      key = inlineValue();
      jj_consume_token(COLON);
      value = inlineValue();
    argument.addProperty(key, value);
    }
    jj_consume_token(43);
  }

  final public int count() throws ParseException {
  Token t;
    jj_consume_token(42);
    t = jj_consume_token(INTEGER);
    jj_consume_token(43);
    try {
      {if (true) return Short.parseShort(t.image);}
    } catch (NumberFormatException e) {
      {if (true) throw new ParseException("Badly formatted integer: " + t.image);}
    }
    throw new Error("Missing return statement in function");
  }

  final public void modifier() throws ParseException {
  ExtendedSignature extendedSignature;
  Argument[] arguments;
  String modifierModifier;
    jj_consume_token(MODIFIER);
    modifierModifier = modifierModifier();
    extendedSignature = extendedSignature();
    jj_consume_token(36);
    arguments = modifierOrQueryOrFieldDeclaration();
    jj_consume_token(37);
    addModifier(extendedSignature, arguments, modifierModifier);
  }

  final public String modifierModifier() throws ParseException {
  Token t;
  String modifierModifier = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case COPY:
    case GEN:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case GEN:
        t = jj_consume_token(GEN);
        break;
      case COPY:
        t = jj_consume_token(COPY);
        break;
      default:
        jj_la1[25] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    modifierModifier = t.image;
      break;
    default:
      jj_la1[26] = jj_gen;
      ;
    }
    {if (true) return modifierModifier;}
    throw new Error("Missing return statement in function");
  }

  final public ExtendedSignature extendedSignature() throws ParseException {
  String signature;
  String superclass = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case SIGNATURE:
      signature = queryOrModifierSignature();
      break;
    case IDENTIFIER:
      superclass = superclass();
      jj_consume_token(COLON);
      signature = queryShortSignature();
      break;
    default:
      jj_la1[27] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return new ExtendedSignature(signature, superclass);}
    throw new Error("Missing return statement in function");
  }

  final public Argument[] modifierOrQueryOrFieldDeclaration() throws ParseException {
  Argument argument;
  List<Argument> argumentList = new ArrayList<Argument>();
    label_10:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ARGUMENT:
      case INTEGER:
      case IDENTIFIER:
      case 42:
        ;
        break;
      default:
        jj_la1[28] = jj_gen;
        break label_10;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ARGUMENT:
        argument = namedArgument();
        break;
      case INTEGER:
      case IDENTIFIER:
      case 42:
        argument = argumentDeclaration();
        break;
      default:
        jj_la1[29] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    argumentList.add(argument);
    }
    {if (true) return argumentList.toArray(new Argument[argumentList.size()]);}
    throw new Error("Missing return statement in function");
  }

  final public Argument namedArgument() throws ParseException {
  Token t;
    jj_consume_token(ARGUMENT);
    t = jj_consume_token(IDENTIFIER);
    jj_consume_token(40);
    Argument argument = namedArguments.get(t.image);
    if (argument == null) {
      {if (true) throw new ParseException("Undefined argument: " + t.image);}
    }
    {if (true) return argument;}
    throw new Error("Missing return statement in function");
  }

  final public void copy() throws ParseException {
  Token t;
  String signature;
  Argument[] arguments;
    jj_consume_token(COPY);
    t = jj_consume_token(SIGNATURE);
    signature = t.image;
    jj_consume_token(36);
    arguments = modifierOrQueryOrFieldDeclaration();
    jj_consume_token(37);
    addCopyConstructor(signature, arguments);
  }

  final public void query() throws ParseException {
  ExtendedSignature extendedSignature;
  Argument[] arguments;
    jj_consume_token(QUERY);
    extendedSignature = extendedSignature();
    jj_consume_token(36);
    arguments = modifierOrQueryOrFieldDeclaration();
    jj_consume_token(37);
    addQuery(extendedSignature, arguments);
  }

  final public String queryOrModifierSignature() throws ParseException {
  Token t;
    t = jj_consume_token(SIGNATURE);
    {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  final public void superclassDeclaration() throws ParseException {
  Token t;
  String name;
  String superclass;
    jj_consume_token(SUPER);
    t = jj_consume_token(IDENTIFIER);
    jj_consume_token(EQUALS);
    name = t.image;
    superclass = typeDescriptor();
    jj_consume_token(40);
    addNamedSuperclass(name, superclass);
  }

  final public String superclass() throws ParseException {
  String superclass;
    superclass = namedSuperclass();
    {if (true) return superclass;}
    throw new Error("Missing return statement in function");
  }

  final public String namedSuperclass() throws ParseException {
  Token t;
    t = jj_consume_token(IDENTIFIER);
    {if (true) return getNamedSuperclass(t.image);}
    throw new Error("Missing return statement in function");
  }

  final public String queryShortSignature() throws ParseException {
  Token t;
    t = jj_consume_token(SHORT_SIGNATURE);
    {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  final public void exclude() throws ParseException {
  Token t;
    jj_consume_token(EXCLUDE);
    t = jj_consume_token(TYPE_DESCRIPTOR);
    jj_consume_token(40);
    addExcludedClass(t.image);
  }

  final public void source() throws ParseException {
  Token t;
  String signature;
  Argument[] arguments;
    jj_consume_token(SOURCE);
    t = jj_consume_token(SIGNATURE);
    signature = t.image;
    jj_consume_token(36);
    arguments = modifierOrQueryOrFieldDeclaration();
    jj_consume_token(37);
    addSource(new ExtendedSignature(signature, null), arguments);
  }

  final public void staticField() throws ParseException {
    jj_consume_token(STATICFIELD);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENTIFIER:
      shortStaticField();
      break;
    case STATIC_FIELD_SIGNATURE:
      extendedStaticField();
      break;
    default:
      jj_la1[30] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void shortStaticField() throws ParseException {
  Token t;
  String signature;
  Argument argument = new Argument();
    t = jj_consume_token(IDENTIFIER);
    argument.setField(getField(t.image, t.beginLine));
    t = jj_consume_token(STATIC_FIELD_SIGNATURE);
    signature = t.image;
    jj_consume_token(EQUALS);
    inlineValues(argument);
    jj_consume_token(40);
    argument.setActions(new String[] { "replace" });
    addConstant(signature, new Argument[] { argument });
  }

  final public void extendedStaticField() throws ParseException {
  Token t;
  String signature;
  Argument[] arguments;
    t = jj_consume_token(STATIC_FIELD_SIGNATURE);
    signature = t.image;
    jj_consume_token(36);
    arguments = modifierOrQueryOrFieldDeclaration();
    jj_consume_token(37);
    addConstant(signature, arguments);
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_2_3(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  private boolean jj_2_4(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_4(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  private boolean jj_3R_25() {
    if (jj_scan_token(INTEGER)) return true;
    return false;
  }

  private boolean jj_3R_12() {
    if (jj_3R_16()) return true;
    return false;
  }

  private boolean jj_3R_24() {
    if (jj_scan_token(42)) return true;
    return false;
  }

  private boolean jj_3_4() {
    if (jj_3R_13()) return true;
    if (jj_scan_token(40)) return true;
    return false;
  }

  private boolean jj_3R_15() {
    if (jj_scan_token(COLON)) return true;
    return false;
  }

  private boolean jj_3R_23() {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  private boolean jj_3_3() {
    if (jj_scan_token(COLON)) return true;
    if (jj_3R_12()) return true;
    return false;
  }

  private boolean jj_3R_11() {
    if (jj_3R_14()) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(40)) {
    jj_scanpos = xsp;
    if (jj_3_3()) {
    jj_scanpos = xsp;
    if (jj_3R_15()) return true;
    }
    }
    return false;
  }

  private boolean jj_3R_22() {
    if (jj_scan_token(42)) return true;
    if (jj_3R_25()) return true;
    return false;
  }

  private boolean jj_3R_19() {
    if (jj_3R_23()) return true;
    return false;
  }

  private boolean jj_3R_20() {
    if (jj_3R_24()) return true;
    return false;
  }

  private boolean jj_3R_16() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_19()) {
    jj_scanpos = xsp;
    if (jj_3R_20()) return true;
    }
    return false;
  }

  private boolean jj_3_2() {
    if (jj_3R_11()) return true;
    return false;
  }

  private boolean jj_3R_13() {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  private boolean jj_3_1() {
    if (jj_scan_token(TYPE_DESCRIPTOR)) return true;
    return false;
  }

  private boolean jj_3R_21() {
    if (jj_scan_token(INTEGER)) return true;
    return false;
  }

  private boolean jj_3R_17() {
    if (jj_3R_21()) return true;
    return false;
  }

  private boolean jj_3R_18() {
    if (jj_3R_22()) return true;
    return false;
  }

  private boolean jj_3R_14() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_17()) {
    jj_scanpos = xsp;
    if (jj_3R_18()) return true;
    }
    return false;
  }

  /** Generated Token Manager. */
  public PropagationParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[31];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x80,0x406b0d00,0x406b0d00,0x0,0x0,0x40000000,0x40000000,0x0,0x0,0x0,0x800000,0x0,0x40000000,0x8000000,0x2000200,0x8000000,0x0,0x800000,0x8000000,0x0,0x8000000,0x40000000,0x0,0x8000000,0x0,0x100800,0x100800,0x60000000,0x48000400,0x48000400,0x40000000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x40,0x400,0x0,0x400,0x200,0x200,0x100,0x0,0x200,0x400,0x402,0x0,0x400,0x200,0x0,0x402,0x200,0x2,0x400,0x200,0x402,0x200,0x0,0x0,0x0,0x400,0x400,0x1,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[4];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public PropagationParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public PropagationParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new PropagationParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public PropagationParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new PropagationParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public PropagationParser(PropagationParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(PropagationParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
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

  static private final class LookaheadSuccess extends Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
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


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private List<int[]> jj_expentries = new ArrayList<int[]>();
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
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[44];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 31; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 44; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 4; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
            case 3: jj_3_4(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
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
