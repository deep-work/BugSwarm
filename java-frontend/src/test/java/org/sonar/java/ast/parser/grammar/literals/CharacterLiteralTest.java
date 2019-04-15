/*
 * SonarQube Java
 * Copyright (C) 2012-2018 SonarSource SA
 * mailto:info AT sonarsource DOT com
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
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.java.ast.parser.grammar.literals;

import org.junit.Test;
import org.sonar.java.ast.api.JavaTokenType;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class CharacterLiteralTest {

  @Test
  public void ok() {
    assertThat(JavaTokenType.CHARACTER_LITERAL)
      .as("single character").matches("'a'")
      .as("escaped LF").matches("'\\n'")
      .as("escaped quote").matches("'\\''")
      .as("octal escape").matches("'\\177'")
      .as("unicode escape").matches("'\\u03a9'")
      .as("unicode escape").matches("'\\uuuu005Cr'")
      .as("unicode escape").matches("'\\u005c\\u005c'");
  }

}
