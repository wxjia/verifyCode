package cn.wxjia.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class VerifyCode {

	/* 该方法主要作用是获得随机生成的颜色 */
	public Color getRandColor(int s, int e) {
		Random random = new Random();
		if (s > 255)
			s = 0;
		if (e > 255)
			e = 255;
		int r, g, b;
		r = s + random.nextInt(e - s); // 随机生成RGB颜色中的r值
		g = s + random.nextInt(e - s); // 随机生成RGB颜色中的g值
		b = s + random.nextInt(e - s); // 随机生成RGB颜色中的b值
		return new Color(r, g, b);
	}

	public String codeGenerate() {
		System.out.println("codeGenerate");
		int width = 100, height = 40; // 指定生成验证码的宽度和高度
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB); // 创建BufferedImage对象,其作用相当于一图片

		Graphics g = image.getGraphics(); // 创建Graphics对象,其作用相当于画笔
		Graphics2D g2d = (Graphics2D) g; // 创建Grapchics2D对象

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height); // 绘制背景

		Font mfont = new Font("楷体", Font.BOLD, 24); // 定义字体样式
		g.setFont(mfont); // 设置字体
		g.setColor(getRandColor(180, 200));

		Random random = new Random();
		// 绘制100条颜色和位置全部为随机产生的线条,该线条为2f
		for (int i = 0; i < 50; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int x1 = random.nextInt(10) + 1;
			int y1 = random.nextInt(4) + 1;
			BasicStroke bs = new BasicStroke(2f, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_BEVEL); // 定制线条样式
			Line2D line = new Line2D.Double(x, y, x + x1, y + y1);
			g2d.setStroke(bs);
			g2d.draw(line); // 绘制直线
		}

		// 输出由英文，数字，和中文随机组成的验证文字，具体的组合方式根据生成随机数确定。
		String sRand = "";
		String ctmp = "";
		int itmp = 0;
		// 制定输出的验证码为四位
		for (int i = 0; i < 4; i++) {
			switch (random.nextInt(3)) {
			case 0: // 生成A-Z的字母
				itmp = random.nextInt(26) + 'A';
				ctmp = String.valueOf((char) itmp);
				break;
			case 1: // 生成a-z的字母
				itmp = random.nextInt(26) + 'a';
				ctmp = String.valueOf((char) itmp);
				break;
			default:// 生成数字
				itmp = random.nextInt(10) + '0';
				ctmp = String.valueOf((char) itmp);
				break;
			}

			sRand += ctmp;
			Color color = getRandColor(20, 90);
			g.setColor(color);

			// 将生成的随机数进行随机缩放并旋转制定角度 PS.建议不要对文字进行缩放与旋转,因为这样图片可能不正常显示
			// 将文字旋转制定角度
			int jiaodu = random.nextInt(16) - 8;
			jiaodu = 8;
			Graphics2D g2d_word = (Graphics2D) g;
			AffineTransform trans = new AffineTransform();
			trans.rotate((jiaodu) * 3.14 / 180, i * width / 4, 0);

			// 缩放文字
			float scaleSize = random.nextFloat() + 1;
			if (scaleSize > 1.3f || scaleSize < 0.7f)
				scaleSize = 1;
			trans.scale(scaleSize, scaleSize);
			g2d_word.setTransform(trans);
			g.drawString(ctmp, width * (i) / 4, height / 2);
		}
		g.dispose(); // 释放g所占用的系统资源

		File file = new File("../webapps/verifyCode/img/verifyCode.jpg");
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			ImageIO.write(image, "JPEG", outputStream);
			
			image.flush();
			outputStream.flush();
			image.flush();
			outputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
				
				Runtime run = Runtime.getRuntime();
				String cmd ="C:\\Windows\\system32\\cmd cp "+ file.getAbsolutePath() +" "+file.getAbsolutePath()+".jpg";
				System.out.println(cmd);
				run.exec(cmd);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("code-" + sRand.toLowerCase());
		return sRand.toLowerCase();
	}
}
