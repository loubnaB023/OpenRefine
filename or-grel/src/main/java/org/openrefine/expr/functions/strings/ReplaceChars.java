/*

Copyright 2010, Google Inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

    * Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above
copyright notice, this list of conditions and the following disclaimer
in the documentation and/or other materials provided with the
distribution.
    * Neither the name of Google Inc. nor the names of its
contributors may be used to endorse or promote products derived from
this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,           
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY           
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.openrefine.expr.functions.strings;

import org.apache.commons.lang3.StringUtils;
import org.openrefine.grel.ControlFunctionRegistry;
import org.openrefine.grel.PureFunction;

import org.openrefine.expr.EvalError;

public class ReplaceChars extends PureFunction {

    private static final long serialVersionUID = -7410222546080297115L;

    @Override
    public Object call(Object[] args) {
        if (args.length == 3) {
            Object o1 = args[0];
            Object o2 = args[1];
            Object o3 = args[2];
            if (o1 != null && o2 != null && o3 != null && o2 instanceof String && o3 instanceof String) {
                String str = (o1 instanceof String) ? (String) o1 : o1.toString();
                return StringUtils.replaceChars(str, (String) o2, (String) o3);
            }
        }
        return new EvalError(ControlFunctionRegistry.getFunctionName(this) + " expects 3 strings");
    }

    
    @Override
    public String getDescription() {
        return "Returns the string obtained by replacing a character in s, identified by find, with the corresponding character identified in replace. You cannot use this to replace a single character with more than one character.";
    }
    
    @Override
    public String getParams() {
        return "string s, string find, string replace";
    }
    
    @Override
    public String getReturns() {
        return "string";
    }
}