package org.example.backend.api.modules.oauth;

import lombok.Builder;

@Builder
public record TokenRequest(String token) {
}
