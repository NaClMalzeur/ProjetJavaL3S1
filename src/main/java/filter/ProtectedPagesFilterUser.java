package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProtectedPagesFilterUser implements Filter {

	/**
	 *
	 * @param request The servlet request we are processing
	 * @param response The servlet response we are creating
	 * @param chain The filter chain we are processing
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet error occurs
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		try {
                    HttpSession session = ((HttpServletRequest) request).getSession(false);
                    HttpServletRequest httpServletRequest = ((HttpServletRequest) request);

                    if (session == null) {
                        ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/connexion.jsp");
                    } 
                    
                    if (session != null &&  session.getAttribute("role") == null) {
                        ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/connexion.jsp");
                    } 
                    
                    if (session != null &&  session.getAttribute("role").equals("user")) {
                      chain.doFilter(request, response); 
                    } else {
                        // Pas connecté, on va vers la page de login (racine)
                        ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/connexion.jsp");
                    }
		} catch (IOException t) {
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
