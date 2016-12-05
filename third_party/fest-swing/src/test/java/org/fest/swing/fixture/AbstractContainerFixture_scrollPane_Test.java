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
import static org.fest.util.Arrays.array;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Dimension;

import javax.annotation.Nonnull;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests lookups of {@code JScrollPane}s in {@link AbstractContainerFixture}.
 *
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_scrollPane_Test extends RobotBasedTestCase {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  private FakeContainerFixture fixture;
  private MyWindow window;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew(getClass());
    fixture = new FakeContainerFixture(robot, window);
  }

  @Test
  public void should_find_visible_JScrollPane_by_name() {
    robot.showWindow(window);
    JScrollPaneFixture scrollPane = fixture.scrollPane("scrollMeScrollBar");
    assertThat(scrollPane.target()).isSameAs(window.scrollPane);
  }

  @Test
  public void should_fail_if_visible_JScrollPane_not_found_by_name() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessage("Unable to find component using matcher");
    thrown.expectMessage("name='myScrollPane', type=javax.swing.JScrollPane, requireShowing=true");
    fixture.scrollPane("myScrollPane");
  }

  @Test
  public void should_find_visible_JScrollPane_by_type() {
    robot.showWindow(window);
    JScrollPaneFixture scrollPane = fixture.scrollPane();
    assertThat(scrollPane.target()).isSameAs(window.scrollPane);
  }

  @Test
  public void should_fail_if_visible_JScrollPane_not_found_by_type() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessage("Unable to find component using matcher");
    thrown.expectMessage("type=javax.swing.JScrollPane, requireShowing=true");
    fixture.scrollPane();
  }

  @Test
  public void should_find_visible_JScrollPane_by_Matcher() {
    robot.showWindow(window);
    JScrollPaneFixture scrollPane = fixture.scrollPane(new GenericTypeMatcher<JScrollPane>(JScrollPane.class) {
      @Override
      protected boolean isMatching(@Nonnull JScrollPane s) {
        return s.getViewport().getView() instanceof JList;
      }
    });
    assertThat(scrollPane.target()).isSameAs(window.scrollPane);
  }

  @Test
  public void should_fail_if_visible_JScrollPane_not_found_by_Matcher() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessage("Unable to find component using matcher");
    fixture.scrollPane(neverMatches(JScrollPane.class));
  }

  private static class MyWindow extends TestWindow {
    final JScrollPane scrollPane = new JScrollPane(new JList(array("One", "Two")));

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
      scrollPane.setName("scrollMeScrollBar");
      scrollPane.setPreferredSize(new Dimension(100, 50));
      addComponents(scrollPane);
    }
  }
}
