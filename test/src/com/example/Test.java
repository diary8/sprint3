package com.example;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.myframework.*;                                                         

public class Test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>MANDE LE IZY !</h1>");
    }
}
