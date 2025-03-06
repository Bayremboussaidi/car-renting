/* 
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(loginRequest.getEmail());
        return ResponseEntity.ok(new LoginResponse(user.getRole().name()));
    }
}
*/


/*
package com.example.comparateur.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.comparateur.DTO.LoginRequest;
//import com.example.comparateur.DTO.LoginResponse;
import com.example.comparateur.Entity.User;
import com.example.comparateur.Exception.ErrorResponse;
import com.example.comparateur.Repository.UserRepository;







@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @SuppressWarnings("unused")
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user


            // Commented out: Set the authentication in the security context
            // SecurityContextHolder.getContext().setAuthentication(authentication);

            
            User user = userRepository.findByEmail(loginRequest.getEmail());

            // Return the whole user object
            return ResponseEntity.ok(user);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new ErrorResponse(false, "Les identifications sont erronées", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse(false, "An error occurred: " + e.getMessage(), null));
        }
    }
}
*/




/* 
package com.example.comparateur.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.comparateur.DTO.LoginRequest;
import com.example.comparateur.Entity.User;
import com.example.comparateur.Exception.ErrorResponse;
import com.example.comparateur.Repository.UserRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager; // ✅ Fixed missing bean

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // ✅ Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            // ✅ Fetch user details after successful authentication
            User user = userRepository.findByEmail(loginRequest.getEmail());

            return ResponseEntity.ok(user);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new ErrorResponse(false, "Les identifications sont erronées", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse(false, "An error occurred: " + e.getMessage(), null));
        }
    }
}

*/


package com.example.comparateur.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.comparateur.DTO.LoginRequest;
import com.example.comparateur.Entity.User;
import com.example.comparateur.Exception.ErrorResponse;
import com.example.comparateur.Repository.UserRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // ✅ Fix: Use PasswordEncoder to check passwords

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // ✅ Fetch user by email
            User user = userRepository.findByEmail(loginRequest.getEmail());

            // ✅ Check if user exists and password matches
            if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(401).body(new ErrorResponse(false, "Les identifications sont erronées", null));
            }

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse(false, "An error occurred: " + e.getMessage(), null));
        }
    }
}
