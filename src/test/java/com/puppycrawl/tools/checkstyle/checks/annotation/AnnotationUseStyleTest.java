////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.annotation;

import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck.MSG_KEY_ANNOTATION_INCORRECT_STYLE;
import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck.MSG_KEY_ANNOTATION_PARENS_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck.MSG_KEY_ANNOTATION_PARENS_PRESENT;
import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck.MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck.MSG_KEY_ANNOTATION_TRAILING_COMMA_PRESENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AnnotationUseStyleTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "annotation" + File.separator + filename);
    }

    /* Additional test for jacoco, since valueOf()
     * is generated by javac and jacoco reports that
     * valueOf() is uncovered.
     */
    @Test
    public void testElementStyleValueOf() {
        AnnotationUseStyleCheck.ElementStyle option =
            AnnotationUseStyleCheck.ElementStyle.valueOf("COMPACT");
        assertEquals(AnnotationUseStyleCheck.ElementStyle.COMPACT, option);
    }

    /* Additional test for jacoco, since valueOf()
     * is generated by javac and jacoco reports that
     * valueOf() is uncovered.
     */
    @Test
    public void testTrailingArrayCommaValueOf() {
        AnnotationUseStyleCheck.TrailingArrayComma option =
            AnnotationUseStyleCheck.TrailingArrayComma.valueOf("ALWAYS");
        assertEquals(AnnotationUseStyleCheck.TrailingArrayComma.ALWAYS, option);
    }

    /* Additional test for jacoco, since valueOf()
     * is generated by javac and jacoco reports that
     * valueOf() is uncovered.
     */
    @Test
    public void testClosingParensValueOf() {
        AnnotationUseStyleCheck.ClosingParens option =
            AnnotationUseStyleCheck.ClosingParens.valueOf("ALWAYS");
        assertEquals(AnnotationUseStyleCheck.ClosingParens.ALWAYS, option);
    }

    /**
     * Test that annotation parens are always present.
     */
    @Test
    public void testParensAlways() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ALWAYS");
        checkConfig.addAttribute("elementStyle", "ignore");
        checkConfig.addAttribute("trailingArrayComma", "ignore");
        final String[] expected = {
            "3: " + getCheckMessage(MSG_KEY_ANNOTATION_PARENS_MISSING),
            "18: " + getCheckMessage(MSG_KEY_ANNOTATION_PARENS_MISSING),
            "23: " + getCheckMessage(MSG_KEY_ANNOTATION_PARENS_MISSING),
        };

        verify(checkConfig, getPath("DifferentUseStyles.java"), expected);
    }

    /**
     * Test that annotation parens are never present.
     */
    @Test
    public void testParensNever() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "NEVER");
        checkConfig.addAttribute("elementStyle", "ignore");
        checkConfig.addAttribute("trailingArrayComma", "ignore");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY_ANNOTATION_PARENS_PRESENT),
            "30: " + getCheckMessage(MSG_KEY_ANNOTATION_PARENS_PRESENT),
            "33: " + getCheckMessage(MSG_KEY_ANNOTATION_PARENS_PRESENT),
        };

        verify(checkConfig, getPath("DifferentUseStyles.java"), expected);
    }

    @Test
    public void testStyleExpanded() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "EXPANDED");
        checkConfig.addAttribute("trailingArrayComma", "ignore");
        final String[] expected = {
            "5: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "EXPANDED"),
            "12: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "EXPANDED"),
            "20: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "EXPANDED"),
            "26: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "EXPANDED"),
            "39: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "EXPANDED"),
            "41: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "EXPANDED"),
            "58: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "EXPANDED"),
        };

        verify(checkConfig, getPath("DifferentUseStyles.java"), expected);
    }

    @Test
    public void testStyleCompact() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "COMPACT");
        checkConfig.addAttribute("trailingArrayComma", "ignore");
        final String[] expected = {
            "43: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "COMPACT"),
            "47: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "COMPACT"),
        };

        verify(checkConfig, getPath("DifferentUseStyles.java"), expected);
    }

    @Test
    public void testStyleCompactNoArray() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "COMPACT_NO_ARRAY");
        checkConfig.addAttribute("trailingArrayComma", "ignore");
        final String[] expected = {
            "5: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "COMPACT_NO_ARRAY"),
            "20: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "COMPACT_NO_ARRAY"),
            "41: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "COMPACT_NO_ARRAY"),
            "43: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "COMPACT_NO_ARRAY"),
            "47: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "COMPACT_NO_ARRAY"),
        };

        verify(checkConfig, getPath("DifferentUseStyles.java"), expected);
    }

    @Test
    public void testCommaAlwaysViolations() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "ignore");
        checkConfig.addAttribute("trailingArrayComma", "ALWAYS");
        final String[] expected = {
            "3:20: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING),
            "6:30: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING),
            "10:40: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING),
            "13:44: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING),
            "16:54: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING),
            "24:37: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING),
            "24:65: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING),
            "26:21: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING),
            "26:30: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING),
            "29:39: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING),
            "29:49: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING),
            "32:21: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING),
            "32:56: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING),
        };

        verify(checkConfig, getPath("AnnotationUseNoTrailingComma.java"), expected);
    }

    @Test
    public void testCommaAlwaysNoViolations() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "ignore");
        checkConfig.addAttribute("trailingArrayComma", "ALWAYS");
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("AnnotationUseWithTrailingComma.java"), expected);
    }

    @Test
    public void testCommaNeverViolations() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "ignore");
        checkConfig.addAttribute("trailingArrayComma", "NEVER");
        final String[] expected = {
            "9:32: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_PRESENT),
            "13:42: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_PRESENT),
            "16:46: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_PRESENT),
            "19:56: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_PRESENT),
            "27:38: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_PRESENT),
            "27:67: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_PRESENT),
            "33:39: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_PRESENT),
            "33:50: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_PRESENT),
        };

        verify(checkConfig, getPath("AnnotationUseWithTrailingComma.java"), expected);
    }

    @Test
    public void testCommaNeverNoViolations() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "ignore");
        checkConfig.addAttribute("trailingArrayComma", "NEVER");
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("AnnotationUseNoTrailingComma.java"), expected);
    }

    @Test
    public void testEverythingMixed() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "ignore");
        checkConfig.addAttribute("trailingArrayComma", "ignore");
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("DifferentUseStyles.java"), expected);
    }

    @Test
    public void testAnnotationsWithoutDefaultValues() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "NEVER");
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("AnnotationsUseStyleParams.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        AnnotationUseStyleCheck constantNameCheckObj = new AnnotationUseStyleCheck();
        int[] actual = constantNameCheckObj.getAcceptableTokens();
        int[] expected = {TokenTypes.ANNOTATION };
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetOption() throws Exception {
        AnnotationUseStyleCheck check = new AnnotationUseStyleCheck();
        try {
            check.setElementStyle("SHOULD_PRODUCE_ERROR");
        }
        catch (ConversionException ex) {
            assertTrue(ex.getMessage().startsWith("unable to parse"));
            return;
        }

        Assert.fail();
    }

    @Test
    public void testStyleNotInList() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "COMPACT_NO_ARRAY");
        checkConfig.addAttribute("trailingArrayComma", "ignore");
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputAnnotationUseStyleCheckTest.java"), expected);

    }

}
