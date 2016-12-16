package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.UserService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class UserLoginAttempt {
    private static final int NETWORK_DELAY = 500;
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

    private final UserService userService;

    @Inject
    public UserLoginAttempt(UserService userService) {
        this.userService = userService;
    }

    public Observable<Boolean> execute(String email, String password) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                emitter.onNext(DUMMY_CREDENTIALS.containsKey(email) && DUMMY_CREDENTIALS.get(email).equals(password));
                emitter.onComplete();
            }
        }).delay(NETWORK_DELAY, TimeUnit.MILLISECONDS);
    }
}
