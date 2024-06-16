<%@ page import="net.tanesha.recaptcha.ReCaptchaImpl" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaResponse" %>

<%
String remoteAddr = request.getRemoteAddr();
ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
reCaptcha.setPrivateKey("6LdLxPkpAAAAAHBEACFKOoDvdBD9HMvEeiMF_Oex");

String challenge = request.getParameter("recaptcha_challenge_field");
String uresponse = request.getParameter("recaptcha_response_field");

ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

if (reCaptchaResponse.isValid()) {
    // reCAPTCHA validation successful, proceed with form processing
    String emailAddress = request.getParameter("emailAddress");
    String password = request.getParameter("password");
    // Your form processing logic here
} else {
    // reCAPTCHA validation failed, handle error
    out.println("<p>Captcha validation failed. Please try again.</p>");
}
%>