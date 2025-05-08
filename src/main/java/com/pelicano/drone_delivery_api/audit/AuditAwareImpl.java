package com.pelicano.drone_delivery_api.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
 * @created 08/05/2025 - 8:41 AM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */
@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNT_MS");
    }
}
