/**
 * This file is part of choco-parsers, https://github.com/chocoteam/choco-parsers
 *
 * Copyright (c) 2017-01-06T09:54:20Z, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.parser.flatzinc.ast.expression;

/*
* User : CPRUDHOM
* Mail : cprudhom(a)emn.fr
* Date : 8 janv. 2010
* Since : Choco 2.1.1
*
* Class for set expressions definition based on flatzinc-like objects,
* defined with two EInt.
*/
public final class ESetBounds extends ESet {

    final int low;
    final int upp;

    public ESetBounds(EInt sl, EInt su) {
        super(EType.SET_B);
        low = sl.value;
        upp = su.value;
    }

    public int[] enumVal() {
        int[] values = new int[upp - low + 1];
        for (int i = low; i <= upp; i++) {
            values[i - low] = i;
        }
        return values;
    }

    @Override
    public String toString() {
        return low + ".." + upp;
    }

    public int getLow() {
        return low;
    }

    public int getUpp() {
        return upp;
    }
}
