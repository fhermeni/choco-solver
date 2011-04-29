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

package solver.search.strategy.selectors;

import java.io.Serializable;

/**
 * A value iterator specfies which value should be chosen to constrained the selected variable.
 * The value chosen must belong to the domain of the selected variable.
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 28 sept. 2010
 */
public interface ValueIterator<V> extends Serializable {

    /**
     * Checks wether there is a remaining value, not yet used, in the domain
     * @return true if there is at least one remaining value to check, false otherwise
     */
    public boolean hasNext();

    /**
     * Selects and returns the value to constrained chosen variable with.
     * The chosen value must belong to the domain of <code>variable</code>.
     * @return the value, based on the domain of variable
     */
    public int selectValue();

}
