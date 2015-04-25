package io.github.andrew_su.passwordgenerator;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.github.andrew_su.passwordgenerator.datasets.PasswordTable;
import io.github.andrew_su.passwordgenerator.providers.PasswordContentProvider;

public class MainActivity extends ActionBarActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Void> {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int LOADER_ID = 100;
    private static final String sLowerCase = "abcdefghijklmnopqrstuvwxyz";
    private static final String sUppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String sNumbers = "0123456789";
    private static final String sSymbols = "-=_+";
    @InjectView(R.id.main_layout)
    View mMainLayout;
    @InjectView(R.id.account_name)
    EditText mAccountNameView;
    @InjectView(R.id.username)
    EditText mUsernameView;
    @InjectView(R.id.password)
    EditText mPasswordView;
    @InjectView(R.id.save)
    Button mSaveButton;
    @InjectView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @InjectView(R.id.use_lowercase)
    CheckBox mUseLowercase;
    @InjectView(R.id.use_uppercase)
    CheckBox mUseUppercase;
    @InjectView(R.id.use_numbers)
    CheckBox mUseNumbers;
    @InjectView(R.id.use_symbols)
    CheckBox mUseSymbols;
    private int mLength = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.save, R.id.generate})
    @Override
    public void onClick(final View v) {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(mMainLayout.getWindowToken(), 0);
        switch (v.getId()) {
            case R.id.save: {
                String accountName = mAccountNameView.getText().toString().trim().toLowerCase();
                String username = mUsernameView.getText().toString().trim();
                String password = mPasswordView.getText().toString().trim();
                if (TextUtils.isEmpty(accountName) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "All fields must be filled to save", Toast.LENGTH_SHORT).show();
                    return;
                }

                v.setEnabled(false);
                Bundle bundle = new Bundle();
                bundle.putString(LoaderKeys.ACCOUNT_NAME, accountName);
                bundle.putString(LoaderKeys.USERNAME, username);
                bundle.putString(LoaderKeys.PASSWORD, password);

                getSupportLoaderManager().restartLoader(LOADER_ID, bundle, this);
                break;
            }
            case R.id.generate: {
                StringBuilder password = new StringBuilder();
                List<String> availableChars = new ArrayList<>();
                if (mUseLowercase.isChecked()) availableChars.add(sLowerCase);
                if (mUseUppercase.isChecked()) availableChars.add(sUppercase);
                if (mUseNumbers.isChecked()) availableChars.add(sNumbers);
                if (mUseSymbols.isChecked()) availableChars.add(sSymbols);

                if (availableChars.isEmpty()) {
                    Toast.makeText(this, "At least one option for password generation must be checked", Toast.LENGTH_SHORT).show();
                    return;
                }

                Random rand = new Random();
                while (password.length() < mLength) {
                    int choice = rand.nextInt(availableChars.size());
                    String chars = availableChars.get(choice);
                    password.append(chars.charAt(rand.nextInt(chars.length())));
                }

                mPasswordView.setText(password);

                break;
            }
        }
    }

    @Override
    public Loader<Void> onCreateLoader(final int id, final Bundle args) {
        final Context applicationContext = getApplicationContext();
        return new AsyncTaskLoader<Void>(this) {
            @Override
            public Void loadInBackground() {
                String accountName = args.getString(LoaderKeys.ACCOUNT_NAME);
                String username = args.getString(LoaderKeys.USERNAME);
                String password = args.getString(LoaderKeys.PASSWORD);

                ArrayList<ContentProviderOperation> operations = new ArrayList<>();
                operations.add(PasswordTable.getOperation(accountName, username, password));
                try {
                    applicationContext.getContentResolver().applyBatch(PasswordContentProvider.AUTHORITY, operations);
                } catch (RemoteException | OperationApplicationException e) {
                    Log.w(TAG, "Failed to insert into database");
                }
                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(final Loader<Void> loader, final Void data) {
        mSaveButton.setEnabled(true);
        Toast.makeText(this, "Account added to database", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(final Loader<Void> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_passwords: {
                PasswordActivity.startInstance(this);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private final class LoaderKeys {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
    }
}
