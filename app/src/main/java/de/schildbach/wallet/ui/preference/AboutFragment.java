/*
 * Copyright 2014 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.schildbach.wallet.ui.preference;

import org.bitcoinj.core.VersionMessage;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import de.schildbach.wallet.BuildConfig;
import de.schildbach.wallet.Constants;
import de.schildbach.wallet.WalletApplication;
import de.schildbach.wallet.R;

/**
 * @author Andreas Schildbach
 */
public final class AboutFragment extends PreferenceFragment
{
	private Activity activity;
	private WalletApplication application;
	private PackageManager packageManager;

	private static final String KEY_ABOUT_VERSION = "about_version";
	private static final String KEY_ABOUT_MARKET_APP = "about_market_app";
    private static final String KEY_ABOUT_CREDITS_BITCOIN_WALLET = "about_credits_bitcoin_wallet";
	private static final String KEY_ABOUT_CREDITS_BITCOINJ = "about_credits_bitcoinj";

	@Override
	public void onAttach(final Activity activity)
	{
		super.onAttach(activity);

		this.activity = activity;
		this.application = (WalletApplication) activity.getApplication();
		this.packageManager = activity.getPackageManager();
	}

	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preference_about);

		findPreference(KEY_ABOUT_VERSION).setSummary("v" + application.packageInfo().versionName
                + " / " + getString(R.string.about_copyright));

		Intent marketIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(String.format(Constants.MARKET_APP_URL, BuildConfig.APPLICATION_ID)));
		if (packageManager.resolveActivity(marketIntent, 0) == null)
			marketIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(String.format(Constants.WEBMARKET_APP_URL, BuildConfig.APPLICATION_ID)));
		findPreference(KEY_ABOUT_MARKET_APP).setIntent(marketIntent);

        findPreference(KEY_ABOUT_CREDITS_BITCOIN_WALLET).setTitle(
                getString(R.string.about_credits_bitcoin_wallet_title, Constants.BITCOIN_WALLET_APP_CODE_BASE_VERSION));
		findPreference(KEY_ABOUT_CREDITS_BITCOINJ).setTitle(
                getString(R.string.about_credits_bitcoinj_title, VersionMessage.BITCOINJ_VERSION));
	}
}
