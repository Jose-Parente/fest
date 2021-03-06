/*
 * Created on Apr 16, 2008
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
package org.fest.swing.fixture;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link FontFixture#requireItalic()}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FontFixture_requireItalic_Test extends FontFixture_TestCase {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void should_pass_if_font_is_italic() {
    FontFixture fixture = new FontFixture(italicFont());
    fixture.requireItalic();
  }

  @Test
  public void should_fail_if_font_is_not_italic() {
    thrown.expect(AssertionError.class);
    thrown.expectMessage("[italic] expected:<true> but was:<false>");
    fixture().requireItalic();
  }

  @Test
  public void should_fail_showing_description_if_font_is_not_italic() {
    thrown.expect(AssertionError.class);
    thrown.expectMessage("[test - italic] expected:<true> but was:<false>");
    FontFixture fixture = new FontFixture(font(), "test");
    fixture.requireItalic();
  }
}
