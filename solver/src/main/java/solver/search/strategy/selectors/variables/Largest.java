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

package solver.search.strategy.selectors.variables;

import choco.kernel.memory.IEnvironment;
import choco.kernel.memory.IStateInt;
import solver.search.strategy.selectors.VariableSelector;
import solver.variables.IntVar;

/**
 * <b>Largest</b> variable selector.
 * It chooses the variable with the largest value in its domain (instantiated variables are ignored).
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 2 juil. 2010
 */
public class Largest implements VariableSelector<IntVar> {

    /* list of variables */
    IntVar[] variables;

    /* index of the smallest domain variable */
    IStateInt large_idx;

    public Largest(IntVar[] variables, IEnvironment environment) {
        this.variables = variables.clone();
        large_idx = environment.makeInt(0);

    }

    @Override
    public boolean hasNext() {
        int idx = 0;
        for(; idx < variables.length && variables[idx].getDomainSize() == 1; idx ++){}
        return idx < variables.length;
    }

    @Override
    public void advance() {
        int large_idx = 0;
        int large_value = Integer.MIN_VALUE;
        for(int idx = 0; idx < variables.length; idx ++){
            int dsize = variables[idx].getDomainSize();
            int upper = variables[idx].getUB();
            if(dsize > 1 && upper > large_value){
               large_value = upper;
               large_idx = idx;
            }
        }
        this.large_idx.set(large_idx);
    }

    @Override
    public IntVar getVariable() {
        return variables[large_idx.get()];
    }
}