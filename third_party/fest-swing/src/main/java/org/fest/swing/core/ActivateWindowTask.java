/*
 * Created on Feb 23, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008-2016 the FEST authors.
 */
package org.fest.swing.core;

import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Window;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;

/**
 * Activates an AWT {@code Window}. "Activate" means that the given window is brought to the front and then gets the
 * keyboard focus.
 * <p>
 * Note <a href="http://java.sun.com/docs/books/tutorial/uiswing/misc/focus.html">How to Use the Focus Subsystem</a>
 * states, “There is no foolproof way, across all platforms, to ensure that a window gains the focus.”
 * This implementation does its best.
 *
 * @author Alex Ruiz
 */
class ActivateWindowTask {
  @RunsInEDT
  static void activateWindow(final @Nonnull Window w) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        w.toFront();
        w.requestFocus();
      }
    });
  }

  private ActivateWindowTask() {}
}
