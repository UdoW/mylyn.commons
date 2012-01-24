/*******************************************************************************
 * Copyright (c) 2012 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.commons.repositories.ui.auth;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.commons.repositories.core.auth.AuthenticationRequest;
import org.eclipse.mylyn.commons.repositories.core.auth.AuthenticationType;
import org.eclipse.mylyn.commons.repositories.core.auth.CertificateCredentials;
import org.eclipse.mylyn.commons.ui.dialogs.CredentialsDialog;
import org.eclipse.mylyn.commons.ui.dialogs.CredentialsDialog.Mode;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Steffen Pingel
 */
public class CertificateCredentialsProviderUi extends AbstractCredentialsProviderUi<CertificateCredentials> {

	private CertificateCredentials credentials;

	@Override
	public CertificateCredentials getCredentials() {
		return credentials;
	}

	@Override
	public IStatus open(Shell shell, AuthenticationRequest<AuthenticationType<CertificateCredentials>> request) {
		CredentialsDialog dialog = new CredentialsDialog(shell, Mode.USER);

		CertificateCredentials oldCredentials = request.getLocation().getCredentials(request.getAuthenticationType());
		if (oldCredentials != null) {
			dialog.setKeyStoreFileName(oldCredentials.getKeyStoreFileName());
			dialog.setPassword(oldCredentials.getPassword());
		}

		// caller provided message takes precedence
		if (request.getMessage() != null) {
			dialog.setMessage(request.getMessage());
		} else {
			dialog.setMessage(getDefaultMessage(request));
		}

		int resultCode = dialog.open();
		if (resultCode == Window.OK) {
			credentials = new CertificateCredentials(dialog.getKeyStoreFileName(), dialog.getPassword(), null);
			request.getLocation().setCredentials(request.getAuthenticationType(), oldCredentials);
			return Status.OK_STATUS;
		} else {
			return Status.CANCEL_STATUS;
		}
	}

	private String getDefaultMessage(AuthenticationRequest<AuthenticationType<CertificateCredentials>> request) {
		AuthenticationType<CertificateCredentials> authType = request.getAuthenticationType();
		if (AuthenticationType.CERTIFICATE == authType) {
			return "Enter key store password";
		}
		return null;
	}

}