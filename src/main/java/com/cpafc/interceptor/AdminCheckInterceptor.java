package com.cpafc.interceptor;

import com.cpafc.model.Coach;
import com.cpafc.model.Role;
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

        Coach coach = optionalCoach.get();

        // Full access if ADMIN
        if (coach.getRole() == Role.ADMIN) {
            return true;
        }

        String requestPath = request.getRequestURI();
        String method = request.getMethod();

        // Example rule: allow GET access to /api/children?team=X only if team matches
        if (requestPath.contains("/api/children") && "GET".equalsIgnoreCase(method)) {
            String teamParam = request.getParameter("team");
            if (teamParam != null && teamParam.equals(coach.getTeam())) {
                return true;
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Access denied: You can only view children from your own team.");
                return false;
            }
        }

        // allow POST/PUT/DELETE on children only if the coach owns that team (add later)
        // Otherwise, deny by default
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Access denied: Only admins can perform this operation.");
        return false;
    }
}
