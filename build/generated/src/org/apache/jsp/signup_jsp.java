package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class signup_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Sign-up Page</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <h1>Welcome to My Twitter Website!</h1>\n");
      out.write("        <p>\n");
      out.write("            Please sign up and then feel free to look around that site! :)\n");
      out.write("        </p>\n");
      out.write("        ");
      business.User user = null;
      synchronized (session) {
        user = (business.User) _jspx_page_context.getAttribute("user", PageContext.SESSION_SCOPE);
        if (user == null){
          user = new business.User();
          _jspx_page_context.setAttribute("user", user, PageContext.SESSION_SCOPE);
        }
      }
      out.write("    \n");
      out.write("        <form action=\"emailList\" method=\"post\">\n");
      out.write("            <input type=\"hidden\" name=\"action\" value=\"add\">\n");
      out.write("            <label class=\"pad_top\">Full Name:</label>\n");
      out.write("            <input type=\"text\" name=\"user\" value=\"fullName\"><br>\n");
      out.write("            <label class=\"pad_top\">Email:</label>\n");
      out.write("            <input type=\"email\" name=\"user\" value=\"email\"><br>\n");
      out.write("            <label class=\"pad_top\">Nickname:</label>\n");
      out.write("            <input type=\"text\" name=\"user\" value=\"nickname\"><br>\n");
      out.write("            <label class=\"pad_top\">Password:</label>\n");
      out.write("            <input type=\"text\" name=\"user\" value=\"password\"><br>\n");
      out.write("            <select>\n");
      out.write("                ");

                    for(int i = 1; i <= 30; i++) {
                
      out.write("\n");
      out.write("                <option value=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${i}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write('>');
      out.print( i+"" );
      out.write("</option>\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("            </select>\n");
      out.write("                \n");
      out.write("            </option>\n");
      out.write("            <label>&nbsp;</label>\n");
      out.write("            <input type=\"submit\" value=\"Join Now\" class=\"margin_left\">\n");
      out.write("        </form>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
