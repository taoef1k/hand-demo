package com.vimouna.demo.handtopic.utils;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Objects;

public class AppUtil {
    private AppUtil() {
        throw new IllegalArgumentException();
    }

    public static boolean isAuthenticated() {
        return Objects.nonNull(SecurityContextHolder.getContext().getAuthentication());
    }

    public static String getAuthUsername() {
        if (!AppUtil.isAuthenticated()) {
            return Strings.EMPTY;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public static String getPrincipalUsername() {
        if (!AppUtil.isAuthenticated()) {
            return Strings.EMPTY;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userSession = (User) auth.getPrincipal();
        return userSession.getUsername();
    }
}

