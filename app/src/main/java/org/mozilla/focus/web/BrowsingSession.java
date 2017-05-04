/* -*- Mode: Java; c-basic-offset: 4; tab-width: 4; indent-tabs-mode: nil; -*-
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.focus.web;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A global object keeping the state of the current browsing session.
 *
 * For now it only tracks whether a browsing session is active or not.
 */
public class BrowsingSession {
    private static BrowsingSession instance;

    public static synchronized BrowsingSession getInstance() {
        if (instance == null) {
            instance = new BrowsingSession();
        }
        return instance;
    }

    private boolean isActive;
    private @Nullable CustomTabConfig customTabConfig;

    private BrowsingSession() {}

    public void start() {
        isActive = true;
    }

    public void stop() {
        isActive = false;
        customTabConfig = null;
    }

    public boolean isActive() {
        return isActive;
    }

    public void loadCustomTabConfig(final @NonNull Intent intent) {
        if (!CustomTabConfig.isCustomTabIntent(intent)) {
            customTabConfig = null;
            return;
        }

        customTabConfig = CustomTabConfig.parseCustomTabIntent(intent);
    }

    public boolean isCustomTab() {
        return customTabConfig != null;
    }

    public @NonNull CustomTabConfig getCustomTabConfig() {
        if (!isCustomTab()) {
            throw new IllegalStateException("Can't retrieve custom tab config for normal browsing session");
        }

        return customTabConfig;
    }
}
