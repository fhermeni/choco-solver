/**
 * Copyright (c) 2016, Ecole des Mines de Nantes
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by the <organization>.
 * 4. Neither the name of the <organization> nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY <COPYRIGHT HOLDER> ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.chocosolver.solver.search.limits;

import org.chocosolver.util.criteria.Criterion;
import org.chocosolver.util.criteria.LongCriterion;



/**
 * An interface to define count smth during search process
 *
 * @author Charles Prud'homme
 * @see NodeCounter
 * @see BacktrackCounter
 * @see FailCounter
 * @see SolutionCounter
 * @since 15 juil. 2010
 */
public interface ICounter extends Criterion, LongCriterion{

    void init();

    void update();

    long getLimitValue();

    void overrideLimit(long newLimit);

    long currentValue();

    enum Impl implements ICounter{
        None {
            @Override
            public boolean isMet(long value) {
                return false;
            }

            @Override
            public boolean isMet() {
                return false;
            }

            @Override
            public void init() {

            }

            @Override
            public void update() {

            }

            @Override
            public long getLimitValue() {
                return 0;
            }

            @Override
            public void overrideLimit(long newLimit) {}

            @Override
            public long currentValue() {
                return 0;
            }
        }
    }

}
