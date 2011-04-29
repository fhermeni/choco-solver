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
package choco.solver.search.enumerations.values;

public abstract class ValueIterator<A> {
    abstract int length();

    abstract A get(int index);

    ValueIterator<A> reverse() {
        return new Reverse<A>(this);
    }

    ValueIterator<A> concat() {
        return new Concat<A>((ValueIterator<ValueIterator<A>>) this);
    }

    ValueIterator<A> zip() {
        return new Zip<A>((ValueIterator<ValueIterator<A>>) this);
    }

    ValueIterator<ValueIterator<A>> unconcat(int n) {
        return new UnConcat<A>(this, n);
    }

    ValueIterator<ValueIterator<A>> unconcat() {
        return unconcat(2);
    }

    ValueIterator<ValueIterator<A>> unzip(int n) {
        return new UnZip<A>(this, n);
    }

    ValueIterator<ValueIterator<A>> unzip() {
        return unzip(2);
    }

    ValueIterator<ValueIterator<A>> splitAt(int n) {
        return new SplitAt<A>(this, n);
    }

    ValueIterator<ValueIterator<A>> split() {
        return splitAt(0);
    }

    ValueIterator<ValueIterator<A>> mapReverse() {
        return new MapReverse<A>((ValueIterator<ValueIterator<A>>) this);
    }

    ValueIterator<ValueIterator<A>> applyReverseAt(int n) {
        return new ApplyReverseAt<A>((ValueIterator<ValueIterator<A>>) this, n);
    }

    public void enumerate() {
        System.out.println(this);
        for (int i = 0; i < length(); i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println("\n");
    }
}
