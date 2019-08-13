
package com.mycompany.phonebookheroku.servlets;

import com.mycompany.phonebookheroku.dao.DaoEjb;
import com.mycompany.phonebookheroku.entitys.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

//запрос post можно передавать несколькими способами либо через форму либо js объект XMLHttpRequest
//если через форму или при использовании js использовать объект FormData то нужна аннотация MultipartConfig
//в js можно использовать заголовок "application/x-www-form-urlencoded" и передавать параметры типа 
//"lorem=ipsum" тогда анотация MultipartConfig не нужна
@MultipartConfig
@WebServlet(name = "NewCreateUser", urlPatterns = {"/NewCreateUser"})
public class NewCreateUser extends HttpServlet {

    @EJB
    private DaoEjb daoEjb;

 
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");//если передаются русские символы
        String fioParam = request.getParameter("fio");
        String phoneParam = request.getParameter("phone");
        List<Users> users = daoEjb.findUsers(fioParam, phoneParam);
        if (users.size() > 0){
            try (PrintWriter out = response.getWriter()) {
                 out.println("A user already exists with such data in the database");
            }
            return;
        }
        
        for (Part part : request.getParts()) {
            if ("image/jpeg".equals(part.getContentType())){
                 boolean res = daoEjb.saveUser(fioParam, phoneParam, part.getInputStream());
                 if (res  == false){
                    try (PrintWriter out = response.getWriter()) {
                         out.println("An error occurred while adding to the database");
                    }
                    return;
                 }
            } 
        }
        
        try (PrintWriter out = response.getWriter()) {
             out.println("the operation was successful");
        }
    }

 
}
