/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved.
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
 */
// Portions Copyright [2017] [Payara Foundation and/or its affiliates]

package org.netbeans.modules.payara.tooling.admin;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.jar.Attributes;
import org.netbeans.modules.payara.tooling.utils.ServerUtils;
import org.netbeans.modules.payara.tooling.data.PayaraServer;

/**
 * Command runner for retrieving resources from server.
 * <p>
 * <p/>
 * @author Tomas Kraus, Peter Benedikovic
 */
public class RunnerHttpListResources extends RunnerHttpTarget {

    ////////////////////////////////////////////////////////////////////////////
    // Instance attributes                                                    //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Payara administration command result containing server resources.
     * <p/>
     * Result instance life cycle is started with submitting task into
     * <code>ExecutorService</code>'s queue. method
     * <code>call()</code>
     * is responsible for correct
     * <code>TaskState</code> and receiveResult value
     * handling.
     */
    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    ResultList<String> result;

    ////////////////////////////////////////////////////////////////////////////
    // Constructors                                                           //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Constructs an instance of administration command executor using
     * HTTP interface.
     * <p/>
     * @param server  Payara server entity object.
     * @param command Payara server administration command entity.
     */
    public RunnerHttpListResources(final PayaraServer server,
            final Command command) {
        super(server, command);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Implemented Abstract Methods                                           //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Create <code>ResultList</code> object corresponding
     * to server log command execution value to be returned.
     */
    @Override
    protected ResultList<String> createResult() {
        return result = new ResultList<String>();
    }

    /**
     * Extracts result value from internal
     * <code>Manifest</code> object.
     * Value of <i>message</i> attribute in
     * <code>Manifest</code> object is
     * stored as <i>value</i> into
     * <code>ResultString</code> result object.
     * <p/>
     * @return true if result was extracted correctly. <code>null</code>
     *         <i>message</i>value is considered as failure.
     */
    @Override
    protected boolean processResponse() {
        String resourcesAttr = manifest.getMainAttributes()
                .getValue("children");
        String[] resources = resourcesAttr != null
                ? resourcesAttr.split(ServerUtils.MANIFEST_RESOURCES_SEPARATOR)
                : null;
        int resoucesCount = resources != null ? resources.length : 0;
        result.value = new ArrayList<String>(resoucesCount);
        if (resources != null) {
            for (String resource : resources) {
                Attributes resourceAttr = manifest.getAttributes(resource);
                String resourceMsg = resourceAttr.getValue("message");
                String name;
                try {
                    if (resourceMsg != null) {
                        name = URLDecoder.decode(resourceMsg, "UTF-8");
                    } else {
                        name = null;
                    }
                    if (name == null || name.length() <= 0) {
                        name = URLDecoder.decode(resource.trim(), "UTF-8");
                    }
                } catch (UnsupportedEncodingException uee) {
                    throw new CommandException(
                            CommandException.HTTP_RESP_UNS_ENC_EXCEPTION, uee);
                }
                result.value.add(name);
            }
        }
        return true;
    }
}
