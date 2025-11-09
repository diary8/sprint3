package com.myframework;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.myframework.annotation.Controller;
import com.myframework.annotation.Route;

public class scan {

    public  static List<Class<?>> findControllerClasses(String basePackage) {
        List<Class<?>> controllers = new ArrayList<>();
        try {
            String basePath = "src";
            File baseDirectory = new File(basePath);
            searchControllers(baseDirectory, basePackage, controllers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return controllers;
    }

    public  static void searchControllers(File directory, String currentPackage, List<Class<?>> controllers) {
        if (!directory.exists()) return;
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                String nextPackage = currentPackage.isEmpty() ?
                        file.getName() : currentPackage + "." + file.getName();
                searchControllers(file, nextPackage, controllers);
            } else if (file.getName().endsWith(".java")) {
                try {
                    String className = currentPackage.isEmpty() ?
                            file.getName().replace(".java", "") :
                            currentPackage + "." + file.getName().replace(".java", "");

                    Class<?> clazz = Class.forName(className);

                    if (clazz.isAnnotationPresent(Controller.class)) {
                        controllers.add(clazz);
                        System.out.println("Found controller: " + clazz.getName());
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("Could not load class: " + file.getName());
                }
            }
        }
    }
    
     public static List<RouteInfo> getAllRoutes() {
        List<RouteInfo> routes = new ArrayList<>();
        List<Class<?>> controllers = findControllerClasses("");
        for (Class<?> controller : controllers) {
            Controller controllerAnnotation = controller.getAnnotation(Controller.class);
            String basePath = controllerAnnotation.value();
            boolean hasRoute = false;
            for (Method method : controller.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Route.class)) {
                    hasRoute = true;
                    Route route = method.getAnnotation(Route.class);
                    RouteInfo info = new RouteInfo();
                    info.controllerClass = controller;
                    info.method = method;
                    info.controllerUrl = basePath;
                    info.routeUrl = route.value();
                    routes.add(info);
                }
            }
            if (!hasRoute) {
                RouteInfo info = new RouteInfo();
                info.controllerClass = controller;
                info.method = null;
                info.controllerUrl = basePath;
                info.routeUrl = "";
                routes.add(info);
            }
        }
        return routes;
    }
    
    
   /*  private static RouteInfo findControllerAndMethod(String fullUrl) {
        List<Class<?>> controllerClasses = findControllerClasses("");

        for (Class<?> controller : controllerClasses) {
            Controller controllerAnnotation = controller.getAnnotation(Controller.class);
            String basePath = controllerAnnotation.value(); // ex: /test2

            // Vérifie si l’URL commence par le chemin du contrôleur
            if (fullUrl.startsWith(basePath)) {t 
                String subPath = fullUrl.substring(basePath.length()); // ex: /teste2

                for (Method method : controller.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Route.class)) {
                        Route route = method.getAnnotation(Route.class);
                        if (route.value().equals(subPath)) {
                            RouteInfo info = new RouteInfo();
                            info.controllerClass = controller;
                            info.method = method;
                            return info;
                        }
                    }
                }
            }
        }
        return null;
    } */

   /*  private static void executeUrl(String fullUrl) {
        RouteInfo info = findControllerAndMethod(fullUrl);

        if (info == null) {
            System.out.println("Aucun contrôleur/méthode trouvé pour l’URL : " + fullUrl);
            return;
        }

        try {
            Object controllerInstance = info.controllerClass.getDeclaredConstructor().newInstance();

            Object result = info.method.invoke(controllerInstance);

            System.out.println(" Exécution réussie : " + info.controllerClass.getSimpleName() + "." + info.method.getName());
            if (result != null) {
                System.out.println(" Valeur retournée : " + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 */

}
