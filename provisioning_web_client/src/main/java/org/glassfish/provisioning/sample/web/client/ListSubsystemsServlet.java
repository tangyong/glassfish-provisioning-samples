package org.glassfish.provisioning.sample.web.client;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.glassfish.obrbuilder.ObrHandlerService;
import org.glassfish.obrbuilder.subsystem.Module;
import org.glassfish.obrbuilder.subsystem.Subsystem;
import org.glassfish.obrbuilder.subsystem.Subsystems;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * DeploySubsystemServlet for deploying subsystem into glassfish
 * 
 * @author TangYong(tangyong@cn.fujitsu.com)
 */
@WebServlet(urlPatterns = "/list")
public class ListSubsystemsServlet extends HttpServlet {
    
    public void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, java.io.IOException {
    	
    	try {
			BundleContext bc = Util.getBundleContext(this.getClass());

			ServiceTracker st = null;

			st = new ServiceTracker(bc, bc.createFilter("(objectClass="
					+ ObrHandlerService.class.getName() + ")"), null);

			st.open();

			ObrHandlerService service = (ObrHandlerService) st
					.waitForService(Util.OBRHADNDLESERVICE_TIMEOUT);
			
			String subsystemsName = req.getParameter("subsystemsName");
	    	
	    	if (subsystemsName != null){
	    		//List current subsystems based on subsystemsName parameter
	    		Subsystems subsystems = service.listSubsystems(subsystemsName);
	    		
	    		 resp.setContentType("text/html");
	    	        PrintWriter out = resp.getWriter();
	    	        out.println("<HTML> <HEAD> <TITLE> Glassfish Subsystem Administration </TITLE> </HEAD> ");
	    	        out.println("<BODY BGCOLOR=#FDF5E6>");
	    	        out.println("<H2 ALIGN=\"CENTER\">Glassfish Subsystem Display Page</H2>");
	    	        out.println("<div align=\"center\">");
	    	        out.println("<a href=\"index.html\">Backing to Subsystem Administration Page</a>");
	    	        out.println("</div>");
	    	        try {
	    	            if (subsystems != null) {
	    	                try {
	    	                	//1 list subsystems description table
	    	                    out.println("<table border=\"1\" align=\"center\">");
	    	                    out.println("<tr>");
    	                        out.println("<td colspan=\"2\">Subsystems</td>");
    	                        out.println("</tr>");
    	                        out.println("<tr>");
    	                        out.println("<td>name</td>");
    	                        out.println("<td>description</td>");
    	                        out.println("</tr>");
    	                        out.println("<tr>");
    	                        out.println("<td>" + subsystems.getName() +"</td>");
    	                        out.println("<td>" + subsystems.getDescription() +"</td>");
    	                        out.println("</tr>");  	                   
	    	                    out.println("</table>");
	    	                    
	    	                    //2 list subsystems and module description table
	    	                    List<Subsystem> list = subsystems.getSubsystem();
	    	                    int count = 1;
	    	                    for(Subsystem subsystem : list){
	    	                    	out.println("<table border=\"1\" align=\"center\">");
		    	                    out.println("<tr>");
	    	                        out.println("<td colspan=\"3\">Subsystems" + count + "</td>");
	    	                        out.println("</tr>");
	    	                        out.println("<tr>");
	    	                        out.println("<td>name</td>");
	    	                        out.println("<td>description</td>");
	    	                        out.println("<td></td>");
	    	                        out.println("</tr>");
	    	                        out.println("<tr>");
	    	                        out.println("<td>" + subsystem.getName() +"</td>");
	    	                        out.println("<td>" + subsystem.getDescription() +"</td>");
	    	                        out.println("<td></td>");
	    	                        out.println("</tr>");  	                   
		    	                    out.println("</table>");
		    	                    List<Module> modules = subsystem.getModule();
		    	                    int moduleCount = 1;
		    	                    for(Module module : modules){
		    	                    	out.println("<table border=\"1\" align=\"center\">");
			    	                    out.println("<tr>");
		    	                        out.println("<td colspan=\"5\">Module" + moduleCount + "</td>");
		    	                        out.println("</tr>");
		    	                        out.println("<tr>");
		    	                        out.println("<td>name</td>");
		    	                        out.println("<td>version</td>");
		    	                        out.println("<td>description</td>");
		    	                        out.println("<td>start</td>");
		    	                        out.println("<td>startlevel</td>");
		    	                        out.println("</tr>");
		    	                        out.println("<tr>");
		    	                        out.println("<td>" + module.getName() +"</td>");
		    	                        out.println("<td>" + module.getVersion() +"</td>");
		    	                        out.println("<td>" + module.getDescription() +"</td>");
		    	                        out.println("<td>" + module.getStart() +"</td>");
		    	                        out.println("<td>" + module.getStartlevel() +"</td>");
		    	                        out.println("<td></td>");
		    	                        out.println("</tr>");  	                   
			    	                    out.println("</table>");
			    	                    
			    	                    moduleCount++;
		    	                    }		    	                    
		    	                    
		    	                    count++;
	    	                    }
	    	                } catch (Exception e) {
	    	                    e.printStackTrace();
	    	                }
	    	            } else {
	    	                out.println("Subsystem:: " + subsystemsName + " is not yet deployed");
	    	            }
	    	        } catch (Exception e) {
	    	            e.printStackTrace(out);
	    	        }
	    	        out.println("</BODY> </HTML> ");
	    	}
	    	
    	}catch(Exception e){
    		
    	}   	
    }
}