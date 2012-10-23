/*
 * Copyright 2010-2011, Sikuli.org
 * Released under the MIT License.
 *
 */
package org.sikuli.core;

import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import org.sikuli.core.Constants;
import org.sikuli.utility.Debug;
import org.sikuli.core.HotkeyListener;
import org.sikuli.core.Key;
import org.sikuli.core.Settings;
import org.sikuli.system.OS;

public abstract class HotkeyManager {

   protected static HotkeyManager _instance = null;

   private static String getOSHotkeyManagerClass() {
      String pkg = "org.sikuli.hotkey.";
      int theOS = Settings.getOS();
      switch (theOS) {
         case Constants.ISMAC:
            return pkg + "MacHotkeyManager";
         case Constants.ISWINDOWS:
            return pkg + "WindowsHotkeyManager";
         case Constants.ISLINUX:
            return pkg + "LinuxHotkeyManager";
         default:
            Debug.error("Error: Hotkey registration is not supported on your OS.");
      }
      return null;
   }

   protected String getKeyCodeText(int key) {
      return KeyEvent.getKeyText(key).toUpperCase();
   }

   protected String getKeyModifierText(int modifiers) {
      String txtMod = KeyEvent.getKeyModifiersText(modifiers).toUpperCase();
      if (Env.isMac()) {
         txtMod = txtMod.replace("META", "CMD");
         txtMod = txtMod.replace("WINDOWS", "CMD");
      } else {
         txtMod = txtMod.replace("META", "WIN");
         txtMod = txtMod.replace("WINDOWS", "WIN");
      }
      return txtMod;
   }

   public static HotkeyManager getInstance() {
      if (_instance == null) {
         String cls = getOSHotkeyManagerClass();
         if (cls != null) {
            try {
               Class c = Class.forName(cls);
               Constructor constr = c.getConstructor();
               _instance = (HotkeyManager) constr.newInstance();
            } catch (Exception e) {
               Debug.error("Can't create " + cls + ": " + e.getMessage());
            }
         }
      }
      return _instance;
   }

   /**
    * install a hotkey listener.
    *
    * @return true if success. false otherwise.
    */
   public boolean addHotkey(String key, int modifiers, HotkeyListener listener) {
      return addHotkey(key.charAt(0), modifiers, listener);
   }

   /**
    * install a hotkey listener.
    *
    * @return true if success. false otherwise.
    */
   public boolean addHotkey(char key, int modifiers, HotkeyListener listener) {
      int[] keyCodes = Key.toJavaKeyCode(key);
      int keyCode = keyCodes[keyCodes.length - 1];
      String txtMod = getKeyModifierText(modifiers);
      String txtCode = getKeyCodeText(keyCode);
      Debug.info("add hotkey: " + txtMod + " " + txtCode);
      return _instance._addHotkey(keyCode, modifiers, listener);
   }

   /**
    * uninstall a hotkey listener.
    *
    * @return true if success. false otherwise.
    */
   public boolean removeHotkey(String key, int modifiers) {
      return removeHotkey(key.charAt(0), modifiers);
   }

   /**
    * uninstall a hotkey listener.
    *
    * @return true if success. false otherwise.
    */
   public boolean removeHotkey(char key, int modifiers) {
      int[] keyCodes = Key.toJavaKeyCode(key);
      int keyCode = keyCodes[keyCodes.length - 1];
      String txtMod = getKeyModifierText(modifiers);
      String txtCode = getKeyCodeText(keyCode);
      Debug.info("remove hotkey: " + txtMod + " " + txtCode);
      return _instance._removeHotkey(keyCode, modifiers);
   }

   abstract public boolean _addHotkey(int keyCode, int modifiers, HotkeyListener listener);

   abstract public boolean _removeHotkey(int keyCode, int modifiers);

   abstract public void cleanUp();
}
