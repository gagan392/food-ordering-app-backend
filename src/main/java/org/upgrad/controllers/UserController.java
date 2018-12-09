package org.upgrad.controllers;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.config.Constants;
import org.upgrad.models.User;
import org.upgrad.models.UserAuthToken;
import org.upgrad.services.UserAuthTokenService;
import org.upgrad.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthTokenService userAuthTokenService;

    /*
    * This endpoint is used to login a user.
    * Here contact number and password has to be provided to match the credentials.
    */
    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<?> login(@RequestParam String contactNumber, @RequestParam String password) {
        String passwordByUser = String.valueOf(userService.findUserPassword(contactNumber));
        String sha256hex = Hashing.sha256()
                .hashString(password, Charsets.US_ASCII)
                .toString();
        if (userService.findUserPassword(contactNumber) == null)
            return new ResponseEntity<>("This contact number has not been registered!", HttpStatus.UNAUTHORIZED);
        else if (!(passwordByUser.equalsIgnoreCase(sha256hex))) {
            return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
        } else {
            User user = userService.findUser(contactNumber);
            String accessToken = UUID.randomUUID().toString();
            userAuthTokenService.addAccessToken(user.getId(), accessToken);
            HttpHeaders headers = new HttpHeaders();
            headers.add("access-token", accessToken);
            List<String> header = new ArrayList<>();
            header.add("access-token");
            headers.setAccessControlExposeHeaders(header);
            return new ResponseEntity<>(user, headers, HttpStatus.OK);
        }
    }

    /*
    * This endpoint is used to logout a user.
    * Authentication is required to access this endpoint, so accessToken is taken as request header to make sure user is authenticated.
    */
    @PutMapping("/logout")
    @CrossOrigin
    public ResponseEntity<String> logout(@RequestHeader String accessToken) {
        if (userAuthTokenService.isUserLoggedIn(accessToken) == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else if (userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() != null) {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else {
            userAuthTokenService.removeAccessToken(accessToken);
            return new ResponseEntity<>("You have logged out successfully!", HttpStatus.OK);
        }
    }

    /*
     * This endpoint is used to register new user.
     */
    @PostMapping("/signup")
    @CrossOrigin
    public ResponseEntity<?> signup(@RequestParam String firstName, @RequestParam(value = "lastName", required = false) String lastName, @RequestParam String email, @RequestParam String contactNumber, @RequestParam String password) {

        User user = userService.findUser(contactNumber);
        if (Objects.nonNull(user)) {
            return new ResponseEntity<Object>("Try any other contact number, this contact number has already been registered!", HttpStatus.CONFLICT);
        }

        Matcher emailMatcher = Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (!emailMatcher.matches()) {
            return new ResponseEntity<Object>("Invalid email-id format!", HttpStatus.BAD_REQUEST);
        }

        Matcher contactMatcher = Constants.VALID_CONTACT_NUMBER_REGEX.matcher(contactNumber);
        if (!contactMatcher.matches()) {
            return new ResponseEntity<Object>("Invalid contact number!", HttpStatus.BAD_REQUEST);
        }

        if (password.length() < Constants.PASSWORD_MIN_LENGTH || password.equals(password.toLowerCase()) || !Constants.NUMBER_REGEX.matcher(password).matches() || !Constants.SPECIAL_REGEX.matcher(password).find()) {
            return new ResponseEntity<Object>("Weak password!", HttpStatus.BAD_REQUEST);
        }

        String sha256hex = Hashing.sha256()
                .hashString(password, Charsets.US_ASCII)
                .toString();
        userService.addUser(firstName, lastName, email, contactNumber, sha256hex);

        return new ResponseEntity<Object>("User with contact number " + contactNumber + " successfully registered!", HttpStatus.CREATED);
    }

    /*
    * This endpoint is used to change user password
    */
    @PutMapping("/password")
    @CrossOrigin
    public ResponseEntity<?> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestHeader String accessToken) {
        UserAuthToken userAuthToken = userAuthTokenService.isUserLoggedIn(accessToken);
        if (userAuthToken == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }

        if (userAuthToken.getLogoutAt() != null) {
            return new ResponseEntity<Object>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }

        User user = userAuthToken.getUser();
        String oldPasswordHex = Hashing.sha256().hashString(oldPassword, Charsets.US_ASCII).toString();
        String oldPasswordDb = user.getPassword();
        if (!oldPasswordDb.equals(oldPasswordHex)) {
            return new ResponseEntity<>("Your password did not match your old password!", HttpStatus.BAD_REQUEST);
        }
        
        if (newPassword.length() < Constants.PASSWORD_MIN_LENGTH || newPassword.equals(newPassword.toLowerCase()) || !Constants.NUMBER_REGEX.matcher(newPassword).matches() || !Constants.SPECIAL_REGEX.matcher(newPassword).find()) {
            return new ResponseEntity<Object>("Weak password!", HttpStatus.BAD_REQUEST);
        }

        String newPasswordHex = Hashing.sha256().hashString(newPassword, Charsets.US_ASCII).toString();
        user.setPassword(newPasswordHex);
        userService.updateUser(user);
        return new ResponseEntity<>("Password updated successfully!", HttpStatus.OK);
    }
}
