/*
 * Copyright 2010-2011, Sikuli.org
 * Released under the MIT License.
 *
 */
package org.sikuli.ide;


public class IDESettings {
   public static String SikuliVersion = "X-1.0-RaiMan2012-1";

	 private static String[] args = new String[0];

	 protected static String[] getArgs() {
		 return args;
	 }

	 protected static void setArgs(String[] args) {
		 IDESettings.args = args;
	 }
}
