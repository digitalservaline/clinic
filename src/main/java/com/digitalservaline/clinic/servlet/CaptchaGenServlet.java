package com.digitalservaline.clinic.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.digitalservaline.clinic.constants.ClinicConstants;
import com.digitalservaline.clinic.util.ClinicUtil;

public class CaptchaGenServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

	public static final String FILE_TYPE = "jpeg";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String captchaStr=ClinicUtil.generateCaptchaText(4);

		HttpSession session = request.getSession(true);
		if("reset".equals(request.getParameter("type"))){
			session.setAttribute(ClinicConstants.CAPTCHA_RESET, captchaStr); // reset password page
		}else if("login".equals(request.getParameter("type"))){
			session.setAttribute(ClinicConstants.CAPTCHA_LOGIN, captchaStr); // login page
		}else if("signup".equals(request.getParameter("type"))){
			session.setAttribute(ClinicConstants.CAPTCHA_SIGNUP, captchaStr); // signup page
		}

		response.setContentType("image/jpg");
		ServletOutputStream out = response.getOutputStream();

		BufferedImage image = new BufferedImage(100, 35, BufferedImage.TYPE_INT_RGB);

		Graphics2D graphics = image.createGraphics();

		// Set back ground of the generated image to white
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, 100, 35);

		// set gradient font of text to be converted to image
		GradientPaint gradientPaint = new GradientPaint(0, 0, Color.DARK_GRAY, 20, 10, Color.LIGHT_GRAY, true);
		graphics.setPaint(gradientPaint);
		Font font = new Font("Comic Sans MS", Font.BOLD, 18);
		graphics.setFont(font);

		graphics.drawString(captchaStr, 10, 20);

		// release resources used by graphics context
		graphics.dispose();

		ImageIO.write(image, FILE_TYPE, out);

		// close the stream
		out.close();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
