/*******************************************************************************
 * Copyright (c) 2004, 2008 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.commons.repositories.auth;

import org.eclipse.equinox.security.storage.StorageException;

/**
 * Provides a user name and password.
 * 
 * @author Steffen Pingel
 * @since 2.2
 * @noextend This class is not intended to be subclassed by clients.
 */
public class UsernamePasswordCredentials extends AuthenticationCredentials {

	private final String userName;

	private final String password;

	/**
	 * @param userName
	 *            the user name, must not be null
	 * @param password
	 *            the password, must not be null
	 */
	public UsernamePasswordCredentials(String userName, String password) {
		if (userName == null) {
			throw new IllegalArgumentException();
		}
		if (password == null) {
			throw new IllegalArgumentException();
		}

		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final UsernamePasswordCredentials other = (UsernamePasswordCredentials) obj;
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (userName == null) {
			if (other.userName != null) {
				return false;
			}
		} else if (!userName.equals(other.userName)) {
			return false;
		}
		return true;
	}

	public static UsernamePasswordCredentials create(ICredentialsStore store, String prefix) throws StorageException {
		String userName = store.get(prefix + ".user", ""); //$NON-NLS-1$ //$NON-NLS-2$
		String password = store.get(prefix + ".password", ""); //$NON-NLS-1$ //$NON-NLS-2$
		return new UsernamePasswordCredentials(userName, password);
	}

	@Override
	public void save(ICredentialsStore store, String prefix) throws StorageException {
		store.put(prefix + ".user", userName, false); //$NON-NLS-1$ 
		store.put(prefix + ".password", password, true); //$NON-NLS-1$ 
	}

}