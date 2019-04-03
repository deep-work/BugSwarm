/*
 * SonarQube Java
 * Copyright (C) 2012 SonarSource
 * sonarqube@googlegroups.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class SAMAnnotatedCheckTest {

  private static final String FILE = "src/test/files/checks/SAMAnnotatedCheck.java";

  @Test
  public void no_issue_with_no_java_version() {
    JavaCheckVerifier.verify("src/test/files/checks/SAMAnnotatedCheck_no_version.java", new SAMAnnotatedCheck());
  }

  @Test
  public void all_issues_with_java_8() {
    JavaCheckVerifier.verify(FILE, new SAMAnnotatedCheck(), 8);
  }

  @Test
  public void no_issue_with_java_7() {
    JavaCheckVerifier.verifyNoIssue(FILE, new SAMAnnotatedCheck(), 7);
  }
}
