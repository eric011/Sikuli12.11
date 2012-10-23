/*
 * Copyright 2010-2011, Sikuli.org
 * Released under the MIT License.
 *
 */
package org.sikuli.utility;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.cli.*;

public class CommandArgs {

  public static String[] getPyArgs(CommandLine cl) {
    ArrayList<String> pargs;
    if (cl.hasOption("args")) {
      pargs = new ArrayList<String>(Arrays.asList(cl.getOptionValues("args")));
      if (cl.hasOption("run")) {
        pargs.add(0, cl.getOptionValue("run"));
      }
    } else {
      pargs = new ArrayList<String>(Arrays.asList(cl.getArgs()));
    }
    return pargs.toArray(new String[0]);
  }

  Options _options;
  String _callerType;

  public static boolean isIDE(String callerType) {
    return ("IDE".equals(callerType));
  }

  public static boolean isScript(String callerType) {
    return ("SCRIPT".equals(callerType));
  }

  public static boolean isOther(String callerType) {
    return (!isIDE(callerType) && !isScript(callerType));
  }

  public CommandArgs(String type) {
    if (!isIDE(type) && !isScript(type)) {
      Debug.error("Commandline Parser not configured for " + type);
      _callerType = "OTHER";
    } else {
      _callerType = type;
    }
    init();
  }

  public CommandLine getCommandLine(String[] args) {
    CommandLineParser parser = new PosixParser();
    CommandLine cmd = null;
    try {
      cmd = parser.parse(_options, args, true);
    } catch (ParseException exp) {
      Debug.error(exp.getMessage());
    }
    return cmd;
  }

  private void init() {
    _options = new Options();
    _options.addOption("h", "help", false, "print this help message");
    if (isIDE(_callerType)) {
      _options.addOption("s", "stderr", false,
              "print runtime errors to stderr instead of popping up a message box");
      _options.addOption(
              OptionBuilder.withLongOpt("load")
              .withDescription("peload scripts in IDE")
              .hasArgs()
              .withArgName("one or more foobar.sikuli")
              .create('l'));
    }
    if (isScript(_callerType)) {
      _options.addOption("i", "interactive", false,
              "start interactive Sikuli Jython session\n(Sikuli, sys, time already imported)");
      _options.addOption(
              OptionBuilder.withLongOpt("test")
              .withDescription("run script using Jython's unittest")
              .hasArg()
              .withArgName("foobar.sikuli")
              .create('t'));
      _options.addOption(
              OptionBuilder.withLongOpt("run")
              .withDescription("run script")
              .hasArg()
              .withArgName("foobar.sikuli")
              .create('r'));
    }
    _options.addOption(
            OptionBuilder.hasArgs()
            .withLongOpt("args")
            .withArgName("arguments")
            .withDescription("arguments passed to Jython's sys.argv")
            .create());

  }

  public void printHelp() {
    HelpFormatter formatter = new HelpFormatter();
    if (isScript(_callerType)) {
      formatter.printHelp(80, "java -jar sikuli-script.jar\n",
              "----- Running Sikuli script using sikuli-script.jar "
              + "---------------------------",
              _options,
              "-----\n<foobar.sikuli>\n"
              + "path relative to current working directory or absolute path\n"
              + "-----------------------------------------------------------",
              true);
    } else if (isIDE(_callerType)) {
      formatter.printHelp("Sikuli-IDE", _options, true);
    } else {
      formatter.printHelp("--?????--", _options, true);
    }
  }
}
