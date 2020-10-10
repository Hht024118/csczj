package com.thsware.framework.security;

import com.thsware.framework.config.Constants;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(ThsSecurityUtils.getDecodedDetailsVaule("account_name").orElse(Constants.SYSTEM_ACCOUNT));
    }
}
