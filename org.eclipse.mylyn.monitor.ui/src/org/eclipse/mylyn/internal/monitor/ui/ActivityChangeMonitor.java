/*******************************************************************************
 * Copyright (c) 2004, 2007 Mylyn project committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.mylyn.internal.monitor.ui;

import org.eclipse.mylyn.monitor.core.InteractionEvent;
import org.eclipse.mylyn.monitor.ui.MonitorUiPlugin;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManagerListener;

/**
 * @author Mik Kersten
 */
public class ActivityChangeMonitor implements IActivityManagerListener {

	private static final String ACTIVITIES_CHANGED = "activities changed";

	public void activityManagerChanged(ActivityManagerEvent activityManagerEvent) {
		if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
			String source = activityManagerEvent.getActivityManager().toString();
			String delta = activityManagerEvent.getActivityManager().getEnabledActivityIds().toString();
			InteractionEvent interactionEvent = InteractionEvent.makePreference(source, ACTIVITIES_CHANGED + ": "
					+ delta);
			MonitorUiPlugin.getDefault().notifyInteractionObserved(interactionEvent);
		}
	}
}