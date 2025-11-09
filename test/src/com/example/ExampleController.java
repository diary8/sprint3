package com.example;

import com.myframework.annotation.*;

@Controller("/example")
public class ExampleController {
    // Méthodes du contrôleur ici
    @Route("/method")
    public void exampleMethod() {
        // Logique de la méthode
    }

}
