package com.example;

import java.util.List;

import com.myframework.scan;
import com.myframework.RouteInfo;

public class ScanTestMain {
    public static void main(String[] args) {
        // Appel de la méthode statique getAllRoutes() de scan
        List<Class<?>> clazz = scan.findControllerClasses("");
        System.out.println("Nombre de contrôleurs trouvés : " + clazz.size());      

        List<RouteInfo> route= scan.getAllRoutes();
        System.out.println("Nombre de routes trouvées : " + route.size());
         
       
    }
}
