package com.example.userservice.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Component
public class BotFilter implements Filter {

    private final Set<String> blockedAgents = Set.of(
        "curl", "python-requests", "wget", "PostmanRuntime"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String userAgent = Optional.ofNullable(req.getHeader("User-Agent")).orElse("").toLowerCase();

        for (String agent : blockedAgents) {
            if (userAgent.contains(agent)) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Bot access denied");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}

