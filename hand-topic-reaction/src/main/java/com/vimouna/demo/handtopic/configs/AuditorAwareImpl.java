package com.vimouna.demo.handtopic.configs;

import com.vimouna.demo.handtopic.utils.AppUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        if (AppUtil.isAuthenticated()) {
            return Optional.of(AppUtil.getAuthUsername());
        }
        return Optional.of("system");
    }
}
