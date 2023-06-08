<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.stimulsoft.webviewer.StiWebViewerOptions"%>
<%@page import="java.io.File"%>
<%@page import="com.stimulsoft.report.StiSerializeManager"%>
<%@page import="com.stimulsoft.report.StiReport"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://stimulsoft.com/webviewer" prefix="stiwebviewer"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Stimulsoft Reports for Java</title>
</head>
<body>
	<%
		com.stimulsoft.base.licenses.StiLicense.setKey("6vJhGtLLLz2GNviWmUTrhSqnOItdDwjBylQzQcAOiHmdVlQak8bQq2qNxpmgTY0FXgcdN4b6ZJI7vUKHxLxtKwvhqz" +
				"oXfmd+TtSYmu3qPVHPVbsbqcjV8vQXucJniGHJ4BtO5lxjJpHzXCSGn/M1fvvWIO2nfiWHoyQBiHlqxYfsNaxBUm6z" +
				"L5RFc7Xory2cPJwA1r+3aeHEu4VUrWUD9l7ubfPzKPrFnc6H3znf0L1y0Cg/YrIXfc/pt1iZ6W39bQnu9vPZjlbi08" +
				"+OrMOAK06QC2JdTyMfKeBvX4t/YsYI79O4x1nuBkxHCHdGET+hhNI9+2hfh0C+7QbxSNBYz9rRWQXQKtoeohEKGOPZ" +
				"2gYj5dNwQa9ybVbNyC3Vtw2t2MB7dVwbaRsogAVfs+SoiUoatptgc9PBsmYdVd5xOoMEU4ieMdXaEOvcbiPZHiMdXc" +
				"HYAZB02v9QdA/AkN68e6XL6ujqy2KSOs4CMiU2CRGGe/RaVhEH4rjGSyljd2/lheV/XkIAYe6cmwJsBHqb1Bw8jgUe" +
				"S6kn0iVqYF9ykegQgc5upp1xVCemQxt7nYVcl2dGZLjmThHzzAzmyu3N3Q==");

	    String reportPath = request.getSession().getServletContext().getRealPath("/reports/Dashboards.mrt");
	    StiReport report = StiSerializeManager.deserializeReport(new File(reportPath));
	    report.render();
	    StiWebViewerOptions options = new StiWebViewerOptions();
	    pageContext.setAttribute("report", report);
	    pageContext.setAttribute("options", options);
	%>
	<stiwebviewer:webviewer report="${report}" options="${options}" />
</body>
</html>