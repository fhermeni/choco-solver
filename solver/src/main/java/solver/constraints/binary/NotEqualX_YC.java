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

package solver.constraints.binary;

import choco.kernel.ESat;
import solver.Solver;
import solver.constraints.IntConstraint;
import solver.constraints.propagators.Propagator;
import solver.constraints.propagators.PropagatorPriority;
import solver.constraints.propagators.binary.PropNotEqualX_YC;
import solver.constraints.propagators.binary.Prop_X_NotEqualX_YC;
import solver.constraints.propagators.binary.Prop_Y_NotEqualX_YC;
import solver.variables.IntVar;

/**
 * Created by IntelliJ IDEA.
 * User: cprudhom
 * <p/>
 * state x =/= y + c
 */
public final class NotEqualX_YC extends IntConstraint<IntVar> {

    public static final boolean _DEFAULT = true;


    IntVar x;
    IntVar y;
    int c;

    public NotEqualX_YC(IntVar x, IntVar y, int c, Solver solver) {
        this(x, y, c, solver, _DEFAULT_THRESHOLD);
    }

    @SuppressWarnings({"unchecked"})
    public NotEqualX_YC(IntVar x, IntVar y, int c, Solver solver,
                        PropagatorPriority threshold) {
        super(new IntVar[]{x, y}, solver, threshold);
        this.x = x;
        this.y = y;
        this.c = c;
        if (_DEFAULT) {
            setPropagators(new PropNotEqualX_YC(new IntVar[]{x, y}, c, solver.getEnvironment(), this));
        } else {
            Propagator<IntVar>[] propagators = new Propagator[2];
            propagators[0] = new Prop_X_NotEqualX_YC(x, y, c, solver.getEnvironment(), this);
            propagators[1] = new Prop_Y_NotEqualX_YC(x, y, c, solver.getEnvironment(), this);
            setPropagators(propagators);
        }
    }

    @Override
    public void updateActivity(Propagator<IntVar> prop) {
        if (_DEFAULT) {
            super.updateActivity(prop);
        } else {
            propagators[0].setPassive();
            propagators[1].setPassive();
            lastPropagatorActive.add(-2);
        }
    }

    @Override
    public ESat isSatisfied(int[] tuple) {
        return ESat.eval(tuple[0] != tuple[1] + c);
    }

    @Override
    public ESat isEntailed() {
        if ((x.getUB() < y.getLB() + this.c) ||
                (y.getUB() < x.getLB() - this.c))
            return ESat.TRUE;
        else if (x.instantiated()
                && y.instantiated()
                && x.getValue() == y.getValue() + this.c)
            return ESat.FALSE;
        else
            return ESat.UNDEFINED;
    }

    public String toString() {
        StringBuilder s = new StringBuilder("");
        s.append(x).append(" =/= ").append(y).append(" + ").append(c);
        return s.toString();
    }
}