package com.shop.controller;

import com.shop.config.JwtTokenProvider;
import com.shop.dto.AuthResponseDTO;
import com.shop.dto.LoginDTO;
import com.shop.dto.RegisterDTO;
import com.shop.entity.Account;
import com.shop.entity.Role;
import com.shop.entity.User;
import com.shop.service.AccountService;
import com.shop.service.RoleService;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final AccountService accountService;
	private final PasswordEncoder passwordEncoder;
	private final RoleService roleService;
	private final UserService userService;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager,
						  AccountService accountService,
						  PasswordEncoder passwordEncoder,
						  RoleService roleService,
						  UserService userService) {

		this.authenticationManager = authenticationManager;
		this.accountService = accountService;
		this.passwordEncoder = passwordEncoder;
		this.roleService = roleService;
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			String token = JwtTokenProvider.generateToken(userDetails.getUsername());

			return ResponseEntity.ok(new AuthResponseDTO(token));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}
	}

	@PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto) {
        if (accountService.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.CONFLICT);
        }
		else {
			User user = new User();
			Account account = new Account();
			account.setUsername((registerDto.getUsername()));
			account.setPassword(passwordEncoder.encode((registerDto.getPassword())));
			accountService.save(account);

			user.setAccount(account);
			user.setEmail(registerDto.getEmail());
			user.setName(registerDto.getName());
			Role role = roleService.findByName("USER");
			user.setRole(role);
			userService.save(user);

			return new ResponseEntity<>("User registered success!", HttpStatus.OK);
		}
    }

	@GetMapping("/getRole")
	public ResponseEntity<?> getRole(Authentication authentication){
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		return ResponseEntity.status(HttpStatus.OK).body(roleService.getRoleByUsername(username).getName());
	}
}
