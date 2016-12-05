/*
 * Created on Jun 7, 2009
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
 * Copyright @2009-2016 the FEST authors.
 */
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.NeverMatchingComponentMatcher.neverMatches;
import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.swing.JRadioButton;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests lookups of {@code JRadioButton}s in {@link AbstractContainerFixture}.
 *
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_radioButton_Test extends RobotBasedTestCase {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  private FakeContainerFixture fixture;
  private MyWindow window;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew(getClass());
    fixture = new FakeContainerFixture(robot, window);
  }

  @Test
  public void should_find_visible_JRadioButton_by_name() {
    robot.showWindow(window);
    JRadioButtonFixture radioButton = fixture.radioButton("selectMeRadioButton");
    assertThat(radioButton.target()).isSameAs(window.radioButton);
  }

  @Test
  public void should_fail_if_visible_JRadioButton_not_found_by_name() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessage("Unable to find component using matcher");
    thrown.expectMessage("name='myRadioButton', type=javax.swing.JRadioButton, requireShowing=true");
    fixture.radioButton("myRadioButton");
  }

  @Test
  public void should_find_visible_JRadioButton_by_type() {
    robot.showWindow(window);
    JRadioButtonFixture radioButton = fixture.radioButton();
    assertThat(radioButton.target()).isSameAs(window.radioButton);
  }

  @Test
  public void should_fail_if_visible_JRadioButton_not_found_by_type() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessage("Unable to find component using matcher");
    thrown.expectMessage("type=javax.swing.JRadioButton, requireShowing=true");
    fixture.radioButton();
  }

  @Test
  public void should_find_visible_JRadioButton_by_Matcher() {
    robot.showWindow(window);
    JRadioButtonFixture radioButton = fixture.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
      @Override
      protected boolean isMatching(@Nonnull JRadioButton r) {
        return "Select Me".equals(r.getText());
      }
    });
    assertThat(radioButton.target()).isSameAs(window.radioButton);
  }

  @Test
  public void should_fail_if_visible_JRadioButton_not_found_by_Matcher() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessage("Unable to find component using matcher");
    fixture.radioButton(neverMatches(JRadioButton.class));
  }

  private static class MyWindow extends TestWindow {
    final JRadioButton radioButton = new JRadioButton("Select Me");

    static @Nonnull MyWindow createNew(final @Nonnull Class<?> testClass) {
      MyWindow result = execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
      return checkNotNull(result);
    }

    private MyWindow(@Nonnull Class<?> testClass) {
      super(testClass);
      radioButton.setName("selectMeRadioButton");
      addComponents(radioButton);
    }
  }
}
