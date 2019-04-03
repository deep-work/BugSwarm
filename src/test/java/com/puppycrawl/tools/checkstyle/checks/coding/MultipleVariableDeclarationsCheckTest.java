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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.MultipleVariableDeclarationsCheck.MSG_MULTIPLE;
import static com.puppycrawl.tools.checkstyle.checks.coding.MultipleVariableDeclarationsCheck.MSG_MULTIPLE_COMMA;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class MultipleVariableDeclarationsCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testIt() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(MultipleVariableDeclarationsCheck.class);

        final String[] expected = {
            "4:5: " + getCheckMessage(MSG_MULTIPLE_COMMA),
            "5:5: " + getCheckMessage(MSG_MULTIPLE),
            "8:9: " + getCheckMessage(MSG_MULTIPLE_COMMA),
            "9:9: " + getCheckMessage(MSG_MULTIPLE),
            "13:5: " + getCheckMessage(MSG_MULTIPLE),
            "16:5: " + getCheckMessage(MSG_MULTIPLE),
        };

        verify(checkConfig,
               getPath("InputMultipleVariableDeclarations.java"),
               expected);
    }

    @Test
    public void testTokensNotNull() {
        MultipleVariableDeclarationsCheck check = new MultipleVariableDeclarationsCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}
