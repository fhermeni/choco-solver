/**
 *  Copyright (c) 1999-2011, Ecole des Mines de Nantes
 *  All rights reserved.
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in the
 *        documentation and/or other materials provided with the distribution.
 *      * Neither the name of the Ecole des Mines de Nantes nor the
 *        names of its contributors may be used to endorse or promote products
 *        derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND ANY
 *  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 *  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.chocosolver.parser.flatzinc.ast.constraints;

import org.chocosolver.parser.flatzinc.FznSettings;
import org.chocosolver.parser.flatzinc.ast.Datas;
import org.chocosolver.parser.flatzinc.ast.expression.EAnnotation;
import org.chocosolver.parser.flatzinc.ast.expression.Expression;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.*;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.util.ESat;

import java.util.List;

import static org.chocosolver.solver.constraints.PropagatorPriority.TERNARY;

/**
 * (a &#8804; b) &#8660; r
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 26/01/11
 */
public class BoolLeReifBuilder implements IBuilder {

    @Override
    public void build(Model model, String name, List<Expression> exps, List<EAnnotation> annotations, Datas datas) {
        BoolVar a = exps.get(0).boolVarValue(model);
        BoolVar b = exps.get(1).boolVarValue(model);
        BoolVar r = exps.get(2).boolVarValue(model);
        if (((FznSettings) model.getSettings()).enableClause()) {
            model.addClausesBoolIsLeVar(a, b, r);
        } else {
            if (((FznSettings) model.getSettings()).adhocReification()) {
                new Constraint("reifBool(a<b,r)", new Propagator<BoolVar>(new BoolVar[]{a, b, r}, TERNARY, false) {
                    @Override
                    public void propagate(int evtmask) throws ContradictionException {
                        if (vars[0].contains(0) || vars[1].contains(1)) {
                            vars[2].setToTrue(this);
                        }
                        if (vars[2].getUB() == 0) {
                            vars[0].setToTrue(this);
                            vars[1].setToFalse(this);
                        }
                    }

                    @Override
                    public ESat isEntailed() {
                        throw new UnsupportedOperationException("isEntailed not implemented ");
                    }
                }).post();
            } else {
                model.arithm(a, "<=", b).reifyWith(r);
            }
        }
    }
}
