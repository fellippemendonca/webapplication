/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010-2011 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package javax.ws.rs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Binds the value(s) of a HTTP query parameter to a resource method parameter, 
 * resource class field, or resource class bean property.
 * Values are URL decoded unless this is disabled using the {@link Encoded}
 * annotation. A default value can be specified using the {@link DefaultValue}
 * annotation.
 * 
 * The type <code>T</code> of the annotated parameter, field or property must 
 * either:
 * <ol>
 * <li>Be a primitive type</li>
 * <li>Have a constructor that accepts a single <code>String</code> argument</li>
 * <li>Have a static method named <code>valueOf</code> or <code>fromString</code>
 * that accepts a single 
 * <code>String</code> argument (see, for example, {@link Integer#valueOf(String)})</li>
 * <li>Be <code>List&lt;T&gt;</code>, <code>Set&lt;T&gt;</code> or 
 * <code>SortedSet&lt;T&gt;</code>, where <code>T</code> satisfies 2 or 3 above.
 * The resulting collection is read-only.</li>
 * </ol>
 * 
 * <p>If the type is not one of those listed in 4 above then the first value 
 * (lexically) of the parameter is used.</p>
 *
 * <p>Because injection occurs at object creation time, use of this annotation 
 * on resource class fields and bean properties is only supported for the 
 * default per-request resource class lifecycle. Resource classes using 
 * other lifecycles should only use this annotation on resource method
 * parameters.</p>
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see DefaultValue
 * @see Encoded
 * @see javax.ws.rs.core.UriInfo#getQueryParameters
 * @since 1.0
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryParam {

    /**
     * Defines the name of the HTTP query parameter whose value will be used
     * to initialize the value of the annotated method argument, class field or
     * bean property. The name is specified in decoded form, any percent encoded
     * literals within the value will not be decoded and will instead be 
     * treated as literal text. E.g. if the parameter name is "a b" then the 
     * value of the annotation is "a b", <i>not</i> "a+b" or "a%20b".
     *
     * @return  specified name of the HTTP query parameter.
     */
    String value();
}