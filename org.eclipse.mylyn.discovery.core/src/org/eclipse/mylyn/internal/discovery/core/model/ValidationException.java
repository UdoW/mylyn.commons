/*******************************************************************************
 * Copyright (c) 2009 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.mylyn.internal.discovery.core.model;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.internal.discovery.core.DiscoveryCore;

/**
 * Indicate that a validation has occurred on the model.
 * 
 * @author David Green
 */
public class ValidationException extends CoreException {

	private static final long serialVersionUID = -7542361242327905294L;

	public ValidationException(String message) {
		super(new Status(IStatus.ERROR, DiscoveryCore.BUNDLE_ID, message));
	}
}