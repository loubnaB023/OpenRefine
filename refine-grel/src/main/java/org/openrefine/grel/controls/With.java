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

package org.openrefine.grel.controls;

import java.util.Properties;

import org.openrefine.grel.Control;
import org.openrefine.grel.ControlDescription;
import org.openrefine.grel.ControlEvalError;
import org.openrefine.grel.ControlFunctionRegistry;
import org.openrefine.grel.ast.GrelExpr;
import org.openrefine.grel.ast.VariableExpr;

public class With implements Control {

    @Override
    public String checkArguments(GrelExpr[] args) {
        if (args.length != 3) {
            return ControlEvalError.expects_three_args(ControlFunctionRegistry.getControlName(this));
        } else if (!(args[1] instanceof VariableExpr)) {
            return ControlEvalError.expects_second_arg_var_name(ControlFunctionRegistry.getControlName(this));
        }
        return null;
    }

    @Override
    public Object call(Properties bindings, GrelExpr[] args) {
        Object o = args[0].evaluate(bindings);
        String name = ((VariableExpr) args[1]).getName();

        Object oldValue = bindings.get(name);
        try {
            if (o != null) {
                bindings.put(name, o);
            } else {
                bindings.remove(name);
            }

            return args[2].evaluate(bindings);
        } finally {
            /*
             * Restore the old value bound to the variable, if any.
             */
            if (oldValue != null) {
                bindings.put(name, oldValue);
            } else {
                bindings.remove(name);
            }
        }
    }

    @Override
    public String getDescription() {
        return ControlDescription.with_desc();
    }

    @Override
    public String getParams() {
        return "expression o, variable v, expression e";
    }

    @Override
    public String getReturns() {
        return "Depends on actual arguments";
    }
}
