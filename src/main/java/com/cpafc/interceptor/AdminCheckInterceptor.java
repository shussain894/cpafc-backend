package com.cpafc.interceptor;

import com.cpafc.model.Coach;
import com.cpafc.repository.CoachRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Component
public class AdminCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private CoachRepository coachRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String email = request.getHeader("X-User-Email");

        if (email == null || email.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing X-User-Email header.");
            return false;
        }

        Optional<Coach> optionalCoach = coachRepository.findByEmail(email);

        if (optionalCoach.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("No user found with that email.");
            return false;
        }

        request.setAttribute("currentCoach", optionalCoach.get());

        return true;
    }
}

