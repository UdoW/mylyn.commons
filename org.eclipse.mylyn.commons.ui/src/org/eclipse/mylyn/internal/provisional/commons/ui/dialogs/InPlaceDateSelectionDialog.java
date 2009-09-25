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

package org.eclipse.mylyn.internal.provisional.commons.ui.dialogs;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.mylyn.internal.commons.ui.Messages;
import org.eclipse.mylyn.internal.provisional.commons.ui.DatePickerPanel;
import org.eclipse.mylyn.internal.provisional.commons.ui.DatePickerPanel.DateSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Ken Sueda
 * @author Mik Kersten
 * @author Rob Elves
 * @author Shawn Minto
 * @since 3.3
 */
public class InPlaceDateSelectionDialog extends AbstractInPlaceDialog {

	private static final int ID_TODAY = ID_CLEAR + 1;

	private Date selectedDate = null;

	private String title = Messages.DateSelectionDialog_Date_Selection;

	private final Calendar initialCalendar = Calendar.getInstance();

	private boolean includeTime = true;

	private int hourOfDay = 0;

	public InPlaceDateSelectionDialog(Shell parentShell, Control openControl, Calendar initialDate, String title,
			boolean includeTime, int hourOfDay) {
		super(parentShell, SWT.RIGHT, openControl);
		this.includeTime = includeTime;
		this.hourOfDay = hourOfDay;
		if (title != null) {
			this.title = title;
		}
		if (initialDate != null) {
			this.initialCalendar.setTime(initialDate.getTime());
		}
		selectedDate = initialCalendar.getTime();
	}

	@Override
	protected Control createControl(Composite parent) {
		getShell().setText(title);
		final DatePickerPanel datePanel = new DatePickerPanel(parent, SWT.NULL, initialCalendar, includeTime,
				hourOfDay, MARGIN_SIZE);
		datePanel.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				if (!event.getSelection().isEmpty()) {
					DateSelection dateSelection = (DateSelection) event.getSelection();
					selectedDate = dateSelection.getDate().getTime();
					if (dateSelection.isDefaultSelection()) {
						close();
					}
				}
			}
		});

		datePanel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

		addEventListener(new IInPlaceDialogListener() {

			public void buttonPressed(InPlaceDialogEvent event) {
				if (!event.isClosing() && event.getReturnCode() == ID_TODAY && !datePanel.isDisposed()) {
					Calendar today = Calendar.getInstance();
					ISelection selection = datePanel.getSelection();
					if (selection instanceof DateSelection && !selection.isEmpty()) {
						Calendar selectedDate = ((DateSelection) selection).getDate();
						if (selectedDate != null) {
							today.set(Calendar.HOUR_OF_DAY, selectedDate.get(Calendar.HOUR_OF_DAY));
							today.set(Calendar.MINUTE, selectedDate.get(Calendar.MINUTE));
							today.set(Calendar.SECOND, selectedDate.get(Calendar.SECOND));
							today.set(Calendar.MILLISECOND, selectedDate.get(Calendar.MILLISECOND));
						}
					}
					datePanel.setDate(today, true);
					setReturnCode(-1);
				}
			}

		});

		return datePanel;
	}

	@Override
	public boolean close() {
		return super.close();
	}

	@Override
	protected void createButtons(Composite composite) {
		createButton(composite, ID_TODAY, Messages.InPlaceDateSelectionDialog_Today, false);
		super.createButtons(composite);
	}

	public Date getDate() {
		return selectedDate;
	}
}