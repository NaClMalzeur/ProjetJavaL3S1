/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.google.gson.Gson;
import entitys.PurchaseOrderEntity;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modele.DAO;
import modele.DAOException;
import modele.DataSourceFactory;

/**
 *
 * @author OncleHank
 */
@WebServlet(name = "ServletControleur", urlPatterns = {"/ServletControleur"})
public class ServletControleur extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DAO  myDAO = new DAO(DataSourceFactory.getDataSource());
            HttpSession session = request.getSession(true); // démarre la session
            Gson gson = new Gson();
            String gsonData = "";
            Map<String,Integer> map = null;
            String action = request.getParameter("action");
            String dateDebut = request.getParameter("dateDebut");
            String dateFin = request.getParameter("dateFin");
            int idCom, quantity, productId, idCli;
            System.out.println(action);
            switch (action) {
		case "CONNEXION":
                    String pageJsp = checkLogin(request);
                    if(pageJsp.equals("connexion.jsp")){
                        request.setAttribute("errorMessage", "Login/Password incorrect");
                    }
                    request.getRequestDispatcher(pageJsp).forward(request, response);
                    
                    break;
                case "logout":
                    doLogout(request);
                    
                    break;
                case "pageClient":
                    //idCli = session.getAttribute("userId");
                    idCli = 2;
                    List<PurchaseOrderEntity> lst = myDAO.rqtCommandes(idCli, null, null, 0, null);
                    gsonData = gson.toJson(lst);
                    out.println(gsonData);  
                    
                    break;
                case "modif":
                    idCom = Integer.parseInt(request.getParameter("idCommande"));
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                    productId = Integer.parseInt(request.getParameter("productId"));
                    myDAO.modificationCommande(productId, quantity, idCom);
                    
                    break;
                case "ajout":
                    idCom = -1;
                    idCli = 2;
                    productId = Integer.parseInt(request.getParameter("productId"));
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                    float shippingCost = Float.parseFloat(request.getParameter("shippingCost"));
                    String salesDate = request.getParameter("salesDate");
                    String shippingDate = request.getParameter("shippingDate");
                    String freightCompany = request.getParameter("freightCompany");
                    PurchaseOrderEntity com = new PurchaseOrderEntity(idCom, idCli, productId, quantity, shippingCost, salesDate, shippingDate, freightCompany);
                    myDAO.ajoutCommande(com);
                    
                    break;
                case "suppression":
                    idCom = Integer.parseInt(request.getParameter("idCommande"));
                    myDAO.suppressionCommande(idCom);
                    
                    break;
                case "pageAdminItem":
                    map = myDAO.chiffreAffaire(true, false, false, dateDebut, dateFin);
                    gsonData = gson.toJson(map);
                    out.println(gsonData);
                    
                    break;
                case "pageAdminZip":
                    map = myDAO.chiffreAffaire(false, true, false, dateDebut, dateFin);
                    gsonData = gson.toJson(map);
                    out.println(gsonData);
                    
                    break;
                case "pageAdminIdCustom":
                    map = myDAO.chiffreAffaire(false, false, true, dateDebut, dateFin);
                    gsonData = gson.toJson(map);
                    out.println(gsonData);
                    
                    break;
            }
        } catch (SQLException | DAOException ex) {
            Logger.getLogger(ServletControleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private String checkLogin(HttpServletRequest request) throws SQLException, DAOException {
            DAO  myDAO = new DAO(DataSourceFactory.getDataSource());
        // vérification que l'utilisateur est connecté
            String userName = findUserInSession(request);
            String pageJsp;
            if (userName == null) { // L'utilisateur n'est pas connecté
                    pageJsp = "connexion.jsp";
            } else { // L'utilisateur est connecté
                // on vérifie si admin ou client

                String login = request.getParameter("loginParam");
                String password = request.getParameter("passwordParam");

                if (myDAO.logInAdmin(login, password)){
                    HttpSession session = request.getSession(true); // démarre la session
                    session.setAttribute("role", "admin");
                    session.setAttribute("userName", userName);
                    session.setAttribute("userId", password);
                    pageJsp = "pageAdmin.jsp";
                }else  if (myDAO.logInUser(login, password)){
                    HttpSession session = request.getSession(true); // démarre la session
                    session.setAttribute("role", "user");
                    session.setAttribute("userName", userName);
                    session.setAttribute("userId", password);
                    pageJsp = "pageAdmin.jsp";
                }else{
                    pageJsp = "connexion.jsp";

                }
            }
            return pageJsp;
	}

	private void doLogout(HttpServletRequest request) {
		// On termine la session
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}
        
    private String findUserInSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session == null) ? null : (String) session.getAttribute("userName");
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
