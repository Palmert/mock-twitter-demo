package com.thompalmer.mocktwitterdemo.data.api;

import com.thompalmer.mocktwitterdemo.data.api.model.request.LoginRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.mock.BehaviorDelegate;

public final class LocalTwitterServer implements TwitterService {
    public static final int ERROR_UNAUTHORIZED = 401;
    public static final String MESSAGE_INVALID_PASSWORD = "Invalid username/password";
    public static final String MESSAGE_USER_DOES_NOT_EXIST = "Username does not exist";
    private final BehaviorDelegate<TwitterService> delegate;
    private final Map<String, String> userAccounts;
    private UserSession userSession;

    public LocalTwitterServer(BehaviorDelegate<TwitterService> delegate) {
        this.delegate = delegate;
        userAccounts = new HashMap<>();

        addUserAccount("thomapalmer@gmail.com", "password1");
    }

//        @Override
//        public Call<List<Contributor>> contributors(String owner, String repo) {
//            List<Contributor> response = Collections.emptyList();
//            Map<String, List<Contributor>> repoContributors = userAccounts.get(owner);
//            if (repoContributors != null) {
//                List<Contributor> contributors = repoContributors.get(repo);
//                if (contributors != null) {
//                    response = contributors;
//                }
//            }
//            return delegate.returningResponse(response).contributors(owner, repo);
//        }

    void addUserAccount(String email, String password) {
        userAccounts.put(email, password);
    }

    @Override
    public Observable<LoginResponse> login(@Body LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        LoginResponse response;

        if (userAccounts.containsKey(email) && userAccounts.get(email).equals(password)) {
            this.userSession = new UserSession(email, new Random().nextLong());
            response = LoginResponse.success(userSession.email, userSession.authToken);
        } else {
            response = LoginResponse.failure(ERROR_UNAUTHORIZED,
                    userAccounts.containsKey(email) ? MESSAGE_INVALID_PASSWORD : MESSAGE_USER_DOES_NOT_EXIST);
        }
        return delegate.returningResponse(response).login(loginRequest);
    }
}