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

package solver.variables.setDataStructures;

import choco.kernel.memory.IEnvironment;
import choco.kernel.memory.IStateObject;
import choco.kernel.memory.copy.EnvironmentCopying;
import choco.kernel.memory.copy.RcObject;
import choco.kernel.memory.copy.RecomputableElement;
import choco.kernel.memory.structure.Operation;

import java.util.LinkedList;

/**
 * Backtrable set
 * @author Jean-Guillaume Fages
 * @since Nov 2012
 */
public class Set_Copy extends RcObject implements ISet {

	// trailing
    private final EnvironmentCopying environment;
	private int timestamp;
	// set (decorator design pattern)
	private ISet set;

    public Set_Copy(EnvironmentCopying environment, ISet set) {
        super(environment,null);
        this.environment = environment;
		this.set = set;
		environment.add(this);
		timestamp = environment.getWorldIndex();
    }

	public Object deepCopy() {
		int[] vals = new int[set.getSize()];
		int k = 0;
		for(int i=set.getFirstElement(); i>=0; i=set.getNextElement()){
			vals[k++] = i;
		}
		return vals;
	}

	protected void _set(final Object y, final int wstamp) {
		int[] vals = (int[]) y;
		set.clear();
		for(int i:vals){
			set.add(i);
		}
		timestamp = wstamp;
	}

    @Override
    public boolean add(int element) {
		if(set.add(element)){
			timestamp = environment.getWorldIndex();
			return true;
		}return false;
    }

    @Override
    public boolean remove(int element) {
		if(set.remove(element)){
			timestamp = environment.getWorldIndex();
			return true;
		}return false;
    }

	@Override
	public boolean contain(int element) {
		return set.contain(element);
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public int getSize() {
		return set.getSize();
	}

	@Override
    public void clear() {
		if(!set.isEmpty()){
			timestamp = environment.getWorldIndex();
		}
		set.clear();
    }

	@Override
	public int getFirstElement() {
		return set.getFirstElement();
	}

	@Override
	public int getNextElement() {
		return set.getNextElement();
	}

	@Override
	public int getType() {
		return RecomputableElement.OBJECT;
	}

	@Override
	public int getTimeStamp() {
		return timestamp;
	}
}