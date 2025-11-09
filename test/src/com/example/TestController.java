package com.example;

import com.myframework.annotation.Controller;
import com.myframework.annotation.Route;

@Controller("/test")
public class TestController {
    @Route("/test")
    public void testMethod() {
        // Méthode de test
    }
}
