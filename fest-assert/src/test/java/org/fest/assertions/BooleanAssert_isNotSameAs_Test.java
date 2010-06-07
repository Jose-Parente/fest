/*
 * Created on Apr 29, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2010 the original author or authors.
 */
package org.fest.assertions;

import static java.lang.Boolean.*;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for <code>{@link BooleanAssert#isNotSameAs(Boolean)}</code>.
 *
 * @author Ansgar Konermann
 * @author Alex Ruiz
 */
public class BooleanAssert_isNotSameAs_Test implements GenericAssert_isNotSameAs_TestCase {

  private static Boolean value;

  @BeforeClass
  public static void setUpOnce() {
    value = TRUE;
  }

  @Test
  public void should_pass_if_actual_and_expected_are_not_same() {
    new BooleanAssert(value).isNotSameAs(FALSE);
  }

  @Test
  public void should_fail_if_actual_and_expected_are_same() {
    expectAssertionError("given objects are same:<true>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(value).isNotSameAs(value);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_and_expected_are_same() {
    expectAssertionError("[A Test] given objects are same:<true>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(value).as("A Test")
                                .isNotSameAs(value);
      }
    });
  }

  @Test
  public void should_fail_with_custom_message_if_actual_and_expected_are_same() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(value).overridingErrorMessage("My custom message")
                                .isNotSameAs(value);
      }
    });
  }

  @Test
  public void should_fail_with_custom_message_ignoring_description_of_assertion_if_actual_and_expected_are_same() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(value).as("A Test")
                                .overridingErrorMessage("My custom message")
                                .isNotSameAs(value);
      }
    });
  }
}