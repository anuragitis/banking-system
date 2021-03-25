package com.eltropy.bankingsystem.service;

import com.eltropy.bankingsystem.apimodel.LoginRequest;
import com.eltropy.bankingsystem.apimodel.UserAccountModel;
import com.eltropy.bankingsystem.apimodel.LoginResponse;

public interface UserAccountService {

    void register(UserAccountModel account);

    LoginResponse login(LoginRequest loginRequest);

}
