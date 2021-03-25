package com.eltropy.bankingsystem.service.impl;

import com.eltropy.bankingsystem.repository.CustomerRepository;
import com.eltropy.bankingsystem.repository.RoleRepository;
import com.eltropy.bankingsystem.repository.UserAccountRepository;
import com.eltropy.bankingsystem.entity.Role.ERole;
import com.eltropy.bankingsystem.entity.Customer;
//import com.eltropy.bankingsystem.service.entity.constant.enums.ResponseCode;
//import com.eltropy.bankingsystem.service.entity.dao.Customer;
import com.eltropy.bankingsystem.entity.Role;
import com.eltropy.bankingsystem.entity.UserAccount;
//import com.eltropy.bankingsystem.service.exception.BusinessLogicException;
import com.eltropy.bankingsystem.apimodel.LoginRequest;
import com.eltropy.bankingsystem.apimodel.UserAccountModel;
import com.eltropy.bankingsystem.apimodel.LoginResponse;
import com.eltropy.bankingsystem.security.jwt.JwtUtils;
import com.eltropy.bankingsystem.security.services.UserDetailsImpl;
import com.eltropy.bankingsystem.service.UserAccountService;
//import com.eltropy.bankingsystem.service.utils.MappingUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	private static final Logger LOG = LoggerFactory.getLogger(UserAccountServiceImpl.class);

	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	CustomerServiceImpl customerServiceImpl;

//	@Autowired
//	MappingUtils mappingUtils;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	CustomerRepository customerRepository;

	@Override
	@Transactional
	public void register(UserAccountModel userAccountModel) {

		UserAccount userAccount = new UserAccount(userAccountModel.getUsername(), userAccountModel.getEmail(),
				encoder.encode(userAccountModel.getPassword()));
		Set<String> strRoles = userAccountModel.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		try {
			Customer customer = new Customer();
			customer.setEmail(userAccountModel.getEmail());
			customer.setPhone(userAccountModel.getContactNo());
			customerRepository.save(customer);
		} catch (Exception exception) {
			LOG.error("Exception while saving the customer data to database : {}", userAccount);
//			throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
//					ResponseCode.SYSTEM_ERROR.getMessage());
		}
		userAccount.setRoles(roles);
		userAccountRepository.save(userAccount);

	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new LoginResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
	}

}
