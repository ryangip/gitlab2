package com.commit451.gitlab.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.commit451.gitlab.R;
import com.commit451.gitlab.data.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Settings
 */
public class SettingsActivity extends BaseActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        return intent;
    }

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.text_launch_activity)
    TextView mTextLaunchActivity;

    @OnClick(R.id.root_launch_activity)
    void onLaunchActivityClicked() {
        new MaterialDialog.Builder(this)
                .title(R.string.setting_starting_view)
                .items(R.array.setting_starting_view_choices)
                .itemsCallbackSingleChoice(getSelectedIndex(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Prefs.setStartingView(SettingsActivity.this, which);
                        bindPrefs();
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                        return true;
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        mToolbar.setTitle(R.string.settings);
        mToolbar.setNavigationIcon(R.drawable.ic_back_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bindPrefs();
    }

    private void bindPrefs() {
        setStartingViewSelection();
    }

    private void setStartingViewSelection() {
        int startinView = Prefs.getStartingView(this);
        switch (startinView) {
            case Prefs.STARTING_VIEW_PROJECTS:
                mTextLaunchActivity.setText(R.string.setting_starting_view_projects);
                break;
            case Prefs.STARTING_VIEW_GROUPS:
                mTextLaunchActivity.setText(R.string.setting_starting_view_groups);
                break;
            case Prefs.STARTING_VIEW_ACTIVITY:
                mTextLaunchActivity.setText(R.string.setting_starting_view_activity);
                break;
            case Prefs.STARTING_VIEW_TODOS:
                mTextLaunchActivity.setText(R.string.setting_starting_view_todos);
                break;

        }
    }

    private int getSelectedIndex() {
        return Prefs.getStartingView(this);
    }
}
