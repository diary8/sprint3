package com.myframework;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

import java.util.List;

public class FrontServlet implements Filter {

    private RequestDispatcher defaultDispatcher;
    private List<RouteInfo> routes;

    @Override
    public void init(FilterConfig filterConfig) {
        defaultDispatcher = filterConfig.getServletContext().getNamedDispatcher("default");
        // Scan des routes au démarrage
        routes = scan.getAllRoutes();
        System.out.println("Routes trouvées au démarrage : " + routes.size());
        for (RouteInfo info : routes) {
            System.out.println("Controller: " + info.controllerClass.getName() +
                ", Method: " + (info.method != null ? info.method.getName() : "none") +
                ", Controller URL: " + info.controllerUrl +
                ", Route URL: " + info.routeUrl);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

     
        String path = req.getRequestURI().substring(req.getContextPath().length());

        boolean resourceExists = req.getServletContext().getResource(path) != null;

        if (resourceExists) {
            defaultDispatcher.forward(req, res);
        } else {
            // Vérifie si l'URL correspond à un contrôleur ou une route
            for (RouteInfo info : routes) {
                String fullRoute = info.controllerUrl + (info.routeUrl != null ? info.routeUrl : "");
                if (path.equals(info.controllerUrl) || path.equals(fullRoute)) {
                    try (var out = res.getWriter()) {
                        res.setContentType("text/html;charset=UTF-8");
                        out.println("<html><head><title>Route trouvée</title></head><body>");
                        out.println("<h1>Controller trouvé !</h1>");
                        out.println("<p>Classe : " + info.controllerClass.getName() + "</p>");
                        out.println("<p>Méthode : " + (info.method != null ? info.method.getName() : "Aucune") + "</p>");
                        out.println("<p>URL contrôleur : " + info.controllerUrl + "</p>");
                        out.println("<p>URL méthode : " + info.routeUrl + "</p>");
                        out.println("</body></html>");
                    }
                    return;
                }
            }
            // Si aucune route ne correspond, affiche Not Found
            try (var out = res.getWriter()) {
                res.setContentType("text/html;charset=UTF-8");
                out.println("<html><head><title>Not Found</title></head><body>");
                out.println("<h1>Unknown resource</h1>");
                out.println("<p>The requested URL was not found: <strong>" + path + "</strong></p>");
                out.println("</body></html>");
            }
        }
    }

    @Override
    public void destroy() { }
}