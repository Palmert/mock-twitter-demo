package com.thompalmer.mocktwitterdemo.presentation.login;

import android.text.TextUtils;

import com.thompalmer.mocktwitterdemo.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter {
    private static final Map<String, String> DUMMY_CREDENTIALS;

    static {
        Map<String, String> credentialsMap = new HashMap<>();
        credentialsMap.put("thomapalmer@gmail.com", "password");
        credentialsMap.put("thomapalmer@gmail.com", "password");
        credentialsMap.put("thomapalmer@gmail.com", "password");
        credentialsMap.put("thomapalmer@gmail.com", "password");
        credentialsMap.put("thomapalmer@gmail.com", "password");
        DUMMY_CREDENTIALS = Collections.unmodifiableMap(credentialsMap);
    }

    public int updateEmailMessageId(String email) {
        int emailMessageId = 0;

        if (TextUtils.isEmpty(email)) {
            emailMessageId = R.string.error_field_required;
        } else if (!isEmailValid(email)) {
            emailMessageId = R.string.error_invalid_email;
        }

        return emailMessageId;
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && email.contains("@");
    }

    public int updatePasswordMessageId(String password) {
        int passwordMessageId = 0;

        if (TextUtils.isEmpty(password)) {
            passwordMessageId = R.string.error_field_required;
        } else if (!isPasswordValid(password)) {
            passwordMessageId = R.string.error_invalid_password;
        }

        return passwordMessageId;
    }

    public boolean isPasswordValid(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 8;
    }

    public boolean shouldValidateCredentials(String email, String password) {
        return isEmailValid(email) && isPasswordValid(password);
    }

    public Observable<Boolean> performLoginAttempt(String email, String password) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                emitter.onNext(DUMMY_CREDENTIALS.containsKey(email) && DUMMY_CREDENTIALS.get(email).equals(password));
                emitter.onComplete();
            }
        }).delay(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread());
    }
}
