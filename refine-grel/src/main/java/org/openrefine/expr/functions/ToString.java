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

package org.openrefine.expr.functions;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UnknownFormatConversionException;

import org.openrefine.expr.EvalError;
import org.openrefine.grel.ControlFunctionRegistry;
import org.openrefine.grel.EvalErrorMessage;
import org.openrefine.grel.FunctionDescription;
import org.openrefine.grel.PureFunction;
import org.openrefine.util.StringUtils;

public class ToString extends PureFunction {

    private static final long serialVersionUID = 3466521976054626379L;

    @Override
    public Object call(Object[] args) {
        if (args.length >= 1) {
            Object o1 = args[0];
            if (args.length == 2 && args[1] instanceof String) {
                Object o2 = args[1];
                if (o1 instanceof OffsetDateTime) {
                    OffsetDateTime odt = (OffsetDateTime) o1;
                    return odt.format(DateTimeFormatter.ofPattern((String) o2));
                } else if (o1 instanceof Number) {
                    try {
                        return String.format((String) o2, o1);
                    } catch (UnknownFormatConversionException e) {
                        return new EvalError(EvalErrorMessage.unknown_format_conversion(e.getMessage()));
                    }
                }
            } else if (args.length == 1) {
                if (o1 instanceof String) {
                    return (String) o1;
                } else {
                    return StringUtils.toString(o1);
                }
            }
        }
        // second argument containing a Date or Number format string");
        return new EvalError(EvalErrorMessage.fun_to_string(ControlFunctionRegistry.getFunctionName(this)));
    }

    @Override
    public String getDescription() {
        return FunctionDescription.fun_to_string();
    }

    @Override
    public String getParams() {
        return "object o, string format (optional)";
    }

    @Override
    public String getReturns() {
        return "string";
    }
}
