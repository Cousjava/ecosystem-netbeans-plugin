/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */
// Portions Copyright [2017] [Payara Foundation and/or its affiliates]

package org.netbeans.modules.payara.common.ui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// This is copy-paste from Payara cloud module. It should be moved
// to some common place later.
// Spource: org.netbeans.modules.glassfish.cloud.wizards.PayaraWizardComponent
/**
 * Event listener to validate component field on the fly.
 * <p/>
 * @author Tomas Kraus
 */
abstract class ComponentFieldListener implements DocumentListener {

    ////////////////////////////////////////////////////////////////////////////
    // Abstract methods                                                       //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Process received notification from all notification types.
     */
    abstract void processEvent();

    ////////////////////////////////////////////////////////////////////////////
    // Implemented Interface Methods                                          //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Gives notification that there was an insert into component field.
     * <p/>
     * @param event Change event object.
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        processEvent();
    }

    /**
     * Gives notification that a portion of component field has been removed.
     * <p/>
     * @param event Change event object.
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        processEvent();
    }

    /**
     * Gives notification that an attribute or set of attributes changed.
     * <p/>
     * @param event Change event object.
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        processEvent();
    }

}
