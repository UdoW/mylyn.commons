/*******************************************************************************
 * Copyright (c) 2009 Tasktop Technologies and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.mylyn.internal.discovery.core.model;

/**
 * A certification for connectors.
 * 
 * @author Steffen Pingel
 */
public class Certification {

	protected String id;

	protected String name;

	protected String description;

	protected Icon icon;

	protected String url;

	public Certification() {
	}

	/**
	 * an id that uniquely identifies the category
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * the name of the category, as it is displayed in the ui.
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * A description of the category
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void validate() throws ValidationException {
		if (id == null || id.length() == 0) {
			throw new ValidationException(Messages.ConnectorCategory_must_specify_connectorCategory_id);
		}
		if (name == null || name.length() == 0) {
			throw new ValidationException(Messages.ConnectorCategory_must_specify_connectorCategory_name);
		}
		if (icon != null) {
			icon.validate();
		}
	}
}
