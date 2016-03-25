/*
 * Copyright (c) 1999-2012, Ecole des Mines de Nantes
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Ecole des Mines de Nantes nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.chocosolver.parser.flatzinc.ast.constraints.global;

import org.chocosolver.parser.flatzinc.ast.Datas;
import org.chocosolver.parser.flatzinc.ast.constraints.IBuilder;
import org.chocosolver.parser.flatzinc.ast.expression.EAnnotation;
import org.chocosolver.parser.flatzinc.ast.expression.Expression;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;

import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;

import org.chocosolver.util.tools.StringUtils;

import java.util.List;

import static org.chocosolver.util.tools.StringUtils.randomName;

/**
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 26/07/12
 */
public class CountEqReifBuilder implements IBuilder {
	@Override
	public void build(Model model, String name, List<Expression> exps, List<EAnnotation> annotations, Datas datas) {
        IntVar[] decVars = exps.get(0).toIntVarArray(model);
        IntVar valVar = exps.get(1).intVarValue(model);
        IntVar countVar = exps.get(2).intVarValue(model);
        BoolVar b = exps.get(3).boolVarValue(model);
        Constraint cstr;
        if (valVar.isInstantiated()) {
            IntVar nbOcc = model.intVar(randomName(), 0, decVars.length, true);
            cstr = model.count(valVar.getValue(), decVars, nbOcc);
            model.arithm(nbOcc, "=", countVar).reifyWith(b);
        } else {
            IntVar value = model.intVar(randomName(), valVar.getLB(), valVar.getUB());
            cstr = model.count(value, decVars, countVar);
            model.arithm(value, "=", valVar).reifyWith(b);
        }
        cstr.post();
    }
}
