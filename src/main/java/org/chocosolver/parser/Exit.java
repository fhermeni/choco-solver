/**
 * This file is part of choco-parsers, https://github.com/chocoteam/choco-parsers
 *
 * Copyright (c) 2017-01-06T09:54:20Z, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.parser;

/**
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 4 oct. 2010
 */
public class Exit {

    public static void log() {
        System.err.println("Expression  unexpected call");
//        new Exception().printStackTrace();
        throw new UnsupportedOperationException();
    }

    public static void log(String msg) {
        System.err.println(msg);
//        new Exception().printStackTrace();
        throw new UnsupportedOperationException();
    }
}
